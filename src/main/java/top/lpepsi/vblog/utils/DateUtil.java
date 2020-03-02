package top.lpepsi.vblog.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @program: v-blog
 * @description: 时间转换工具类
 * @author: 林北
 * @create: 2020-02-27 12:19
 **/
@Component
public class DateUtil {

    /**
    * @Description: date转String
    * @Param: [date, pattern]
    * @return: java.lang.String
    * @Author: 林北
    * @Date: 2020-02-28
    */
    public static String date2String(Date date, String pattern){
        LocalDateTime localDateTime = date2LocalDateTime(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String format = localDateTime.format(formatter);
        return format;
    }

    /**
    * @Description: String转LocalDateTime
    * @Param: [date, pattern]
    * @return: java.time.LocalDateTime
    * @Author: 林北
    * @Date: 2020-02-28
    */
    public static LocalDateTime string2Date(String date,String pattern) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return LocalDateTime.parse(date,formatter);
    }

    private static LocalDateTime date2LocalDateTime(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
