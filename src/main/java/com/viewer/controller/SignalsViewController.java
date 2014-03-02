package com.viewer.controller;

import com.viewer.domain.Signal;
import com.viewer.exception.ViewerException;
import com.viewer.service.SignalService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/signalsviewer")
@Controller
public class SignalsViewController {

    private static Logger LOG = LoggerFactory.getLogger(SignalsViewController.class);

    @Resource
    private SignalService signalService;

    @RequestMapping(method = RequestMethod.GET)
    public String startPage() {
        return "upload";
    }

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        signalService.saveFile(file);
        return "viewer";
    }

    @RequestMapping(value = "/{startdate:.+}/{enddate:.+}", method = RequestMethod.GET)
    public @ResponseBody
    List<Signal> getSignalsForAllDevices(@PathVariable String startdate, @PathVariable String enddate) throws ParseException {
        List<Signal> signals = signalService.getSignalsByDate(startdate, enddate);
        return signals;
    }

    @RequestMapping(value = "/{deviceid}/{startdate:.+}/{enddate:.+}", method = RequestMethod.GET)
    public @ResponseBody
    List<Signal> getSignalsForOneDevice(@PathVariable Long deviceid, @PathVariable String startdate,
                                        @PathVariable String enddate) {
        List<Signal> signals = signalService.getSignalsByDateAndDevice(deviceid, startdate, enddate);
        return signals;

    }

    @ExceptionHandler(ViewerException.class)
    public String viewerErrorHandler(ViewerException ex) {
        LOG.error("ERROR: ", ex);
        return "errorPage";
    }

}
