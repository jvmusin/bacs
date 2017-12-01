INSERT INTO user(user_id, username, password) VALUES (1, 'tourist', '$2a$10$1/dMsM7IGrxwFSf/bpQP0.JzRORVvt7pY8A6jpbzE26CiFdaTSn/K');
INSERT INTO user(user_id, username, password) VALUES (2, 'Petr'   , '$2a$10$1/dMsM7IGrxwFSf/bpQP0.JzRORVvt7pY8A6jpbzE26CiFdaTSn/K');
INSERT INTO user(user_id, username, password) VALUES (3, 'Musin'  , '$2a$10$1/dMsM7IGrxwFSf/bpQP0.JzRORVvt7pY8A6jpbzE26CiFdaTSn/K');

INSERT INTO problem(problem_id) VALUES ('SYBON@1');
INSERT INTO problem(problem_id) VALUES ('SYBON@2');
INSERT INTO problem(problem_id) VALUES ('SYBON@3');
INSERT INTO problem(problem_id) VALUES ('SYBON@4');
INSERT INTO problem(problem_id) VALUES ('SYBON@5');
INSERT INTO problem(problem_id) VALUES ('SYBON@20004');
INSERT INTO problem(problem_id) VALUES ('SYBON@20005');
INSERT INTO problem(problem_id) VALUES ('SYBON@20006');
INSERT INTO problem(problem_id) VALUES ('SYBON@20008');

INSERT INTO contest(contest_id, contest_name, start_time, finish_time) VALUES (1, 'Main contest', '2017-01-02', '2018-03-04');
INSERT INTO contest(contest_id, contest_name, start_time, finish_time) VALUES (2, 'Main2 contest', '2017-01-02', '2018-03-04');

INSERT INTO contest_problems(contest_id, problem_id, `order`) VALUES (1, 'SYBON@1', 0);
INSERT INTO contest_problems(contest_id, problem_id, `order`) VALUES (1, 'SYBON@2', 1);
INSERT INTO contest_problems(contest_id, problem_id, `order`) VALUES (1, 'SYBON@3', 2);
INSERT INTO contest_problems(contest_id, problem_id, `order`) VALUES (1, 'SYBON@4', 3);
INSERT INTO contest_problems(contest_id, problem_id, `order`) VALUES (1, 'SYBON@5', 4);
INSERT INTO contest_problems(contest_id, problem_id, `order`) VALUES (1, 'SYBON@20004', 5);
INSERT INTO contest_problems(contest_id, problem_id, `order`) VALUES (1, 'SYBON@20005', 6);
INSERT INTO contest_problems(contest_id, problem_id, `order`) VALUES (1, 'SYBON@20006', 7);
INSERT INTO contest_problems(contest_id, problem_id, `order`) VALUES (1, 'SYBON@20008', 8);

INSERT INTO contest_problems(contest_id, problem_id, `order`) VALUES (2, 'SYBON@20005', 0);