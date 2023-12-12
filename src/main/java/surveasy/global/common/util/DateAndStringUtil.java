package surveasy.global.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAndStringUtil {

    public static final SimpleDateFormat SDF_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat SDF_DOT_YYYY_MM_DD = new SimpleDateFormat("yyyy.MM.dd");


    public static Date stringToDateYMD(String strDate) throws ParseException {
        if(strDate == null) return null;
        return SDF_YYYY_MM_DD.parse(strDate);
    }

    public static String dateToStringYMD(Date date) {
        return SDF_YYYY_MM_DD.format(date);
    }
}
