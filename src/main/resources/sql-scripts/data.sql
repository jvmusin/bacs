INSERT INTO user(user_id, username, password, authorities) VALUES (1, 'Musin'   ,'$2a$10$1/dMsM7IGrxwFSf/bpQP0.JzRORVvt7pY8A6jpbzE26CiFdaTSn/K', 'ROLE_USER,ROLE_ADMIN');
INSERT INTO user(user_id, username, password, authorities) VALUES (2, 'Petr'    ,'$2a$10$1/dMsM7IGrxwFSf/bpQP0.JzRORVvt7pY8A6jpbzE26CiFdaTSn/K', 'ROLE_USER');
INSERT INTO user(user_id, username, password, authorities) VALUES (3, 'tourist' ,'$2a$10$1/dMsM7IGrxwFSf/bpQP0.JzRORVvt7pY8A6jpbzE26CiFdaTSn/K', 'ROLE_USER');

INSERT INTO contest(contest_id, contest_name, start_time, finish_time, problems) VALUES (1, 'Running contest', '2017-01-02', '2018-03-04',
'SYBON@1,SYBON@11,SYBON@12,SYBON@13,SYBON@14');
INSERT INTO contest(contest_id, contest_name, start_time, finish_time, problems) VALUES (2, 'Previous contest', '2017-01-02', '2017-02-02', 'SYBON@1');
INSERT INTO contest(contest_id, contest_name, start_time, finish_time, problems) VALUES (3, 'Future contest', '2018-01-01', '2018-02-02', 'SYBON@1');
INSERT INTO contest(contest_id, contest_name, start_time, finish_time, problems) VALUES (4, 'Running empty contest', '2017-01-02', '2018-03-04', '');