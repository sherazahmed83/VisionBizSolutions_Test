package com.visionbizsolutions.mvc.controllers;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.http.MediaType;
 
/**
 * Handles requests for the application home page.
 */
@Controller
public class StaticContentsController {
     
    private static final Logger logger = LoggerFactory.getLogger(StaticContentsController.class);
     
    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String home(Locale locale, Model model) {
        logger.info("Welcome home! The client locale is {}.", locale);
     
        return "home";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about(Locale locale, Model model) {
        logger.info("Welcome AboutUs! The client locale is {}.", locale);
     
        return "about";
    }

    @RequestMapping(value = "/pricing", method = RequestMethod.GET)
    public String pricing(Locale locale, Model model) {
        logger.info("Welcome to our Pricing Page! The client locale is {}.", locale);
     
        return "pricing";
    }

    @RequestMapping(value = "/services", method = RequestMethod.GET)
    public String services(Locale locale, Model model) {
        logger.info("Welcome to our Services Page! The client locale is {}.", locale);
     
        return "services";
    }

    @RequestMapping(value = "/datasecurity", method = RequestMethod.GET)
    public String datasecurity(Locale locale, Model model) {
        logger.info("Welcome to our Data Security Page! The client locale is {}.", locale);
     
        return "datasecurity";
    }

    @RequestMapping(value = "/support", method = RequestMethod.GET)
    public String support(Locale locale, Model model) {
        logger.info("Welcome to our Customer Care and Support Page! The client locale is {}.", locale);
     
        return "support";
    }

    @RequestMapping(value = "/faqs", method = RequestMethod.GET)
    public String faqs(Locale locale, Model model) {
        logger.info("Welcome to our FAQs Page! The client locale is {}.", locale);
     
        return "faqs";
    }}