package com.nexus.backend.global.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceMapper {
    // MSSQL (Service DB) 전용 마커
}