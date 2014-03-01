package com.viewer.repository;

import com.viewer.domain.Signal;
import com.viewer.exception.ViewerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.viewer.Constants.*;

/**
 * Created by mikalai on 2/28/14.
 */
@Repository
public class SignalRepositoryImpl implements SignalRepository {

    private static Logger LOG = LoggerFactory.getLogger(SignalRepositoryImpl.class);

    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${select_signals_by_date.sql}')).file)}")
    private String findSignalsByDateQuery;
    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${select_signals_by_date_and_device_id.sql}')).file)}")
    private String findSignalsByDateAndDeviceIdQuery;

    /**
     * Allowing the use of named parameters rather than traditional '?' placeholders.
     */
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Allow inserting data into table.
     */
    private SimpleJdbcInsert jdbcInsert;

    @Resource
    private DataSource dataSource;

    @PostConstruct
    public void initJdbcTemplate() {
        if (namedParameterJdbcTemplate == null) {
            namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        }
        if (jdbcInsert == null) {
            jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(SIGNAL_TABLE).usingGeneratedKeyColumns(SIGNAL_ID_COLUMN);
        }
    }

    @Override
    @Transactional
    public void save(Signal signal) {
        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put(DEVICE_ID_COLUMN, signal.getDeviceId());
        parameters.put(DATE_COLUMN, signal.getDate());
        parameters.put(LATITUDE_COLUMN, signal.getLatitude());
        parameters.put(LONGITUDE_COLUMN, signal.getLongitude());
        parameters.put(STRENGTH_COLUMN, signal.getStrength());

        try {
            jdbcInsert.execute(parameters);
        } catch (Exception ex) {
            LOG.error("ERROR: ", ex);
            throw new ViewerException("Cannot save signal in db!");
        }
    }

    @Override
    public List<Signal> getSignalsByDate(Date startDate, Date endDate) {
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put(START_DATE, startDate);
        parameters.put(END_DATE, endDate);

        try {
            final List<Signal> signals = namedParameterJdbcTemplate.query(findSignalsByDateQuery, parameters, new BeanPropertyRowMapper<>(Signal.class));
            return signals;
        } catch (Exception ex) {
            LOG.error("ERROR: ", ex);
            throw new ViewerException("Cannot get signals from db!");
        }
    }

    @Override
    public List<Signal> getSignalsByDateAndDevice(Long deviceId, Date startDate, Date endDate) {
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put(START_DATE, startDate);
        parameters.put(END_DATE, endDate);
        parameters.put(DEVICE_ID, deviceId);

        try {
            final List<Signal> signals = namedParameterJdbcTemplate.query(findSignalsByDateAndDeviceIdQuery, parameters, new BeanPropertyRowMapper<>(Signal.class));
            return signals;
        } catch (Exception ex) {
            LOG.error("ERROR: ", ex);
            throw new ViewerException("Cannot get signals from db!");
        }
    }
}
