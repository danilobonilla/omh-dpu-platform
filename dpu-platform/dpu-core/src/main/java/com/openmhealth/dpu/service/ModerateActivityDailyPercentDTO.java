package com.openmhealth.dpu.service;

import com.openmhealth.dto.SchemaId;
import com.openmhealth.dto.SchemaValue;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SchemaId("omh:dpu:moderate_activity:daily_percents")
public class ModerateActivityDailyPercentDTO {

    @SchemaValue
    private List<Long> timestamp;

    @SchemaValue
    private List<Double> percents;

    public List<Long> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(List<Long> timestamp) {
        this.timestamp = timestamp;
    }

    public List<Double> getPercents() {
        return percents;
    }

    public void setPercents(List<Double> percents) {
        this.percents = percents;
    }
}
