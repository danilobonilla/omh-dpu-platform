package com.openmhealth.dpu.service;

import java.util.Date;

public class ActivityEntryDTO {

    private String activityName;

    private Date startTime;

    private Date endTime;

    public ActivityEntryDTO(String activityName, Date startTime, Date endTime) {
        this.activityName = activityName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ActivityEntryDTO() {
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
