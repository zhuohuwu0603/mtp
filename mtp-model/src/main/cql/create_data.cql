
INSERT INTO user (id, login, password , firstname, lastname ,email ,activated ,lang_key, activation_key, authorities)
VALUES('1','system','$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG','','System','system@localhost',true,'en','', {'ROLE_USER', 'ROLE_ADMIN'});

INSERT INTO user_by_login (login, id)
VALUES('system','1');

INSERT INTO user (id, login, password , firstname, lastname ,email ,activated ,lang_key, activation_key, authorities)
VALUES('2','anonymousUser','$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO','','Anonymous','anonymous@localhost',true,'en','', {});

INSERT INTO user_by_login (login, id)
VALUES('anonymousUser','2');

INSERT INTO user (id, login, password , firstname, lastname ,email ,activated ,lang_key, activation_key, authorities)
VALUES('3','admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','','Administrator','admin@localhost',true,'en','', {'ROLE_USER', 'ROLE_ADMIN'});

INSERT INTO user_by_login (login, id)
VALUES('admin','3');

INSERT INTO user (id, login, password , firstname, lastname ,email ,activated ,lang_key, activation_key, authorities)
VALUES('4','user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','','User','user@localhost',true,'en','', {'ROLE_USER'});

INSERT INTO user_by_login (login, id)
VALUES('user','4');

INSERT INTO user (id, login, password , firstname, lastname ,email ,activated ,lang_key, activation_key, authorities)
VALUES('5','demo','$2a$10$qgAmnVC7HhzD36WK30N6d.kor5I.vSA0eVMXeb2/5IWHEkBKe3txW','','Demo','demo@localhost',true,'en','', {'ROLE_DEMO'});

INSERT INTO user_by_login (login, id)
VALUES('demo','5');

