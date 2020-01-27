package com.GasGuru.GasGuru.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.GasGuru.GasGuru.Exception.BadRequestException;





@Component
public class StringToDateConverter {
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
    public Date convert(String  dateInString) {
    	/**formatter.setTimeZone(TimeZone.getTimeZone("UTC"));**/
	    try {
	        Date date = formatter.parse(dateInString);

	       return date;
	    } catch (ParseException e) {
	        throw new BadRequestException("enter valid date format (yyyy-mm-dd)");
	    }
    
	    
    }
    

}
