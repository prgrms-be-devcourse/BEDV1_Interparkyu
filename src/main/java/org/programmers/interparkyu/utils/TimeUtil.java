package org.programmers.interparkyu.utils;

import java.time.LocalDate;

public class TimeUtil {
  
  public static LocalDate toLocalDate(String time){
    return LocalDate.of(
        Integer.parseInt(time.substring(0, 4)),
        Integer.parseInt(time.substring(4, 6)),
        Integer.parseInt(time.substring(6, 8))
    );
  }

}
