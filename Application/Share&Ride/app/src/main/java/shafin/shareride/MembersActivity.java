package shafin.shareride;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MembersActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MembersActivity";
    FirebaseAuth firebaseAuth;
    TextView textViewemail;
    Button logout;
    TextView textViewName,textViewID,textViewPhone;
    ImageView imageView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth.AuthStateListener mAuthListner;
    String userid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewemail=(TextView)findViewById(R.id.email);
        textViewName=(TextView)findViewById(R.id.name);
        textViewID=(TextView)findViewById(R.id.id);
        textViewPhone=(TextView)findViewById(R.id.phone);
        imageView=(ImageView)findViewById(R.id.image);

        loadUserInformation();


        logout=(Button)findViewById(R.id.logout);
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        final FirebaseUser user=firebaseAuth.getCurrentUser();
        userid=user.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child(userid);

        if (firebaseAuth.getCurrentUser()==null){
            finish();
            Intent intent=new Intent(MembersActivity.this,LoginActivity.class);
            startActivity(intent);

        }




        textViewemail.setText( user.getEmail());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                firebaseAuth.signOut();
                Intent intent=new Intent(MembersActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "You are a current enrolled NSU student.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


               UserInformation userinfo =dataSnapshot.getValue(UserInformation.class);

               if (userinfo==null){
                   return;
               }
               String id=userinfo.getId();
               String phone=userinfo.getPhone();
               textViewPhone.setText("Phone: "+phone);
               textViewID.setText("ID: "+id);
                //Toast.makeText(getApplicationContext(),"id is : "+id,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });


    }



    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            Intent intent=new Intent(MembersActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }

    private void loadUserInformation() {


       FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
       if (user!=null){
           if (user.getPhotoUrl()!=null){

              Glide.with(this).load(user.getPhotoUrl() .toString()).into(imageView);
              String photoUrl =user.getPhotoUrl().toString() ;
           }
           if (user.getDisplayName()!=null) {
               textViewName.setText(user.getDisplayName());
           }
       }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.members, menu);
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
            Intent intent=new Intent(MembersActivity.this,ToolActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent=new Intent(MembersActivity.this, FindRideActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent=new Intent(MembersActivity.this, PostActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            Intent intent=new Intent(MembersActivity.this, FeedbackActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Intent intent=new Intent(MembersActivity.this, ToolActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Intent intent=new Intent(MembersActivity.this, FAQActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {
            Intent intent=new Intent(MembersActivity.this, ComplainActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
