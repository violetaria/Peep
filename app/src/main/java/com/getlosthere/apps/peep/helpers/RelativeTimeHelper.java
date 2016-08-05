package com.getlosthere.apps.peep.helpers;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by violetaria on 8/4/16.
 */
public class RelativeTimeHelper {
    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // change what is sent back to smaller format
        if(relativeDate.contains("seconds ago")) {
            relativeDate = relativeDate.replace("seconds ago","s");
        } else if (relativeDate.contains("minutes ago")) {
            relativeDate = relativeDate.replace("minutes ago","m");
        } else if (relativeDate.contains("hours ago")) {
            relativeDate = relativeDate.replace("hours ago","h");
        } else if (relativeDate.contains("days ago")) {
            relativeDate = relativeDate.replace("days ago","d");
        }

        return relativeDate;
    }
}
