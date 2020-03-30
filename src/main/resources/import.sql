INSERT INTO `role`( `role`) VALUES ("ROLE_ADMIN");
INSERT INTO `role`( `role`) VALUES ("ROLE_USER");

INSERT INTO `user`( `enabled`, `password`, `username`) VALUES (true, "$2a$10$0Gdl4wkmMSDDMjMAh9/PI.MVydfp0izSJx1tWDX3AP39xme0j1yWu","admin");#admin

INSERT INTO `user_role`(`user_id`, `role_id`) VALUES (1,1);