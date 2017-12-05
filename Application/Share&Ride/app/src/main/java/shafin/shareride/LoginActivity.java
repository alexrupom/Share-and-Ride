package shafin.shareride;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    Button login; TextView textView;
    EditText editTextEmail,editTextPassword;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        editTextEmail=(EditText)findViewById(R.id.input_email);
        editTextPassword=(EditText)findViewById(R.id.input_password);
        progressDialog= new ProgressDialog(this);
        firebaseAuth= FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!= null){

            finish();
            Intent intent=new Intent(LoginActivity.this,MembersActivity.class);
            startActivity(intent);
            Toast.makeText(this, "You are already logged in.", Toast.LENGTH_SHORT).show();
        }

        login=(Button)findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent=new Intent(LoginActivity.this,MembersActivity.class);
                //startActivity(intent);
                userlogin();
            }
        });

        textView=(TextView)findViewById(R.id.link_signup);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,SigninActivity.class);
                startActivity(intent);
            }
        });

    }

    private void userlogin() {
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();

        if (email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();

            return;
        }
        if (password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        if (password.length()<6)
        {
            editTextPassword.setError("Minimum length should be 6 ");
            editTextPassword.requestFocus();
            return;
        }

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              progressDialog.dismiss();
                if (task.isSuccessful()){
                    finish();
                    Intent intent=new Intent(LoginActivity.this,MembersActivity.class);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), "User is successfully Loggedin", Toast.LENGTH_SHORT).show();
                }
                else {

                    Toast.makeText(getApplicationContext(), "User is not successfully Loggedin", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
