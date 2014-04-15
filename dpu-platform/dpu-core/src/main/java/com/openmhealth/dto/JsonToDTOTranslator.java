package com.openmhealth.dto;

import java.io.IOException;

public interface JsonToDTOTranslator<T> {

    T convert(String jsonData) throws IOException;

}
