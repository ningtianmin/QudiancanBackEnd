package com.qudiancan.backend.aspect;

import com.qudiancan.backend.common.Constant;
import com.qudiancan.backend.common.ShopAccountHolder;
import com.qudiancan.backend.common.ShopRequiredAuthority;
import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.enums.shop.ShopIsCreator;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.po.AccountPO;
import com.qudiancan.backend.pojo.po.AuthorityPO;
import com.qudiancan.backend.repository.AccountRepository;
import com.qudiancan.backend.repository.AuthorityRepository;
import com.qudiancan.backend.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author NINGTIANMIN
 */
@Component
@Aspect
@Slf4j
public class ShopAccountAspect {

    public static final String COMMA = ",";
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
            "&& !execution(public * com.qudiancan.backend.controller.shop.ShopAccountController.*(..))" +
            "|| execution(public * com.qudiancan.backend.controller.merchant.*.*(..))" +
            "&& !execution(public * com.qudiancan.backend.controller.merchant.MerchantAccountController.*(..))")
    public void pointcut() {

    }

    /**
     * @apiNote controller的参数只能为非空引用类型
     */
    @Around("pointcut()")
    @SuppressWarnings("unchecked")
    public Object checkAccountAuthority(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String accountSession = CookieUtil.get(request, Constant.COOKIE_ACCOUNT_SESSION);
        if (StringUtils.isEmpty(accountSession)) {
            throw new ShopException(ResponseEnum.SHOP_INVALID_SESSION_ID, "请重新登录");
        }
        // 验证sessionId的有效性
        String redisSessionKey = String.format(Constant.REDIS_ACCOUNT_KEY_PREFIX + "%s", accountSession);
        // TODO: 18/02/16 redis需释放资源
        String redisSessionValue = redisTemplate.opsForValue().get(redisSessionKey);
        if (StringUtils.isEmpty(redisSessionValue)) {
            throw new ShopException(ResponseEnum.SHOP_INVALID_SESSION_ID, "请重新登录");
        }
        Integer accountId = Integer.valueOf(redisSessionValue);
        AccountPO accountPO = accountRepository.findOne(accountId);
        // 设置accountPO到AccountHolder
        ShopAccountHolder.set(accountPO);
        // 权限验证
        Object result;
        Object[] args = joinPoint.getArgs();
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
                    if (Objects.isNull(authorityPO)) {
                        throw new ShopException(ResponseEnum.SERVER_INTERNAL_ERROR, "权限数据库有误");
                    }
                    String accessibleRoleIds = authorityPO.getRoleIds();
                    String accountRoleIds = accountPO.getRoleIds();
                    if (StringUtils.isEmpty(accessibleRoleIds) || StringUtils.isEmpty(accountRoleIds)) {
                        log.warn("[权限不足]accountId:{},authority:{}", accountId, authorityPO);
                        throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
                    }
                    Set<String> accessibleRoleSet = Arrays.stream(authorityPO.getRoleIds().split(COMMA)).collect(Collectors.toSet());
                    if (Arrays.stream(accountRoleIds.split(COMMA)).anyMatch(accessibleRoleSet::contains)) {
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
