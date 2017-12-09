INSERT INTO user(user_id, username, password) VALUES (1, 'tourist', '$2a$10$1/dMsM7IGrxwFSf/bpQP0.JzRORVvt7pY8A6jpbzE26CiFdaTSn/K');
INSERT INTO user(user_id, username, password) VALUES (2, 'Petr'   , '$2a$10$1/dMsM7IGrxwFSf/bpQP0.JzRORVvt7pY8A6jpbzE26CiFdaTSn/K');
INSERT INTO user(user_id, username, password) VALUES (3, 'Musin'  , '$2a$10$1/dMsM7IGrxwFSf/bpQP0.JzRORVvt7pY8A6jpbzE26CiFdaTSn/K');

INSERT INTO contest(contest_id, contest_name, start_time, finish_time, problems) VALUES (1, 'Running contest', '2017-01-02', '2018-03-04',
'SYBON@1,SYBON@2,SYBON@3,SYBON@4,SYBON@5,SYBON@20004,SYBON@20005,SYBON@20006,SYBON@20008');
INSERT INTO contest(contest_id, contest_name, start_time, finish_time, problems) VALUES (2, 'Previous contest', '2017-01-02', '2017-02-02', 'SYBON@20005');
INSERT INTO contest(contest_id, contest_name, start_time, finish_time, problems) VALUES (3, 'Future contest', '2018-01-01', '2018-02-02', 'SYBON@20005');
INSERT INTO contest(contest_id, contest_name, start_time, finish_time, problems) VALUES (4, 'Running empty contest', '2017-01-02', '2018-03-04', '');