package gregmachado.com.panappfirebase.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import gregmachado.com.panappfirebase.R;
import gregmachado.com.panappfirebase.adapter.FeedAdapter;
import gregmachado.com.panappfirebase.util.LibraryClass;

/**
 * Created by gregmachado on 29/10/16.
 */

public class UserMainActivity extends CommonActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "UserMainActivity";
    private TextView tvUserName, tvUserEmail;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private RecyclerView rvFeed;
    private TextView tvNoFeed;
    private FeedAdapter adapter;
    private ImageView icFeed;
    private String bakeryName, userName, userID, userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_base);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent it = getIntent();
        params = it.getExtras();
        if (params != null) {
            userName = params.getString("name");
            userEmail = params.getString("email");
        }
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {
                    Intent intent = new Intent(UserMainActivity.this, SelectLoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        databaseReference = LibraryClass.getFirebase();
        databaseReference.getRef();
        //frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        initViews();
        rvFeed.setItemAnimator(new DefaultItemAnimator());
        rvFeed.setLayoutManager(new LinearLayoutManager(UserMainActivity.this));
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        assert firebaseUser != null;
        userID = firebaseUser.getUid();
        firebaseAuth.addAuthStateListener(authStateListener);
        String token = FirebaseInstanceId.getInstance().getToken();
        mDatabaseReference.child("users").child(userID).child("token").setValue(token);
        loadFeed();
    }

    private void loadFeed() {
        openProgressBar();
        mDatabaseReference.child("users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("feed")) {
                    closeProgressBar();
                    if (tvNoFeed.getVisibility() == View.VISIBLE) {
                        tvNoFeed.setVisibility(View.GONE);
                    }
                    if (icFeed.getVisibility() == View.VISIBLE) {
                        icFeed.setVisibility(View.GONE);
                    }
                    adapter = new FeedAdapter(mDatabaseReference.child("users").child(userID).child("feed").getRef(),
                            UserMainActivity.this, false
                    ) {};
                    rvFeed.setAdapter(adapter);
                } else {
                    closeProgressBar();
                    tvNoFeed.setVisibility(View.VISIBLE);
                    icFeed.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        params.putString("id", userID);
        params.putBoolean("type", false);
        params.putString("name", userName);

        if (id == R.id.nav_bakerys) {
            Intent intentBakeryList = new Intent(UserMainActivity.this, BakeryListActivity.class);
            intentBakeryList.putExtras(params);
            startActivity(intentBakeryList);
        } else if (id == R.id.nav_favorites) {
            Intent intentFavoriteBakeryList = new Intent(UserMainActivity.this, FavoriteListActivity.class);
            intentFavoriteBakeryList.putExtras(params);
            startActivity(intentFavoriteBakeryList);
        } else if (id == R.id.nav_configuration) {

        } else if (id == R.id.nav_follow_orders) {
            Intent intentRequest = new Intent(UserMainActivity.this, RequestActivity.class);
            intentRequest.putExtras(params);
            startActivity(intentRequest);
        } else if (id == R.id.nav_history) {
            Intent intentHistoric = new Intent(UserMainActivity.this, HistoricActivity.class);
            intentHistoric.putExtras(params);
            startActivity(intentHistoric);
        } else if (id == R.id.nav_my_adrees) {
            Intent intentAdressList = new Intent(UserMainActivity.this, AdressListActivity.class);
            intentAdressList.putExtras(params);
            startActivity(intentAdressList);
        } else if (id == R.id.nav_offers) {
            Intent intentOffers = new Intent(UserMainActivity.this, OfferActivity.class);
            intentOffers.putExtras(params);
            startActivity(intentOffers);
        } else if (id == R.id.nav_talk_whit_us) {

        } else if (id == R.id.nav_exit) {
            onBackPressed();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        tvUserName = (TextView) header.findViewById(R.id.tv_user_name);
        tvUserEmail = (TextView) header.findViewById(R.id.tv_user_email);
        tvUserName.setText(userName);
        tvUserEmail.setText(userEmail);
        tvNoFeed = (TextView) findViewById(R.id.tv_no_feed);
        icFeed = (ImageView) findViewById(R.id.ic_feed);
        progressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        rvFeed = (RecyclerView) findViewById(R.id.rv_feed);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sair?");
        builder.setMessage("Deseja realmente sair?");
        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });
        builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}