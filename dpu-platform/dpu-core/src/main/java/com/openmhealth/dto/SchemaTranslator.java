package com.openmhealth.dto;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface SchemaTranslator {
    String schema();
    Class<? extends JsonToDTOTranslator> converter();
}
