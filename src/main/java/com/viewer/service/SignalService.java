package com.viewer.service;

import com.viewer.domain.Signal;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by mikalai on 3/1/14.
 */
public interface SignalService {

    public void saveFile(MultipartFile file);

    public List<Signal> getSignalsByDate(String startDateStr, String endDateStr);
}
