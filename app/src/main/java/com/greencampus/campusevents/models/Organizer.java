package com.greencampus.campusevents.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Makafui-PC on 3/12/2017.
 */

public class Organizer {

    public String name;
    public String description;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public Organizer() {
        // Default constructor required for calls to DataSnapshot.getValue(Event.class)
    }

    public Organizer(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("description", description);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }
}
