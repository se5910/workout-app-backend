-- INSERT INTO `role`( `role`) VALUES ("ROLE_ADMIN");
-- INSERT INTO `role`( `role`) VALUES ("ROLE_USER");

-- INSERT INTO `user`( `enabled`, `password`, `username`) VALUES (true, "$2a$10$0Gdl4wkmMSDDMjMAh9/PI.MVydfp0izSJx1tWDX3AP39xme0j1yWu","admin");

-- INSERT INTO `user_role`(`user_id`, `role_id`) VALUES (1,1);

INSERT INTO `user` VALUES (1,NULL,'2020-05-04 01:45:51.000000',_binary '\0','Joshua K Haunty',_binary '','$2a$10$Z4j4L6K0L0G09L.0T/2oCOMyqbbkgGXw7cQHx6LGFyTYPRuIPGwOa','2020-05-04 01:45:51.000000','josh@hype4fitness.com'),
(2,NULL,'2020-05-04 01:45:51.000000',_binary '\0','John Irle',_binary '','$2a$10$Z4j4L6K0L0G09L.0T/2oCOMyqbbkgGXw7cQHx6LGFyTYPRuIPGwOa','2020-05-04 01:45:51.000000','john@hype4fitness.com'),
(3,NULL,'2020-05-04 01:45:51.000000',_binary '\0','Glynn Leininger',_binary '','$2a$10$Z4j4L6K0L0G09L.0T/2oCOMyqbbkgGXw7cQHx6LGFyTYPRuIPGwOa','2020-05-04 01:45:51.000000','glynn@hype4fitness.com'),
(4,NULL,'2020-05-04 01:45:51.000000',_binary '\0','Shayne The Boss',_binary '','$2a$10$Z4j4L6K0L0G09L.0T/2oCOMyqbbkgGXw7cQHx6LGFyTYPRuIPGwOa','2020-05-04 01:45:51.000000','shayne@hype4fitness.com'),
(5,NULL,'2020-05-05 14:22:05.496000',_binary '\0','John',_binary '\0','$2a$10$yqS1iMt9XNcS7JhHP50NEeFjL6qaUS/Buj12IM2SEcdAV0Eu2.Sm.',NULL,'irlejohn@gmail.com'),
(6,NULL,'2020-05-05 14:23:27.934000',_binary '\0','josh',_binary '\0','$2a$10$e1l9zq1.E4w4txWU2w2REOey4yk.62KPKtgjf81U6mxNTRyZOHAP6',NULL,'josh@gmail.com'),
(7,NULL,'2020-05-05 14:24:46.983000',_binary '\0','glynn',_binary '\0','$2a$10$k7oLLDHe46Xv.4SAnS7dj.eRKhlyMLX223reQjkEyARZGNxmYfPvy',NULL,'glynn@gmail.com');

INSERT INTO `client` VALUES (1,26,26,'john@hype4fitness.com','2020-05-05 14:23:07.570000','I want john',111,'nothin',75,_binary '\0','John',70,NULL,152,5),
(2,24,22,'josh@hype4fitness.com','2020-05-05 14:24:16.139000','I want josh',170,'none',75,_binary '\0','josh',74,NULL,250,6),
(3,21,12,'glynn@hype4fitness.com','2020-05-05 14:25:36.604000','I want glynn',120,'none',75,_binary '\0','glynn',120,NULL,170,7);

INSERT INTO 
  exercise (id, exercise_name, muscle_group) 
VALUES
  (1,'bench','chest'), 
  (2,'db flys','chest'),
  (3,'push ups','chest');
