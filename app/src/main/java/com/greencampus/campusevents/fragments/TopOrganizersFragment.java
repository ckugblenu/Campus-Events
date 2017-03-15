package com.greencampus.campusevents.fragments;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Makafui-PC on 3/13/2017.
 */

public class TopOrganizersFragment extends OrganizerListFragment {

    public TopOrganizersFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START my_top_posts_query]
        // My top posts by number of stars
        Query myTopOrganizersQuery = databaseReference.child("organizers")
                .orderByChild("starCount");
        // [END my_top_posts_query]

        return myTopOrganizersQuery;
    }
}
