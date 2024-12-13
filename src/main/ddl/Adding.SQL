INSERT INTO USERS (
    USER_NAME,
    USER_FULL_NAME,
    EMAIL_ADDR,
    PASSWORD,
    PROFILE_IMG_LOC,
    SKILL_LVL,
    ACC_CREATION_DATE
) VALUES
      ('jane_smith', 'Jane Smith', 'jane.smith@example.com', '$2a$10$xx8vlZ3Mdx0sDKNiPfbM0.BDhB9Bdl8a7AzlyTHlh7OdPrncAoUnO', '/images/jane_smith.jpg', 2, '2024-11-11 03:46:07.739750'),
      ('ImElena1004', 'Elena Green', 'elena.green@example.com', '$2a$10$xx8vlZ3Mdx0sDKNiPfbM0.BDhB9Bdl8a7AzlyTHlh7OdPrncAoUnO', '/images/elena_green.jpg', 4, '2024-11-11 03:46:07.739750'),
      ('olivia_brown', 'Olivia Brown', 'olivia.brown@example.com', '$2a$10$xx8vlZ3Mdx0sDKNiPfbM0.BDhB9Bdl8a7AzlyTHlh7OdPrncAoUnO', '/images/olivia_brown.jpg', 3, '2024-11-11 03:46:07.739750'),
      ('benjamin_martinez', 'Benjamin Martinez', 'benjamin.martinez@example.com', '$2a$10$xx8vlZ3Mdx0sDKNiPfbM0.BDhB9Bdl8a7AzlyTHlh7OdPrncAoUnO', '/images/benjamin_martinez.jpg', 2, '2024-11-11 03:46:07.739750'),
      ('Garth_Dilliam2', 'Garth Dilliam', 'Garthdilliam2@gmail.com', '$2a$10$xx8vlZ3Mdx0sDKNiPfbM0.BDhB9Bdl8a7AzlyTHlh7OdPrncAoUnO', NULL, 2, '2024-11-10 21:47:12.974000'),
      ('Andrew_Larson9', 'Andrew Larson', 'andrewlarsonlovespickle3@gmail.com', '$2a$10$xx8vlZ3Mdx0sDKNiPfbM0.BDhB9Bdl8a7AzlyTHlh7OdPrncAoUnO', NULL, 2, '2024-11-10 21:57:51.686000'),
      ('JohnRobertson22', 'John Robertson', 'johnrobertsonwork@gmail.com', '$2a$10$xx8vlZ3Mdx0sDKNiPfbM0.BDhB9Bdl8a7AzlyTHlh7OdPrncAoUnO', NULL, 3, '2024-11-11 22:14:39.321000');

INSERT INTO REQUESTS (
    REQUEST_TYPE,
    REQUEST_DATE,
    ORIGIN_ID,
    EVENT_ID,
    DESTINATION_ID,
    REQUEST_STATUS
) VALUES
-- Received requests (REC) to Andrew Larson
('FRIEND_REQUEST', '2024-11-20 10:15:00', 'jane_smith', NULL, 'Andrew_Larson9', 'REC'),
('FRIEND_REQUEST', '2024-11-20 11:30:00', 'ImElena1004', NULL, 'Andrew_Larson9', 'REC'),

-- Sent requests (REC) from Andrew Larson
('FRIEND_REQUEST', '2024-11-20 15:45:00', 'Andrew_Larson9', NULL, 'benjamin_martinez', 'REC'),

-- Accepted friend requests (ACC) with Andrew Larson where it is accepted
('FRIEND_REQUEST', '2024-11-20 16:30:00', 'Andrew_Larson9', NULL, 'JohnRobertson22', 'ACC'),
('FRIEND_REQUEST', '2024-11-20 16:30:00', 'Andrew_Larson9', NULL, 'Garth_Dilliam2', 'ACC'),
('FRIEND_REQUEST', '2024-11-20 14:00:00', 'olivia_brown', NULL, 'Andrew_Larson9', 'ACC');

-- Requests that are occuring between other users, exclusing andrew larson

INSERT INTO REQUESTS (
    REQUEST_TYPE,
    REQUEST_DATE,
    ORIGIN_ID,
    EVENT_ID,
    DESTINATION_ID,
    REQUEST_STATUS
) VALUES
-- Received requests (REC)
('FRIEND_REQUEST', '2024-11-18 10:00:00', 'jane_smith', NULL, 'ImElena1004', 'REC'),
('FRIEND_REQUEST', '2024-11-19 13:30:00', 'olivia_brown', NULL, 'benjamin_martinez', 'REC'),
('FRIEND_REQUEST', '2024-11-19 16:45:00', 'JohnRobertson22', NULL, 'benjamin_martinez', 'REC'),

-- Accepted friend requests (ACC)
('FRIEND_REQUEST', '2024-11-18 09:30:00', 'jane_smith', NULL, 'olivia_brown', 'ACC'),
('FRIEND_REQUEST', '2024-11-18 11:00:00', 'benjamin_martinez', NULL, 'jane_smith', 'ACC'),
('FRIEND_REQUEST', '2024-11-19 14:45:00', 'JohnRobertson22', NULL, 'olivia_brown', 'ACC'),
('FRIEND_REQUEST', '2024-11-20 12:00:00', 'Garth_Dilliam2', NULL, 'olivia_brown', 'ACC'),
('FRIEND_REQUEST', '2024-11-20 14:15:00', 'Garth_Dilliam2', NULL, 'JohnRobertson22', 'ACC'),
('FRIEND_REQUEST', '2024-11-20 11:30:00', 'jane_smith', NULL, 'benjamin_martinez', 'ACC');





INSERT INTO LOCATIONS (LOC_ID, COURT_NAME, COURT_PIC, NUM_OF_COURTS, ADDRESS, COORDINATES, COURT_META_ID)
VALUES
    (1, 'Downtown Court', '/images/downtown_court.jpg', 4, '123 Main St, Cityville', '40.7128,-74.0060', 1),
    (2, 'Westside Park', '/images/westside_park.jpg', 6, '456 West St, Townsville', '34.0522,-118.2437', 2),
    (3, 'Eastwood Courts', '/images/eastwood_courts.jpg', 3, '789 East Ave, Villageton', '51.5074,-0.1278', 3),
    (4, 'Riverside Court', '/images/riverside_court.jpg', 2, '101 River Rd, Waterbury', '35.6895,139.6917', 4),
    (5, 'Central Park Courts', '/images/central_park_courts.jpg', 8, '202 Central Ave, Cityville', '31.6895,14.6917', 5),
    (6, 'Meador Park', '/images/meador_park.jpg', 10, '2600 S Fremont Ave Springfield', '37.1672,-93.271689', 6);

INSERT INTO EVENTS (EVENT_START, EVENT_END, EVENT_LOC, EVENT_TITLE, EVENT_DESC)
VALUES
    ('2024-11-01 12:00:00', '2025-10-01 12:00:00', 1, 'Pickleball Tournament', 'Join us for an exciting pickleball tournament!'),
    (NOW() - INTERVAL '30 days', '2025-10-05 16:00:00', 2, 'Community Game Day', 'A fun-filled day of pickleball games for everyone!'),
    (NOW() - INTERVAL '30 days', '2025-10-10 11:00:00', 3, 'Beginner Lessons', 'Free lessons for beginners to learn the game of pickleball.'),
    (NOW(), '2025-10-15 20:00:00', 4, 'Night Play', 'Enjoy pickleball under the lights!'),
    (NOW(), '2025-10-20 21:00:00', 5, 'Pickleball Social', 'Join us for a social evening of pickleball and networking!');
