CREATE TABLE IF NOT EXISTS user(
  user_id INT PRIMARY KEY AUTO_INCREMENT,

  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS problem(
  problem_id INT PRIMARY KEY,

  problem_name VARCHAR(255),
  statement_url VARCHAR(255),

  pretest_count INT,
  tests_count INT,
  time_limit_millis INT,
  memory_limit_bytes INT
);

CREATE TABLE IF NOT EXISTS contest(
  contest_id INT PRIMARY KEY AUTO_INCREMENT,

  contest_name VARCHAR(255) NOT NULL,

  start_time DATETIME NULL,
  finish_time DATETIME NULL
);

CREATE TABLE IF NOT EXISTS submission(
  submission_id INT PRIMARY KEY AUTO_INCREMENT,

  author_id INT NOT NULL,
  contest_id INT NOT NULL,
  problem_id INT NOT NULL,

  creation_time DATETIME NOT NULL,
  language VARCHAR(255) NOT NULL,
  solution BLOB NOT NULL,

  verdict VARCHAR(255) NOT NULL,
  first_failed_test INT NULL,
  time_consumed_millis DOUBLE NULL,
  memory_consumed_bytes DOUBLE NULL
);

CREATE TABLE IF NOT EXISTS contest_problems(
  contest_id INT NOT NULL,
  problem_id INT NOT NULL,
  `order` INT NOT NULL,

  PRIMARY KEY (contest_id, problem_id, `order`)
);