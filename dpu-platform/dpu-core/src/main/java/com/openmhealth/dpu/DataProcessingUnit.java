package com.openmhealth.dpu;

import com.openmhealth.dpu.exception.DpuApiException;

/**
 * Basic contract for a data processing unit.
 */
public interface DataProcessingUnit<O> {

    String[] getInputSchemas();

    String getOutputSchema();

    String describeOutputSchema();

    /**
     * Main method to be called when invoking the
     * data processing unit.
     *
     * @param schemaId       - The schema in which the input param data was sent.
     * @param inputParamData - The input param data
     * @return - A concordia DTO that represent the results.
     */
    O process(String schemaId, String inputParamData) throws DpuApiException;
}
