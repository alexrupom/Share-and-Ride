package shafin.shareride;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FindRideActivity extends AppCompatActivity {
    private static final String TAG = "FindRideActivity";
ListView listView;
TextView textView;
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

        listView = (ListView) findViewById(R.id.findride);


        FirebaseDatabase database=FirebaseDatabase.getInstance();
        myRef = database.getReference("post");



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
                PostInformation chat = messages.get(position);
                ( (TextView) view.findViewById(R.id.line_a)).setText("From: "+chat.getFrom());
                ( (TextView) view.findViewById(R.id.line_b)).setText("To: "+chat.getTo());
                ( (TextView) view.findViewById(R.id.line_c)).setText("Time: "+chat.getTime());
                ( (TextView) view.findViewById(R.id.line_d)).setText("Vehicle: "+chat.getVehicle());
                ( (TextView) view.findViewById(R.id.line_e)).setText("Posted by: "+chat.getId());


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

    private void toastMsg(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}

