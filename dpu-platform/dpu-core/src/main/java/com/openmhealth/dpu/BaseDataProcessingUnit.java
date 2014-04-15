package com.openmhealth.dpu;

import com.openmhealth.dpu.exception.DpuApiException;
import com.openmhealth.dpu.exception.InvalidDpuParametersException;
import com.openmhealth.dpu.exception.UnsupportedSchemaException;
import com.openmhealth.dto.JsonToDTOTranslator;
import com.openmhealth.dto.SchemaUtils;

import java.io.IOException;

public abstract class BaseDataProcessingUnit<I, O> implements DataProcessingUnit<O> {

    protected Class<I> inputClazz;

    protected Class<O> outputClazz;

    protected BaseDataProcessingUnit(Class<I> inputClazz, Class<O> outputClazz) {
        this.inputClazz = inputClazz;
        this.outputClazz = outputClazz;
    }

    @Override
    public String[] getInputSchemas() {
        return SchemaUtils.getSchemasSupported(inputClazz);
    }

    @Override
    public String getOutputSchema() {
        return SchemaUtils.getOutputSchema(outputClazz);
    }

    @Override
    public String describeOutputSchema() {
        return SchemaUtils.describeOutputSchema(outputClazz);
    }

    /**
     * Inspect the inputClass to determine which
     * converter is required for producing the input DTO.
     *
     * @param schemaId       - Schema represented by the inputParamData
     * @param inputParamData - Raw json string representing parameters
     * @return - The local input value required by DPU.
     */
    @SuppressWarnings("unchecked")
    private I convertToInputParameters(String schemaId, String inputParamData)
        throws UnsupportedSchemaException, InvalidDpuParametersException {
        JsonToDTOTranslator<I> translator =
            (JsonToDTOTranslator<I>) SchemaUtils.getSchemaTranslator(schemaId, inputClazz);
        try {
            return translator.convert(inputParamData);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidDpuParametersException(
                "Could not convert data to proper DPU input DTO");
        }
    }

    /**
     * @param schemaId       - The schema in which the input param data was sent.
     * @param inputParamData - The input param data
     * @return
     * @throws DpuApiException
     */
    @Override
    public O process(String schemaId, String inputParamData) throws DpuApiException {
        return process(convertToInputParameters(schemaId, inputParamData));
    }

    public abstract O process(I inputDTO) throws DpuApiException;
}
