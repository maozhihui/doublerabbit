package com.comba.server.dao.util;

import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期转换类
 *
 */
public class DateConvertUtil {

    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static Date convertToDate(LocalDateTime time){
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = time.atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    public static LocalDateTime convertToLocalDateTime(Date date){
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        LocalDateTime time = LocalDateTime.ofInstant(instant,zone);
        return time;
    }


    public static String localDateTimeConvertToStr(LocalDateTime time){
        LocalDateTimeStringConverter converter = new LocalDateTimeStringConverter(dateTimeFormatter,null);
        return converter.toString(time);
    }

    public static LocalDateTime strConvertToLocalDateTime(String time){
        return LocalDateTime.parse(time,dateTimeFormatter);
    }

    public static String LocalDateToStr(LocalDate localDate){
        LocalDateStringConverter converter = new LocalDateStringConverter(dateFormatter,null);
        return converter.toString(localDate);
    }

    public static Date strToDate(String time){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.parse(time);
        } catch (ParseException e) {
            return null;
        }

    }
}
