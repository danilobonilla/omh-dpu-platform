package com.openmhealth.dpu.service;

import com.openmhealth.dto.SchemaId;
import com.openmhealth.dto.SchemaTranslator;
import com.openmhealth.dto.SchemasSupported;
import com.openmhealth.dto.translator.FitnessJournalToActivityEntrySet;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.List;

@SchemaId("omh:dpu:moderate_activity:entries")
@SchemasSupported(
    @SchemaTranslator(schema = "omh:fitness_diary:journal",
        converter = FitnessJournalToActivityEntrySet.class)
)
public class ActivityEntrySetDTO {

    private List<ActivityEntryDTO> entries;

    public ActivityEntrySetDTO(List<ActivityEntryDTO> entries) {
        this.entries = entries;
    }

    public ActivityEntrySetDTO() {
    }

    public List<ActivityEntryDTO> getEntries() {
        return entries;
    }

    public void setEntries(List<ActivityEntryDTO> entries) {
        this.entries = entries;
    }
}
