--
-- Database: 'naviiDB'
--

-- --------------------------------------------------------

--
-- Table structure for table 'Activities'
--

CREATE TABLE 'Activities' (
  'ActivityID' int(11) NOT NULL,
  'StartTime' datetime DEFAULT NULL,
  'EndTime' datetime DEFAULT NULL,
  'ItineraryID' int(11) DEFAULT NULL,
  'AttractionID' int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table 'Atmospheres'
--

CREATE TABLE 'Atmospheres' (
  'AtmosphereID' int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table 'Attractions'
--

CREATE TABLE 'Attractions' (
  'AttractionID' int(11) NOT NULL,
  'Name' varchar(256) DEFAULT NULL,
  'Location' varchar(256) DEFAULT NULL,
  'PhotoURI' varchar(256) DEFAULT NULL,
  'BlurbURI' varchar(256) DEFAULT NULL,
  'Price' decimal(10,2) DEFAULT NULL,
  'DURATION' int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table 'Cuisines'
--

CREATE TABLE 'Cuisines' (
  'CuisineID' int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table 'Diets'
--

CREATE TABLE 'Diets' (
  'DietID' int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table 'Itineraries'
--

CREATE TABLE 'Itineraries' (
  'ItineraryID' int(11) NOT NULL,
  'TotalCost' decimal(10,2) DEFAULT NULL,
  'StartDate' datetime DEFAULT NULL,
  'EndDate' datetime DEFAULT NULL,
  'Tags' varchar(256) DEFAULT NULL,
  'Description' varchar(256) DEFAULT NULL,
  'AuthorID' int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table 'Itineraries'
--

INSERT INTO 'Itineraries' ('ItineraryID', 'TotalCost', 'StartDate', 'EndDate', 'Tags', 'Description', 'AuthorID') VALUES
(1, '300.00', '2015-09-23 00:00:00', '2015-09-30 00:00:00', 'FUN CLUB', 'HITLERS CAFE', 1),
(2, '100.00', '2015-10-05 00:00:00', '2015-10-06 00:00:00', 'DEMO', 'DEMO', 1);

-- --------------------------------------------------------

--
-- Table structure for table 'PointOfInterests'
--

CREATE TABLE 'PointOfInterests' (
  'PointOfInterestsID' int(11) NOT NULL,
  'TypeID' int(11) DEFAULT NULL,
  'ActivityLevel' int(11) DEFAULT NULL,
  'CulturalAuthenticity' tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table 'Restaurants'
--

CREATE TABLE 'Restaurants' (
  'RestaurantID' int(11) NOT NULL,
  'CuisineID' int(11) DEFAULT NULL,
  'RankOfLife' int(11) DEFAULT NULL,
  'AtmosphereID' int(11) DEFAULT NULL,
  'DietID' int(11) DEFAULT NULL,
  'AttractionID' int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table 'Types'
--

CREATE TABLE 'Types' (
  'TypeID' int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table 'Users'
--

CREATE TABLE 'Users' (
  'UserID' int(11) NOT NULL,
  'Username' varchar(16) NOT NULL,
  'SaltedPassword' char(64) NOT NULL,
  'Salt' char(64) NOT NULL,
  'isFacebook' tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table 'Users'
--

INSERT INTO 'Users' ('UserID', 'Username', 'SaltedPassword', 'Salt', 'isFacebook') VALUES
(1, 'naviiCreator', 'DefaultPassword', 'DefaultPassword', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table 'Activities'
--
ALTER TABLE 'Activities'
  ADD PRIMARY KEY ('ActivityID'),
  ADD KEY 'AttractionID' ('AttractionID'),
  ADD KEY 'ItineraryID' ('ItineraryID');

--
-- Indexes for table 'Atmospheres'
--
ALTER TABLE 'Atmospheres'
  ADD PRIMARY KEY ('AtmosphereID');

--
-- Indexes for table 'Attractions'
--
ALTER TABLE 'Attractions'
  ADD PRIMARY KEY ('AttractionID');

--
-- Indexes for table 'Cuisines'
--
ALTER TABLE 'Cuisines'
  ADD PRIMARY KEY ('CuisineID');

--
-- Indexes for table 'Diets'
--
ALTER TABLE 'Diets'
  ADD PRIMARY KEY ('DietID');

--
-- Indexes for table 'Itineraries'
--
ALTER TABLE 'Itineraries'
  ADD PRIMARY KEY ('ItineraryID'),
  ADD KEY 'AuthorID' ('AuthorID');

--
-- Indexes for table 'PointOfInterests'
--
ALTER TABLE 'PointOfInterests'
  ADD PRIMARY KEY ('PointOfInterestsID'),
  ADD KEY 'TypeID' ('TypeID');

--
-- Indexes for table 'Restaurants'
--
ALTER TABLE 'Restaurants'
  ADD PRIMARY KEY ('RestaurantID'),
  ADD KEY 'AttractionID' ('AttractionID'),
  ADD KEY 'CuisineID' ('CuisineID'),
  ADD KEY 'AtmosphereID' ('AtmosphereID'),
  ADD KEY 'DietID' ('DietID');

--
-- Indexes for table 'Types'
--
ALTER TABLE 'Types'
  ADD PRIMARY KEY ('TypeID');

--
-- Indexes for table 'Users'
--
ALTER TABLE 'Users'
  ADD PRIMARY KEY ('UserID'),
  ADD UNIQUE KEY 'Username' ('Username');

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table 'Activities'
--
ALTER TABLE 'Activities'
  MODIFY 'ActivityID' int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table 'Itineraries'
--
ALTER TABLE 'Itineraries'
  MODIFY 'ItineraryID' int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table 'Users'
--
ALTER TABLE 'Users'
  MODIFY 'UserID' int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- Constraints for dumped tables
--

--
-- Constraints for table 'Activities'
--
ALTER TABLE 'Activities'
  ADD CONSTRAINT 'Activities_ibfk_1' FOREIGN KEY ('AttractionID') REFERENCES 'Attractions' ('AttractionID') ON DELETE CASCADE,
  ADD CONSTRAINT 'Activities_ibfk_2' FOREIGN KEY ('ItineraryID') REFERENCES 'Itineraries' ('ItineraryID') ON DELETE SET NULL;

--
-- Constraints for table 'Itineraries'
--
ALTER TABLE 'Itineraries'
  ADD CONSTRAINT 'Itineraries_ibfk_1' FOREIGN KEY ('AuthorID') REFERENCES 'Users' ('UserID') ON DELETE SET NULL;

--
-- Constraints for table 'PointOfInterests'
--
ALTER TABLE 'PointOfInterests'
  ADD CONSTRAINT 'PointOfInterests_ibfk_1' FOREIGN KEY ('TypeID') REFERENCES 'Types' ('TypeID') ON DELETE SET NULL;

--
-- Constraints for table 'Restaurants'
--
ALTER TABLE 'Restaurants'
  ADD CONSTRAINT 'Restaurants_ibfk_1' FOREIGN KEY ('AttractionID') REFERENCES 'Attractions' ('AttractionID') ON DELETE CASCADE,
  ADD CONSTRAINT 'Restaurants_ibfk_2' FOREIGN KEY ('CuisineID') REFERENCES 'Cuisines' ('CuisineID') ON DELETE CASCADE,
  ADD CONSTRAINT 'Restaurants_ibfk_3' FOREIGN KEY ('AtmosphereID') REFERENCES 'Atmospheres' ('AtmosphereID') ON DELETE CASCADE,
  ADD CONSTRAINT 'Restaurants_ibfk_4' FOREIGN KEY ('DietID') REFERENCES 'Diets' ('DietID') ON DELETE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
