/*Passwords are hashed. Admin's password is "administrator", master's password is "yevhenMaster", user's password is "olichkaPrincess"*/
INSERT INTO `beauty_saloon_system`.`user` VALUES 
(1, 'admin_account@gmail.com', '\r�+�R��Y��+�%\06̔K��Z��)�\Z���2ZC���ɵ�c69͊�+��@�d@	oD��9�', '+380501112233', 'Artem', 'Khomchanovskii', 'ADMINISTRATOR'),/*administrator*/
(2, 'yevhen.shevchenko@gmail.com', 'MƗ�ʵ{�M�pӠ�Sŏ�VȢt{{�f�\"`-��<��!NK�p:��9�\"�P|B�ե', '+380981231234', 'Yevhen', 'Shevchenko', 'MASTER'),/*yevhenMaster*/
(3, 'yulia_nesterova@gmail.com', '��������8�?�3����v����_�%x�a�X�Z����F�Vʘ�^�n���gОk���ް', '+380985587974', 'Yulia', 'Nesterova', 'MASTER'),/*yulichkaMaestro*/
(4, 'makarenko.olga@ukr.net', 'Ԛ3ʉ3��+�\nz\'G�hjWa�tNF�i;��T�۝��t���!�3�k��J����������', '+380989874561', 'Olga', 'Makarenko', 'MASTER'), /*olichkaPrincess*/
(5, 'chudomargo@ukr.net', '��\rAٓ<}�]s|�N��˕MF\'��t;���}5�\'�>��8�u�[��)~�%z�9d�\r>�', '+380635026902', 'Margaryta', 'Chudniv', 'USER'), /*sweetpassword*/
(6, 'Khmarka@ukr.net', ']�3�1�q�9��z��9��=��0�\r����}\\1���c��[`�\\ ��`،W����=��t', '+380994485123', 'Roksolana', 'Khmara', 'USER');/*khmarka*/

INSERT INTO `beauty_saloon_system`.`procedure` VALUES
(1, 'Manicure', 
'Manicure is a cosmetic treatment of the hands involving shaping and often painting of the nails, removal of the cuticles, and softening of the skin.', 
'Манікюр','Манікюр — косметична обробка рук, яка включає в себе ( містить в собі) формування і часте фарбування нігтів, видалення кутикули та пом’якшення шкіри.',
'Маникюр','Маникюр — косметическая обработка рук, включающая формирование и частое окрашивание ногтей, удаление кутикулы и смягчение кожи.',
350),
(2, 'Pedicure', 
'Pedicure is a comprehensive treatment of your feet and is suitable for both men and women. It involves cutting, trimming and shaping your toenails, tending to your cuticles, exfoliating, hydrating and massaging your feet, and, if desired, painting your toenails.',
'Педикюр','Педикюр — це комплексне лікування ваших ніг, яке підходить як чоловікам, так і жінкам.  Включає в себе обрізання та формування нігтів, догляд за кутикулами, відлущування, зволоження та масаж ніг, а також, за бажанням, фарбування нігтів.',
'Педикюр','Педикюр — это комплексное лечение ваших ног и подходит как мужчинам, так и женщинам.  Оно включает в себя обрезание, подрезание и придание формы ногтям на ногах, уход за кутикулой, отшелушивание, увлажнение и массаж ног, а также, при желании, покраску ногтей на ногах.',
400),
(3, 'Massage', 'Massage is rubbing and kneading of muscles and joints of the body with the hands, especially to relieve tension or pain.',
'Масаж','Масаж — розтирання і розминання м’язів і суглобів тіла руками, особливо для зняття напруги або болю.',
'Массаж','Массаж — это растирание и разминание мышц и суставов тела руками, особенно для снятия напряжения или боли.',
500);

INSERT INTO `beauty_saloon_system`.`slot` VALUES
(1, '2020-01-29', '10:00', '11:30', 2, 5, 'BOOKED', 1, true),
(2, '2020-02-15', '12:00', '13:30', 3, 5, 'FREE', 2, false),
(3, '2020-02-15', '15:00', '16:00', 4, NULL, 'FREE', 3, false),
(4, '2020-02-17', '9:00', '10:30', 2, NULL, 'FREE', 1, false),
(5, '2020-02-21', '14:00', '15:30', 3, NULL, 'FREE', 2, false),
(6, '2020-02-27', '13:30', '14:30', 4, null, 'FREE', 3, false);

INSERT INTO `beauty_saloon_system`.`feedback` VALUE (1, 1, 'This is the best manicure I\'ve ever had. My nails are so smooth and shiny. The lovely people working here really take their time on the manicure. And it looks fab. Highly recommend. Great choice of colours too.');
