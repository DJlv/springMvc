package com.example.boot.demoboot;

import java.lang.annotation.*;

@Documented  //用于标明该注解将会被包含在javadoc中
@Retention(RetentionPolicy.RUNTIME) //用于标明注解的生命周期
@Target({ElementType.METHOD}) //用于标明注解的作用范围
public @interface Recording {
}
