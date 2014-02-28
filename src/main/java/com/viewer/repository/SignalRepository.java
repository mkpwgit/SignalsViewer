package com.viewer.repository;

import com.viewer.domain.Signal;

import java.util.Date;
import java.util.List;

/**
 * Created by mikalai on 2/28/14.
 */
public interface SignalRepository {

    public void save(Signal signal);

    public List<Signal> getSignalsByDate(Date startDate, Date endDate);

    public List<Signal> getSignalsByDateAndDeviceId(Date startDate, Date endDate);
}
