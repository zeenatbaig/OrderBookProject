use orderBook;


INSERT INTO stock (name,symbol) VALUES ('APPLE' , 'AAPL');
INSERT INTO stock (name,symbol) VALUES ( 'Amazon','AMAZN');
INSERT INTO stock (name,symbol) VALUES ( 'Netflix','NFLX');
INSERT INTO stock (name,symbol) VALUES ( 'Tesla','TSLA');
INSERT INTO stock (name,symbol) VALUES ( 'Walmart','WMT');
INSERT INTO stock (name,symbol) VALUES ( ' NVIDIA Corporation','NVDA');
INSERT INTO stock (name,symbol) VALUES ( 'JPMorgan Chase & Co.','JPM');
INSERT INTO stock (name,symbol) VALUES ( 'Google','GOOG');
INSERT INTO stock (name,symbol) VALUES ( 'Alibaba Group Holding Limited','BABA');



INSERT INTO stock_order (side,status,stock_id,quantity,datetime,price) VALUES ('SELL', 'CANCELLED', '3', '320','2020-08-02 19:06:30.140250','2.00');
INSERT INTO stock_order (side,status,stock_id,quantity,datetime,price) VALUES ('SELL', 'CANCELLED', '2', '3550','2020-07-16 22:07:51.140212','2.00');
INSERT INTO stock_order (side,status,stock_id,quantity,datetime,price) VALUES ('SELL', 'IN-PROGRESS', '4', '33450','2020-08-16 07:02:51.140222','2.00');
INSERT INTO stock_order (side,status,stock_id,quantity,datetime,price) VALUES ('SELL', 'IN-PROGRESS', '5', '30','2020-03-16 20:06:41.140211','1.00');
INSERT INTO stock_order (side,status,stock_id,quantity,datetime,price) VALUES ('SELL', 'IN-PROGRESS', '6', '50','2020-08-11 05:12:11.140220','98.00');
INSERT INTO stock_order (side,status,stock_id,quantity,datetime,price) VALUES ('SELL', 'IN-PROGRESS', '7', '310','2020-08-16 16:14:21.140255','567.00');
INSERT INTO stock_order (side,status,stock_id,quantity,datetime,price) VALUES ('SELL', 'IN-PROGRESS', '5', '35','2020-08-16 06:11:51.14034','2.00');
INSERT INTO stock_order (side,status,stock_id,quantity,datetime,price) VALUES ('SELL', 'IN-PROGRESS', '7', '30','2020-08-16 15:05:51.140215','2.00');
INSERT INTO stock_order (side,status,stock_id,quantity,datetime,price) VALUES ('SELL', 'IN-PROGRESS', '2', '380','2020-08-16 17:07:51.140212','3967.00');
INSERT INTO stock_order (side,status,stock_id,quantity,datetime,price) VALUES ('SELL', 'IN-PROGRESS', '2', '20','2020-08-16 18:08:51.140211','452.00');
INSERT INTO stock_order (side,status,stock_id,quantity,datetime,price) VALUES ('BUY', 'IN-PROGRESS', '2', '398','2020-08-16 19:15:51.140210','2.00');
INSERT INTO stock_order (side,status,stock_id,quantity,datetime,price) VALUES ('BUY', 'IN-PROGRESS', '1', '98','2020-08-16 15:17:51.140239','2.00');
INSERT INTO stock_order (side,status,stock_id,quantity,datetime,price) VALUES ('BUY', 'IN-PROGRESS', '4', '120','2020-08-16 11:34:51.140238','2.00');
INSERT INTO stock_order (side,status,stock_id,quantity,datetime,price) VALUES ('BUY', 'IN-PROGRESS', '8', '250','2020-08-16 21:23:51.140237','987.00');
INSERT INTO stock_order (side,status,stock_id,quantity,datetime,price) VALUES ('BUY', 'IN-PROGRESS', '9', '300','2020-08-16 22:45:51.140236','2.00');
INSERT INTO stock_order (side,status,stock_id,quantity,datetime,price) VALUES ('BUY', 'IN-PROGRESS', '6', '360','2020-08-16 12:02:51.140235','98.00');
INSERT INTO stock_order (side,status,stock_id,quantity,datetime,price) VALUES ('BUY', 'IN-PROGRESS', '4', '90','2020-08-16 11:13:51.140234','12.00');
INSERT INTO stock_order (side,status,stock_id,quantity,datetime,price) VALUES ('BUY', 'IN-PROGRESS', '3', '55','2020-08-16 15:05:51.140233','900.00');
INSERT INTO stock_order (side,status,stock_id,quantity,datetime,price) VALUES ('BUY', 'IN-PROGRESS', '5', '40','2020-08-16 14:09:51.140232','17.00');
INSERT INTO stock_order (side,status,stock_id,quantity,datetime,price) VALUES ('BUY', 'IN-PROGRESS', '7', '69','2020-08-16 19:16:51.140231','15.00');


-- INSERT INTO `orderTransaction` (order_id,order_stock_id,quantity, datetime, transaction_type) VALUES ('1', '1', '250', '2020-09-16 12:03:21.140234','FULFILLED');