CREATE TABLE IF NOT EXISTS user (
  user_id  INT PRIMARY KEY AUTO_INCREMENT,

  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,

  roles    VARCHAR(255) NOT NULL
)
  CHARACTER SET 'utf8';

CREATE TABLE IF NOT EXISTS problem (
  problem_id         VARCHAR(255) PRIMARY KEY,

  name               VARCHAR(255) NOT NULL,
  statement_url      TEXT         NOT NULL,

  time_limit_millis  INT          NOT NULL,
  memory_limit_bytes INT          NOT NULL
)
  CHARACTER SET 'utf8';

CREATE TABLE IF NOT EXISTS contest (
  contest_id  INT PRIMARY KEY AUTO_INCREMENT,

  name        VARCHAR(255) NOT NULL,

  start_time  DATETIME     NOT NULL,
  finish_time DATETIME     NOT NULL
)
  CHARACTER SET 'utf8';

CREATE TABLE IF NOT EXISTS contest_problem (
  contest_problem_id VARCHAR(255) PRIMARY KEY,

  contest_id         INT          NOT NULL,
  problem_id         VARCHAR(255) NOT NULL,
  problem_index      CHAR(3)      NOT NULL
)
  CHARACTER SET 'utf8';

CREATE TABLE IF NOT EXISTS submission (
  # Submission

  submission_id          INT PRIMARY KEY AUTO_INCREMENT,

  author_id              INT          NOT NULL,
  contest_problem_id     VARCHAR(255) NOT NULL,

  pretests_only          BOOLEAN      NOT NULL,
  created                DATETIME     NOT NULL,
  language               VARCHAR(255) NOT NULL,
  solution               TEXT         NOT NULL,

  external_submission_id INT          NULL,

  # SubmissionResult

  build_info             BLOB         NULL,
  verdict                VARCHAR(255) NOT NULL,

  tests_passed           INT          NULL,
  time_used_millis       INT          NULL,
  memory_used_bytes      INT          NULL
)
  CHARACTER SET 'utf8';