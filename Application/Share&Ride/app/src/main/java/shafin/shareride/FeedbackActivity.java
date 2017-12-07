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

public class FeedbackActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String userid;
    EditText editText;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        final FirebaseUser user=firebaseAuth.getCurrentUser();
        userid=user.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("feedback").child(userid);


        editText=(EditText)findViewById(R.id.editText);

        button=(Button)findViewById(R.id.feedbackbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postInformation();
            }
        });

    }

    private void postInformation() {

        String feedback=editText.getText().toString().trim();
        if (feedback.isEmpty()){
            editText.setError("Text required");
            editText.requestFocus();
            return;
        }
        FirebaseUser user=firebaseAuth.getCurrentUser();

        FeedbackInformation postInformation = new FeedbackInformation(user.getDisplayName(),user.getUid(),feedback);


        databaseReference.push().setValue(postInformation);
        Toast.makeText(this, "Thank you for your feedback.", Toast.LENGTH_SHORT).show();
    }
}
