package com.openmhealth.dpu;

import com.openmhealth.dto.SchemaId;

/**
 * Generic DPU API error.
 */
@SchemaId("omh:dpu:error")
public class DpuApiErrorDTO {

    private Object errors;

    public DpuApiErrorDTO(Object errors) {
        this.errors = errors;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }
}
