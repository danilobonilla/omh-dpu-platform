package com.openmhealth.dto.translator;

import com.openmhealth.dpu.service.ActivityEntrySetDTO;
import com.openmhealth.dto.JsonToDTOTranslator;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class FitnessJournalToActivityEntrySet implements JsonToDTOTranslator<ActivityEntrySetDTO> {

    @Override
    public ActivityEntrySetDTO convert(String jsonStringData) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonStringData, ActivityEntrySetDTO.class);
    }
}
