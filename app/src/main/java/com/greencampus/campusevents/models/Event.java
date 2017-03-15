package com.greencampus.campusevents.models;

import com.google.firebase.database.Exclude;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Makafui-PC on 3/12/2017.
 */

public class Event {

    public String organizer;
    public String title;
    public long startDate;
    public long endDate;
    public String location;
    public String category;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public Event() {
        // Default constructor required for calls to DataSnapshot.getValue(Event.class)
    }

    public Event( String title, String organizer, long startDate, long endDate, String location, String category) {
        this.organizer = organizer;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.category = category;
    }

    /**
     * Sets the start date of this event. Months are 0-indexed: e.g. Month 0 is January.
     */
    public void putStartTime(int year, int month, int dayOfMonth, int hour, int minute) {
        Calendar c = new GregorianCalendar(year, month, dayOfMonth, hour, minute);
        startDate = c.getTimeInMillis();
    }

    /**
     * Gets the start date of this event as a Calendar object.
     */
    public Calendar startTimeAsCalendar() {
        Calendar c = new GregorianCalendar();
        c.setTimeInMillis(startDate);
        return c;
    }

    /**
     * Formats the start time given the format string following
     * <a href="http://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html">SimpleDateFormat conventions</a>:
     * e.g. A format of "yyyy-MM-dd" will return "2017-03-01" for March 3rd, 2017.
     */
    public String formattedStartTime(String fmt) {
        return new SimpleDateFormat(fmt).format(startTimeAsCalendar().getTime());
    }

    /**
     * Get time in milliseconds from the Unix epoch, given a date string and a format string
     * of the date passed in.
     * @param date
     * @param fmt
     * @return
     */
    public static long getEpochTime(String date, String fmt) {
        try {
            return new SimpleDateFormat(fmt).parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Sets the end date of this event. Months are 0-indexed: e.g. Month 0 is January.
     */
    public void putEndTime(int year, int month, int dayOfMonth, int hour, int minute) {
        Calendar c = new GregorianCalendar(year, month, dayOfMonth, hour, minute);
        endDate = c.getTimeInMillis();
    }

    /**
     * Gets the end date of this event as a Calendar object.
     */
    public Calendar endTimeAsCalendar() {
        Calendar c = new GregorianCalendar();
        c.setTimeInMillis(endDate);
        return c;
    }

    /**
     * Formats the end time given the format string following
     * <a href="http://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html">SimpleDateFormat conventions</a>:
     * e.g. A format of "yyyy-MM-dd" will return "2017-03-01" for March 3rd, 2017.
     */
    public String formattedEndTime(String fmt) {
        return new SimpleDateFormat(fmt).format(endTimeAsCalendar().getTime());
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("organizer", organizer);
        result.put("title", title);
        result.put("startDate", startDate);
        result.put("endDate", endDate);
        result.put("location", location);
        result.put("category", category);
        result.put("stars", stars);

        return result;
    }
    // [END post_to_map]

}
