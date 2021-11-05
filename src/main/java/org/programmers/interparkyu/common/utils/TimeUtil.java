package org.programmers.interparkyu.common.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static DateTimeFormatter performanceTimeFormatter = DateTimeFormatter.ofPattern("HHmm");

    public static DateTimeFormatter ticketingTimeFormatter = DateTimeFormatter.ofPattern(
        "yyyyMMssHHmm");

    public static LocalDate toLocalDate(final String time) {
        return LocalDate.of(
            Integer.parseInt(time.substring(0, 4)),
            Integer.parseInt(time.substring(4, 6)),
            Integer.parseInt(time.substring(6, 8))
        );
    }

}
