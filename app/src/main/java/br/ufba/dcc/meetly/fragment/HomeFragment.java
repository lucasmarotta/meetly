package br.ufba.dcc.meetly.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.ufba.dcc.meetly.R;

public class HomeFragment extends android.support.v4.app.Fragment
{
    private View mHomeView;

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
        getActivity().setTitle(getResources().getString(R.string.title_activity_home));
        return mHomeView;
    }
}
