package com.viewer.service;

import com.viewer.domain.Signal;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SignalService {

    public void saveFile(MultipartFile file);

    public List<Signal> getSignalsByDate(String startDateStr, String endDateStr);

    public List<Signal> getSignalsByDateAndDevice(Long deviceId, String startDateStr, String endDateStr);
}
