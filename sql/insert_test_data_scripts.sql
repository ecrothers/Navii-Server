INSERT INTO preference_questions (preference_type, preference_question) VALUES (1, 'What type of person are you?');
INSERT INTO preference_questions (preference_type, preference_question) VALUES (2, 'What is your personal vibe?');
INSERT INTO preference_questions (preference_type, preference_question) VALUES (3, 'What are your dining preferences?');

INSERT INTO preferences (preference, preference_type) VALUES ('gluten-free', 1);
INSERT INTO preferences (preference, preference_type) VALUES ('vegan', 1);
INSERT INTO preferences (preference, preference_type) VALUES ('vegetarian', 1);
INSERT INTO preferences (preference, preference_type) VALUES ('halal', 1);

INSERT INTO preferences (preference, preference_type) VALUES ('adventure', 2);
INSERT INTO preferences (preference, preference_type) VALUES ('outdoor', 2);
INSERT INTO preferences (preference, preference_type) VALUES ('adult', 2);
INSERT INTO preferences (preference, preference_type) VALUES ('foodie', 2);
INSERT INTO preferences (preference, preference_type) VALUES ('sporty', 2);
INSERT INTO preferences (preference, preference_type) VALUES ('lazy', 2);
INSERT INTO preferences (preference, preference_type) VALUES ('cultural', 2);

INSERT INTO preferences (preference, preference_type) VALUES ('hipster', 3);
INSERT INTO preferences (preference, preference_type) VALUES ('sophisticated', 3);
INSERT INTO preferences (preference, preference_type) VALUES ('cheap', 3);

INSERT INTO tags (tag) VALUES 
('chinese'),
('greek'),
('hiking'),
('historical'),
('indoor'),
('italian'),
('japanese'),
('mexican'),
('monument'),
('museum'),
('musical theater'),
('night life'),
('theater'),
('urban')
;

INSERT INTO yelp_categories (yelp_category) VALUES
  ('arts'),
  ('active'),
  ('nightlife'),
  ('adult'),
  ('beautysvc'),
  ('fooddeliveryservices'),
  ('shopping'),
  ('localflavor'),
  ('halal'),
  ('gluten-free'),
  ('vegan'),
  ('vegetarian')
;

INSERT INTO yelp_preference_category (preference, yelp_category) VALUES
  ('sophisticated', 'arts'),
  ('adventure', 'active'),
  ('adventure', 'nightlife'),
  ('adventure', 'localflavor'),
  ('sporty', 'active'),
  ('adult', 'adult'),
  ('adult', 'nightlife'),
  ('adult', 'beautysvc'),
  ('outdoor', 'active'),
  ('lazy', 'fooddeliveryservices'),
  ('lazy', 'shopping'),
  ('cultural', 'localflavor'),
  ('halal', 'halal'),
  ('gluten-free', 'gluten-free'),
  ('vegan', 'vegan'),
  ('vegetarian', 'vegetarian')
;

INSERT INTO users (username, password, salt, is_facebook) VALUES ('naviCreator', 'saltedpass', 'salt', 'n', 'n');




