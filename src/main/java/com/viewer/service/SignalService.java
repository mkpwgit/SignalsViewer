package com.viewer.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by mikalai on 3/1/14.
 */
public interface SignalService {

    public void saveFile(MultipartFile file);
}
