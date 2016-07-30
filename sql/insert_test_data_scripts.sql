INSERT INTO preference_questions (preference_type, preference_question) VALUES (1, "What type of person are you?");
INSERT INTO preference_questions (preference_type, preference_question) VALUES (2, "What is your personal vibe?");
INSERT INTO preference_questions (preference_type, preference_question) VALUES (3, "What are your dining preferences?");

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

INSERT INTO tags (tag) VALUES 
('adventurous'),
('chinese'),
('greek'),
('hiking'),
('historical'),
('Indoor'),
('italian'),
('japanese'),
('mexican'),
('monument'),
('museum'),
('musical theater'),
('night life'),
('outdoor'),
('theater'),
('urban')
;

INSERT INTO users (username, saltedPassword, salt, isFacebook, verified) VALUES ('naviCreator', 'saltedpass', 'salt', 'n', 'n');




