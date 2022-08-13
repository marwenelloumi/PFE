const express = require('express')
const jwt =require('jsonwebtoken')
const router = express.Router()
const bcrypt = require('bcrypt')

const Utilisateur = require('../Model/Utilisateur')

//authanticate user 
async function findUtilisateur (req, res, next) {
  let utilisateur
  try {
    utilisateur = await Utilisateur.findOne({email:req.body.email})
  } 
  catch (err) {
    return res.status(500).json({ message: err.message })
  }
  res.utilisateur = utilisateur
  next()
}

router.post('/',findUtilisateur ,async (req,res) => {
  if (res.utilisateur == null){
    return res.status(404).json({ message: 'wrong email ' })
  }
  if(await bcrypt.compare(req.body.password,res.utilisateur.password)) {
    const util= {
    email: res.utilisateur.email,
    password:res.utilisateur.password,
    name:res.utilisateur.name
    }
    const accessToken = jwt.sign(util ,process.env.ACCSESS_TOKEN);
   res.json({accessToken : accessToken});
  } 
  else {
    return res.status(404).json({ message: 'wrong password' })
  }
})

module.exports = router