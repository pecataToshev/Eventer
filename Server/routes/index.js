var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
var prSc = require('../schemas/participant_schema');
var userSc = require('../schemas/user_schema');

const jwt = require('jsonwebtoken');
const exjwt = require('express-jwt');


mongoose.connect('mongodb://localhost/eventer', {useNewUrlParser: true});

var db = mongoose.connection;

/*router.get('/', function(req, res, next) {
  
    var participant = new prSc({fName: "Gosho"});
    console.log(participant.fName)
    participant.save(function(err){
      console.log(err);
    })
    res.send("lolol");
  });*/

router.post("/Eventer/usersCreate", function(req, res, next) {

  console.log(req);

  var user = new userSc({fName: req.param('firstName'), lName: req.param('lastName'),
   username: req.param('username'), email: req.param('email'), password: req.param('password') });
  user.save(function(err){
    console.log(err);
    //res.send("failure");
  });
  res.json("success");
});


const jwtMW = exjwt({
  secret: 'keyboard cat 4 ever'
});

router.post('/Eventer/login', (req, res) => {
  const { username, password } = req.body;
  // Use your DB ORM logic here to find user and compare password
  users = userSc.findOne({ 'username': username, 'password': password  }, 'firstName lastName username', function (err, user) {
    if (err) return handleError(err);
    // Prints "Space Ghost is a talk show host".

    if(user == null)
    {
      res.status(401).json({
        sucess: false,
        token: null,
        err: 'Username or password is incorrect'
    });
    }
    else{
      let token = jwt.sign({ id: user.id, username: user.username }, 'keyboard cat 4 ever', { expiresIn: 129600 }); // Sigining the token
            res.json({
                sucess: true,
                err: null,
                token
            });
        }
  });
});

router.get('/', jwtMW /* Using the express jwt MW here */, (req, res) => {
  res.send('You are authenticated'); //Sending some response when authenticated
});

module.exports = router;
