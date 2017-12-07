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

public class ComplainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String userid;
    EditText editText;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        final FirebaseUser user=firebaseAuth.getCurrentUser();
        userid=user.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("complain").child(userid);


        editText=(EditText)findViewById(R.id.editText);

        button=(Button)findViewById(R.id.complainbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postInformation();
            }
        });

    }

    private void postInformation() {

        String complain=editText.getText().toString().trim();
        if (complain.isEmpty()){
            editText.setError("Text required");
            editText.requestFocus();
            return;
        }
        FirebaseUser user=firebaseAuth.getCurrentUser();

        ComplainInformation postInformation = new ComplainInformation(user.getDisplayName(),user.getUid(),complain);


        databaseReference.push().setValue(postInformation);
        Toast.makeText(this, "Information posted", Toast.LENGTH_SHORT).show();
    }
}
