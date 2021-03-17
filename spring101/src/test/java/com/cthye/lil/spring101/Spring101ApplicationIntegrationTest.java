package com.cthye.lil.spring101;

import com.cthye.lil.spring101.api.GuestDetailApiControllerTest;
import com.cthye.lil.spring101.web.RoomReservationWebControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RoomReservationWebControllerTest.class,
        GuestDetailApiControllerTest.class
})
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@AutoConfigureMockMvc
class Spring101ApplicationIntegrationTest {

}
