DROP TABLE IF EXISTS configuration;
CREATE TABLE configuration (
  twitter_callback_url VARCHAR(255) NOT NULL,
  twitter_consumer_key VARCHAR(64) NOT NULL,
  twitter_consumer_secret VARCHAR(64) NOT NULL
);

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  user_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  twitter_id INTEGER NOT NULL,
  oauth_token VARCHAR(64) NOT NULL,
  oauth_token_secret VARCHAR(64) NOT NULL
);

DROP TABLE IF EXISTS groups;
CREATE TABLE groups (
  group_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  user_id INTEGER NOT NULL,
  group_name VARCHAR(255) DEFAULT NULL,
  description VARCHAR(255) NOT NULL,
  enabled bool DEFAULT FALSE,
  CONSTRAINT FK_USERS_USERID_GROUPS_USERID FOREIGN KEY (user_id) REFERENCES users (user_id)
);

DROP TABLE IF EXISTS tweets;
CREATE TABLE tweets (
  tweet_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  user_id INTEGER NOT NULL,
  group_id INTEGER DEFAULT NULL,
  scheduled_date VARCHAR(20) NOT NULL,
  tweet VARCHAR(140) NOT NULL,
  scheduled bool DEFAULT FALSE,
  tweeted bool DEFAULT FALSE,
  img_url VARCHAR(255) DEFAULT NULL,
  longitude DOUBLE DEFAULT NULL,
  latitude DOUBLE DEFAULT NULL,
  CONSTRAINT FK_USERS_USERID_TWEETS_USERID FOREIGN KEY (user_id) REFERENCES users (user_id)
);

DROP TABLE IF EXISTS wikipedia;
CREATE TABLE wikipedia (
  user_id INTEGER NOT NULL,
  url VARCHAR(255) NOT NULL,
  UNIQUE (user_id, url),
  CONSTRAINT FK_USERS_USERID_WIKIPEDIA_USERID FOREIGN KEY (user_id) REFERENCES users(user_id)
);