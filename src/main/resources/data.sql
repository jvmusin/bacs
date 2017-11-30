INSERT INTO user(user_id, username, password) VALUES (1, 'tourist', '$2a$10$1/dMsM7IGrxwFSf/bpQP0.JzRORVvt7pY8A6jpbzE26CiFdaTSn/K');
INSERT INTO user(user_id, username, password) VALUES (2, 'Petr'   , '$2a$10$1/dMsM7IGrxwFSf/bpQP0.JzRORVvt7pY8A6jpbzE26CiFdaTSn/K');
INSERT INTO user(user_id, username, password) VALUES (3, 'Musin'  , '$2a$10$1/dMsM7IGrxwFSf/bpQP0.JzRORVvt7pY8A6jpbzE26CiFdaTSn/K');

INSERT INTO problem(problem_id) VALUES (1);
INSERT INTO problem(problem_id) VALUES (2);
INSERT INTO problem(problem_id) VALUES (3);
INSERT INTO problem(problem_id) VALUES (4);
INSERT INTO problem(problem_id) VALUES (5);
INSERT INTO problem(problem_id) VALUES (20004);
INSERT INTO problem(problem_id) VALUES (20005);
INSERT INTO problem(problem_id) VALUES (20006);
INSERT INTO problem(problem_id) VALUES (20008);

INSERT INTO contest(contest_id, contest_name, start_time, finish_time) VALUES (1, 'Main contest', '2017-01-02', '2018-03-04');

INSERT INTO contest_problems(contest_id, problem_id, `order`) VALUES (1, 1, 0);
INSERT INTO contest_problems(contest_id, problem_id, `order`) VALUES (1, 2, 1);
INSERT INTO contest_problems(contest_id, problem_id, `order`) VALUES (1, 3, 2);
INSERT INTO contest_problems(contest_id, problem_id, `order`) VALUES (1, 4, 3);
INSERT INTO contest_problems(contest_id, problem_id, `order`) VALUES (1, 5, 4);
INSERT INTO contest_problems(contest_id, problem_id, `order`) VALUES (1, 20004, 5);
INSERT INTO contest_problems(contest_id, problem_id, `order`) VALUES (1, 20005, 6);
INSERT INTO contest_problems(contest_id, problem_id, `order`) VALUES (1, 20006, 7);
INSERT INTO contest_problems(contest_id, problem_id, `order`) VALUES (1, 20008, 8);