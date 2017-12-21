CREATE TABLE IF NOT EXISTS user (
  user_id  INT PRIMARY KEY AUTO_INCREMENT,

  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL
);
CREATE INDEX IF NOT EXISTS idx_username
  ON user (username);

CREATE TABLE IF NOT EXISTS contest (
  contest_id   INT PRIMARY KEY AUTO_INCREMENT,

  contest_name VARCHAR(255) NOT NULL,

  start_time   DATETIME     NOT NULL,
  finish_time  DATETIME     NOT NULL,

  problems     VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS submission (
  submission_id          INT PRIMARY KEY AUTO_INCREMENT,

  author_id              INT          NOT NULL,
  contest_id             INT          NOT NULL,
  problem_id             VARCHAR(255) NOT NULL,

  pretests_only          BOOLEAN      NOT NULL,
  creation_time          DATETIME     NOT NULL,
  language               INT          NOT NULL,
  solution               BLOB         NOT NULL,

  external_submission_id VARCHAR(255) NOT NULL,

  FOREIGN KEY (author_id) REFERENCES user(user_id),
  FOREIGN KEY (contest_id) REFERENCES contest(contest_id)
);
CREATE INDEX IF NOT EXISTS idx_contest_author
  ON submission (contest_id, author_id);