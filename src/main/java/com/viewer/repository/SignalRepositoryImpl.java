package com.viewer.repository;

import com.viewer.domain.Signal;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by mikalai on 2/28/14.
 */
@Repository
public class SignalRepositoryImpl implements SignalRepository {

    @Override
    public void save(Signal signal) {

    }

    @Override
    public List<Signal> getSignalsByDate(Date startDate, Date endDate) {
        return null;
    }

    @Override
    public List<Signal> getSignalsByDateAndDeviceId(Date startDate, Date endDate) {
        return null;
    }
}
