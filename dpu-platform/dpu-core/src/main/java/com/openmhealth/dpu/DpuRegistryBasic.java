package com.openmhealth.dpu;

import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Repository of of DPU's supported by API.
 */
public class DpuRegistryBasic implements DpuRegistry {

    private Map<String, DataProcessingUnit> supportedDpus;

    @Override
    public boolean isDpuSupported(String dpuName) {
        String dpuLookup = dpuName.trim().toLowerCase();
        return supportedDpus.containsKey(dpuLookup);
    }

    @Override
    public DataProcessingUnit getDpu(String dpuName) {
        String dpuLookup = dpuName.trim().toLowerCase();
        return supportedDpus.get(dpuLookup);
    }

    @Override
    public String[] getDpuNames() {
        List<String> names = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(supportedDpus)) {
            names.addAll(supportedDpus.keySet());
            Collections.sort(names);
            return names.toArray(new String[names.size()]);
        }
        return new String[0];
    }

    public void setSupportedDpus(Map<String, DataProcessingUnit> supportedDpus) {
        this.supportedDpus = supportedDpus;
    }
}
