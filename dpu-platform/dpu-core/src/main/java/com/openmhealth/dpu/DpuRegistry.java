package com.openmhealth.dpu;

/**
 * Contract for DPU registry. All it needs to do
 * is define a method for retrieval of supported DPU.
 */
public interface DpuRegistry {

    boolean isDpuSupported(String dpuName);

    DataProcessingUnit getDpu(String dpuName);

    String[] getDpuNames();
}
