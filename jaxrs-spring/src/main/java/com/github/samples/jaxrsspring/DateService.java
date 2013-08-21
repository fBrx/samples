package com.github.samples.jaxrsspring;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;


/**
 * Simple class which formats the current date and time in a given format.
 * 
 * @author Florian Müller
 * @see SimpleDateFormat
 */
@Component
public class DateService {

    public String getDate(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }
}
