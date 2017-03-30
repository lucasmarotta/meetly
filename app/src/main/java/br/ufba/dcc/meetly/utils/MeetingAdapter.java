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

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.ufba.dcc.meetly.R;
import br.ufba.dcc.meetly.activity.MeetingActivity;
import br.ufba.dcc.meetly.dao.MeetingDAO;
import br.ufba.dcc.meetly.helper.SessionHelper;
import br.ufba.dcc.meetly.models.MeetingModel;
import br.ufba.dcc.meetly.models.UserModel;


public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder>
{

    private ArrayList<MeetingModel> meetingItems;
    private SessionHelper session;
    private Context context;
    private int lastPosition = -1;
    private int position;


    /**
     * Meeting Item View Holder. Load the elements of the item.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener
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
            view.setOnCreateContextMenuListener(this);
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

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
        {
            final RecyclerView recyclerView  = (RecyclerView) v.getRootView().findViewById(R.id.home_meeting_list);
            final MeetingAdapter adapter = (MeetingAdapter) recyclerView.getAdapter();
            final MeetingModel meeting =  adapter.getItemByPosition(getAdapterPosition());
            final UserModel sessionUser = adapter.getSession().getSessionUser();

            MenuItem menuItem = menu.add("Ver Local");
            menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item)
                {
                    String mapLocation = "Brasil, "+meeting.getAddressState()+", "+meeting.getAddressCity()+", "+meeting.getAddressName();

                    if(meeting.getAddressNumber() != null) {
                        mapLocation = mapLocation+", "+meeting.getAddressNumber();
                    }

                    if(meeting.getAddressCep() != null) {
                        mapLocation = mapLocation+", CEP "+meeting.getAddressCep();
                    }

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+mapLocation));
                    itemView.getContext().startActivity(intent);

                    return false;
                }
            });

            menuItem = menu.add("Visualizar");
            menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item)
                {
                    Intent intent = new Intent(itemView.getContext(),MeetingActivity.class);
                    intent.putExtra("meeting",meeting);
                    itemView.getContext().startActivity(intent);
                    return false;
                }
            });

            if(sessionUser.getId() == meeting.getUserId())
            {
                menuItem = menu.add("Excluir");
                menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        MeetingDAO meetingDAO = new MeetingDAO(itemView.getContext());
                        if(meetingDAO.delete(meeting))
                        {
                            adapter.removeItem(getAdapterPosition());
                        }
                        return false;
                    }
                });
            }
        }
    }

    /**
     * Initialize MeetingAdapter
     */
    public MeetingAdapter(Context context, ArrayList<MeetingModel> meetingItems)
    {
        this.context = context;
        this.meetingItems = meetingItems;
        session = new SessionHelper(context);
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
        UserModel user = session.getSessionUser();
        MeetingModel meeting = meetingItems.get(position);
        if(meeting.getColor() != null) {
            viewHolder.getItemTag().setBackgroundColor(Color.parseColor(meeting.getColor()));
        } else {
            viewHolder.getItemTag().setBackgroundColor(ContextCompat.getColor(context,R.color.md_blue_grey_500));
        }
        viewHolder.getItemTitle().setText(meeting.getTitle());
        viewHolder.getItemDate().setText(meeting.getDateBySqlDate());
        viewHolder.getItemTime().setText(meeting.getTime());

        if(meeting.getUserId().intValue() == user.getId().intValue()) {
            viewHolder.getItemShared().setVisibility(View.INVISIBLE);
        } else {
            viewHolder.getItemShared().setVisibility(View.VISIBLE);
        }

        viewHolder.getItemLocation().setText(meeting.getAddressState()+", "+meeting.getAddressCity()+", "+meeting.getAddressName());
        bindElementsEvents(viewHolder,meeting);
        setAnimation(viewHolder.getItemView(),position);
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
     * @param meeting MeetingModel meeting model from position
     */
    public void bindElementsEvents(final ViewHolder viewHolder, final MeetingModel meeting)
    {
        //Abre o Mapa
        viewHolder.getItemLocation().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String mapLocation = "Brasil, "+meeting.getAddressState()+", "+meeting.getAddressCity()+", "+meeting.getAddressName();

                if(meeting.getAddressNumber() != null) {
                    mapLocation = mapLocation+", "+meeting.getAddressNumber();
                }

                if(meeting.getAddressCep() != null) {
                    mapLocation = mapLocation+", CEP "+meeting.getAddressCep();
                }

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+mapLocation));
                viewHolder.getItemView().getContext().startActivity(intent);
            }
        });

        viewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(viewHolder.getItemView().getContext(),MeetingActivity.class);
                intent.putExtra("meeting",meeting);
                viewHolder.getItemView().getContext().startActivity(intent);
            }
        });
    }

    public void updateItems(ArrayList<MeetingModel> meetingItems)
    {
        this.meetingItems.clear();
        this.meetingItems.addAll(meetingItems);
        notifyDataSetChanged();
    }

    public void removeItem(int position)
    {
        meetingItems.remove(position);
        if(lastPosition == position)
        {
            if(lastPosition >= 0) {
                lastPosition--;
            } else {
                lastPosition = -1;
            }
        }
        notifyDataSetChanged();
    }

    public void addItem(MeetingModel meeting)
    {
        meetingItems.add(meeting);
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public SessionHelper getSession()
    {
        return session;
    }

    public MeetingModel getItemByPosition(int position)
    {
        return meetingItems.get(position);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
