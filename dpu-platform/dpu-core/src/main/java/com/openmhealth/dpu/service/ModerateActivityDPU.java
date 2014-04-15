package com.openmhealth.dpu.service;

import com.openmhealth.dpu.BaseDataProcessingUnit;
import com.openmhealth.dpu.exception.DpuApiException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Given a set of activity entries, returns the percent
 * of day reflecting moderate activity.
 */
public class ModerateActivityDPU
    extends BaseDataProcessingUnit<ActivityEntrySetDTO, ModerateActivityDailyPercentDTO> {

    private Set<String> moderateActivities;

    private final static String DATE_FORMAT = "yyyyMMdd";

    private final static Long MS_PER_DAY = 24l * 60l * 60l * 1000l;

    public ModerateActivityDPU() {
        super(ActivityEntrySetDTO.class, ModerateActivityDailyPercentDTO.class);
    }

    @Override
    public ModerateActivityDailyPercentDTO process(ActivityEntrySetDTO params)
        throws DpuApiException {

        ModerateActivityDailyPercentDTO results = new ModerateActivityDailyPercentDTO();

        Map<String, Long> dataPoints = new HashMap<String, Long>();
        for (ActivityEntryDTO activityEntry : params.getEntries()) {
            if (moderateActivities.contains(activityEntry.getActivityName())) {
                String dateKey =
                    new SimpleDateFormat(DATE_FORMAT).format(activityEntry.getStartTime());

                long startMs = activityEntry.getStartTime().getTime();
                long endMs = activityEntry.getEndTime().getTime();

                long totalMs = endMs - startMs;
                if (!dataPoints.containsKey(dateKey)) {
                    dataPoints.put(dateKey, 0l);
                }

                dataPoints.put(dateKey, dataPoints.get(dateKey) + totalMs);
            }
        }

        Map<Long, Double> resultMap = new HashMap<Long, Double>();
        for (String dateKey : dataPoints.keySet()) {
            Double pct =
                (dataPoints.get(dateKey).doubleValue() / MS_PER_DAY.doubleValue()) * 100;
            pct = pct > 100d ? 100d : pct;
            try {
                Calendar cal = Calendar.getInstance();
                cal.setTime(new SimpleDateFormat(DATE_FORMAT).parse(dateKey));
                Long ts = cal.getTimeInMillis();
                resultMap.put(ts, pct);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new DpuApiException("Unexpected error getting " +
                    "moderate activity parameters: " + e.getMessage());
            }
        }

        /**
         * Setup both date points and percentage points
         */
        List<Long> timestamps = new ArrayList<Long>(resultMap.keySet());
        Collections.sort(timestamps);

        List<Double> percents = new ArrayList<Double>();
        for (Long ts : timestamps) {
            percents.add(resultMap.get(ts));
        }

        results.setTimestamp(timestamps);
        results.setPercents(percents);

        return results;
    }

    public void setModerateActivities(Set<String> moderateActivities) {
        this.moderateActivities = moderateActivities;
    }
}
