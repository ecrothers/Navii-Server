INSERT INTO preference_questions (preference_type, preference_question) VALUES (1, "What type of person are you?");
INSERT INTO preference_questions (preference_type, preference_question) VALUES (2, "What is your personal vibe?");
INSERT INTO preference_questions (preference_type, preference_question) VALUES (3, "What are your dining preferences?");

INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('gluten-free', 1);
INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('vegan', 1);
INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('vegetarian', 1);
INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('halal', 1);

INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('adventure', 2);
INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('outdoor', 2);
INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('adult', 2);
INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('foodie', 2);
INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('sporty', 2);
INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('lazy', 2);
INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('cultural', 2);

INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('hipster', 3);
INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('sophisticated', 3);
INSERT INTO preferences (preference, photoURL, preference_type) VALUES ('cheap', 3);

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

INSERT INTO users (username, password, salt, is_facebook, verified) VALUES ('naviCreator', 'saltedpass', 'salt', 'n', 'n');




