CREATE TABLE IF NOT EXISTS user (
  user_id     INT PRIMARY KEY AUTO_INCREMENT,

  username    VARCHAR(255) NOT NULL UNIQUE,
  password    VARCHAR(255) NOT NULL,

  authorities VARCHAR(255) NOT NULL
)
  CHARACTER SET 'utf8';


CREATE TABLE IF NOT EXISTS problem (
  problem_id         VARCHAR(255) PRIMARY KEY,

  problem_name       VARCHAR(255) NOT NULL,
  statement_url      TEXT         NOT NULL,

  time_limit_millis  INT          NOT NULL,
  memory_limit_bytes INT          NOT NULL
)
  CHARACTER SET 'utf8';

CREATE TABLE IF NOT EXISTS contest (
  contest_id   INT PRIMARY KEY AUTO_INCREMENT,

  contest_name VARCHAR(255) NOT NULL,

  start_time   DATETIME     NOT NULL,
  finish_time  DATETIME     NOT NULL
)
  CHARACTER SET 'utf8';

CREATE TABLE IF NOT EXISTS contest_problem (
  contest_problem_id INT PRIMARY KEY AUTO_INCREMENT,

  contest_id         INT          NOT NULL,
  problem_id         VARCHAR(255) NOT NULL,
  problem_index      CHAR(3)      NOT NULL,

  FOREIGN KEY (contest_id) REFERENCES contest (contest_id),
  FOREIGN KEY (problem_id) REFERENCES problem (problem_id)
)
  CHARACTER SET 'utf8';

CREATE TABLE IF NOT EXISTS submission (
  submission_id          INT PRIMARY KEY AUTO_INCREMENT,

  author_id              INT          NOT NULL,
  contest_problem_id     INT          NOT NULL,

  pretests_only          BOOLEAN      NOT NULL,
  created                DATETIME     NOT NULL,
  language               VARCHAR(255) NOT NULL,
  solution               BLOB         NOT NULL,

  external_submission_id VARCHAR(255) NULL,

  FOREIGN KEY (author_id) REFERENCES user (user_id),
  FOREIGN KEY (contest_problem_id) REFERENCES contest_problem (contest_problem_id)
)
  CHARACTER SET 'utf8';

CREATE TABLE IF NOT EXISTS submission_result (
  submission_result_id INT PRIMARY KEY AUTO_INCREMENT,

  submission_id        INT          NOT NULL,

  build_info           BLOB         NULL,
  verdict              VARCHAR(255) NOT NULL,

  tests_passed         INT          NULL,
  time_used_millis     INT          NULL,
  memory_used_bytes    INT          NULL,

  FOREIGN KEY (submission_id) REFERENCES submission (submission_id)
)
  CHARACTER SET 'utf8';