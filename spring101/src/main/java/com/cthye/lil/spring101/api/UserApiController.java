package com.cthye.lil.spring101.api;

import com.cthye.lil.spring101.business.domain.LoginDto;
import com.cthye.lil.spring101.business.service.UserService;
import com.cthye.lil.spring101.data.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/api/users")
public class UserApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserApiController.class);

    private final UserService userService;
    private final MessageSource messageSource;

    @Autowired
    public UserApiController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @PostMapping("/signin")
    public String login(@RequestBody @Valid LoginDto loginDto) {
        LOGGER.debug("Signing in user: " + loginDto.getUsername());
        return userService.signin(loginDto.getUsername(), loginDto.getPassword()).orElseThrow(()->
                new HttpServerErrorException(HttpStatus.FORBIDDEN, "Login Failed"));
    }

    @PostMapping("/signup")
    //Uses Spring Web filter to filter who can access these methods
    @PreAuthorize("hasRole('ENDPOINT_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public User signup(@RequestBody @Valid LoginDto loginDto, @RequestParam(required = false, name = "lang") String languageLocale){
        LOGGER.debug("Signing up user: " + loginDto.getUsername());
        return userService.signup(loginDto.getUsername(), loginDto.getPassword(), loginDto.getFirstName(),loginDto.getLastName())
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST,
                        Objects.requireNonNull(messageSource.getMessage("api.message.already.exist", null, "User Already Exist", languageLocale != null ? Locale.forLanguageTag(languageLocale) : null))
        ));
    }

    @GetMapping
    //Uses Spring Web filter to filter who can access these methods
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Callable<List<User>> getAllUsers(HttpServletRequest request) {
        LOGGER.debug("is Async Supported:" + request.isAsyncSupported());
        return () ->{ return userService.getAll();};
    }
}
