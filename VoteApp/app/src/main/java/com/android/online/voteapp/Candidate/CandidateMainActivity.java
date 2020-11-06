package com.android.online.voteapp.Candidate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.online.voteapp.Candidate.Fragments.HomeFragment;
import com.android.online.voteapp.Candidate.Fragments.ProfileFragment;
import com.android.online.voteapp.Candidate.Fragments.SubmitFragment;
import com.android.online.voteapp.Candidate.Fragments.UpdateFragment;
import com.android.online.voteapp.R;
import com.android.online.voteapp.Session.Prevalent;
import com.android.online.voteapp.authenticate;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class CandidateMainActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_main);

        Toolbar toolbar=findViewById(R.id.vet_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("ivote Dashboard");

        drawer= findViewById(R.id.vet_drawer_layout);
        navigationView= findViewById(R.id.cad_nav_view);
        View headerView = navigationView.getHeaderView(0);

        TextView user= headerView.findViewById(R.id.nav_header_name);
        TextView phone= headerView.findViewById(R.id.nav_header_phone);
        CircleImageView profile_img= headerView.findViewById(R.id.user_profile_image);
/*
        user.setText(Prevalent.currentOnlineUser.getName());
        phone.setText(Prevalent.currentOnlineUser.getPhone());

        Glide.with(this).load(Prevalent.currentOnlineUser.getImage()).into(profile_img);

 */

        setNavigation(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState== null){
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container_admin,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.user_nav_home);
            }


        CheckApproval();
    }



    private void setNavigation(Toolbar toolbar) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem Item) {
                switch (Item.getItemId()) {
                    case R.id.user_nav_home:
                        toolbar.setTitle("ivote Dashboard");
                        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container_admin,
                                new HomeFragment()).commit();

                        break;
                    case R.id.user_nav_submit:
                        toolbar.setTitle("ivote Submit Details");
                        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container_admin,
                                new SubmitFragment()).commit();

                        break;

                    case R.id.user_nav_profile:
                        toolbar.setTitle("ivote Update Profile");
                        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container_admin,
                                new ProfileFragment()).commit();

                        break;

                    case R.id.user_nav_update:
                        toolbar.setTitle("ivote Update Seat");
                       /* getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container_admin,
                                new UpdateFragment()).commit();

                        */

                        Toast.makeText(CandidateMainActivity.this, "no approved forms.", Toast.LENGTH_SHORT).show();

                        break;

                    case R.id.customer_nav_signout:

                        Signout();

                        break;

                    case R.id.user_nav_share:

                        Toast.makeText(CandidateMainActivity.this, "Share this app", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.user_nav_send:

                        Toast.makeText(CandidateMainActivity.this, "Send this app", Toast.LENGTH_SHORT).show();
                        break;

                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void Signout() {

        startActivity(new Intent(this, authenticate.class));
        finish();
    }

   private void hideItem( int id)
    {
        navigationView = (NavigationView) findViewById(R.id.cad_nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(id).setVisible(false);
    }

    private void CheckApproval() {


    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }}


}