const mongoose = require('mongoose')
const utilisateurSchema = new mongoose.Schema({
  name: {
    type: String,
    required: true
  },
  email:{
    type: String,
    required: true
  },
  password:{
    type: String,
    required: true,
    default:'1234'
  }
})  

module.exports = mongoose.model('Utilisateur', utilisateurSchema)