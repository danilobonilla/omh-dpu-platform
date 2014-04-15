/**
 * Simple backbone view to create fitness journals
 */
var FitnessJournal = Backbone.View.extend({

    initialize: function () {
        var self = this;

        self.activityEntries = self.options && self.options.activityEntries ?
                self.options.activityEntries : null;

        if (!self.activityEntries) {
            console.warn("Did not properly load activity entries");
        }

        self.listenTo(self.activityEntries, 'add remove', function () {
            self.render();
        });

        self.render();
    },

    render: function () {
        var self = this;
        self.$el.empty();
        self.$el.append("<h4>Fitness Journal:</h4>");
        self.$el.append('<form>' +
                '<div>Activity: <input type="text" id="activityName" value=""/></div>' +
                '<div>Date: <input type="text" id="date" value=""/></div>' +
                '   <div>Start Time: <input type="text" id="startTime" value=""/></div>' +
                '   <div>End Time: <input type="text" id="endTime" value=""/></div>' +
                '</form>');

        self.$el.append("<button id='addBtn'>Add</button>");

        /**
         * Render the activity journal here.
         */
        self.$el.append("<h4>Activities:</h4>");
        self.activityEntries.each(function (model) {
            self.$el.append('<div class="activityBox">' +
                    '<span>' + model.get("activityName") + '<br>' + model.getPrettyTime() + '</span>' +
                    '<a class="removeBtn" model-id="' + model.get("id") + '" href="#">[remove]</a>' +
                    '</div>');
        });
        self.delegateEvents();
    },

    addActivity: function () {
        var self = this;
        var activity = $("#activityName", self.$el).val();
        var dateString = $("#date", self.$el).val();
        var startTimeString = $("#startTime", self.$el).val();
        var endTimeString = $("#endTime", self.$el).val();

        var startTime = new Date(dateString + " " + startTimeString).getTime();
        var endTime = new Date(dateString + " " + endTimeString).getTime();

        self.activityEntries.add({
            id: startTime + new Date().getTime(), //Temporary fake id
            activityName: activity,
            startTime: startTime,
            endTime: endTime
        });
    },

    removeEntry: function (id) {
        var self = this;
        var model = self.activityEntries.where({id: id })[0];
        if (model) {
            //model.destroy();
            self.activityEntries.remove(model);
        }
    },

    events: {
        'click #addBtn': function (evt) {
            evt.stopPropagation();
            var self = this;
            self.addActivity();
        },

        'click .removeBtn': function (evt) {
            var self = this;
            evt.stopPropagation();
            evt.preventDefault();
            var id = parseInt($(evt.currentTarget).attr("model-id"));
            self.removeEntry(id);
        }
    }
});