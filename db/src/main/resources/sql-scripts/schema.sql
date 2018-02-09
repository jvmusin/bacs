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

CREATE TYPE VERDICT AS ENUM (
  'SCHEDULED',

  'ACCEPTED',
  'WRONG_ANSWER',
  'PRESENTATION_ERROR',
  'QUERIES_LIMIT_EXCEEDED',
  'INCORRECT_REQUEST',
  'INSUFFICIENT_DATA',
  'EXCESS_DATA',
  'OUTPUT_LIMIT_EXCEEDED',
  'TERMINATION_REAL_TIME_LIMIT_EXCEEDED',
  'ABNORMAL_EXIT',
  'MEMORY_LIMIT_EXCEEDED',
  'TIME_LIMIT_EXCEEDED',
  'REAL_TIME_LIMIT_EXCEEDED',
  'TERMINATED_BY_SYSTEM',
  'CUSTOM_FAILURE',
  'FAIL_TEST',
  'FAILED',
  'SKIPPED',

  'COMPILE_ERROR',
  'PENDING',

  'SERVER_ERROR'
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
  verdict                VERDICT      NOT NULL,
  tests_passed           INTEGER      NULL,
  time_used_millis       INTEGER      NULL,
  memory_used_bytes      INTEGER      NULL
);