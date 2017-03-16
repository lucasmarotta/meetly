package br.ufba.dcc.meetly.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import br.ufba.dcc.meetly.R;
import br.ufba.dcc.meetly.fragment.ArchivedFragment;
import br.ufba.dcc.meetly.fragment.HomeFragment;
import br.ufba.dcc.meetly.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FragmentManager mFragmentManager;

    /**
     * Initialize Activity
     * @param savedInstanceState Bundle object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mFragmentManager = getSupportFragmentManager();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mFragmentManager.beginTransaction().replace(R.id.content_frame_main, new HomeFragment()).commit();
    }

    /**
     * Handler for the back button of navigation bar
     */
    @Override
    public void onBackPressed()
    {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Context Menu options listener
     * @param menu context menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options_main, menu);
        return true;
    }

    /**
     * Action Bar options listener
     * @param item items of Action Bar
     * @return Return the selected action bar item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handle the drawer navigation drawer item for Home item
     * @param item item from navigation drawer
     */
    public void goHomeAction(MenuItem item)
    {
        mFragmentManager.beginTransaction().replace(R.id.content_frame_main, new HomeFragment()).commit();
        item.setChecked(true);
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    /**
     * Handle the drawer navigation drawer for Profile item
     * @param item item from navigation drawer
     */
    public void goProfileAction(MenuItem item)
    {
        mFragmentManager.beginTransaction().replace(R.id.content_frame_main, new ProfileFragment()).commit();
        item.setChecked(true);
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    /**
     * Handle the drawer navigation for Archived item
     * @param item item from navigation drawer
     */
    public void goArchivedAction(MenuItem item)
    {
        mFragmentManager.beginTransaction().replace(R.id.content_frame_main, new ArchivedFragment()).commit();
        item.setChecked(true);
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    /**
     * Handle the drawer navigation for Logout item
     * @param item item from navigation drawer
     */
    public void logoutAction(MenuItem item)
    {
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Handle the drawer navigation for Logout item
     * @param item item from navigation drawer
     */
    public void searchAction(MenuItem item)
    {

    }

    /**
     * Listener for navigation drawer. Disabled to use onClick attributes
      * @param item items from navigation draeer
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        return false;
    }
}
