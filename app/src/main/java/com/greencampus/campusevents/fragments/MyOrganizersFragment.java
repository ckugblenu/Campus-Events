package com.greencampus.campusevents.fragments;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Makafui-PC on 3/13/2017.
 */

public class MyOrganizersFragment extends OrganizerListFragment {

    public MyOrganizersFragment() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my organizers
        return databaseReference.child("user-organizers")
                .child(getUid());
    }
}
