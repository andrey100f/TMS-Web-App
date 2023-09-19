const express = require('express');
const cors = require('cors');

const EventController = require('./controllers/EventController');
const OrderController = require('./controllers/OrderController');
const UserController = require('./controllers/UserController');

const app = express();

app.use(express.json());

app.use(cors({
    origin: 'http://localhost:5173'
}));

app.use('/api/events', EventController);
app.use('/api/orders', OrderController);
app.use('/', UserController);

app.listen(3000, () => {
    console.log("REST API Server ready at: http://localhost:3000");
});
