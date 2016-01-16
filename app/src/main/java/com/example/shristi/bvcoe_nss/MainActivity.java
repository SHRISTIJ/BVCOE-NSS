package com.example.shristi.bvcoe_nss;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView mtextView=(TextView) findViewById(R.id.mtextView);
        String htmlString="<B><u>ABOUT US</u></B>";
        mtextView.setText(Html.fromHtml(htmlString));
        TextView text = (TextView) findViewById(R.id.text);
        text.setText(" ■ Faculty Coordinators:\nDr. Anil Kumar (NSS Officer)\n\n ■ Student Representatives:\nLakshay Mutereja and Meenal Gupta\n\n ■ Description about the cell:\nThe National Service Scheme (NSS) was established in India on September 24, 1969.\nThe NSS is an Indian government-sponsored public service program conducted by the Department of Youth Affairs and Sports of the Government of India.\nIn Bharati Vidyapeeth’s College of Engineering, New Delhi;\nNSS Unit  had started in 2013 under the guidance of NSS officer Dr. Anil Kumar (A.P. in Physics Applied Sc. Deptt.)");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_reg) {
            Intent i=new Intent (getApplicationContext(),registration.class);
            startActivity(i);
        } else if (id == R.id.nav_upload) {
            Intent i=new Intent (getApplicationContext(),Upload.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
