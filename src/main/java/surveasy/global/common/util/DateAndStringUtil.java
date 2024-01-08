package surveasy.global.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateAndStringUtil {

    public static final DateTimeFormatter DTF_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DTF_DOT_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy.MM.dd");


    public static LocalDate stringToLocalDate(String strDate) {
        if(strDate == null) return null;
        return LocalDate.parse(strDate);
    }

    public static LocalDateTime stringToLocalDateTime(String strDate) {
        if(strDate == null) return null;
        return LocalDateTime.parse(strDate);
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        if(date == null) return null;
        return date.toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    }

    public static String localDateToString(LocalDate localDate) {
        return localDate.format(DTF_YYYY_MM_DD);
    }

    public static String localDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.format(DTF_YYYY_MM_DD);
    }
}
