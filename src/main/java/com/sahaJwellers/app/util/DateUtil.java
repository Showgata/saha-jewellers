package com.sahaJwellers.app.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
	
public static final DateUtil dateUtil = new DateUtil();
private static final String DATE_FORMAT = "dd-M-yyyy hh:mm:ss a";	

private DateUtil() {	
}
	
public static Date atStartOfDay(Date date) {
	
   /* date = localizedDate(date,"Asia/Dhaka");*/
    LocalDateTime localDateTime = dateToLocalDateTime(date);
    LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);

    return localDateTimeToDate(startOfDay);
}

private static Date localizedDate(Date date,String zone) {
	SimpleDateFormat bangladeshFormat = new SimpleDateFormat(DATE_FORMAT);
    TimeZone bangladeshTimeZone = TimeZone.getTimeZone(zone);
    bangladeshFormat.setTimeZone(bangladeshTimeZone);
    
    String dateValue = bangladeshFormat.format(date);
    try {
		date = bangladeshFormat.parse(dateValue);
	} catch (ParseException e) {
		e.printStackTrace(System.err);
	}
	return date;
}

public static Date atEndOfDay(Date date) {
	/*date = localizedDate(date,"Asia/Dhaka");*/
    LocalDateTime localDateTime = dateToLocalDateTime(date);
    LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
    return localDateTimeToDate(endOfDay);
}

private static LocalDateTime dateToLocalDateTime(Date date) {
    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("Asia/Dhaka"));
}

private static Date localDateTimeToDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.of("Asia/Dhaka")).toInstant());
}
}
