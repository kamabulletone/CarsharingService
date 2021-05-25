--
--ALTER TABLE order_status AUTO_INCREMENT = 1;
--ALTER TABLE cars AUTO_INCREMENT = 1;

INSERT INTO roles(id, name)
values (1, 'ROLE_USER'),
(2, 'ROLE_ADMIN')
ON CONFLICT (id)
DO UPDATE SET name = excluded.name;


INSERT INTO order_status(description)
values ('in process'),
       ('finished'),
       ('payed')
ON CONFLICT (id,description)
DO UPDATE SET description = excluded.description;




INSERT INTO cars(car_registr_num, car_mark, car_model, car_status)
values ('У199УХ99', 'Toyota', 'Kamri', 'free'),
       ('А199ТА98', 'Mercedes', 'Benz', 'in use'),
       ('У177УМ48', 'KIA', 'RIO', 'free')
ON CONFLICT (car_id)
DO UPDATE SET car_registr_num = excluded.car_registr_num,
              car_mark = excluded.car_mark,
              car_model = excluded.car_model,
              car_status = excluded.car_status;
