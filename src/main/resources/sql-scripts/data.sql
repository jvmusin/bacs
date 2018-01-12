INSERT INTO user(user_id, username, password, authorities) VALUES (1, 'Musin'   ,'$2a$10$1/dMsM7IGrxwFSf/bpQP0.JzRORVvt7pY8A6jpbzE26CiFdaTSn/K', 'ROLE_USER,ROLE_ADMIN');
INSERT INTO user(user_id, username, password, authorities) VALUES (2, 'Petr'    ,'$2a$10$1/dMsM7IGrxwFSf/bpQP0.JzRORVvt7pY8A6jpbzE26CiFdaTSn/K', 'ROLE_USER');
INSERT INTO user(user_id, username, password, authorities) VALUES (3, 'tourist' ,'$2a$10$1/dMsM7IGrxwFSf/bpQP0.JzRORVvt7pY8A6jpbzE26CiFdaTSn/K', 'ROLE_USER');


INSERT INTO problem(problem_id, problem_name, statement_url, time_limit_millis, memory_limit_bytes) VALUES ('SYBON@1' , '', '', 0, 0);
INSERT INTO problem(problem_id, problem_name, statement_url, time_limit_millis, memory_limit_bytes) VALUES ('SYBON@11', '', '', 0, 0);
INSERT INTO problem(problem_id, problem_name, statement_url, time_limit_millis, memory_limit_bytes) VALUES ('SYBON@12', '', '', 0, 0);
INSERT INTO problem(problem_id, problem_name, statement_url, time_limit_millis, memory_limit_bytes) VALUES ('SYBON@13', '', '', 0, 0);
INSERT INTO problem(problem_id, problem_name, statement_url, time_limit_millis, memory_limit_bytes) VALUES ('SYBON@14', '', '', 0, 0);


INSERT INTO contest(contest_id, contest_name, start_time, finish_time) VALUES (1, 'Running contest'      , '2017-01-02', '2018-03-04');
INSERT INTO contest(contest_id, contest_name, start_time, finish_time) VALUES (2, 'Previous contest'     , '2017-01-02', '2017-02-02');
INSERT INTO contest(contest_id, contest_name, start_time, finish_time) VALUES (3, 'Future contest'       , '2018-01-01', '2018-02-02');
INSERT INTO contest(contest_id, contest_name, start_time, finish_time) VALUES (4, 'Running empty contest', '2017-01-02', '2018-03-04');


INSERT INTO contest_problem(contest_problem_id, contest_id, problem_id, problem_index) VALUES (1, 1, 'SYBON@1' , 'A');
INSERT INTO contest_problem(contest_problem_id, contest_id, problem_id, problem_index) VALUES (2, 1, 'SYBON@11', 'B');
INSERT INTO contest_problem(contest_problem_id, contest_id, problem_id, problem_index) VALUES (3, 1, 'SYBON@12', 'C');
INSERT INTO contest_problem(contest_problem_id, contest_id, problem_id, problem_index) VALUES (4, 1, 'SYBON@13', 'Z');
INSERT INTO contest_problem(contest_problem_id, contest_id, problem_id, problem_index) VALUES (5, 1, 'SYBON@14', 'T');
INSERT INTO contest_problem(contest_problem_id, contest_id, problem_id, problem_index) VALUES (6, 2, 'SYBON@1' , 'A');
INSERT INTO contest_problem(contest_problem_id, contest_id, problem_id, problem_index) VALUES (7, 3, 'SYBON@1' , 'A');