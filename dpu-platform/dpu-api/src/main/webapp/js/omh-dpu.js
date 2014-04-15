/**
 * Namespaces and configuration.
 */
var Omh = {};
Omh.Dvu = {};

/**
 * DPU API URL
 */
Omh.DPU_API_URL = "/dpu-api/dpu";

/**
 * Base DPU class, runs a DPU on the API server
 */
Omh.Dpu = function (options) {
    var self = this;

    /**
     * Path to DPU on the DPU API.
     */
    self.dpu = options && options.dpu ? options.dpu : null;
    if (!self.dpu) {
        console.warn("No DPU path has been configured.");
    }

    /**
     * Schema in which parameters will be sent.
     */
    self.schema = options && options.schema ? options.schema : null;
    if (!self.schema) {
        console.warn("No parameter schema has been indicated.");
    }

    /**
     * Parameters to be sent to the server for processing.
     */
    self.params = options && options.params ? options.params : null;
    if (!self.params) {
        console.warn("No parameters have been configured.")
    }
};

/**
 * Runs the DPU on the server.
 *
 * Takes opts.success, and opts.error callbacks
 * @param opts
 */
Omh.Dpu.prototype.run = function (opts) {
    var self = this;
    var params = "" + JSON.stringify(self.params) + "";
    var url = Omh.DPU_API_URL + "/" + self.dpu + "/run?schema=" + self.schema + "&params=" + params;
    $.ajax({
        url: url, type: "GET",
        success: function (response) {
            if (typeof opts.success == "function") {
                opts.success(response);
            }
        },
        error: function (response) {
            if (typeof opts.error == "function") {
                opts.error(response);
            }
        }
    });
};