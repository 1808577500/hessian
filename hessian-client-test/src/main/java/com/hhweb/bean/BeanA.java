package com.hhweb.bean;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ClassName BeanA
 * @Description TODO
 * @Author zhangcx
 * @Date 2020/5/26 17:04
 * @Version 1.0
 */
@Getter
@Setter
@Data
@NoArgsConstructor
public class BeanA implements Serializable {
    private String proA;

    private String proB;
}
