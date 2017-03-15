package com.greencampus.campusevents;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.greencampus.campusevents.models.Event;

import java.util.HashMap;
import java.util.Map;

public class NewEventActivity extends BaseActivity {

    private static final String TAG = "NewEventActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private EditText mTitleField;
    private EditText mOrganizerField;
    private FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mTitleField = (EditText) findViewById(R.id.field_title);
        mOrganizerField = (EditText) findViewById(R.id.field_organizer);
        mSubmitButton = (FloatingActionButton) findViewById(R.id.fab_submit_post);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEvent();
            }
        });
    }

    private void submitEvent() {
        final String title = mTitleField.getText().toString();
        final String body = mOrganizerField.getText().toString();
        final long startDate = 1489520700000L;
        final long endDate = 1489607100000L;
        final String location = "Karankokatu 4 C 12";
        final String category = "Party";

        // Title is required
        if (TextUtils.isEmpty(title)) {
            mTitleField.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(body)) {
            mOrganizerField.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        final String userId = getUid();
        writeNewEvent(userId, title, body, startDate, endDate, location, category);

        // Finish this Activity, back to the stream
        setEditingEnabled(true);
        finish();


    }

    private void setEditingEnabled(boolean enabled) {
        mTitleField.setEnabled(enabled);
        mOrganizerField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    // [START write_fan_out]
    private void writeNewEvent(String userId, String title, String organizer, long startDate, long endDate, String location, String category) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("events").push().getKey();
        Event event= new Event(title, organizer, startDate, endDate, location, category);
        Map<String, Object> eventValues = event.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/events/" + key, eventValues);
        childUpdates.put("/user-events/" + userId + "/" + key, eventValues);

        mDatabase.updateChildren(childUpdates);
    }
}
