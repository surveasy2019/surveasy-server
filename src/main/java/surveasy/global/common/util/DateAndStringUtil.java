package surveasy.global.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAndStringUtil {

    public static Date stringToDateYMD(String strDate) throws ParseException {
        if(strDate == null) return null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(strDate);
        return date;
    }

    public static String dateToStringYMD(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = format.format(date);
        return strDate;
    }
}
