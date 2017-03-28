package br.ufba.dcc.meetly.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.ufba.dcc.meetly.R;
import br.ufba.dcc.meetly.activity.MainActivity;
import br.ufba.dcc.meetly.models.MeetingModel;
import br.ufba.dcc.meetly.utils.MeetingAdapter;

public class HomeFragment extends android.support.v4.app.Fragment
{
    private View mHomeView;
    private Context context;
    private MainActivity mainActivity;
    private RecyclerView mRecyclerView;
    private MeetingAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    /**
     * Load Home view to the fragment
     * @param inflater LayoutInflater object
     * @param container ViewGroup object
     * @param savedInstanceState Bundle object
     * @return Home view
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mHomeView = inflater.inflate(R.layout.view_home, container, false);
        mainActivity = (MainActivity) getActivity();
        context = mHomeView.getContext();
        mainActivity.setTitle(getResources().getString(R.string.title_activity_home));

        mRecyclerView = (RecyclerView) mHomeView.findViewById(R.id.home_meeting_list);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<MeetingModel> meetingItems = new ArrayList<MeetingModel>();
        mAdapter = new MeetingAdapter(meetingItems);
        mRecyclerView.setAdapter(mAdapter);


        return mHomeView;
    }
}
