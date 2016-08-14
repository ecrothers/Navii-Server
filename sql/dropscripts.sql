Use naviDB;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS voyagers;
DROP TABLE IF EXISTS attractions;
DROP TABLE IF EXISTS itineraries;
DROP TABLE IF EXISTS activities;
DROP TABLE IF EXISTS cuisines;
DROP TABLE IF EXISTS atmospheres;
DROP TABLE IF EXISTS diets;
DROP TABLE IF EXISTS types;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS pointofinterests;
DROP TABLE IF EXISTS preferences;
DROP TABLE IF EXISTS preference_questions;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS userspreferences;
DROP TABLE IF EXISTS attractionspreferences;
DROP TABLE IF EXISTS attractionstags;
DROP TABLE IF EXISTS itinerariespreferences;
DROP TABLE IF EXISTS itinereariestags;
DROP TABLE IF EXISTS yelp_categories;
DROP TABLE IF EXISTS yelp_preference_category;
DROP TABLE IF EXISTS yelp_filters;

SET FOREIGN_KEY_CHECKS = 1;
