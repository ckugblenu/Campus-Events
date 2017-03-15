package com.greencampus.campusevents.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.greencampus.campusevents.R;
import com.greencampus.campusevents.models.Organizer;
import com.greencampus.campusevents.viewholder.OrganizerViewHolder;

/**
 * Created by Makafui-PC on 3/12/2017.
 */

public abstract class OrganizerListFragment extends Fragment {

    private static final String TAG = "OrganizerListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Organizer, OrganizerViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    public OrganizerListFragment() {

    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_all_organizers, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = (RecyclerView) rootView.findViewById(R.id.organizers_list);
        mRecycler.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query organizersQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<Organizer, OrganizerViewHolder>(Organizer.class, R.layout.item_organizer,
                OrganizerViewHolder.class, organizersQuery) {
            @Override
            protected void populateViewHolder(final OrganizerViewHolder viewHolder, final Organizer model, final int position) {
                final DatabaseReference organizerRef = getRef(position);

                // Set click listener for the whole post view
                final String organizerKey = organizerRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        // Launch OrganizerDetailsActivity
//                        Intent intent = new Intent(getActivity(), OrganizerDetailsActivity.class);
//                        intent.putExtra(OrganizerDetailsActivity.EXTRA_POST_KEY, postKey);
//                        startActivity(intent);
                    }
                });

                // Determine if the current user has liked this post and set UI accordingly
                if (model.stars.containsKey(getUid())) {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_24);
                } else {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_outline_24);
                }

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToOrganizer(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        // Need to write to both places the post is stored
                        DatabaseReference globalOrganizerRef = mDatabase.child("organizers").child(organizerRef.getKey());
//                        DatabaseReference userOrganizerRef = mDatabase.child("user-organizers").child(model.uid).child(organizerRef.getKey());
//                        Run two transactions
                        onStarClicked(globalOrganizerRef);
//                        onStarClicked(userOrganizerRef);
                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    // [START post_stars_transaction]
    private void onStarClicked(DatabaseReference organizerRef) {
        organizerRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Organizer o = mutableData.getValue(Organizer.class);
                if (o == null) {
                    return Transaction.success(mutableData);
                }

                if (o.stars.containsKey(getUid())) {
                    // Unstar the post and remove self from stars
                    o.starCount = o.starCount - 1;
                    o.stars.remove(getUid());
                } else {
                    // Star the post and add self to stars
                    o.starCount = o.starCount + 1;
                    o.stars.put(getUid(), true);
                }

                // Set value and report transaction success
                mutableData.setValue(o);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "organizerTransaction:onComplete:" + databaseError);
            }
        });
    }
    // [END post_stars_transaction]
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public abstract Query getQuery(DatabaseReference databaseReference);

}
