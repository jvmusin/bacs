INSERT INTO user(user_id, username, password) VALUES (1, 'tourist', '$2a$10$1/dMsM7IGrxwFSf/bpQP0.JzRORVvt7pY8A6jpbzE26CiFdaTSn/K');
INSERT INTO user(user_id, username, password) VALUES (2, 'Petr'   , '$2a$10$1/dMsM7IGrxwFSf/bpQP0.JzRORVvt7pY8A6jpbzE26CiFdaTSn/K');
INSERT INTO user(user_id, username, password) VALUES (3, 'Musin'  , '$2a$10$1/dMsM7IGrxwFSf/bpQP0.JzRORVvt7pY8A6jpbzE26CiFdaTSn/K');


-- INSERT INTO problem(problemId, problem_name, time_limit_millis, memory_limit_bytes) VALUES (1, 'A + B',   1.5 * 1000, 64.0  * 1024 * 1024);
-- INSERT INTO problem(problemId, problem_name, time_limit_millis, memory_limit_bytes) VALUES (2, 'Maze',    2.0 * 1000, 256.0 * 1024 * 1024);
-- INSERT INTO problem(problemId, problem_name, time_limit_millis, memory_limit_bytes) VALUES (3, 'Theatre', 2.0 * 1000, 128.0 * 1024 * 1024);
-- INSERT INTO problem(problemId, problem_name, time_limit_millis, memory_limit_bytes) VALUES (4, 'Bit++',   9.9 * 1000, 64.0  * 1024 * 1024);
-- INSERT INTO problem(problemId, problem_name, time_limit_millis, memory_limit_bytes) VALUES (5, 'Team',    2.0 * 1000, 128.0 * 1024 * 1024);
-- INSERT INTO problem(problemId, problem_name, time_limit_millis, memory_limit_bytes) VALUES (6, 'Taxi',    2.0 * 1000, 256.0 * 1024 * 1024);


INSERT INTO contest(contest_id, contest_name, start_time, finish_time) VALUES (1, 'Happy New Year',  '2017-01-01 00:01:00', '2017-01-01 05:01:00');
INSERT INTO contest(contest_id, contest_name, start_time, finish_time) VALUES (2, 'Kotlin Love Day', '2017-04-23 12:00:00', '2017-04-23 15:00:00');


-- INSERT INTO contest_problems(contest_id, problemId, `order`) VALUES (1, 1, 0);
-- INSERT INTO contest_problems(contest_id, problemId, `order`) VALUES (1, 2, 1);
--
-- INSERT INTO contest_problems(contest_id, problemId, `order`) VALUES (2, 3, 0);
-- INSERT INTO contest_problems(contest_id, problemId, `order`) VALUES (2, 4, 1);
-- INSERT INTO contest_problems(contest_id, problemId, `order`) VALUES (2, 5, 2);
-- INSERT INTO contest_problems(contest_id, problemId, `order`) VALUES (2, 6, 3);


INSERT INTO submission(submission_id, author_id, contest_id, problem_id, creation_time, language, solution, verdict, first_failed_test, time_consumed_millis, memory_consumed_bytes) VALUES (1, 1, 1, 1,  '2017-01-01 00:06:00', 'CPP',    '...C++ code...',                'Accepted',          null, 0.25 * 1000, 5.0   * 1024 * 1024);
INSERT INTO submission(submission_id, author_id, contest_id, problem_id, creation_time, language, solution, verdict, first_failed_test, time_consumed_millis, memory_consumed_bytes) VALUES (2, 3, 1, 1,  '2017-01-01 00:06:21', 'Kotlin', '...Kotlin code...',             'Accepted',          null, 1.2  * 1000, 21.0  * 1024 * 1024);
INSERT INTO submission(submission_id, author_id, contest_id, problem_id, creation_time, language, solution, verdict, first_failed_test, time_consumed_millis, memory_consumed_bytes) VALUES (3, 2, 1, 1,  '2017-01-01 00:08:00', 'Kotlin', '...Java code...',               'CompileError',      null, null,        null               );
INSERT INTO submission(submission_id, author_id, contest_id, problem_id, creation_time, language, solution, verdict, first_failed_test, time_consumed_millis, memory_consumed_bytes) VALUES (4, 2, 1, 1,  '2017-01-01 00:08:13', 'Java',   '...Java code...',               'Accepted',          null, 1.35 * 1000, 23.3  * 1024 * 1024);
INSERT INTO submission(submission_id, author_id, contest_id, problem_id, creation_time, language, solution, verdict, first_failed_test, time_consumed_millis, memory_consumed_bytes) VALUES (5, 3, 1, 2,  '2017-01-01 02:42:00', 'Java',   '...Slow Java code...',          'TimeLimitExceeded', 4,    2.0  * 1000, 201.0 * 1024 * 1024);
INSERT INTO submission(submission_id, author_id, contest_id, problem_id, creation_time, language, solution, verdict, first_failed_test, time_consumed_millis, memory_consumed_bytes) VALUES (6, 3, 1, 2,  '2017-01-01 02:57:00', 'Java',   '...Slow Java code...',          'TimeLimitExceeded', 6,    2.0  * 1000, 72.0  * 1024 * 1024);
INSERT INTO submission(submission_id, author_id, contest_id, problem_id, creation_time, language, solution, verdict, first_failed_test, time_consumed_millis, memory_consumed_bytes) VALUES (7, 2, 1, 2,  '2017-01-01 04:21:00', 'Java',   '...Fast Java code...',          'Accepted',          null, 0.89 * 1000, 102.0 * 1024 * 1024);
INSERT INTO submission(submission_id, author_id, contest_id, problem_id, creation_time, language, solution, verdict, first_failed_test, time_consumed_millis, memory_consumed_bytes) VALUES (8, 1, 1, 2,  '2017-01-01 04:24:11', 'CPP',    '...Fast C++ code...',           'Accepted',          null, 0.5  * 1000, 55.0  * 1024 * 1024);
INSERT INTO submission(submission_id, author_id, contest_id, problem_id, creation_time, language, solution, verdict, first_failed_test, time_consumed_millis, memory_consumed_bytes) VALUES (9, 3, 1, 2,  '2017-01-01 04:51:45', 'Python', '...Fast Python code...',        'Accepted',          null, 0.97 * 1000, 155.0 * 1024 * 1024);

INSERT INTO submission(submission_id, author_id, contest_id, problem_id, creation_time, language, solution, verdict, first_failed_test, time_consumed_millis, memory_consumed_bytes) VALUES (10, 3, 2, 3, '2017-04-23 12:50:45', 'Kotlin', '...Kotlin code...',             'Accepted',          null, 1.3  * 1000, 30.0  * 1024 * 1024);
INSERT INTO submission(submission_id, author_id, contest_id, problem_id, creation_time, language, solution, verdict, first_failed_test, time_consumed_millis, memory_consumed_bytes) VALUES (11, 3, 2, 4, '2017-04-23 13:20:01', 'Kotlin', '...Kotlin code...',             'Accepted',          null, 8.7  * 1000, 56.0  * 1024 * 1024);
INSERT INTO submission(submission_id, author_id, contest_id, problem_id, creation_time, language, solution, verdict, first_failed_test, time_consumed_millis, memory_consumed_bytes) VALUES (12, 3, 2, 5, '2017-04-23 14:00:30', 'Kotlin', '...Kotlin code...',             'Accepted',          null, 0.3  * 1000, 120.0 * 1024 * 1024);
INSERT INTO submission(submission_id, author_id, contest_id, problem_id, creation_time, language, solution, verdict, first_failed_test, time_consumed_millis, memory_consumed_bytes) VALUES (13, 3, 2, 6, '2017-04-23 14:31:00', 'Kotlin', '...Unoptimized Kotlin code...', 'Accepted',          911,  0.19 * 1000, 256.0 * 1024 * 1024);
INSERT INTO submission(submission_id, author_id, contest_id, problem_id, creation_time, language, solution, verdict, first_failed_test, time_consumed_millis, memory_consumed_bytes) VALUES (14, 3, 2, 6, '2017-04-23 14:36:00', 'Kotlin', '...Optimized Kotlin code...',   'Accepted',          null, 0.21 * 1000, 241.1 * 1024 * 1024);