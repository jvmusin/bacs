INSERT INTO user(user_id, username, password, authorities) VALUES (1, 'Musin', '$2a$10$1/dMsM7IGrxwFSf/bpQP0.JzRORVvt7pY8A6jpbzE26CiFdaTSn/K', 'ROLE_USER,ROLE_ADMIN');


INSERT INTO problem(problem_id, name, statement_url, time_limit_millis, memory_limit_bytes) VALUES ('FAKE@1',  '', '', 0, 0);
INSERT INTO problem(problem_id, name, statement_url, time_limit_millis, memory_limit_bytes) VALUES ('FAKE@2',  '', '', 0, 0);
INSERT INTO problem(problem_id, name, statement_url, time_limit_millis, memory_limit_bytes) VALUES ('FAKE@5',  '', '', 0, 0);
INSERT INTO problem(problem_id, name, statement_url, time_limit_millis, memory_limit_bytes) VALUES ('FAKE@10', '', '', 0, 0);
INSERT INTO problem(problem_id, name, statement_url, time_limit_millis, memory_limit_bytes) VALUES ('FAKE@11', '', '', 0, 0);

INSERT INTO problem(problem_id, name, statement_url, time_limit_millis, memory_limit_bytes) VALUES ('SYBON@1', '', '', 0, 0);


INSERT INTO contest(contest_id, name, start_time, finish_time) VALUES (1, 'Running empty contest', '2017-01-02 23:01', '2030-03-04 23:02');
INSERT INTO contest(contest_id, name, start_time, finish_time) VALUES (2, 'Future contest',        '2050-01-01 09:00', '2050-01-02 13:00');
INSERT INTO contest(contest_id, name, start_time, finish_time) VALUES (3, 'Previous contest',      '2017-01-02 12:30', '2017-02-02 13:30');
INSERT INTO contest(contest_id, name, start_time, finish_time) VALUES (4, 'Running fake contest',  '2017-12-31 12:00', '2047-04-23 17:30');
INSERT INTO contest(contest_id, name, start_time, finish_time) VALUES (5, 'Running sybon contest', '2017-12-31 12:00', '2047-04-23 17:30');


INSERT INTO contest_problem(contest_problem_id, contest_id, problem_id, problem_index) VALUES (1, 2, 'FAKE@1' , 'A');

INSERT INTO contest_problem(contest_problem_id, contest_id, problem_id, problem_index) VALUES (2, 3, 'FAKE@1' , '123');

INSERT INTO contest_problem(contest_problem_id, contest_id, problem_id, problem_index) VALUES (3, 4, 'FAKE@1',  'A');
INSERT INTO contest_problem(contest_problem_id, contest_id, problem_id, problem_index) VALUES (4, 4, 'FAKE@2',  'B');
INSERT INTO contest_problem(contest_problem_id, contest_id, problem_id, problem_index) VALUES (5, 4, 'FAKE@5',  'CQ');
INSERT INTO contest_problem(contest_problem_id, contest_id, problem_id, problem_index) VALUES (6, 4, 'FAKE@10', 'DW');
INSERT INTO contest_problem(contest_problem_id, contest_id, problem_id, problem_index) VALUES (7, 4, 'FAKE@11', 'EE');

INSERT INTO contest_problem(contest_problem_id, contest_id, problem_id, problem_index) VALUES (8, 5, 'SYBON@1', 'XXX');