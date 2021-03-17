package com.cthye.lil.spring101.api;

import com.cthye.lil.spring101.business.domain.GuestDetail;
import com.cthye.lil.spring101.business.service.GuestService;
import com.cthye.lil.spring101.data.entity.Guest;
import com.cthye.lil.spring101.data.repository.GuestDetailRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(GuestDetailApiController.class)
public class GuestDetailApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GuestService guestService;
    @MockBean
    private GuestDetailRepository guestDetailRepository;
    private static final String GUEST_URL = "/api/guests";
    private final String GUEST_DETAIL_NAME = "testing123";
    private final String GUEST_EMAIL = "testing@email.com";
    private final Long GUEST_ID = 9999999L;
    private final GuestDetail guestDetail = new GuestDetail();
    private final Guest guest = new Guest();

    @Before
    public void beforeClass() {
        guestDetail.setFull_name(GUEST_DETAIL_NAME);
        guestDetail.setGuest_id(GUEST_ID);
        guest.setFirstname(GUEST_DETAIL_NAME);
        guest.setLastname(GUEST_DETAIL_NAME);
        guest.setEmail(GUEST_EMAIL);
        List<GuestDetail> guestDetails = new ArrayList<>();
        guestDetails.add(guestDetail);
//      works the same as:  given(guestService.getAllGuests()).willReturn(guestDetails);
        when(guestService.getAllGuests()).thenReturn(guestDetails);
        when(guestService.getGuest(GUEST_ID)).thenReturn(guestDetail);
    }

    @Test
    public void getAllGuestsTest_Pass() throws Exception {

        MvcResult res = mockMvc.perform(get(GUEST_URL)).andReturn();
        assertEquals("Return status is incorrect", HttpStatus.OK.value(), res.getResponse().getStatus());
        assertTrue("Return value did not contain mocked detail name", res.getResponse().getContentAsString().contains( GUEST_DETAIL_NAME));
        //Using Jackson, convert the JsonString to object
        ObjectMapper objectMapper = new ObjectMapper();
        List guest = objectMapper.readValue(res.getResponse().getContentAsString(), List.class);
        GuestDetail guestDetail = objectMapper.convertValue(guest.get(0), GuestDetail.class);
        assertEquals("Return value should be mocked object", GUEST_DETAIL_NAME, guestDetail.getFull_name());

    }

    @Test
    public void getSpecificGuest_Pass() throws Exception {
        MvcResult res = mockMvc.perform(get(GUEST_URL + "/" + GUEST_ID.toString())).andReturn();
        assertEquals("Return status is incorrect", HttpStatus.OK.value(), res.getResponse().getStatus());
        ObjectMapper objectMapper = new ObjectMapper();
        GuestDetail guest = objectMapper.readValue(res.getResponse().getContentAsString(), GuestDetail.class);
        assertTrue("Return value should be mocked object", guest.equals(guestDetail));
    }

    @Test
    public void createNewGuest_Pass() throws Exception {
        when(guestDetailRepository.findById(GUEST_ID)).thenReturn(null);
        when(guestDetailRepository.findByEmail(GUEST_EMAIL)).thenReturn(null);
        when(guestDetailRepository.save(any())).thenReturn(null);

        ObjectMapper mapper = new ObjectMapper();
        String requestJson=mapper.writeValueAsString(guest);
        MvcResult res = mockMvc.perform(post(GUEST_URL ).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andReturn();
        assertEquals("Return status is incorrect", HttpStatus.CREATED.value(), res.getResponse().getStatus());
    }

    @Test
    public void createNewGuest_GuestInvalidInputException() throws Exception {
        when(guestDetailRepository.findById(GUEST_ID)).thenReturn(any());
        when(guestDetailRepository.findByEmail(GUEST_EMAIL)).thenReturn(new Guest());

        ObjectMapper mapper = new ObjectMapper();
        String requestJson=mapper.writeValueAsString(guest);
        MvcResult res = mockMvc.perform(post(GUEST_URL ).contentType(MediaType.APPLICATION_JSON).content(requestJson)).andReturn();
        assertEquals("Return status is incorrect", HttpStatus.BAD_REQUEST.value(), res.getResponse().getStatus());
        assertEquals("Return message is incorrect", "Duplicated guest", res.getResponse().getContentAsString());

    }

}
