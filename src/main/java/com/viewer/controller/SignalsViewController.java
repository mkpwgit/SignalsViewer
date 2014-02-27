package com.viewer.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

}
