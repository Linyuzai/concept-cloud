package com.github.linyuzai.concept.cloud.web.result;

import lombok.*;

import java.io.Serializable;

/**
 * 用于返回值的包装，提供额外的数据信息
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WebResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success;

    private String code;

    private String message;

    private Object object;
}
