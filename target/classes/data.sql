
ALTER TABLE carsharing.order_status AUTO_INCREMENT = 1;
ALTER TABLE carsharing.cars AUTO_INCREMENT = 1;
--ALTER TABLE carsharing.clients AUTO_INCREMENT = 1;
--ALTER TABLE carsharing.orders AUTO_INCREMENT = 1;



--INSERT INTO carsharing.clients(full_name,driver_license,email,face_photo,passport,phone_num)
--values ('Mark R.G', 'driver_license', 'markrg@yandex.ru', 'facephoto', 'passport', '88005535'),
--        ('Rodger B.G.', 'driver_license', 'rodgerbg@yandex.ru', 'facephoto', 'passport', '+78434712'),
--        ('Okone C.B.', 'driver_license', 'okonecb@yandex.ru', 'facephoto', 'passport', '+79052232113')
--ON DUPLICATE KEY UPDATE full_name = values(full_name),driver_license = values(driver_license),email = values(email),
--                        face_photo = values(face_photo),passport = values(passport),phone_num = values(phone_num);

INSERT INTO carsharing.order_status (description)
values ('in process'),
       ('finished'),
       ('payed')
ON DUPLICATE KEY UPDATE description = values(description);


INSERT INTO carsharing.cars(car_registr_num, car_mark, car_model, car_status)
values ('У037АК', 'Toyota', 'Kamri', 'free'),
       ('Г228ВП', 'Mercedes', 'Benz', 'free'),
       ('Ы232ВЕ', 'KIA', 'RIO', 'free')
ON DUPLICATE KEY UPDATE car_registr_num = values(car_registr_num),
                        car_mark = values(car_mark), car_model=values(car_model),
                        car_status = values(car_status);


--INSERT INTO carsharing.orders (order_id,car_id_fk,client_id_fk,order_status_fk, created_on)
-- values (1,1,1,1, curdate() ),
--        (2,2,1,2, curdate() ),
--        (3,3,3,3, curdate() )
--ON DUPLICATE KEY UPDATE car_id_fk = values(car_id_fk),client_id_fk = values(client_id_fk),
--                        order_status_fk = values(order_status_fk), created_on = values(created_on);

INSERT INTO carsharing.roles(id, name)
values (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN')
ON DUPLICATE KEY UPDATE name = values(name);