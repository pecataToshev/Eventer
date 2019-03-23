import { Schema } from "schema_import";

var scheduleSchema = new Schema({

    startHour: String,
    endHour: String,
    date: Date,
    name: String

});


var scheduleModel = mongoose.model("Schedule", scheduleSchema);

module.exports = scheduleModel; 