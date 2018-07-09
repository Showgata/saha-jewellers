package com.sahaJwellers.app.util;

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
    LocalDateTime localDateTime = dateToLocalDateTime(date);
    LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
    
    SimpleDateFormat bangladeshFormat = new SimpleDateFormat(DATE_FORMAT);
    TimeZone bangladeshTimeZone = TimeZone.getTimeZone("Asia/Dhaka");
    bangladeshFormat.setTimeZone(bangladeshTimeZone);
   /* Loca
    String bangladeshFormat = bangladeshFormat.fo
    Date dateInAmerica = bangladeshFormat.parse(sDateInAmerica);*/
    
    return localDateTimeToDate(startOfDay);
}

public static Date atEndOfDay(Date date) {
    LocalDateTime localDateTime = dateToLocalDateTime(date);
    LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
    return localDateTimeToDate(endOfDay);
}

private static LocalDateTime dateToLocalDateTime(Date date) {
    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
}

private static Date localDateTimeToDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
}
}
