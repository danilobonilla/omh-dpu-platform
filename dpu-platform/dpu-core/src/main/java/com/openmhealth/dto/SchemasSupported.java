package com.openmhealth.dto;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface SchemasSupported {
    SchemaTranslator[] value();
}
