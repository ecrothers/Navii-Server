
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Fun', 'funpic.png', 1);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Boring', 'boringpic.png', 1);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Adult', 'adultpic.png', 1);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Kids', 'kidspic.png', 1);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Pious', 'piouspic.png', 1);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Clean', 'cleanpic.png', 1);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Dirty', 'dirtypic.png', 1);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Hardcore', 'hardcorepic.png', 1);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Chinese', 'chinesepic.png', 1);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('French', 'frenchpic.png', 1);
INSERT INTO preferences(preference, photoURL, preference_type VALUES ('Black', 'blackpic.png', 1);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('White', 'whitepic.png', 1);

INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Dancing', 'funpic.png', 2);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Museum', 'boringpic.png', 2);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Sports', 'adultpic.png', 2);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Silent', 'kidspic.png', 2);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Film', 'piouspic.png', 2);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Crack', 'cleanpic.png', 2);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Cocaine', 'dirtypic.png', 2);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Hardcore', 'hardcorepic.png', 2);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Chinese', 'chinesepic.png', 2);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('French', 'frenchpic.png', 2);
INSERT INTO preferences(preference, photoURL, preference_type VALUES ('Black', 'blackpic.png', 2);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('White', 'whitepic.png', 2);

INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Dancing', 'funpic.png', 3);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Museum', 'boringpic.png', 3);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Sports', 'adultpic.png', 3);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Silent', 'kidspic.png', 3);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Film', 'piouspic.png', 3);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Crack', 'cleanpic.png', 3);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Cocaine', 'dirtypic.png', 3);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Hardcore', 'hardcorepic.png', 3);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('Chinese', 'chinesepic.png', 3);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('French', 'frenchpic.png', 3);
INSERT INTO preferences(preference, photoURL, preference_type VALUES ('Black', 'blackpic.png', 3);
INSERT INTO preferences(preference, photoURL, preference_type) VALUES ('White', 'whitepic.png', 3);

INSERT INTO tags(tag) VALUES ('Lifechanging');
INSERT INTO tags(tag) VALUES ('Cheat');
INSERT INTO tags(tag) VALUES ('Gambling');
INSERT INTO tags(tag) VALUES ('Girlsgirlsgirls');
INSERT INTO tags(tag) VALUES ('church');
INSERT INTO tags(tag) VALUES ('playground');

INSERT INTO itineraries(totalcost, startdate, enddate, description, authorid) VALUES ('400.00', NOW(), NOW(), 'wtf', 6);
INSERT INTO itineraries(totalcost, startdate, enddate, description, authorid) VALUES ('600.00', NOW(), NOW(), 'this', 6);
INSERT INTO itineraries(totalcost, startdate, enddate, description, authorid) VALUES ('5000.00', NOW(), NOW(), 'cost', 6);  
INSERT INTO itineraries(totalcost, startdate, enddate, description, authorid) VALUES ('123000.00', NOW(), NOW(), 'stripper', 6);  

DROP TABLE itinerariespreferences;  
CREATE TABLE IF NOT EXISTS itinerariespreferences (
	itineraryid INT,
    preference VARCHAR(10),
    UNIQUE KEY (itineraryid,preference),
    FOREIGN KEY (itineraryid) REFERENCES itineraries(itineraryid) ON DELETE CASCADE,
    FOREIGN KEY (preference) REFERENCES preferences(preference) ON DELETE CASCADE
);
  
INSERT INTO itinerariespreferences(itineraryid,preference) VALUES(1,'Fun');
INSERT INTO itinerariespreferences(itineraryid,preference) VALUES(1,'Kids');
INSERT INTO itinerariespreferences(itineraryid,preference) VALUES(2,'Boring');
INSERT INTO itinerariespreferences(itineraryid,preference) VALUES(2,'Adult');
INSERT INTO itinerariespreferences(itineraryid,preference) VALUES(3,'Fun');
INSERT INTO itinerariespreferences(itineraryid,preference) VALUES(3,'Adult');
INSERT INTO itinerariespreferences(itineraryid,preference) VALUES(4,'Pious');
INSERT INTO itinerariespreferences(itineraryid,preference) VALUES(4,'Adult');

INSERT INTO itinerariestags(itineraryid,tag) VALUES(1,'church');
INSERT INTO itinerariestags(itineraryid,tag) VALUES(2,'Lifechanging');
INSERT INTO itinerariestags(itineraryid,tag) VALUES(3,'Gambling');
INSERT INTO itinerariestags(itineraryid,tag) VALUES(4,'Cheat');
INSERT INTO itinerariestags(itineraryid,tag) VALUES(4,'Girlsgirlsgirls');

SELECT itineraryid, startdate, enddate FROM itineraries WHERE itineraryid IN (SELECT itineraryid FROM itinerariespreferences WHERE preference='Fun' OR preference='Kids');

ALTER TABLE itinerariespreferences ADD UNIQUE (itineraryid,preference);
ALTER TABLE itinerariestags ADD UNIQUE(itineraryid, tag);
Insert into users(username, saltedPassword, salt, isFacebook) VALUES('naviCreator', 'saltedpass', 'salt' , 'n'); 

SELECT * FROM users;
SELECT * FROM itineraries;
SELECT * FROM itinerariestags;



