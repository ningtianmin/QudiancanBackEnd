package com.qudiancan.backend.pojo;

import com.qudiancan.backend.enums.ResponseEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
@Data
@NoArgsConstructor
public class PageResponse<T> {

    private Integer code;
    private String message;
    private Long count;
    private List<T> data;

    public static <T> PageResponse<T> success(Page<T> page) {
        PageResponse<T> response = new PageResponse<>();
        response.setCode(ResponseEnum.OK.getCode());
        response.setMessage(ResponseEnum.OK.getMessage());
        response.setData(page.getContent());
        response.setCount(page.getTotalElements());
        return response;
    }

}
