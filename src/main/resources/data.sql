-- INSERT INTO `role`( `role`) VALUES ("ROLE_ADMIN");
-- INSERT INTO `role`( `role`) VALUES ("ROLE_USER");

-- INSERT INTO `user`( `enabled`, `password`, `username`) VALUES (true, "$2a$10$0Gdl4wkmMSDDMjMAh9/PI.MVydfp0izSJx1tWDX3AP39xme0j1yWu","admin");

-- INSERT INTO `user_role`(`user_id`, `role_id`) VALUES (1,1);

INSERT INTO `user` (`id`, `coach_requested`, `created_at`, `enabled`, `full_name`, `is_coach`, `password`, `updated_at`, `username`) VALUES
(1, NULL, '2020-05-04 01:45:51.000000', b'0', 'Joshua K Haunty', b'1', '$2a$10$P4aOGdW3OeY93zvY1Dmr2.j.piGxrpv.kaBNXNzOwyJf06Q5u/eai', '2020-05-04 01:45:51.000000', 'test'),
(2, NULL, '2020-05-04 01:45:51.000000', b'0', 'John Irle', b'0', '$2a$10$G2ZmPgnxqFlW.4gar3vi2OiuEOT1I7j8HPnaMDq/VCORK3.NMhe4S', '2020-05-04 01:45:51.000000', 'test2');
