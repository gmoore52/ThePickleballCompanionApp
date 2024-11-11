INSERT INTO USERS (USER_NAME, USER_FULL_NAME, EMAIL_ADDR, PASSWORD, PROFILE_IMG_LOC, SKILL_LVL, ACC_CREATION_DATE)
VALUES
    ('jane_smith', 'Jane Smith', 'jane.smith@example.com', 'password123', '/images/jane_smith.jpg', 5, NOW()),
    ('must_select', 'Must Select', 'must.select@example.com', 'password456', '/images/must_select.jpg', 4, NOW()),
    ('olivia_brown', 'Olivia Brown', 'olivia.brown@example.com', 'password789', '/images/olivia_brown.jpg', 3, NOW()),
    ('benjamin_martinez', 'Benjamin Martinez', 'benjamin.martinez@example.com', 'password101', '/images/benjamin_martinez.jpg', 2, NOW()),
    ('Peter_Dinklage3', 'Peter Dinklage', 'dinkle@example.com', 'password101', '/images/benjamin_martinez.jpg', 2, NOW());


INSERT INTO LOCATIONS (LOC_ID, COURT_NAME, COURT_PIC, NUM_OF_COURTS, ADDRESS, COORDINATES, COURT_META_ID)
VALUES
    (1, 'Downtown Court', '/images/downtown_court.jpg', 4, '123 Main St, Cityville', '40.7128,-74.0060', 1),
    (2, 'Westside Park', '/images/westside_park.jpg', 6, '456 West St, Townsville', '34.0522,-118.2437', 2),
    (3, 'Eastwood Courts', '/images/eastwood_courts.jpg', 3, '789 East Ave, Villageton', '51.5074,-0.1278', 3),
    (4, 'Riverside Court', '/images/riverside_court.jpg', 2, '101 River Rd, Waterbury', '35.6895,139.6917', 4),
    (5, 'Central Park Courts', '/images/central_park_courts.jpg', 8, '202 Central Ave, Cityville', '31.6895,14.6917', 5);

INSERT INTO EVENTS (EVENT_START, EVENT_END, EVENT_LOC, EVENT_TITLE, EVENT_DESC)
VALUES
    ('2024-11-01 12:00:00', '2025-10-01 12:00:00', 1, 'Pickleball Tournament', 'Join us for an exciting pickleball tournament!'),
    (NOW() - INTERVAL '6 days', '2025-10-05 16:00:00', 2, 'Community Game Day', 'A fun-filled day of pickleball games for everyone!'),
    (NOW() - INTERVAL '5 days', '2025-10-10 11:00:00', 3, 'Beginner Lessons', 'Free lessons for beginners to learn the game of pickleball.'),
    (NOW(), '2025-10-15 20:00:00', 4, 'Night Play', 'Enjoy pickleball under the lights!'),
    (NOW(), '2025-10-20 21:00:00', 5, 'Pickleball Social', 'Join us for a social evening of pickleball and networking!');
