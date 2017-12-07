package shafin.shareride;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    EditText editTextFrom,editTextTo,editTextTime,editTextVehicle;
    Button button;
    String userid;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        final FirebaseUser user=firebaseAuth.getCurrentUser();
        userid=user.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("postRide").child(userid);

        editTextFrom=(EditText)findViewById(R.id.from);
        editTextTime=(EditText)findViewById(R.id.time);
        editTextTo=(EditText)findViewById(R.id.to);
        editTextVehicle=(EditText)findViewById(R.id.vehicle);

        button=(Button)findViewById(R.id.postButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postInformation();
            }
        });

    }

    private void postInformation() {
        String from=editTextFrom.getText().toString().trim();
        if (from.isEmpty()){
            editTextFrom.setError("Name required");
            editTextFrom.requestFocus();
            return;
        }
        String to=editTextTo.getText().toString().trim();
        if (to.isEmpty()){
            editTextTo.setError("ID required");
            editTextTo.requestFocus();
            return;
        }
        String time=editTextTime.getText().toString().trim();
        if (time.isEmpty()){
            editTextTime.setError("Phone required");
            editTextTime.requestFocus();
            return;
        }
        String vehicle=editTextVehicle.getText().toString().trim();
        if (vehicle.isEmpty()){
            editTextVehicle.setError("Phone required");
            editTextVehicle.requestFocus();
            return;
        }
        FirebaseUser user=firebaseAuth.getCurrentUser();
        userid=user.getUid();
        PostInformation postInformation = new PostInformation(from,to,time,vehicle,userid,user.getDisplayName());


        databaseReference.setValue(postInformation);
        Toast.makeText(this, "Information posted", Toast.LENGTH_SHORT).show();
    }
}
