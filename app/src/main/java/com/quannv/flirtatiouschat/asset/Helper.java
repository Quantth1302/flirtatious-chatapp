package com.quannv.flirtatiouschat.asset;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Helper {
    public static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public static String getCurrentDate() {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        return currentDate.format(calendar.getTime());
    }
}
