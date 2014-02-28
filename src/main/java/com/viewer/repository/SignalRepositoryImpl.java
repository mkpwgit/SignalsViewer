package com.viewer.repository;

import com.viewer.domain.Signal;
import com.viewer.exception.ViewerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.*;

import static com.viewer.Constants.*;

/**
 * Created by mikalai on 2/28/14.
 */
@Repository
public class SignalRepositoryImpl implements SignalRepository {

    //TODO log should be done

    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${select_user_by_id.sql}')).file)}")
    private String findSignalsByDateQuery;

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

        jdbcInsert.execute(parameters);
    }

    @Override
    public List<Signal> getSignalsByDate(Date startDate, Date endDate) {
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put(START_DATE, startDate);
        parameters.put(END_DATE, endDate);

        try {
            final List<Signal> signals = namedParameterJdbcTemplate.query(findSignalsByDateQuery, parameters, new BeanPropertyRowMapper<>(Signal.class));
            return signals != null ? signals : Collections.<Signal>emptyList();
        } catch (DataAccessException e) {
            new ViewerException(e.getLocalizedMessage());
        }

        return null;
    }

    @Override
    public List<Signal> getSignalsByDateAndDeviceId(Date startDate, Date endDate) {
        return null;
    }
}
