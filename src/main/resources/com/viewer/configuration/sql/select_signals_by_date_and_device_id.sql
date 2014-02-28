SELECT
  device_id AS deviceId,
  date,
  latitude,
  longitude,
  strength
FROM signal_t
WHERE date > :startDate and
date < :endDate and
device_id = :deviceId;