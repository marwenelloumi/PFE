const express = require('express')
const router = express.Router()
const jwt =require('jsonwebtoken')
const bcrypt = require('bcrypt')

const Utilisateur = require('../Model/Utilisateur')
const Plante = require('../Model/Plante')

async function authenticateToken(req,res,next){
    const authHeader = req.headers['authorization']
    const token = authHeader && authHeader.split(' ')[1]
    if (token == null) return res.sendStatus(401);
    jwt.verify(token,process.env.ACCSESS_TOKEN,async(err,utilisateur)=> {
        if(err) return res.sendStatus(403)
        req.utilisateur=utilisateur
        try {
            utilisateur = await Utilisateur.findOne({email:req.utilisateur.email })
       
        } 
        catch (err) {
            return res.status(500).json({ message: err.message })
        }
        res.utilisateur = utilisateur
        next()
    })
}
//Middleware function 
async function getPlante (req, res, next) {
    let plante
    try {
      plante = await Plante.findById(req.params.id)
      if (plante == null) {
         return res.status(404).json({ message: 'Cannot find plante' })
      }
    } 
    catch (err) {
        return res.status(500).json({ message: err.message })
    }
  
    res.plante = plante
    next()
}

//Getting All
router.get('/',authenticateToken,async (req,res) => {

    try{
        plantes = await Plante.findOne({userEmail:res.utilisateur.email })
        res.json(plantes)

    } catch(err){
        res.status(500).json({message:err.message})
    }
})
// Getting with email
router.get('/',async (req,res) => {
    try{
        const plantes= await Plante.find({userEmail:req.body.userEmail})
        res.json(plantes)

    } catch(err){
        res.status(500).json({message:err.message})
    }
})

//Getting One
router.get('/:id',getPlante ,(req,res) => {
    res.send(res.plante)
})

//creating one
router.post('/',async(req,res) => {
    const plante  = new Plante({
        name: req.body.name,
        userEmail : req.body.userEmail
    })
    try {
        const newPlante = await plante.save()
        res.status(201).json(newPlante)
    } 
    catch (err) {
        res.status(400).json({ message: err.message })
    }
})
    
//upadating one 
router.patch('/:id',getPlante,async (req,res) => {

    if (req.body.name != null) {
        res.plante.name = req.body.name
    }
    if (req.body.userEmail != null) {   
        res.plante.userEmail = req.body.userEmail
    }
    try {
        const updatedPlante = await res.plante.save()
        res.json(updatedPlante)
    } 
    catch (err) {
        res.status(400).json({ message: err.message })
    }
})

//deleting one 
router.delete('/:id',getPlante,async(req,res) => {
    try{
        await res.plante.remove()
        res.json({ message: 'Deleted plante' })
    } 
    catch(err){
        res.status(500).json({ message: err.message })
    }
})

module.exports = router


