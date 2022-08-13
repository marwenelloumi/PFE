const express = require('express')
const router = express.Router()
const bcrypt = require('bcrypt')
const Utilisateur = require('../Model/Utilisateur')

//Middleware function
async function getUtilisateur (req, res, next) {
  let utilisateur
  try {
    utilisateur = await Utilisateur.findById(req.params.id)
    if (utilisateur == null) {
      return res.status(404).json({ message: 'Cannot find utilisateur' })
    }
  } 
  catch (err) {
    return res.status(500).json({ message: err.message })
  } 
  res.utilisateur = utilisateur
  next()
}

// Getting all
router.get('/',async (req,res) => {
 try{
    const utilisateurs= await Utilisateur.find()
    res.json(utilisateurs)

  } 
  catch(err){
    res.status(500).json({message:err.message})
  }
})

//Getting One
router.get('/:id',getUtilisateur ,(req,res) => {
  res.send(res.utilisateur)
})

//creating one
router.post('/',async(req,res) => {
  try {
    await Utilisateur.countDocuments({email:req.body.email},async(err,count)=>{
      if (count !== 0) {
      return res.status(404).json({ message: 'email already exist' })
      }
      else {
      const hashedPassword = await bcrypt.hash(req.body.password, 10)
      const utilisateur  = new Utilisateur({
        name: req.body.name,
        email :req.body.email,
        password :hashedPassword
      })
      try {
        const newUtilisateur = await utilisateur.save()
        res.status(201).json(newUtilisateur)
      } 
      catch (err) {
        res.status(400).json({ message: err.message })
      }

    }
    })
  } 
  catch (err) {
    return res.status(500).json({ message: err.message })
  }
})
//upadating one 
router.patch('/:id',getUtilisateur,async (req,res) => {
  if (req.body.name != null) {
    res.utilisateur.name = req.body.name
  }
  if (req.body.email != null) {
    res.utilisateur.email = req.body.email
  }
  if (req.body.password != null) {
    const hashedPassword = await bcrypt.hash(req.body.password, 10)
    res.utilisateur.password = hashedPassword
  }
  try { 
    const updatedUtilisateur = await res.utilisateur.save()
    res.json(updatedUtilisateur)
  } 
  catch (err) {
    res.status(400).json({ message: err.message })
  }
})



//deleting one 
router.delete('/:id',getUtilisateur,async(req,res) => {
    try{
        await res.utilisateur.remove()
    res.json({ message: 'Deleted utilisateur' })

    } catch(err){
        res.status(500).json({ message: err.message })
    }

})
module.exports = router