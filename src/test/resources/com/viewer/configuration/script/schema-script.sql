DROP TABLE IF EXISTS signal_t;

CREATE TABLE IF NOT EXISTS signal_t (
  signal_id INT(11) NOT NULL AUTO_INCREMENT,
  device_id INT(11) NOT NULL,
  date DATE NOT NULL,
  latitude DOUBLE NOT NULL,
  longitude DOUBLE NOT NULL,
  strength TINYINT NOT NULL,
  PRIMARY KEY (signal_id)
);

INSERT INTO signal_t(device_id, date, latitude, longitude, strength) VALUE (1, 2014-01-05, 12.34, 35.67, -100);
INSERT INTO signal_t(device_id, date, latitude, longitude, strength) VALUE (2, 2014-01-06, 12.34, 95.67, -100);
INSERT INTO signal_t(device_id, date, latitude, longitude, strength) VALUE (3, 2014-01-07, 45.34, 35.67, -100);
INSERT INTO signal_t(device_id, date, latitude, longitude, strength) VALUE (4, 2014-01-06, 23.34, 23.67, -100);
