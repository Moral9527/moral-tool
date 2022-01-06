package icu.moral.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    private static Calendar calendar = Calendar.getInstance();

    public static Date present(){
        return new Date();
    }

    public static String birthTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        calendar.set(Calendar.YEAR, (int) (Math.random() * 50) + 1970);
        calendar.set(Calendar.MONTH, (int) (Math.random() * 12));
        calendar.set(Calendar.DATE, (int) (Math.random() * 28));

        return format.format(calendar.getTime());
    }
}
