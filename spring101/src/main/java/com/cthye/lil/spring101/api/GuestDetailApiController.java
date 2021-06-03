package com.cthye.lil.spring101.api;

import com.cthye.lil.spring101.business.domain.GuestDetail;
import com.cthye.lil.spring101.business.service.GuestService;
import com.cthye.lil.spring101.data.entity.Guest;
import com.cthye.lil.spring101.data.repository.GuestDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/api")
public class GuestDetailApiController {
    private final GuestService guestService;
    private final GuestDetailRepository guestRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(GuestDetailApiController.class);

    @Autowired
    public GuestDetailApiController(GuestService guestService, GuestDetailRepository guestDetailRepository) {
        super();
        this.guestService = guestService;
        this.guestRepository = guestDetailRepository;
    }

    @GetMapping("/guests")
    public Callable<List<GuestDetail>> getAllGuests(HttpServletRequest request){
        LOGGER.debug("Getting all guests");
        LOGGER.debug("is Async Supported:" + request.isAsyncSupported());
        return () ->{ return this.guestService.getAllGuests();};
    }

    @GetMapping("/guests/{guestId}")
    public GuestDetail getSpecificGuest(@PathVariable("guestId") Long guestID){
        LOGGER.debug(String.format("getting %s information",Long.toString(guestID)));
        return this.guestService.getGuest(guestID);
    }

    @PostMapping("/guests")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional //should actually be added to service instead of controller because we can handle exception here but there's no other POST method.
    public void createNewGuest(@RequestBody @Validated Guest guest){
        LOGGER.debug("Saving guest..");
        if(! isDuplicateGuest(guest)) {
            guestRepository.save(guest);
            LOGGER.debug("Guest saved");
        } else {
            throw new GuestInvalidInputException("Duplicated guest");
        }
    }

    private boolean isDuplicateGuest(Guest guest){
        if(guest.getId() != null && ! Long.toString(guest.getId()).isEmpty()){
            return this.guestRepository.findById(guest.getId()).isPresent();
        }
        return this.guestRepository.findByEmail(guest.getEmail()) != null;
    }

    class GuestInvalidInputException extends RuntimeException{
        GuestInvalidInputException(String message) {super(message);}
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(GuestInvalidInputException.class)
    public String returnInvalidInputException(GuestInvalidInputException ex){
        return ex.getMessage();
    }
}
