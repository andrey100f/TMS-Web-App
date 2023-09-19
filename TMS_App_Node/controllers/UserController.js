const router = require('express').Router();
const UserService = require('../services/UserService');

router.post('/login', UserService.getUserByUsernameAndPassword);

module.exports = router;
