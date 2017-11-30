CREATE TABLE IF NOT EXISTS user(
  user_id INT PRIMARY KEY AUTO_INCREMENT,

  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS problem(
  problem_id INT PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS contest(
  contest_id INT PRIMARY KEY AUTO_INCREMENT,

  contest_name VARCHAR(255) NOT NULL,

  start_time DATETIME NULL,
  finish_time DATETIME NULL
);

CREATE TABLE IF NOT EXISTS submission(
  submission_id INT PRIMARY KEY,

  author_id INT NOT NULL,
  contest_id INT NOT NULL,
  problem_id INT NOT NULL,

  creation_time DATETIME NOT NULL,
  language VARCHAR(255) NOT NULL,
  solution BLOB NOT NULL
);

CREATE TABLE IF NOT EXISTS contest_problems(
  contest_id INT NOT NULL,
  problem_id INT NOT NULL,
  `order` INT NOT NULL,

  PRIMARY KEY (contest_id, problem_id, `order`)
);

CREATE TABLE IF NOT EXISTS contest_submissions(
  contest_id INT NOT NULL,
  submission_id INT NOT NULL,

  PRIMARY KEY (contest_id, submission_id)
)