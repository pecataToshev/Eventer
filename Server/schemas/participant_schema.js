// import { Schema } from "schema_import";
var mongoose = require('mongoose');

var participantSchema = new mongoose.Schema({

    fName: String,
    lName: String,
    phone: String,
    role: String,
    scenario: String

});

var participantModel = mongoose.model("Participant", participantSchema);

module.exports = participantModel; 

//participantSchema.methods.nameOfMethod 