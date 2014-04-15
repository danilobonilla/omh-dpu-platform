package com.openmhealth.dto;

import com.openmhealth.dpu.exception.UnsupportedSchemaException;
import name.jenkins.paul.john.concordia.Concordia;
import name.jenkins.paul.john.concordia.jackson.ConcordiaDeserializer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;

/**
 * Utilities for inspecting schema annotations.
 */
public class SchemaUtils {

    public static String[] getSchemasSupported(Class<?> clazz) {
        Set<String> schemas = new HashSet<String>();
        Annotation[] annotations = clazz.getAnnotations();
        if (annotations != null && annotations.length > 0) {
            for (Annotation a : annotations) {
                if (a instanceof SchemasSupported) {
                    SchemaTranslator[] schemasSupported = ((SchemasSupported) a).value();
                    for (SchemaTranslator translator : schemasSupported) {
                        schemas.add(translator.schema());
                    }
                }
            }
        }
        return schemas.toArray(new String[schemas.size()]);
    }

    public static String getOutputSchema(Class<?> clazz) {
        Annotation[] annotations = clazz.getAnnotations();
        if (annotations != null && annotations.length > 0) {
            for (Annotation a : annotations) {
                if (a instanceof SchemaId) {
                    return ((SchemaId) a).value();
                }
            }
        }
        return null;
    }

    /**
     * Builds output schema using annotations in given class.
     * <p/>
     * TODO: Complete with nested recursion!
     *
     * @param clazz - Class to introspect for annotations.
     * @return Concordia Schema;
     */
    public static String describeOutputSchema(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        String fieldsJson = "";
        for (Field f : fields) {
            for (Annotation a : f.getAnnotations()) {
                if (a instanceof SchemaValue) {
                    if (Collection.class.isAssignableFrom(f.getType())) {
                        Type type = ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0];
                        Class<?> paramClass = Number.class.isAssignableFrom((Class<?>)type) ? Number.class : (Class<?>)type;
                        fieldsJson +=
                            "{" +
                                "\"name\":\"" + f.getName().toLowerCase() + "\"," +
                                "\"type\":\"array\"," +
                                "\"constType\":{" +
                                "\"type\":\"" + paramClass.getSimpleName().toLowerCase() + "\"" +
                                "}" +
                                "},";
                    } else {
                        String typeName =
                            f.getType().getSimpleName().toLowerCase().trim();
                        fieldsJson +=
                            "{" +
                                "\"name\":\"" + f.getName().toLowerCase() + "\"," +
                                "\"type\":\"" + typeName + "\"" +
                                "},";
                    }
                }
            }
        }
        fieldsJson = fieldsJson.length() > 0 ?
            fieldsJson.substring(0, fieldsJson.length() - 1) : fieldsJson;
        return "{\"type\":\"object\",\"fields:[" + fieldsJson + "]\"}";
    }

    public static JsonToDTOTranslator getSchemaTranslator(String schemaId, Class<?> clazz)
        throws UnsupportedSchemaException {
        Annotation[] annotations = clazz.getAnnotations();
        if (annotations != null && annotations.length > 0) {
            for (Annotation a : annotations) {
                if (a instanceof SchemasSupported) {
                    SchemaTranslator[] schemasSupported = ((SchemasSupported) a).value();
                    for (SchemaTranslator translator : schemasSupported) {
                        if (translator.schema().equals(schemaId)) {
                            try {
                                return translator.converter().newInstance();
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        throw new UnsupportedSchemaException("schema '" + schemaId + "' " +
            "is not supported by " + clazz.getSimpleName());
    }

}
