
ALTER TABLE carsharing.order_status AUTO_INCREMENT = 1;

INSERT INTO carsharing.order_status (description) values ('in process'), ('finished'), ('payed'), ('cringe'), ('mega bruh')
ON DUPLICATE KEY UPDATE description = values(description);

--SELECT 'ready', 'not ready'
--WHERE NOT EXISTS (SELECT * FROM carsharing.order_status);


--insert into carsharing.order_status(description) value ("ready")