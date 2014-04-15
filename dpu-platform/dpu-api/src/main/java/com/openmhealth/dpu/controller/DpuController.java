package com.openmhealth.dpu.controller;

import com.openmhealth.dpu.*;
import com.openmhealth.dpu.exception.DpuApiException;
import com.openmhealth.dpu.exception.UnsupportedDpuException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("dpu")
public class DpuController {

    @Resource
    private DpuRegistry dpuRegistry;

    /**
     * Presents a list of all the DPU's available for processing.
     *
     * @return - String array of all DPU names.
     */
    @RequestMapping(value = "registry", method = RequestMethod.GET)
    public
    @ResponseBody
    Object dpuRegistry() {
        return dpuRegistry.getDpuNames();
    }

    /**
     * Returns the schemaId's supported for input to the DPU.
     *
     * @param dpuName - The dpu in whose schema's we're interested.
     * @return - String array of schemaId's supported for input.
     */
    @RequestMapping(value = "{dpuName}/schema/in", method = RequestMethod.GET)
    public
    @ResponseBody
    Object dpuInputSchema(
        @PathVariable("dpuName") final String dpuName) {
        String dpuLookUp = dpuName.trim().toLowerCase();
        return dpuRegistry.getDpu(dpuLookUp).getInputSchemas();
    }

    /**
     * Returns the output schemaId for the DPU.
     *
     * @param dpuName - The dpu in whose schema's we're interested.
     * @return - String array of schemaId's supported for input.
     */
    @RequestMapping(value = "{dpuName}/schema/out", method = RequestMethod.GET)
    public
    @ResponseBody
    Object dpuOutputSchema(
        @PathVariable("dpuName") final String dpuName) {
        String dpuLookUp = dpuName.trim().toLowerCase();
        return dpuRegistry.getDpu(dpuLookUp).getOutputSchema();
    }

    /**
     * Returns the concordia schema for the output produced by DPU.
     *
     * @param dpuName - The dpu in whose schema's we're interested.
     * @return - JSON for output concordia schema.
     */
    @RequestMapping(value = "{dpuName}/schema/out/detail", method = RequestMethod.GET)
    public
    @ResponseBody
    Object dpuOutputSchemaDetail(
        @PathVariable("dpuName") final String dpuName) {
        String dpuLookUp = dpuName.trim().toLowerCase();
        return dpuRegistry.getDpu(dpuLookUp).describeOutputSchema();
    }

    /**
     * Runs the DPU specified in the path with the given parameters.
     *
     * @param dpuName   - DPU key in the registry
     * @param paramData - HTTP request with parameters to be sent to DPU.
     * @return - Resulting DTO, which conforms to output concordia schema.
     */
    @RequestMapping(
        value = "{dpuName}/run",
        method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET}
    )
    public
    @ResponseBody
    Object runDpu(
        @PathVariable("dpuName") final String dpuName,
        @RequestParam("schema") final String schema,
        @RequestParam(value = "params") final String paramData
    ) throws DpuApiException {

        String dpuLookUp = dpuName.trim().toLowerCase();

        if (!dpuRegistry.isDpuSupported(dpuLookUp)) {
            throw new UnsupportedDpuException("DPU '" + dpuLookUp + "' is not supported.");
        } else {
            DataProcessingUnit dpu = dpuRegistry.getDpu(dpuName);
            return dpu.process(schema, paramData);
        }
    }

    /**
     * EXCEPTION HANDLERS : Will return specific HTTP status codes and
     * error messages for exceptions thrown
     * ----------------------------------------------------------------------------------
     */
    @ExceptionHandler(DpuApiException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public
    @ResponseBody
    DpuApiErrorDTO handleNoSuchDpu(DpuApiException invalid) {
        return new DpuApiErrorDTO(invalid.getMessage());
    }
}
