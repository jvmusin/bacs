CREATE TABLE "user" (
  user_id  SERIAL  NOT NULL PRIMARY KEY,
  username TEXT    NOT NULL UNIQUE,
  password TEXT    NOT NULL,
  roles    TEXT [] NOT NULL
);

CREATE TABLE user_personal_details (
  user_id           INT       NOT NULL PRIMARY KEY,

  email             TEXT      NULL,

  first_name        TEXT      NULL,
  middle_name        TEXT      NULL,
  last_name         TEXT      NULL,

  birth_date        TIMESTAMP NULL,
  registration_date TIMESTAMP NULL
);

CREATE TYPE TYPE_RESOURCE_NAME AS ENUM (
  'FAKE',
  'SYBON'
);

CREATE TABLE problem (
  resource_name       TYPE_RESOURCE_NAME NOT NULL,
  resource_problem_id TEXT               NOT NULL,

  name                TEXT               NOT NULL,
  statement_url       TEXT               NOT NULL,
  time_limit_millis   INTEGER            NOT NULL,
  memory_limit_bytes  INTEGER            NOT NULL,

  PRIMARY KEY (resource_name, resource_problem_id)
);

CREATE TABLE contest (
  contest_id  SERIAL    NOT NULL PRIMARY KEY,
  name        TEXT      NOT NULL,
  start_time  TIMESTAMP NOT NULL,
  finish_time TIMESTAMP NOT NULL
);

CREATE TABLE contest_problem (
  contest_id          INTEGER            NOT NULL,
  problem_index       TEXT               NOT NULL,
  resource_name       TYPE_RESOURCE_NAME NOT NULL,
  resource_problem_id TEXT               NOT NULL,
  PRIMARY KEY (contest_id, problem_index)
);

CREATE TYPE TYPE_VERDICT AS ENUM (
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

CREATE TABLE submission (
  --   Submission
  submission_id          SERIAL       NOT NULL PRIMARY KEY,
  author_id              INTEGER      NOT NULL,

  contest_id             INTEGER      NOT NULL,
  problem_index          TEXT         NOT NULL,

  pretests_only          BOOLEAN      NOT NULL,
  created                TIMESTAMP    NOT NULL,
  language               TEXT         NOT NULL,
  solution               TEXT         NOT NULL,
  external_submission_id INTEGER      NULL,

  --   SubmissionResult
  build_info             TEXT         NULL,
  verdict                TYPE_VERDICT NOT NULL,
  tests_passed           INTEGER      NULL,
  time_used_millis       INTEGER      NULL,
  memory_used_bytes      INTEGER      NULL
);