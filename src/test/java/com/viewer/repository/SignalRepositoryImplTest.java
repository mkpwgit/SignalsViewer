package com.viewer.repository;

import com.viewer.domain.Signal;
import com.viewer.exception.ViewerException;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.viewer.Constants.DATE_FORMAT;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:com/viewer/configuration/spring/main-test-context.xml"})
@Transactional
public class SignalRepositoryImplTest {

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    private static Date startDate;
    private static Date endDate;
    private Long deviceId = 2L;
    private static Signal signal;

    @Resource
    private SignalRepository signalRepository;

    @BeforeClass
    public static void setUp() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        startDate = dateFormat.parse("2014-01-06T02:05:02");
        endDate = dateFormat.parse("2014-01-09T02:05:02");

        signal = new Signal();
        signal.setDeviceId(5L);
        signal.setDate(endDate);
        signal.setLongitude(11.34);
        signal.setLatitude(35.67);
        signal.setStrength(-53);
    }

    @Test
    public void saveSuccessTest() {
        signal.setDeviceId(5L);
        signalRepository.save(signal);
    }

    @Test
    public void saveExceptionTest() {
        signal.setDeviceId(null);
        thrown.expect( ViewerException.class );
        thrown.expectMessage("Cannot save signal in db!");
        signalRepository.save(signal);
    }

    @Test
    public void getSignalsByDateTest() {
        List<Signal> signals = signalRepository.getSignalsByDate(startDate, endDate);

        assertEquals(3, signals.size());
    }

    @Test
    public void getSignalsByDateAndDeviceIdTest() {
        List<Signal> signals = signalRepository.getSignalsByDateAndDevice(deviceId, startDate, endDate);

        assertEquals(1, signals.size());

        Signal signal = signals.get(0);
        assertEquals(new Long(2), signal.getDeviceId());
        assertEquals(new Double(12.34), signal.getLatitude());
        assertEquals(new Double(95.67), signal.getLongitude());
    }

}
