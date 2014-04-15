package com.openmhealth.dto;

import java.lang.annotation.*;

/**
 * Represents a Concordia schema value.
 */
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface SchemaValue {
    String value() default "";
}
