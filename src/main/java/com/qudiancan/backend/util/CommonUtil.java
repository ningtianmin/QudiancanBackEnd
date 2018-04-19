package com.qudiancan.backend.util;

import com.qudiancan.backend.enums.StringPair;
import com.qudiancan.backend.enums.StringPairDTO;
import com.qudiancan.backend.enums.shop.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author NINGTIANMIN
 */
public class CommonUtil {

    public static Map<String, List<StringPairDTO>> getConstants() {
        List<Class<? extends StringPair>> classes = Arrays.asList(OrderPayStatus.class, OrderProductStatus.class,
                OrderStatus.class, ShopBranchProductStatus.class, ShopBranchStatus.class, ShopBranchTableStatus.class,
                ShopHolderType.class, ShopIsCreator.class, ShopStatus.class, OrderPayMethod.class);
        Map<String, List<StringPairDTO>> map = new HashMap<>(classes.size());
        classes.forEach(o -> {
            List<StringPairDTO> collect = Arrays.stream(o.getEnumConstants())
                    .map(j -> new StringPairDTO(j.getKey(), j.getValue()))
                    .collect(Collectors.toList());
            String simpleName = o.getSimpleName();
            map.put(simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1), collect);
        });
        return map;
    }

}
