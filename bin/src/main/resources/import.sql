insert into user (username, password, enabled, role) values ('adam', 'adam', true, 'ROLE_USER');
insert into user (username, password, enabled, role) values ('user', 'user', true, 'ROLE_USER');

insert into TO_DO (user_id, completed, name, category, created_at) values (1, false, 'Complete the WEB ENGINEERING project', 'INITIATED', CURRENT_TIMESTAMP());
insert into TO_DO (user_id, completed, name, category, created_at) values (1, false, 'DATABASE Project Completion', 'ESTIMATING', CURRENT_TIMESTAMP());
insert into TO_DO (user_id, completed, name, category, created_at) values (2, false, 'Advance ML Assignment', 'IMPLEMENT', CURRENT_TIMESTAMP());
insert into TO_DO (user_id, completed, name, category, created_at) values (2, false, 'CIS Report', 'REVIEW', CURRENT_TIMESTAMP());
insert into TO_DO (user_id, completed, name, category, created_at) values (2, false, 'Optimization Questions', 'RELEASE', CURRENT_TIMESTAMP());



