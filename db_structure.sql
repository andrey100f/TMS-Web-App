USE event_ticket_db
GO

CREATE SCHEMA tms_schema

BEGIN TRANSACTION;
	CREATE TABLE tms_schema.user_roles(
		role_id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
		role_name VARCHAR(50) UNIQUE NOT NULL,
		user_role_version INT DEFAULT 1
	);
COMMIT TRANSACTION;

BEGIN TRANSACTION;
	CREATE TABLE tms_schema.event_categories(
		category_id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
		category_name VARCHAR(50) UNIQUE NOT NULL,
		event_category_version INT DEFAULT 1
	);
COMMIT TRANSACTION;

BEGIN TRANSACTION;
	CREATE TABLE tms_schema.venues(
		venue_id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
		location VARCHAR(255),
		type VARCHAR(50),
		capacity INT,
		venue_version INT DEFAULT 1
	);
COMMIT TRANSACTION;

BEGIN TRANSACTION;
	CREATE TABLE tms_schema.users(
		user_id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
		username VARCHAR(255) UNIQUE NOT NULL,
		email VARCHAR(255) UNIQUE NOT NULL,
		password VARCHAR(255) NOT NULL,
		customer_name VARCHAR(255),
		user_role_id UNIQUEIDENTIFIER NOT NULL,
		user_version INT DEFAULT 1
		CONSTRAINT FK_users_user_roles FOREIGN KEY(user_role_id) REFERENCES tms_schema.user_roles(role_id)
			ON UPDATE CASCADE
			ON DELETE CASCADE
	);
COMMIT TRANSACTION;

BEGIN TRANSACTION;
	CREATE TABLE tms_schema.events(
		event_id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
		category_id UNIQUEIDENTIFIER NOT NULL,
		venue_id UNIQUEIDENTIFIER NOT NULL,
		event_description VARCHAR(255),
		event_name VARCHAR(255),
		start_date DATETIME,
		end_date DATETIME,
		event_version INT DEFAULT 1
		CONSTRAINT FK_events_event_categories FOREIGN KEY(category_id) REFERENCES tms_schema.event_categories(category_id)
			ON UPDATE CASCADE
			ON DELETE CASCADE,
		CONSTRAINT FK_events_venues FOREIGN KEY(venue_id) REFERENCES tms_schema.venues(venue_id)
			ON UPDATE CASCADE
			ON DELETE CASCADE
	);
COMMIT TRANSACTION;

BEGIN TRANSACTION;
	CREATE TABLE tms_schema.ticket_categories(
		ticket_category_id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
		event_id UNIQUEIDENTIFIER NOT NULL,
		description VARCHAR(255),
		price DECIMAL(10, 2),
		ticket_category_version INT DEFAULT 1
		CONSTRAINT ticket_categories_events FOREIGN KEY(event_id) REFERENCES tms_schema.events(event_id)
			ON UPDATE CASCADE
			ON DELETE CASCADE
	);
COMMIT TRANSACTION;

BEGIN TRANSACTION;
	CREATE TABLE tms_schema.orders(
		order_id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
		user_id UNIQUEIDENTIFIER NOT NULL,
		ticket_category_id UNIQUEIDENTIFIER NOT NULL,
		ordered_at DATETIME,
		number_of_tickets INT,
		total_price DECIMAL(10, 2),
		order_version INT DEFAULT 1
		CONSTRAINT FK_orders_users FOREIGN KEY(user_id) REFERENCES tms_schema.users(user_id)
			ON UPDATE CASCADE
			ON DELETE CASCADE,
		CONSTRAINT FK_orders_ticket_categories FOREIGN KEY(ticket_category_id) REFERENCES tms_schema.ticket_categories(ticket_category_id)
			ON UPDATE CASCADE
			ON DELETE CASCADE
	);
COMMIT TRANSACTION;

BEGIN TRANSACTION;
	CREATE TABLE tms_schema.event_reviews(
		review_id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
		event_id UNIQUEIDENTIFIER NOT NULL,
		user_id UNIQUEIDENTIFIER NOT NULL,
		review_text VARCHAR(255),
		rating INT,
		event_review_version INT DEFAULT 1
		CONSTRAINT FK_event_reviews_events FOREIGN KEY(event_id) REFERENCES tms_schema.events(event_id)
			ON UPDATE CASCADE
			ON DELETE CASCADE,
		CONSTRAINT FK_event_reviews_users FOREIGN KEY(user_id) REFERENCES tms_schema.users(user_id)
			ON UPDATE CASCADE
			ON DELETE CASCADE
	);
COMMIT TRANSACTION;