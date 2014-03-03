package com.viewer.repository;

import com.viewer.domain.Signal;

import java.util.Date;
import java.util.List;

public interface SignalRepository {

    /**
     * Save signal object in db.
     *
     * @param signal object that should be saved.
     */
    public void save(Signal signal);

    /**
     * Get signals from db by start and end date.
     *
     * @param startDate start date.
     * @param endDate end date.
     * @return list of signals.
     */
    public List<Signal> getSignalsByDate(Date startDate, Date endDate);

    /**
     * Get signals from db by device id, start and end date.
     *
     * @param deviceId device id.
     * @param startDate start date.
     * @param endDate end date.
     * @return list of signals.
     */
    public List<Signal> getSignalsByDateAndDevice(Long deviceId, Date startDate, Date endDate);
}
