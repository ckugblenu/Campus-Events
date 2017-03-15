package com.greencampus.campusevents.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.greencampus.campusevents.R;
import com.greencampus.campusevents.models.Organizer;

/**
 * Created by Makafui-PC on 3/12/2017.
 */

public class OrganizerViewHolder extends RecyclerView.ViewHolder {

    public TextView nameView;
    public ImageView starView;
    public TextView numStarsView;

    public OrganizerViewHolder(View itemView) {

        super( itemView);
        nameView = (TextView) itemView.findViewById(R.id.event_organizer);
        starView = (ImageView) itemView.findViewById(R.id.star);
        numStarsView = (TextView) itemView.findViewById(R.id.organizer_num_stars);

    }

    public void bindToOrganizer(Organizer organizer, View.OnClickListener starClickListener) {
        nameView.setText(organizer.name);
        numStarsView.setText(String.valueOf(organizer.starCount));

        starView.setOnClickListener(starClickListener);
    }
}
