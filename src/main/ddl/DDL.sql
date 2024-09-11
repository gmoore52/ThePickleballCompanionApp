DROP TABLE IF EXISTS GAMES;
DROP TABLE IF EXISTS REQUESTS;
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS LOCATIONS;
DROP TABLE IF EXISTS LOCATION_META;
DROP TABLE IF EXISTS EVENTS;

CREATE TABLE USERS(
    USER_ID SERIAL PRIMARY KEY,
    EMAIL_ADDR VARCHAR(120) NOT NULL,
    PASSWORD VARCHAR(30) NOT NULL,
    PROFILE_IMG_LOC VARCHAR(120),
    SKILL_LVL INTEGER,
    ACC_CREATION_DATE TIMESTAMP NOT NULL
);

CREATE TABLE LOCATION_META(
    META_ID SERIAL PRIMARY KEY,
    AVAILABILITY VARCHAR(8),
    SHELTER BOOLEAN,
    NET_TYPE VARCHAR(10)
);

CREATE TABLE LOCATIONS(
    LOC_ID SERIAL PRIMARY KEY,
    COURT_NAME VARCHAR(50) NOT NULL,
    NUM_OF_COURTS INTEGER NOT NULL,
    ADDRESS VARCHAR(120) NOT NULL,
    COORDINATES VARCHAR(120),
    COURT_META_ID INTEGER REFERENCES LOCATION_META(META_ID)
);

CREATE TABLE GAMES(
    GAME_ID SERIAL PRIMARY KEY,
    GAME_DATE TIMESTAMP NOT NULL,
    PLAYER_1_ID INTEGER NOT NULL REFERENCES USERS(USER_ID),
    PLAYER_2_ID INTEGER NOT NULL REFERENCES USERS(USER_ID),
    PLAYER_3_ID INTEGER REFERENCES USERS(USER_ID),
    PLAYER_4_ID INTEGER REFERENCES USERS(USER_ID),
    LOCATION_ID INTEGER REFERENCES LOCATIONS(LOC_ID),
    TEAM_1_SCORE INTEGER,
    TEAM_2_SCORE INTEGER
);


CREATE TABLE EVENTS(
    EVENT_ID SERIAL PRIMARY KEY,
    EVENT_START TIMESTAMP NOT NULL,
    EVENT_END TIMESTAMP NOT NULL,
    EVENT_DESC VARCHAR(120) NOT NULL
);

CREATE TABLE REQUEST_META(
    META_ID SERIAL PRIMARY KEY,
    REQUEST_TYPE VARCHAR(25)
);

CREATE TABLE REQUESTS
(
    REQUEST_ID       SERIAL PRIMARY KEY,
    REQUEST_TYPE     VARCHAR(25) NOT NULL,
    REQUEST_DATE     TIMESTAMP NOT NULL,
    ORIGIN_ID        INTEGER   NOT NULL REFERENCES USERS (USER_ID),
    EVENT_ID         INTEGER REFERENCES EVENTS (EVENT_ID),
    DESTINATION_ID   INTEGER   NOT NULL REFERENCES USERS (USER_ID),
    REQUEST_STATUS   INTEGER   NOT NULL
);

-- Insert all variations of the base metadata for each possible location
INSERT INTO LOCATION_META(AVAILABILITY, SHELTER, NET_TYPE)
SELECT * FROM
     (
         (SELECT 'PUBLIC' UNION
          SELECT 'PRIVATE' UNION
          SELECT 'MEMBER' UNION
          SELECT 'FEE') A
           CROSS JOIN
         (SELECT TRUE UNION
          SELECT FALSE) S
            CROSS JOIN
         (SELECT 'BYO' UNION
          SELECT 'PERMANENT' UNION
          SELECT 'PORTABLE') N_T
     );