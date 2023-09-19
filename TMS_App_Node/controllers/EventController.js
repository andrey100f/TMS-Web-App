const router = require('express').Router();
const EventService = require('../services/EventService');

router.get('/', EventService.getAllEvents);
router.get('/filters', EventService.getAllEventsByVenueLocationAndEventCategory);
router.get('/search', EventService.searchEventsByName);

module.exports = router;
