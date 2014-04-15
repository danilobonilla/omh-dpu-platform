package com.openmhealth.dpu.exception;

/**
 * Thrown whenever input parameters whose schema was not supported
 * were sent to the DPU.
 */
public class UnsupportedSchemaException extends DpuApiException {
    public UnsupportedSchemaException() {
    }

    public UnsupportedSchemaException(String message) {
        super(message);
    }
}
