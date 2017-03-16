package br.ufba.dcc.meetly.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.ufba.dcc.meetly.R;

public class ArchivedFragment extends android.support.v4.app.Fragment
{
    private View mArchivedView;

    /**
     * Load Archived view to the fragment
     * @param inflater LayoutInflater object
     * @param container ViewGroup object
     * @param savedInstanceState Bundle object
     * @return Archived view
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mArchivedView = inflater.inflate(R.layout.view_archived, container, false);
        return mArchivedView;
    }
}
