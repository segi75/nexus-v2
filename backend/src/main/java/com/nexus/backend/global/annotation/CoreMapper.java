package com.nexus.backend.global.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE) // 클래스/인터페이스 위에 붙임
@Retention(RetentionPolicy.RUNTIME) // 런타임까지 유지
@Documented
public @interface CoreMapper {
    // MySQL (Core DB) 전용 마커
}