package com.qian.jwt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * TimeTest is
 *
 * @author QIAN
 * Date 2020/06/10
 * Time 14:08
 */
public class TimeTest {

    public static void main(String[] args) throws ParseException {

        Date date = new SimpleDateFormat("yyyy-mm-dd HH:mm").parse("2020-06-10 11:05");
        String s = calculateBeforeTime(270D, date);
        System.out.println(s);

    }

    /**
     * 在time之后的时间
     *
     * @param time .
     * @return 。
     */
    public static String calculateBeforeTime(Double time, Date date) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.SECOND, (int)(time * 60));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            long rightTime = calendar.getTime().getTime() /*- (8 * 60 * 60 * 1000L)*/;
            return formatter.format(rightTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
