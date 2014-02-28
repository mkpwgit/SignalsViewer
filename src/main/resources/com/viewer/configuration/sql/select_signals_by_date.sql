SELECT
  device_id,
  date,
  latitude,
  longitude,
  strength
FROM signal_t
WHERE date >= :startDate and date <= :endDate;