/**
 * Moderate Activity DVU will take a collection
 * of Activity Entries.
 */
Omh.Dvu.ModerateActivityGraph = Backbone.View.extend({

    initialize: function () {
        var self = this;

        self.selector = self.options && self.options.selector;

        /**
         * Uses the moderate activiy calculation to draw graph.
         */
        self.dpu = new Omh.Dpu({
            dpu: "omh:dpu:moderate_activity",
            schema: "omh:fitness_diary:journal",
            params: {}
        });

        /**
         * Backbone collection representing activity entries.
         */
        self.activityEntries = self.options && self.options.activityEntries ?
                self.options.activityEntries : null;

        if (!self.activityEntries) {
            console.warn("Activity entries collection was not" +
                    "submitted to moderate activity DPU");
        } else {
            /**
             * Whenever the activity entries change, re-run DPU and
             * update the graph.
             */
            self.listenTo(self.activityEntries, 'add change remove', function () {
                self.render();
            });
        }
    },

    /**
     * Render a D3 graph using the data returned by the DPU.
     */
    render: function () {
        var self = this;

        var margin = {top: 20, right: 20, bottom: 30, left: 40},
                width = 960 - margin.left - margin.right,
                height = 500 - margin.top - margin.bottom;

        var x = d3.scale.ordinal()
                .rangeRoundBands([0, width], .1);

        var y = d3.scale.linear()
                .range([height, 0]);

        var xAxis = d3.svg.axis()
                .scale(x)
                .orient("bottom")
                .tickFormat(d3.time.format("%A %d %B %Y"));

        var yAxis = d3.svg.axis()
                .scale(y)
                .orient("left")
                .ticks(10, "%");

        $(self.selector).empty();
        var svg = d3.select(self.selector).append("svg")
                .attr("width", width + margin.left + margin.right)
                .attr("height", height + margin.top + margin.bottom)
                .append("g")
                .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        var entries = [];
        self.activityEntries.each(function (model) {
            var json = model.toJSON();
            entries.push({activityName: json.activityName, startTime: json.startTime, endTime: json.endTime});
        });

        self.dpu.params = {entries: entries};
        self.dpu.run({
            success: function (response) {
                var timestamps = response.timestamp;
                var percents = response.percents;
                var data = [];

                $.each(timestamps, function (i, timestamp) {
                    data.push({ timestamp: new Date(timestamp), percent: parseFloat(percents[i] / 100) });
                });

                x.domain(data.map(function (d) {
                    return d.timestamp;
                }));

                y.domain([0, d3.max(data, function (d) {
                    return d.percent;
                })]);

                svg.append("g")
                        .attr("class", "x axis")
                        .attr("transform", "translate(0," + height + ")")
                        .call(xAxis);

                svg.append("g")
                        .attr("class", "y axis")
                        .call(yAxis)
                        .append("text")
                        .attr("transform", "rotate(-90)")
                        .attr("y", 6)
                        .attr("dy", ".71em")
                        .style("text-anchor", "end")
                        .text("% Moderate Activity");

                svg.selectAll(".bar")
                        .data(data)
                        .enter().append("rect")
                        .attr("class", "bar")
                        .attr("x", function (d) {
                            return x(d.timestamp);
                        })
                        .attr("width", x.rangeBand())
                        .attr("y", function (d) {
                            return y(d.percent);
                        })
                        .attr("height", function (d) {
                            return height - y(d.percent);
                        });
            },
            error: function () {
                alert("Error occurred");
            }
        });
    }

});