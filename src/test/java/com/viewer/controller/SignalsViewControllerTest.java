package com.viewer.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:com/viewer/configuration/spring/main-test-context.xml"})
public class SignalsViewControllerTest {

    private MockMvc mockMvc;

    @Resource
    private SignalsViewController signalsViewController;

    @Before
    public void setUp() {
        this.mockMvc = standaloneSetup(signalsViewController).build();
    }

    @Test
    public void viewerErrorHandlerTest() throws Exception {
        mockMvc.perform(
                get("/signalsviewer/{startdate:.+}/{enddate:.+}", "InvalidDate", "InvalidDate"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("errorPage"));
    }

}
