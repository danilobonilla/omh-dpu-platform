package com.openmhealth.dpu.exception;

/**
 * Error thrown when a dpuName was not
 * found in the API's registry.
 */
public class UnsupportedDpuException extends DpuApiException {

    public UnsupportedDpuException() {
    }

    public UnsupportedDpuException(String message) {
        super(message);
    }
}
