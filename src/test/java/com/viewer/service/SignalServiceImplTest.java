package com.viewer.service;


import com.viewer.domain.Signal;
import com.viewer.exception.ViewerException;
import com.viewer.repository.SignalRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.viewer.Constants.DATE_FORMAT;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SignalServiceImplTest {

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    private SignalService signalService;
    private SignalRepository signalRepository;
    private Date startDate;
    private Date endDate;
    private String startDateStr = "2014-01-05T02:05:03";
    private String endDateStr = "2014-01-06T02:05:03";

    @Before
    public void setUp() throws ParseException {
        signalService = new SignalServiceImpl();
        signalRepository = mock(SignalRepository.class);

        ReflectionTestUtils.setField(signalService, "signalRepository", signalRepository);

        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        startDate = dateFormat.parse(startDateStr);
        endDate = dateFormat.parse(endDateStr);
    }

    @Test
    public void getSignalsByDateTest() {
        when(signalRepository.getSignalsByDate(startDate, endDate)).thenReturn(new ArrayList<Signal>());

        List<Signal> signals = signalService.getSignalsByDate(startDateStr, endDateStr);

        verify(signalRepository).getSignalsByDate(startDate, endDate);
    }

    @Test
    public void getSignalsByDateExceptionTest() {
        String startDateInvalid = "InvalidDate";
        thrown.expect(ViewerException.class);
        signalService.getSignalsByDate(startDateInvalid, endDateStr);
    }
}
