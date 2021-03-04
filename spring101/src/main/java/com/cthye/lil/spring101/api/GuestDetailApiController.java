package com.cthye.lil.spring101.api;

import com.cthye.lil.spring101.business.domain.GuestDetail;
import com.cthye.lil.spring101.business.service.GuestService;
import com.cthye.lil.spring101.data.entity.Guest;
import com.cthye.lil.spring101.data.repository.GuestDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GuestDetailApiController {
    private final GuestService guestService;
    private final GuestDetailRepository guestRepository;

    @Autowired
    public GuestDetailApiController(GuestService guestService, GuestDetailRepository guestDetailRepository) {
        super();
        this.guestService = guestService;
        this.guestRepository = guestDetailRepository;
    }

    @GetMapping("/guests")
    public List<GuestDetail> getAllGuests(){
        return this.guestService.getAllGuests();
    }

    @GetMapping("/guests/{guestId}")
    public GuestDetail getSpecificGuest(@PathVariable("guestId") Long guestID){
        return this.guestService.getGuest(guestID);
    }

    @PostMapping("/guests")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewGuest(@RequestBody @Validated Guest guest){
        if(! isDuplicateGuest(guest)) {
            guestRepository.save(guest);
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
