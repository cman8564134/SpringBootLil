package com.cthye.lil.spring101;

import com.cthye.lil.spring101.business.service.GuestServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GuestServiceTest.class,
})
@SpringBootTest
public class Spring101ApplicationUnitTest {
}
