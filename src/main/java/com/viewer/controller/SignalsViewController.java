package com.viewer.controller;

import com.viewer.domain.Signal;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mikalai on 2/27/14.
 */
@RequestMapping("/signalsviewer")
@Controller
public class SignalsViewController {

    @RequestMapping(method = RequestMethod.GET)
    public String startPage(Model uiModel) {
        return "test";
    }

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            List rows = IOUtils.readLines(file.getInputStream(), "UTF-8");
            for (Object row: rows) {
                System.out.println(row);
            }
        }
        return "test6";
    }

    @RequestMapping(value = "/{startdate:.+}/{enddate:.+}", method = RequestMethod.GET)
    public @ResponseBody
    List<Signal> getSignalsForAllIds(@PathVariable String startdate, @PathVariable String enddate) throws ParseException {
        System.out.println(startdate+" "+enddate);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date startDate = dateFormat.parse(startdate);
        Date endDate = dateFormat.parse(enddate);
        System.out.println(startDate + " !!! "+endDate);

        List<Signal> signals = new ArrayList<>();
        Signal signal = new Signal();
        signal.setDeviceId(1L);
        signal.setDate(startDate);
        signal.setLatitude(12.34);
        signal.setLongitude(15.67);

        signals.add(signal);
        signals.add(signal);

        return signals;
    }

}
