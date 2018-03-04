INSERT INTO "user"(user_id, username, password, roles) VALUES (1, 'Musin', '$2a$10$1/dMsM7IGrxwFSf/bpQP0.JzRORVvt7pY8A6jpbzE26CiFdaTSn/K', '{ROLE_USER, ROLE_ADMIN}');

INSERT INTO problem(resource_name, resource_problem_id, name, statement_url, time_limit_millis, memory_limit_bytes) VALUES ('FAKE', '1',  '', '', 1, 1);
INSERT INTO problem(resource_name, resource_problem_id, name, statement_url, time_limit_millis, memory_limit_bytes) VALUES ('FAKE', '2',  '', '', 1, 1);
INSERT INTO problem(resource_name, resource_problem_id, name, statement_url, time_limit_millis, memory_limit_bytes) VALUES ('FAKE', '5',  '', '', 1, 1);
INSERT INTO problem(resource_name, resource_problem_id, name, statement_url, time_limit_millis, memory_limit_bytes) VALUES ('FAKE', '10', '', '', 1, 1);
INSERT INTO problem(resource_name, resource_problem_id, name, statement_url, time_limit_millis, memory_limit_bytes) VALUES ('FAKE', '11', '', '', 1, 1);

INSERT INTO contest(contest_id, name, start_time, finish_time) VALUES (1, 'Running empty contest', '2017-01-02 23:01', '2030-03-04 23:02');
INSERT INTO contest(contest_id, name, start_time, finish_time) VALUES (2, 'Future contest',        '2050-01-01 09:00', '2050-01-02 13:00');
INSERT INTO contest(contest_id, name, start_time, finish_time) VALUES (3, 'Ended contest',         '2017-01-02 12:30', '2017-02-02 13:30');
INSERT INTO contest(contest_id, name, start_time, finish_time) VALUES (4, 'Fake contest',          '2017-12-31 12:00', '2047-04-23 17:30');


INSERT INTO contest_problem(contest_id, problem_index, resource_name, resource_problem_id) VALUES (2, 'A',   'FAKE',  '1');

INSERT INTO contest_problem(contest_id, problem_index, resource_name, resource_problem_id) VALUES (3, '123', 'FAKE',  '1');

INSERT INTO contest_problem(contest_id, problem_index, resource_name, resource_problem_id) VALUES (4, 'A',   'FAKE',  '1');
INSERT INTO contest_problem(contest_id, problem_index, resource_name, resource_problem_id) VALUES (4, 'B',   'FAKE',  '2');
INSERT INTO contest_problem(contest_id, problem_index, resource_name, resource_problem_id) VALUES (4, 'CQ',  'FAKE',  '5');
INSERT INTO contest_problem(contest_id, problem_index, resource_name, resource_problem_id) VALUES (4, 'DW',  'FAKE',  '10');
INSERT INTO contest_problem(contest_id, problem_index, resource_name, resource_problem_id) VALUES (4, 'EE',  'FAKE',  '11');