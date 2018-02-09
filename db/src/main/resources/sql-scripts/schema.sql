CREATE TABLE IF NOT EXISTS "user" (
  user_id  SERIAL PRIMARY KEY,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  roles    VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS problem (
  problem_id         VARCHAR(255) PRIMARY KEY,
  name               VARCHAR(255) NOT NULL,
  statement_url      TEXT         NOT NULL,
  time_limit_millis  INTEGER      NOT NULL,
  memory_limit_bytes INTEGER      NOT NULL
);

CREATE TABLE IF NOT EXISTS contest (
  contest_id  SERIAL       NOT NULL      PRIMARY KEY,
  name        VARCHAR(255) NOT NULL,
  start_time  TIMESTAMP    NOT NULL,
  finish_time TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS contest_problem (
  contest_problem_id VARCHAR(255) PRIMARY KEY,
  contest_id         INTEGER      NOT NULL,
  problem_id         VARCHAR(255) NOT NULL,
  problem_index      VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS submission (
  --   Submission
  submission_id          SERIAL       NOT NULL      PRIMARY KEY,
  author_id              INTEGER      NOT NULL,
  contest_problem_id     VARCHAR(255) NOT NULL,
  pretests_only          BOOLEAN      NOT NULL,
  created                TIMESTAMP    NOT NULL,
  language               VARCHAR(255) NOT NULL,
  solution               TEXT         NOT NULL,
  external_submission_id INTEGER      NULL,

  --   SubmissionResult
  build_info             TEXT         NULL,
  verdict                VARCHAR(255) NOT NULL,
  tests_passed           INTEGER      NULL,
  time_used_millis       INTEGER      NULL,
  memory_used_bytes      INTEGER      NULL
);