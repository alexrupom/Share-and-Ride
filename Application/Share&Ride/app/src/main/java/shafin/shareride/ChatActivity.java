package shafin.shareride;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.LinkedList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    TextView textViewChatname;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String userid;
    EditText editTextMessageArea;

    ImageView imageViewSendButton;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        textViewChatname=(TextView)findViewById(R.id.chatname);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        String stuff = bundle.getString("chatname");
        String id = bundle.getString("id");
        textViewChatname.setText(stuff);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        final FirebaseUser user=firebaseAuth.getCurrentUser();
        userid=user.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("postRide").child(id);

        imageViewSendButton=(ImageView)findViewById(R.id.sendButton);
        editTextMessageArea=(EditText)findViewById(R.id.messageArea);
        listView=(ListView)findViewById(R.id.listview);

        imageViewSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChatMessage chat  = new ChatMessage(user.getDisplayName(), editTextMessageArea.getText().toString());
                databaseReference.push().setValue(chat);
                editTextMessageArea.setText("");
                Toast.makeText(getApplicationContext(),"Your message is sent",Toast.LENGTH_SHORT).show();



            }
        });





    }

}
