package com.cthye.lil.spring101.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/isDevEnv")
@PropertySource("classpath:application.yml")
public class SpringExpressionLanguageController {

    @Value("#{new Boolean(environment['spring.profiles.active']!='dev')}")
    private boolean isDevEnv;

    @GetMapping
    public boolean isDevEnv(){
        return isDevEnv;
    }

}
