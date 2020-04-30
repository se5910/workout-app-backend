-- INSERT INTO `role`( `role`) VALUES ("ROLE_ADMIN");
-- INSERT INTO `role`( `role`) VALUES ("ROLE_USER");

-- INSERT INTO `user`( `enabled`, `password`, `username`) VALUES (true, "$2a$10$0Gdl4wkmMSDDMjMAh9/PI.MVydfp0izSJx1tWDX3AP39xme0j1yWu","admin");

-- INSERT INTO `user_role`(`user_id`, `role_id`) VALUES (1,1);

INSERT INTO `user` VALUES (1,'2020-04-29 14:46:31.321000',_binary '\0','John Irle',_binary '','$2a$10$8.ECMXccg4RATvpDx79Iwe7qTA3/wfdfvZPnkCocT.vLKpBocJmkC',NULL,'irlejohn@gmail.com'),(2,'2020-04-29 17:47:36.567000',_binary '\0','Mike Hawk',_binary '\0','$2a$10$G2ZmPgnxqFlW.4gar3vi2OiuEOT1I7j8HPnaMDq/VCORK3.NMhe4S',NULL,'mike@gmail.com'),(3,'2020-04-29 17:48:17.888000',_binary '\0','Steve Smith',_binary '\0','$2a$10$AX4SvCtQ98SxvJ5GPFMtkecHqxQzqa3cC4PhqjWfHdd5hoOqBHHWe',NULL,'steve@gmail.com');

INSERT INTO `client` VALUES (1,52,NULL,'irlejohn@gmail.com','2020-04-29 17:47:57.738000',NULL,32,'Health History is required',72,'Mike Hawk',NULL,NULL,32,2),(2,52,NULL,'irlejohn@gmail.com','2020-04-29 17:48:41.281000',NULL,32,'Health History is required',72,'Steve Smith',NULL,NULL,32,3);
