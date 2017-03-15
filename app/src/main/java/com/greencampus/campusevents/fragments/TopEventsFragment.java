package com.greencampus.campusevents.fragments;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Makafui-PC on 3/12/2017.
 */

public class TopEventsFragment extends EventListFragment {
    public TopEventsFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START my_top_posts_query]
        // My top posts by number of stars
        Query myTopEventsQuery = databaseReference.child("events")
                .orderByChild("starCount");
        // [END my_top_posts_query]

        return myTopEventsQuery;
    }
}
