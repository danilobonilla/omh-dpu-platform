package com.openmhealth.dpu.exception;

/**
 * Root exception for DPU API.
 */
public class DpuApiException extends Exception{

    public DpuApiException() {
    }

    public DpuApiException(String message) {
        super(message);
    }
}
