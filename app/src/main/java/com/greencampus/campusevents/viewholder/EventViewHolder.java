package com.greencampus.campusevents.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.greencampus.campusevents.R;
import com.greencampus.campusevents.models.Event;

/**
 * Created by Makafui-PC on 3/12/2017.
 */

public class EventViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView organizerView;
    public ImageView starView;
    public TextView numStarsView;

    public EventViewHolder(View itemView) {
        super(itemView);

        titleView = (TextView) itemView.findViewById(R.id.post_title);
        organizerView = (TextView) itemView.findViewById(R.id.event_organizer);
        starView = (ImageView) itemView.findViewById(R.id.star);
        numStarsView = (TextView) itemView.findViewById(R.id.post_num_stars);

    }

    public void bindToPost(Event event, View.OnClickListener starClickListener) {
        titleView.setText(event.title);
        organizerView.setText(event.organizer);
        numStarsView.setText(String.valueOf(event.starCount));

        starView.setOnClickListener(starClickListener);
    }
}
