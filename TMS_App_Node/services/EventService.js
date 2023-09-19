const { PrismaClient } = require('@prisma/client');
const prisma = new PrismaClient();

const EventController = {
    getAllEvents: async (req, res) => {
        try {
            const events = await prisma.events.findMany({
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
            });

            if (!events) {
                return res.status(404).json({ error: 'No Events Found!!' });
            }

            return res.status(200).json(events);
        }
        catch (error) {
            return res.status(500).json({ error: 'Internal server error...' });
        }
    },

    getAllEventsByVenueLocationAndEventCategory: async (req, res) => {
        try {
            const venueLocation = req.query.location;
            const eventCategory = req.query.category;

            const events = await prisma.events.findMany({
                where: {
                    venues: {
                        location: venueLocation
                    },
                    event_categories: {
                        category_name: eventCategory
                    }
                },
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
            });

            if (!events) {
                return res.status(404).json({ error: 'No Events Found!!' });
            }

            return res.status(200).json(events);
        }
        catch (error) {
            return res.status(500).json({ error: 'Internal server error...' });
        }
    },

    searchEventsByName: async (req, res) => {
        try {
            const eventName = req.query.name;

            const events = await prisma.events.findMany({
                where: {
                    event_name: {
                        contains: eventName
                    }
                },
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
            });

            if (!events) {
                return res.status(404).json({ error: 'No Events Found!!' });
            }

            return res.status(200).json(events);
        }
        catch (error) {
            return res.status(500).json({ error: 'Internal server error...' });
        }
    }
}

module.exports = EventController;
