package shafin.shareride;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email,password;
    Button signup;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        firebaseAuth= FirebaseAuth.getInstance();

        progressDialog= new ProgressDialog(this);
      //  progressBar=new ProgressBar(this);

        email=(EditText)findViewById(R.id.your_email_address);
        password=(EditText)findViewById(R.id.create_new_password);
        signup=(Button)findViewById(R.id.signup);

        signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view ==signup){
            registeruser();
        }
    }

    private void registeruser() {
        String emails=email.getText().toString().trim();
        String pass=password.getText().toString().trim();
        if (TextUtils.isEmpty(emails)){
            Toast.makeText(this, "Email is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering user...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(emails,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
              if (task.isSuccessful()){

                  Toast.makeText(getApplicationContext(), "User is successfully registered", Toast.LENGTH_SHORT).show();
              }
              else {

                  Toast.makeText(getApplicationContext(), "User is not successfully registered", Toast.LENGTH_SHORT).show();
              }
            }
        });
    }
}
