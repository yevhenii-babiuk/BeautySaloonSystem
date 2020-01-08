/*Passwords are hashed. Admin's password is "administrator", master's password is "yevhenMaster", user's password is "olichkaPrincess"*/
INSERT INTO `beauty_saloon_system`.`user` VALUES 
(NULL, 'admin_account@gmail.com', '\r�+�R��Y��+�%\06̔K��Z��)�\Z���2ZC���ɵ�c69͊�+��@�d@	oD��9�', '+380501112233', 'Artem', 'Khomchanovskii', 'ADMINISTRATOR'),
(NULL, 'yevhen.shevchenko@gmail.com', 'MƗ�ʵ{�M�pӠ�Sŏ�VȢt{{�f�\"`-��<��!NK�p:��9�\"�P|B�ե', '+380981231234', 'Yevhen', 'Shevchenko', 'MASTER'),
(NULL, 'makarenko.olga@ukr.net', 'Ԛ3ʉ3��+�\nz\'G�hjWa�tNF�i;��T�۝��t���!�3�k��J����������', '+380989874561', 'Olga', 'Makarenko', 'USER'), /*password "olichkaPrincess"*/
(NULL, 'chudomargo@ukr.net', '��\rAٓ<}�]s|�N��˕MF\'��t;���}5�\'�>��8�u�[��)~�%z�9d�\r>�', '+380635026902', 'Margaryta', 'Chudniv', 'USER'); /*password "sweetpassword"*/

INSERT INTO `beauty_saloon_system`.`procedure` VALUE
(NULL, 
'Manicure', 
'A manicure consists of filing and shaping the free edge, pushing and clipping (with a cuticle pusher and cuticle nippers) any nonliving tissue (but limited to the cuticle and hangnails), treatments with various liquids, massage of the hand, and the application of fingernail polish.', 
350);

INSERT INTO `beauty_saloon_system`.`slot` 
(`date`, `start_time`, `end_time`, `master`, `user`, `status`, `procedure`)
VALUES
('2020-01-20', '10:00', '11:30', 2, 5, 'BOOKED', 1),
('2020-01-20', '12:00', '13:30', 2, NULL, 'FREE', 1),
('2020-02-15', '15:00', '16:30', 2, null, 'FREE', 1);

INSERT INTO `beauty_saloon_system`.`feedback` VALUE(NULL, 1, 'Perfect work and perfect master');

SELECT `s`.`date` AS `slot_date`,
`s`.`start_time` AS `start_time`,
`s`.`end_time` AS `end_time`,
`s`.`status` AS `status`,
`u1`.`email` AS `master_email`,
`u1`.`phone` AS `master_phone`,
`u1`.`first_name` AS `master_first_name`,
`u1`.`last_name` AS `master_last_name`,
`u2`.`email` AS `user_email`,
`u2`.`phone` AS `user_phone`,
`u2`.`first_name` AS `user_first_name`,
`u2`.`last_name` AS `user_last_name`,
`pr`.`name` AS `procedure_name`,
`pr`.`description` AS `procedure_description`,
`pr`.`price` AS `procedure_price`,
`f`.`text` AS `feedback_text`
FROM `beauty_saloon_system`.`slot` AS `s`
INNER JOIN `beauty_saloon_system`.`user` AS `u1` ON `u1`.`user_id`=`s`.`master`
LEFT JOIN `beauty_saloon_system`.`user` AS `u2` ON `u2`.`user_id`=`s`.`user`
INNER JOIN `beauty_saloon_system`.`procedure` AS pr ON `s`.`procedure`=`pr`.`procedure_id`
LEFT JOIN `beauty_saloon_system`.`feedback` AS `f`  ON `s`.`slot_id`=`f`.`slot`
WHERE `s`.`slot_id`=4;

SELECT * FROM beauty_saloon_system.slot;

SELECT `feedback_id`, `slot`, `text` FROM `beauty_saloon_system`.`feedback` AS f
INNER JOIN `beauty_saloon_system`.`slot` AS `sl` ON `f`.`slot`=`sl`.`slot_id` AND `sl`.`master` = 2
INNER JOIN `beauty_saloon_system`.`slot` AS `sl_pr` ON `f`.`slot`=`sl_pr`.`slot_id` AND `sl_pr`.`procedure` = 1
INNER JOIN `beauty_saloon_system`.`slot` AS `sl_or` ON `f`.`slot`=`sl_or`.`slot_id` ORDER BY `sl_or`.`date`, `sl_or`.`start_time` DESC;