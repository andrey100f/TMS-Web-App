const { PrismaClient } = require('@prisma/client');
const prisma = new PrismaClient();

const UserController = {
    getUserByUsernameAndPassword: async (req, res) => {
        try {
            const username = req.body.username;
            const password = req.body.password;

            const user = await prisma.users.findFirst({
                where: {
                    username: username, password: password
                },
                select: {
                    user_id: true, username: true, email: true, customer_name: true, image_url: true
                }
            });

            if (!user) {
                return res.status(404).json({ error: 'No Users Found...' });
            }

            return res.status(200).json(user);
        }
        catch (error) {
            return res.status(500).json({ error: 'Internal server error...' });
        }
    }
}

module.exports = UserController;
