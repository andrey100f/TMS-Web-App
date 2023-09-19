const { PrismaClient } = require('@prisma/client');
const prisma = new PrismaClient();

const OrderController = {
    getAllOrdersByUserId: async (req, res) => {
        try {
            const userId = req.params.userId;

            const orders = await prisma.orders.findMany({
                select: {
                    order_id: true, ordered_at: true, number_of_tickets: true, total_price: true,
                    users: {
                        select: {
                            username: true, email: true, customer_name: true, image_url: true,
                            user_roles: {
                                select: {
                                    role_name: true
                                }
                            },
                        }
                    },
                    ticket_categories: {
                        select: {
                            description: true, price: true,
                            events: {
                                select: {
                                    event_description: true, event_name: true, start_date: true, end_date: true, image_url: true,
                                    event_categories: {
                                        select: {
                                            category_name: true
                                        }
                                    },
                                    ticket_categories: {
                                        select: {
                                            description: true, price: true
                                        }
                                    },
                                    venues: {
                                        select: {
                                            location: true, type: true, capacity: true
                                        }
                                    }
                                }
                            }
                        }
                    },
                },
                where: {
                    user_id: userId
                }
            });

            if (!orders) {
                return res.status(404).json({ error: 'No Orders Found...' });
            }

            return res.status(200).json(orders);
        }
        catch (error) {
            return res.status(500).json({ error: 'Internal server error...' });
        }
    },

    saveOrder: async (req, res) => {
        try {
            const userId = req.params.userId;
            const eventName = req.body.eventName;
            const ticketCategoryDescription = req.body.ticketCategory;

            const event = await prisma.events.findFirst({
                select: {
                    event_id: true
                },
                where: {
                    event_name: eventName
                }
            });

            if (!event) {
                return res.status(404).json({ error: 'No Events Found...' });
            }

            const eventId = event.event_id;

            const ticketCategory = await prisma.ticket_categories.findFirst({
                select: {
                    ticket_category_id: true, price: true
                },
                where: {
                    event_id: eventId, description: ticketCategoryDescription
                }
            });

            if (!ticketCategory) {
                return res.status(404).json({ error: 'No Ticket Categories Found...' });
            }

            const ticketCategoryId = ticketCategory.ticket_category_id;
            const price = ticketCategory.price;
            const orderDate = new Date();
            const numberOfTickets = req.body.numberOfTickets;
            const totalPrice = price * numberOfTickets;

            const orderData = {
                user_id: userId,
                ticket_category_id: ticketCategoryId,
                ordered_at: orderDate,
                number_of_tickets: numberOfTickets,
                total_price: totalPrice
            };

            const order = await prisma.orders.create({
                data: orderData
            });

            return res.status(200).json(order);
        }
        catch (error) {
            return res.status(500).json({ error: 'Internal server error...' });
        }
    },

    updateOrder: async (req, res) => {
        try {
            const orderId = req.params.orderId;
            const eventName = req.body.eventName;
            const ticketCategoryDescription = req.body.ticketCategory;
            const numberOfTickets = req.body.numberOfTickets;

            const event = await prisma.events.findFirst({
                select: {
                    event_id: true
                },
                where: {
                    event_name: eventName
                }
            });

            if (!event) {
                return res.status(404).json({ error: 'No Events Found...' });
            }

            const eventId = event.event_id;

            const ticketCategory = await prisma.ticket_categories.findFirst({
                select: {
                    ticket_category_id: true, price: true
                },
                where: {
                    event_id: eventId, description: ticketCategoryDescription
                }
            });

            if (!ticketCategory) {
                return res.status(404).json({ error: 'No Ticket Categories Found...' });
            }

            const ticketCategoryId = ticketCategory.ticket_category_id;
            const price = ticketCategory.price;
            const totalPrice = price * numberOfTickets;

            const orderData = {
                ticket_category_id: ticketCategoryId,
                number_of_tickets: numberOfTickets,
                total_price: totalPrice
            };

            const orderUpdated = await prisma.orders.update({
                where: {
                    order_id: orderId
                },
                data: orderData
            });

            return res.status(200).json(orderUpdated);
        }
        catch (error) {
            return res.status(500).json({ error: 'Internal server error...' });
        }
    },

    deleteOrder: async (req, res) => {
        try {
            const orderId = req.params.orderId;
            const orderDeleted = await prisma.orders.delete({
                where: {
                    order_id: orderId
                }
            });

            if (!orderDeleted) {
                return res.status(404).json({ error: 'No Orders Found...' });
            }

            return res.status(200).json(orderDeleted);
        }
        catch (error) {
            return res.status(500).json({ error: 'Internal server error...' });
        }
    }
}

module.exports = OrderController;
