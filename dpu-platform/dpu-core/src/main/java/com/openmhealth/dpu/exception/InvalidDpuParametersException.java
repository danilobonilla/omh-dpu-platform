package com.openmhealth.dpu.exception;

/**
 * Thrown whenever invalid parameters were submitted to a DPU.
 */
public class InvalidDpuParametersException extends DpuApiException {

    public InvalidDpuParametersException() {
    }

    public InvalidDpuParametersException(String message) {
        super(message);
    }
}
