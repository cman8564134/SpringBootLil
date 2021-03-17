package com.cthye.lil.spring101.business.service;

import com.cthye.lil.spring101.business.domain.GuestDetail;
import com.cthye.lil.spring101.data.entity.Guest;
import com.cthye.lil.spring101.data.repository.GuestDetailRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GuestServiceTest {
    @Mock
    private GuestDetailRepository guestRepository;
    @InjectMocks
    private GuestService guestService;

    private static final long GUEST_ID_1 = 99999999L;
    private static final long GUEST_ID_2 = 99999990L;

    @Before
    public void beforeMethod(){
        //setting up return values for Mock Methods that is used for all methods
        //can be put into individual method if its only used in the method.
        Guest guestMock1 = new Guest();
        guestMock1.setId(GUEST_ID_1);
        when(guestRepository.findById(GUEST_ID_1)).thenReturn(Optional.of(guestMock1));
    }

    @Test
    public void getAllGuestSortByNameTest_Affirmative(){
        Guest guestMock1 = new Guest();
        Guest guestMock2 = new Guest();
        List<Guest> guestList = new ArrayList<>() ;
        guestMock1.setId(GUEST_ID_1);
        guestMock1.setLastname(Long.toString(GUEST_ID_1));
        guestMock2.setId(GUEST_ID_2);
        guestMock2.setLastname(Long.toString(GUEST_ID_2));
        guestList.add(guestMock2);
        guestList.add(guestMock1);
        when(guestRepository.findAll()).thenReturn(guestList);
        assertEquals("getAllGuest mapping or sorting is incorrect", GUEST_ID_1, ((GuestDetail) guestService.getAllGuests().toArray()[1]).getGuest_id());
    }

    @Test
    public void getAllGuestSortByNameTest_Negative(){
        Guest guestMock1 = new Guest();
        Guest guestMock2 = new Guest();
        List<Guest> guestList = new ArrayList<>() ;
        guestMock1.setId(GUEST_ID_1);
        guestMock1.setLastname(Long.toString(GUEST_ID_1));
        guestMock2.setId(GUEST_ID_2);
        guestMock2.setLastname(Long.toString(GUEST_ID_2));
        guestList.add(guestMock2);
        guestList.add(guestMock1);
        when(guestRepository.findAll()).thenReturn(guestList);
        assertNotEquals("getAllGuest sorting is incorrect", GUEST_ID_1, ((GuestDetail) guestService.getAllGuests().toArray()[0]).getGuest_id());
        assertNotEquals("getAllGuest sorting is incorrect", GUEST_ID_2, ((GuestDetail) guestService.getAllGuests().toArray()[1]).getGuest_id());
    }

    @Test
    public void getAllGuestSortByIDTest_Affirmative(){
        Guest guestMock1 = new Guest();
        Guest guestMock2 = new Guest();
        List<Guest> guestList = new ArrayList<>() ;
        guestMock1.setId(GUEST_ID_1);
        guestMock2.setId(GUEST_ID_2);
        guestList.add(guestMock1);
        guestList.add(guestMock2);
        when(guestRepository.findAll()).thenReturn(guestList);
        assertEquals("getAllGuest mapping or sorting is incorrect", GUEST_ID_2, ((GuestDetail) guestService.getAllGuests().toArray()[0]).getGuest_id());
    }

    @Test
    public void getGuest_Affirmative(){
        assertEquals("getGuest mapping is incorrect", GUEST_ID_1, guestService.getGuest(GUEST_ID_1).getGuest_id());
    }

    @Test
    public void getGuest_Negative(){
        assertNotEquals("getGuest mapping is incorrect", GUEST_ID_2, guestService.getGuest(GUEST_ID_1).getGuest_id());
    }

}
