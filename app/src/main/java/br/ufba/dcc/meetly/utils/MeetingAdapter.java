/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package br.ufba.dcc.meetly.utils;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.ufba.dcc.meetly.R;
import br.ufba.dcc.meetly.helper.SessionHelper;
import br.ufba.dcc.meetly.models.MeetingModel;
import br.ufba.dcc.meetly.models.UserModel;


public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder>
{

    private ArrayList<MeetingModel> meetingItems;
    private SessionHelper session;


    /**
     * Meeting Item View Holder. Load the elements of the item.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private View itemView;
        private ImageView itemTag;
        private TextView itemTitle;
        private TextView itemDate;
        private TextView itemTime;
        private LinearLayout itemShared;
        private TextView itemLocation;

        public ViewHolder(View view)
        {
            super(view);
            itemView = view;
            setElements();
        }

        public void setElements()
        {
            itemTag = (ImageView) itemView.findViewById(R.id.meeting_item_tag);
            itemTitle = (TextView) itemView.findViewById(R.id.meeting_item_title);
            itemDate = (TextView) itemView.findViewById(R.id.meeting_item_date);
            itemTime = (TextView) itemView.findViewById(R.id.meeting_item_time);
            itemShared = (LinearLayout) itemView.findViewById(R.id.meeting_item_shared);
            itemLocation = (TextView) itemView.findViewById(R.id.meeting_item_location);
        }

        public ImageView getItemTag()
        {
            return itemTag;
        }

        public TextView getItemTitle()
        {
            return itemTitle;
        }

        public TextView getItemDate()
        {
            return itemDate;
        }

        public TextView getItemTime()
        {
            return itemTime;
        }

        public LinearLayout getItemShared()
        {
            return itemShared;
        }

        public TextView getItemLocation()
        {
            return itemLocation;
        }

        public View getItemView()
        {
            return itemView;
        }
    }

    /**
     * Initialize MeetingAdapter
     */
    public MeetingAdapter(ArrayList<MeetingModel> meetingItems)
    {
        this.meetingItems = meetingItems;
    }

    /**
     * Create the Meeting Item from layout
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.meeting_item, viewGroup, false);
        return new ViewHolder(v);
    }

    /**
     * Load values into Meeting Item
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position)
    {
        if(session == null) {
            session = new SessionHelper(viewHolder.getItemView().getContext());
        }
        UserModel user = session.getSessionUser();
        MeetingModel meeting = meetingItems.get(position);
        if(meeting.getColor() != null) {
            viewHolder.getItemTag().setBackgroundColor(Color.parseColor(meeting.getColor()));
        } else {
            viewHolder.getItemTag().setBackgroundColor(ContextCompat.getColor(viewHolder.getItemView().getContext(),R.color.md_blue_grey_500));
        }
        viewHolder.getItemTitle().setText(meeting.getTitle());
        viewHolder.getItemDate().setText(meeting.getDateBySqlDate());
        viewHolder.getItemTime().setText(meeting.getTime());

        if(meeting.getUserId() == user.getId()) {
            viewHolder.getItemShared().setVisibility(View.INVISIBLE);
        } else {
            viewHolder.getItemShared().setVisibility(View.VISIBLE);
        }

        viewHolder.getItemLocation().setText(meeting.getAddressName());
        bindElementsEvents(viewHolder,meeting);
    }

    /**
     * Get Adapter Size
     * @return int adapter size
     */
    @Override
    public int getItemCount()
    {
        return meetingItems.size();
    }

    /**
     * Bind Events to Meeting Item elements
     * @param viewHolder ViewHolder holder
     * @param meetingModel MeetingModel meeting model from position
     */
    public void bindElementsEvents(final ViewHolder viewHolder, final MeetingModel meetingModel)
    {
        //Abre o Mapa
        viewHolder.getItemLocation().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+meetingModel.getAddressName()));
                viewHolder.getItemView().getContext().startActivity(intent);
            }
        });

        viewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });
    }
}
