CREATE TABLE IF NOT EXISTS signal_t (
  signal_id INT(11) NOT NULL AUTO_INCREMENT,
  device_id INT(11) NOT NULL,
  date DATE NOT NULL,
  latitude DOUBLE NOT NULL,
  longitude DOUBLE NOT NULL,
  strength TINYINT NOT NULL,
  PRIMARY KEY (signal_id)
)
