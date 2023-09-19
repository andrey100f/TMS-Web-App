const router = require('express').Router();
const OrderService = require('../services/OrderService');

router.get('/:userId', OrderService.getAllOrdersByUserId);
router.post('/:userId', OrderService.saveOrder);
router.patch('/:userId/:orderId', OrderService.updateOrder);
router.delete('/:userId/:orderId', OrderService.deleteOrder);

module.exports = router;
