INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('gluten-free', 'funpic.png', 1);
INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('vegan', 'boringpic.png', 1);
INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('vegetarian', 'adultpic.png', 1);
INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('halal', 'kidspic.png', 1);

INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('advebture', 'funpic.png', 2);
INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('outdoor', 'boringpic.png', 2);
INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('slutty', 'adultpic.png', 2);
INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('foodie', 'kidspic.png', 2);
INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('sporty', 'piouspic.png', 2);
INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('lazy', 'cleanpic.png', 2);
INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('cultural', 'dirtypic.png', 2);

INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('hipster', 'funpic.png', 3);
INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('sophisticated', 'boringpic.png', 3);
INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('cheap', 'adultpic.png', 3);

INSERT INTO tags (tag) VALUES ('italian');
INSERT INTO tags (tag) VALUES ('mexican');
INSERT INTO tags (tag) VALUES ('chinese');
INSERT INTO tags (tag) VALUES ('theater');
INSERT INTO tags (tag) VALUES ('museum');

INSERT INTO preference_questions (preference_type, preference_question) VALUES (1, "Do you like dancing?");
INSERT INTO preference_questions (preference_type, preference_question) VALUES (2, "Can I talk body dance with me?");
INSERT INTO preference_questions (preference_type, preference_question) VALUES (3, "What are your Dining preferences?");

INSERT INTO tags (tag) VALUES ('italian');
INSERT INTO tags (tag) VALUES ('mexican');
INSERT INTO tags (tag) VALUES ('chinese');
INSERT INTO tags (tag) VALUES ('theater');
INSERT INTO tags (tag) VALUES ('museum');


INSERT INTO itineraries (totalcost, startdate, enddate, description, authorid)
VALUES ('400.00', NOW(), NOW(), 'wtf', 6);
INSERT INTO itineraries (totalcost, startdate, enddate, description, authorid)
VALUES ('600.00', NOW(), NOW(), 'this', 6);
INSERT INTO itineraries (totalcost, startdate, enddate, description, authorid)
VALUES ('5000.00', NOW(), NOW(), 'cost', 6);
INSERT INTO itineraries (totalcost, startdate, enddate, description, authorid)
VALUES ('123000.00', NOW(), NOW(), 'stripper', 6);

INSERT INTO itinerariespreferences (itineraryid, preference) VALUES (1, 'Fun');
INSERT INTO itinerariespreferences (itineraryid, preference) VALUES (1, 'Kids');
INSERT INTO itinerariespreferences (itineraryid, preference) VALUES (2, 'Boring');
INSERT INTO itinerariespreferences (itineraryid, preference) VALUES (2, 'Adult');
INSERT INTO itinerariespreferences (itineraryid, preference) VALUES (3, 'Fun');
INSERT INTO itinerariespreferences (itineraryid, preference) VALUES (3, 'Adult');
INSERT INTO itinerariespreferences (itineraryid, preference) VALUES (4, 'Pious');
INSERT INTO itinerariespreferences (itineraryid, preference) VALUES (4, 'Adult');

INSERT INTO itinerariestags (itineraryid, tag) VALUES (1, 'church');
INSERT INTO itinerariestags (itineraryid, tag) VALUES (2, 'Lifechanging');
INSERT INTO itinerariestags (itineraryid, tag) VALUES (3, 'Gambling');
INSERT INTO itinerariestags (itineraryid, tag) VALUES (4, 'Cheat');
INSERT INTO itinerariestags (itineraryid, tag) VALUES (4, 'Girlsgirlsgirls');

INSERT INTO users (username, saltedPassword, salt, isFacebook) VALUES ('naviCreator', 'saltedpass', 'salt', 'n');



