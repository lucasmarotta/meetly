package br.ufba.dcc.meetly.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.ufba.dcc.meetly.R;

public class ProfileFragment extends android.support.v4.app.Fragment
{
    private View mProfileView;

    /**
     * Load Profile view to the fragment
     * @param inflater LayoutInflater object
     * @param container ViewGroup object
     * @param savedInstanceState Bundle object
     * @return Profile view
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mProfileView = inflater.inflate(R.layout.view_profile, container, false);
        return mProfileView;
    }
}
