package com.viewer.repository;

import com.viewer.domain.Signal;

import java.util.Date;
import java.util.List;

public interface SignalRepository {

    public void save(Signal signal);

    public List<Signal> getSignalsByDate(Date startDate, Date endDate);

    public List<Signal> getSignalsByDateAndDevice(Long deviceId, Date startDate, Date endDate);
}
