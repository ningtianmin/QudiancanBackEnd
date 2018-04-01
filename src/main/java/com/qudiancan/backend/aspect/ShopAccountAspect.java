package com.qudiancan.backend.aspect;

import com.qudiancan.backend.common.Constant;
import com.qudiancan.backend.common.ShopAccountHolder;
import com.qudiancan.backend.common.ShopRequiredAuthority;
import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.enums.shop.ShopIsCreator;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.Session;
import com.qudiancan.backend.pojo.po.AccountPO;
import com.qudiancan.backend.pojo.po.AuthorityPO;
import com.qudiancan.backend.repository.AccountRepository;
import com.qudiancan.backend.repository.AuthorityRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author NINGTIANMIN
 */
@Component
@Aspect
@Slf4j
public class ShopAccountAspect {
    private StringRedisTemplate redisTemplate;
    private AccountRepository accountRepository;
    private AuthorityRepository authorityRepository;

    public ShopAccountAspect(StringRedisTemplate redisTemplate, AccountRepository accountRepository, AuthorityRepository authorityRepository) {
        Assert.notNull(redisTemplate, "redisTemplate不能为空");
        Assert.notNull(accountRepository, "accountRepository不能为空");
        Assert.notNull(authorityRepository, "authorityRepository不能为空");
        this.redisTemplate = redisTemplate;
        this.accountRepository = accountRepository;
        this.authorityRepository = authorityRepository;
    }

    @Pointcut("execution(public * com.qudiancan.backend.controller.shop.*.*(..))" +
            "&& !execution(public * com.qudiancan.backend.controller.shop.ShopAccountController.*(..))")
    public void pointcut() {

    }

    /**
     * @apiNote 1. advised method必须包含com.qudiancan.backend.pojo.Session参数
     * 2. advised method参数不能是int/double等类型,必须是Integer/Double等类型
     * 3. 非必填的参数类型不能是String/Integer类型,应该封装起来,否则抛出参数不完整异常
     */
    @Around("pointcut()")
    @SuppressWarnings("unchecked")
    public Object checkAccountAuthority(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        Object[] args = joinPoint.getArgs();
        if (Arrays.stream(args).anyMatch(Objects::isNull)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM);
        }
        Optional<Object> optional = Arrays.stream(args).filter((t) -> Session.class.equals(t.getClass())).findFirst();
        if (!optional.isPresent()) {
            log.error("[服务器错误]接口没有接收Session的对象");
            throw new ShopException(ResponseEnum.SERVER_INTERNAL_ERROR);
        }
        String sessionId = ((Session) optional.get()).getSessionId();
        if (StringUtils.isEmpty(sessionId)) {
            throw new ShopException(ResponseEnum.SHOP_NO_SESSION_ID);
        }
        // 验证sessionId的有效性
        String redisSessionKey = String.format(Constant.REDIS_ACCOUNT_KEY_PREFIX + "%s", sessionId);
        // TODO: 18/02/16 redis需释放资源
        String redisSessionValue = redisTemplate.opsForValue().get(redisSessionKey);
        if (StringUtils.isEmpty(redisSessionValue)) {
            throw new ShopException(ResponseEnum.SHOP_INVALID_SESSION_ID);
        }
        // 更新redis上sessionId的过期时间
        // TODO: 18/02/16 redis需释放资源
        redisTemplate.opsForValue().set(redisSessionKey, redisSessionValue, Constant.REDIS_ACCOUNT_EXPIRY, TimeUnit.MINUTES);
        Integer accountId = Integer.valueOf(redisSessionValue);
        AccountPO accountPO = accountRepository.findOne(accountId);
        // 设置accountPO到AccountHolder
        ShopAccountHolder.set(accountPO);
        // 权限验证
        if (accountPO.getIsCreator().equals(ShopIsCreator.YES.name())) {
            result = joinPoint.proceed();
        } else {
            Class declaringType = joinPoint.getSignature().getDeclaringType();
            Class[] collect = Arrays.stream(args).map(Object::getClass).toArray(Class[]::new);
            try {
                Method method = declaringType.getMethod(joinPoint.getSignature().getName(), collect);
                if (method.isAnnotationPresent(ShopRequiredAuthority.class)) {
                    ShopRequiredAuthority annotation = method.getAnnotation(ShopRequiredAuthority.class);
                    Integer requiredAuthorityId = annotation.value().getId();
                    AuthorityPO authorityPO = authorityRepository.findOne(requiredAuthorityId);
                    if (authorityPO == null) {
                        throw new ShopException(ResponseEnum.SERVER_INTERNAL_ERROR, "系统权限数据有误");
                    }
                    String accessibleRoleIds = authorityPO.getRoleIds();
                    String accountRoleIds = accountPO.getRoleIds();
                    if (StringUtils.isEmpty(accessibleRoleIds) || StringUtils.isEmpty(accountRoleIds)) {
                        log.warn("[权限不足]accountId:{},authority:{}", accountId, authorityPO);
                        throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
                    }
                    Set<String> accessibleRoleSet = Arrays.stream(authorityPO.getRoleIds().split(",")).collect(Collectors.toSet());
                    Optional<String> stringOptional = Arrays.stream(accountRoleIds.split(",")).filter(accessibleRoleSet::contains).findFirst();
                    if (stringOptional.isPresent()) {
                        result = joinPoint.proceed();
                    } else {
                        log.warn("[权限不足]accountId:{},authority:{}", accountId, authorityPO);
                        throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
                    }
                } else {
                    result = joinPoint.proceed();
                }
            } catch (NoSuchMethodException e) {
                log.error("{}", e);
                throw new ShopException(ResponseEnum.SERVER_INTERNAL_ERROR);
            }

        }
        // 回收ThreadLocal
        ShopAccountHolder.remove();
        return result;
    }
}
