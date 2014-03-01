package com.viewer.service;

import com.viewer.domain.Signal;
import com.viewer.exception.ViewerException;
import com.viewer.repository.SignalRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.viewer.Constants.DATE_FORMAT;

/**
 * Created by mikalai on 3/1/14.
 */
@Service
public class SignalServiceImpl implements SignalService {

    private static Logger LOG = LoggerFactory.getLogger(SignalServiceImpl.class);

    @Resource
    private SignalRepository signalRepository;

    @Override
    @Transactional
    public void saveFile(MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                List rows = IOUtils.readLines(file.getInputStream(), "UTF-8");
                for (Object row : rows) {
                    String [] rowParts = ((String) row).split(",");

                    DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

                    Signal signal = new Signal();
                    signal.setDate(dateFormat.parse(rowParts[0].trim()));
                    signal.setDeviceId(Long.parseLong(rowParts[1].trim()));
                    signal.setLatitude(Double.parseDouble(rowParts[2].trim()));
                    signal.setLongitude(Double.parseDouble(rowParts[3].trim()));
                    signal.setStrength(Integer.parseInt(rowParts[4].trim()));

                    signalRepository.save(signal);
                }
            }
        } catch (Exception ex) {
            LOG.error("ERROR: ", ex);
            throw new ViewerException("Cannot read and save file!");
        }
    }
}
