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
