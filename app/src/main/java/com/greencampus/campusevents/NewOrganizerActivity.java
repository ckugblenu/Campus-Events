package com.greencampus.campusevents;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.greencampus.campusevents.models.Organizer;

import java.util.HashMap;
import java.util.Map;

public class NewOrganizerActivity extends BaseActivity {

    private static final String TAG = "NewOrgganizerActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private EditText mNameField;
    private EditText mDescriptionField;
    private FloatingActionButton mSubmitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_organizer);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mNameField = (EditText) findViewById(R.id.field_name);
        mDescriptionField = (EditText) findViewById(R.id.field_description);
        mSubmitButton = (FloatingActionButton) findViewById(R.id.fab_submit_post);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrganizer();
            }
        });
    }

    private void submitOrganizer() {
        final String name = mNameField.getText().toString();
        final String description = mDescriptionField.getText().toString();

        // Title is required
        if (TextUtils.isEmpty(name)) {
            mNameField.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(description)) {
            mDescriptionField.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        final String userId = getUid();
        writeNewOrganizer(userId, name, description);

        // Finish this Activity, back to the stream
        setEditingEnabled(true);
        finish();


    }

    private void setEditingEnabled(boolean enabled) {
        mNameField.setEnabled(enabled);
        mDescriptionField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    // [START write_fan_out]
    private void writeNewOrganizer(String userId, String name, String description) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("organizers").push().getKey();
        Organizer organizer= new Organizer(name, description);
        Map<String, Object> organizerValues = organizer.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/organizers/" + key, organizerValues);
        childUpdates.put("/user-organizers/" + userId + "/" + key, organizerValues);

        mDatabase.updateChildren(childUpdates);
    }
}
