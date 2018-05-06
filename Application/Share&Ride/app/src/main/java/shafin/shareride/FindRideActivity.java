package shafin.shareride;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FindRideActivity extends AppCompatActivity {
    private static final String TAG = "FindRideActivity";
    ListView listView;
    EditText editTextSearch;
    Button buttonSearch;
    String i;
    Button buttonAddnow;
    public static String name;


    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth.AuthStateListener mAuthListner;
    String userid;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ride);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        listView = (ListView) findViewById(R.id.findride);
        editTextSearch=(EditText)findViewById(R.id.search);
        buttonSearch=(Button)findViewById(R.id.searchbutton);



        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        myRef = database.getReference("postRide");

        showingFromDatabase();



        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search=editTextSearch.getText().toString();
                if (search==null){
                    Toast.makeText(getApplicationContext(),"Please enter something",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent=new Intent(FindRideActivity.this,SearchActivity.class);


                    //Create the bundle
                    Bundle searchitem = new Bundle();
                    //Add your data to bundle
                    searchitem.putString("searchitem", search);

                    //Add the bundle to the intent
                    intent.putExtras(searchitem);

                    startActivity(intent);

                }
            }
        });


    }

    private void showingFromDatabase() {

//showing
        final List<PostInformation>messages=new LinkedList<>();
        final ArrayAdapter<PostInformation>adapter = new ArrayAdapter<PostInformation>(
                this, android.R.layout.simple_list_item_2, messages
        ){
            @NonNull
            @Override
            public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

                if (view == null){
                    view=getLayoutInflater().inflate(R.layout.twolines, parent, false);
                }



                final PostInformation chat = messages.get(position);
                ( (TextView) view.findViewById(R.id.line_a)).setText("From: "+chat.getFrom());
                ( (TextView) view.findViewById(R.id.line_b)).setText("To: "+chat.getTo());
                ( (TextView) view.findViewById(R.id.line_c)).setText("Time: "+chat.getTime());
                ( (TextView) view.findViewById(R.id.line_d)).setText("Vehicle: "+chat.getVehicle());
                ( (TextView) view.findViewById(R.id.line_e)).setText("Poasted by: "+chat.getUserName());
                ( (Button  ) view.findViewById(R.id.addnow)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String chatname=chat.getUserName()+", has created the post. \nFrom: "+chat.getFrom()+" To: "+chat.getTo()+"\nat "+chat.getTime()+" using "+chat.getVehicle();
                        String id=chat.getId();
                        // Toast.makeText(getApplicationContext(),chat.getUserName(),Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(FindRideActivity.this, ChatActivity.class);
                        //Create the bundle
                        Bundle bundle = new Bundle();
                        Bundle b = new Bundle();
                        //Add your data to bundle
                        bundle.putString("chatname", chatname);
                        b.putString("id",id);

                        //Add the bundle to the intent
                        i.putExtras(bundle);
                        i.putExtras(b);
                        startActivity(i);
                    }
                });;


                i=chat.getId().toString();
                databaseReference= FirebaseDatabase.getInstance().getReference().child(i);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        UserInformation userinfo =dataSnapshot.getValue(UserInformation.class);

                        if (userinfo==null){
                            return;
                        }
                        name=userinfo.getName();







                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Log.w(TAG, "Failed to read value.", databaseError.toException());
                    }
                });



                return view;}
        };


        listView.setAdapter(adapter );


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PostInformation chat = dataSnapshot.getValue(PostInformation.class);
                messages.add(chat);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });








    }





}

