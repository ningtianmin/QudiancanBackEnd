package com.qudiancan.backend.service.impl.shop;

import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.enums.shop.ShopBranchStatus;
import com.qudiancan.backend.enums.shop.ShopIsCreator;
import com.qudiancan.backend.enums.shop.ShopStatus;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.dto.shop.AccountInfoDTO;
import com.qudiancan.backend.pojo.po.AccountPO;
import com.qudiancan.backend.pojo.po.BranchPO;
import com.qudiancan.backend.pojo.po.RolePO;
import com.qudiancan.backend.pojo.po.ShopPO;
import com.qudiancan.backend.pojo.vo.shop.PerfectShopVO;
import com.qudiancan.backend.pojo.vo.shop.ShopVO;
import com.qudiancan.backend.repository.AccountRepository;
import com.qudiancan.backend.repository.BranchRepository;
import com.qudiancan.backend.repository.RoleRepository;
import com.qudiancan.backend.repository.ShopRepository;
import com.qudiancan.backend.service.shop.RoleService;
import com.qudiancan.backend.service.shop.ShopService;
import com.qudiancan.backend.service.util.shop.ShopServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author NINGTIANMIN
 */
@Service
@Slf4j
public class ShopServiceImpl implements ShopService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;

    @Override
    public ShopPO getShop(Integer accountId, String shopId) {
        log.info("[获取餐厅]accountId:{},shopId:{}", accountId, shopId);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId");
        }
        AccountPO accountPO = accountRepository.findOne(accountId);
        if (Objects.isNull(accountPO)) {
            throw new ShopException(ResponseEnum.SHOP_UNKNOWN_ACCOUNT);
        }
        if (!accountPO.getShopId().equals(shopId)) {
            throw new ShopException(ResponseEnum.AUTHORITY_NOT_ENOUGH);
        }
        return shopRepository.findOne(shopId);
    }

    @Override
    public ShopPO updateShop(Integer accountId, String shopId, ShopVO shopVO, Set<String> fieldNames) {
        log.info("[更新餐厅]accountId:{},shopId:{},shopVO:{}", accountId, shopId, shopVO);
        if (Objects.isNull(accountId) || StringUtils.isEmpty(shopId) || Objects.isNull(shopVO)) {
            throw new ShopException(ResponseEnum.SHOP_INCOMPLETE_PARAM, "accountId,shopId, shopVO");
        }
        ShopPO shopPO = getShop(accountId, shopId);
        // 检查字段
        ShopServiceUtil.checkShopVO(shopVO, fieldNames);

        shopPO.setHolderIdentify(fieldNames.contains("holderIdentify") ? shopVO.getHolderIdentify() : shopPO.getHolderIdentify());
        shopPO.setHolderName(fieldNames.contains("holderName") ? shopVO.getHolderName() : shopPO.getHolderName());
        shopPO.setHolderType(fieldNames.contains("holderType") ? shopVO.getHolderType() : shopPO.getHolderType());
        shopPO.setIntroduction(fieldNames.contains("introduction") ? shopVO.getIntroduction() : shopPO.getIntroduction());
        shopPO.setName(fieldNames.contains("name") ? shopVO.getName() : shopPO.getName());
        shopPO.setTelephone(fieldNames.contains("telephone") ? shopVO.getTelephone() : shopPO.getTelephone());
        return shopRepository.save(shopPO);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public BranchPO perfectShop(Integer accountId, PerfectShopVO perfectShopVO) {
        log.info("【完善餐厅】accountId：{}，perfectShopVO：{}", accountId, perfectShopVO);
        if (Objects.isNull(accountId)) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE, "accountId");
        }
        ShopServiceUtil.checkPerfectShopVO(perfectShopVO);
        AccountPO accountPO = accountRepository.findOne(accountId);
        if (Objects.isNull(accountPO) || !accountPO.getIsCreator().equals(ShopIsCreator.YES.getKey())) {
            throw new ShopException(ResponseEnum.PARAM_INVALID, "accountId");
        }
        ShopPO shopPO = shopRepository.findOne(accountPO.getShopId());
        if (Objects.isNull(shopPO) || !shopPO.getStatus().equals(ShopStatus.REMAIN_PERFECT.getKey())) {
            throw new ShopException(ResponseEnum.BAD_REQUEST);
        }

        shopPO.setName(perfectShopVO.getShopName());
        shopPO.setHolderType(perfectShopVO.getShopHolderType());
        shopPO.setHolderName(perfectShopVO.getHolderName());
        shopPO.setHolderIdentify(perfectShopVO.getHolderIdentify());
        shopPO.setIntroduction(perfectShopVO.getShopIntroduction());
        shopPO.setStatus(ShopStatus.NORMAL.getKey());
        shopRepository.save(shopPO);

        BranchPO branchPO = new BranchPO(null, shopPO.getId(), perfectShopVO.getBranchName(),
                perfectShopVO.getBranchNotice(), perfectShopVO.getBranchPhone(),
                perfectShopVO.getBranchAddress(), perfectShopVO.getBranchLongitude(),
                perfectShopVO.getBranchLatitude(), perfectShopVO.getBranchIntroduction(),
                ShopBranchStatus.NORMAL.getKey(), null);
        return branchRepository.save(branchPO);
    }

    @Override
    public List<AccountInfoDTO> listShopAccount(String shopId) {
        if (StringUtils.isEmpty(shopId)) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE);
        }
        if (Objects.isNull(shopRepository.findOne(shopId))) {
            throw new ShopException(ResponseEnum.PARAM_INVALID);
        }
        List<AccountPO> accountPOList = accountRepository.findByShopId(shopId);
        List<BranchPO> branchPOList = branchRepository.findByShopId(shopId);
        List<RolePO> rolePOList = roleRepository.findAll();

        return accountPOList.stream().map(o -> {
            AccountInfoDTO accountInfoDTO = new AccountInfoDTO();
            BeanUtils.copyProperties(o, accountInfoDTO);
            if (!StringUtils.isEmpty(o.getBranchIds())) {
                Set<Integer> branchIdList = Arrays.stream(o.getBranchIds().split(",")).map(Integer::valueOf).collect(Collectors.toSet());
                accountInfoDTO.setBranches(branchPOList.stream().filter(j -> branchIdList.contains(j.getId())).collect(Collectors.toList()));
                accountInfoDTO.setBranchesString(accountInfoDTO.getBranches().stream().map(BranchPO::getName).collect(Collectors.joining(",")));
            }
            if (!StringUtils.isEmpty(o.getRoleIds())) {
                Set<Integer> roleIdList = Arrays.stream(o.getRoleIds().split(",")).map(Integer::valueOf).collect(Collectors.toSet());
                accountInfoDTO.setRoles(rolePOList.stream().filter(j -> roleIdList.contains(j.getId())).collect(Collectors.toList()));
                accountInfoDTO.setRolesString(accountInfoDTO.getRoles().stream().map(RolePO::getName).collect(Collectors.joining(",")));
            }
            accountInfoDTO.setIsCreator(ShopIsCreator.valueOf(accountInfoDTO.getIsCreator()).getValue());
            return accountInfoDTO;
        }).collect(Collectors.toList());
    }

}
