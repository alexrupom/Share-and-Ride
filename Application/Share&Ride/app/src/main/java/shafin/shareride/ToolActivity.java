package shafin.shareride;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class ToolActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    EditText editTextName,editTextID,editTextPhone;
    Button button;
    FirebaseAuth firebaseAuth;
    ImageView imageView;
    private static final int CHOOSE_IMAGE=101;
    Uri uriProfileImage ;
    ProgressBar progressBar;
    String profileImageURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        databaseReference= FirebaseDatabase.getInstance().getReference();

        firebaseAuth= FirebaseAuth.getInstance();

        editTextName=(EditText) findViewById(R.id.name);
        editTextID=(EditText)findViewById(R.id.id);
        editTextPhone=(EditText)findViewById(R.id.phone);

        imageView=(ImageView)findViewById(R.id.image);
        progressBar=findViewById(R.id.progressbar);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showImageChooser();
            }
        });

        button=(Button)findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveUserinformation();
                finish();
                Intent intent=new Intent(ToolActivity.this,MembersActivity.class);
                startActivity(intent);
            }
        });

    }

    private void saveUserinformation() {
        String name=editTextName.getText().toString().trim();
        if (name.isEmpty()){
            editTextName.setError("Name required");
            editTextName.requestFocus();
            return;
        }
        String id=editTextID.getText().toString().trim();
        if (id.isEmpty()){
            editTextID.setError("ID required");
            editTextID.requestFocus();
            return;
        }
        String phone=editTextPhone.getText().toString().trim();
        if (phone.isEmpty()){
            editTextPhone.setError("Phone required");
            editTextPhone.requestFocus();
            return;
        }

        UserInformation userInformation = new UserInformation(name,id,phone);
        FirebaseUser user=firebaseAuth.getCurrentUser();


        if (user !=null && profileImageURL!= null){
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name).setPhotoUri(Uri.parse(profileImageURL)).build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                   if (task.isSuccessful()){
                       Toast.makeText(ToolActivity.this, "profile Updated", Toast.LENGTH_SHORT).show();
                   }
                }
            });
        }

        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(this, "Information saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         if(requestCode==CHOOSE_IMAGE && resultCode==RESULT_OK && data != null && data.getData()!=null){
              uriProfileImage = data.getData();
             try {
                 Bitmap bitmap = MediaStore.Images.Media.getBitmap( getContentResolver(),uriProfileImage);
                  imageView.setImageBitmap(bitmap );

                  uploadImageToFirebaseStorage();
             } catch (IOException e) {

             }
         }
    }

    private void uploadImageToFirebaseStorage() {
        StorageReference profileImageReference = FirebaseStorage.getInstance().getReference("profilepics/"+System.currentTimeMillis()+".jpg");
        if (uriProfileImage != null){
            progressBar.setVisibility(View.VISIBLE);
           profileImageReference.putFile(uriProfileImage)

                 .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                     @Override
                     public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                         progressBar.setVisibility(View.GONE);
                         profileImageURL =taskSnapshot.getDownloadUrl().toString();
                     }
                 }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   progressBar.setVisibility(View.GONE);
                   Toast.makeText(ToolActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
               }
           });
        }
    }

    private void showImageChooser(){

      Intent intent=new Intent();
      intent.setType("image/*");
      intent.setAction(Intent.ACTION_GET_CONTENT);
      startActivityForResult( Intent.createChooser(intent,"Select Profile image"),CHOOSE_IMAGE );

    }
}
