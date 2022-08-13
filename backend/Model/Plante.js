const mongoose = require('mongoose')
const planteSchema = new mongoose.Schema({
  name: {
    type: String,
    required: true
  },
  userEmail:{
    type: String,
    required: true
  }
})
 module.exports = mongoose.model('Plante', planteSchema)