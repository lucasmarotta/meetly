package br.ufba.dcc.meetly.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

import br.ufba.dcc.meetly.R;
import br.ufba.dcc.meetly.activity.MainActivity;
import br.ufba.dcc.meetly.dao.MeetingDAO;
import br.ufba.dcc.meetly.models.MeetingModel;
import br.ufba.dcc.meetly.utils.MeetingAdapter;

public class ArchivedFragment extends android.support.v4.app.Fragment
{
    private View mArchivedView;
    private Context context;
    private MainActivity mainActivity;
    private RecyclerView mRecyclerView;
    private MeetingAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MeetingDAO meetingDAO;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * Load Archived view to the fragment
     * @param inflater LayoutInflater object
     * @param container ViewGroup object
     * @param savedInstanceState Bundle object
     * @return Home view
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mArchivedView = inflater.inflate(R.layout.view_home, container, false);
        mainActivity = (MainActivity) getActivity();
        context = mArchivedView.getContext();
        mainActivity.setTitle(getResources().getString(R.string.title_view_archived));
        meetingDAO = new MeetingDAO(context);

        mRecyclerView = (RecyclerView) mArchivedView.findViewById(R.id.home_meeting_list);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<MeetingModel> meetingItems = meetingDAO.getArchivedMeetings();
        mAdapter = new MeetingAdapter(context, meetingItems);
        mRecyclerView.setAdapter(mAdapter);

        return mArchivedView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu,inflater);
    }

    /**
     * Action Bar options listener
     * @param item items of Action Bar
     * @return Return the selected action bar item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.action_menu_go_to_latest:
            {
                mRecyclerView.getLayoutManager().scrollToPosition(0);
                break;
            }
            case R.id.action_menu_refresh:
            {
                mAdapter.updateItems(meetingDAO.getArchivedMeetings());
                break;
            }
            case R.id.action_menu_settings:
            {
                showSettingsDialog();
            }
        }
        return true;
    }

    private void showSettingsDialog()
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.view_daterange_picker_dialog);
        dialog.setTitle("Defina um período");

        TextView startDate = (TextView) dialog.findViewById(R.id.dialog_start_date);
        TextView endDate = (TextView) dialog.findViewById(R.id.dialog_end_date);

        startDate.setText("Banana");
        endDate.setText("Pijama");

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    //Remover depois, só pra testar
    private ArrayList<MeetingModel> simulateMeetings()
    {
        ArrayList<MeetingModel> meetingItems = new ArrayList<MeetingModel>();
        MeetingModel m = new MeetingModel();
        m.setTitle("Novo Produto");
        m.setDate("2017-3-15");
        m.setTime("12:45");
        m.setAddressState("Bahia");
        m.setAddressCity("Salvador");
        m.setAddressName("Rua Ceará 121");
        m.setUserId(2);
        meetingItems.add(m);
        m = new MeetingModel();
        m.setTitle("Novo Produto");
        m.setTime("12:45");
        m.setDate("2017-3-15");
        m.setAddressState("Bahia");
        m.setAddressCity("Salvador");
        m.setAddressName("Rua Ceará 121");
        m.setUserId(1);
        meetingItems.add(m);
        m = new MeetingModel();
        m.setTitle("Novo Produto");
        m.setTime("12:45");
        m.setDate("2017-3-15");
        m.setAddressState("Bahia");
        m.setAddressCity("Salvador");
        m.setAddressName("Rua Ceará 121");
        m.setUserId(2);
        meetingItems.add(m);
        m = new MeetingModel();
        m.setTitle("Novo Produto");
        m.setTime("12:45");
        m.setDate("2017-3-15");
        m.setAddressState("Bahia");
        m.setAddressCity("Salvador");
        m.setAddressName("Rua Ceará 121");
        m.setUserId(2);
        meetingItems.add(m);
        m = new MeetingModel();
        m.setTitle("Novo Produto");
        m.setTime("12:45");
        m.setDate("2017-3-15");
        m.setAddressState("Bahia");
        m.setAddressCity("Salvador");
        m.setAddressName("Rua Ceará 121");
        m.setUserId(2);
        meetingItems.add(m);
        m = new MeetingModel();
        m.setTitle("Novo Produto");
        m.setTime("12:45");
        m.setDate("2017-3-15");
        m.setAddressState("Bahia");
        m.setAddressCity("Salvador");
        m.setAddressName("Rua Ceará 121");
        m.setUserId(2);
        meetingItems.add(m);
        m = new MeetingModel();
        m.setTitle("Novo Produto");
        m.setTime("12:45");
        m.setDate("2017-3-15");
        m.setAddressState("Bahia");
        m.setAddressCity("Salvador");
        m.setAddressName("Rua Ceará 121");
        m.setUserId(2);
        meetingItems.add(m);
        return meetingItems;
    }
}
