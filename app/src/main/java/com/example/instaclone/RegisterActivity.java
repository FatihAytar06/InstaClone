package com.example.instaclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText username,name,email,password;
    private Button registerBtn;
    private TextView loginUser;

    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.usernameTextView);
        name= findViewById(R.id.nameTextView);
        email = findViewById(R.id.emailTextView);
        password = findViewById(R.id.passwordTextView);
        registerBtn = findViewById(R.id.registerActivityRegiterBtn);
        loginUser=findViewById(R.id.logInUser);

        mRootRef = FirebaseDatabase.getInstance().getReference();

        mAuth=FirebaseAuth.getInstance();

        pd = new ProgressDialog(this);

        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LogInActivity.class));
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtUserName=username.getText().toString();
                String txtName = name.getText().toString();
                String txtEmail = email.getText().toString();
                String txtPassword = password.getText().toString();

                if(TextUtils.isEmpty(txtUserName)||TextUtils.isEmpty(txtName)||TextUtils.isEmpty(txtEmail)||TextUtils.isEmpty(txtPassword)){
                    Toast.makeText(RegisterActivity.this,"Lütfen boş alan bırakmayınız...",Toast.LENGTH_SHORT).show();
                }
                else if(txtPassword.length()<6)
                {
                    Toast.makeText(RegisterActivity.this,"Parola çok kısa!",Toast.LENGTH_SHORT).show();
                }
                else{
                    registerUser(txtUserName,txtName,txtEmail,txtPassword);
                }
            }
        });
    }

    private void registerUser(String username, String name, String email, String password) {

        pd.setMessage("Please Wait...");
        pd.show();
        mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                HashMap<String,Object> map = new HashMap<>();
                map.put("name",name);
                map.put("email",email);
                map.put("username",username);
                map.put("bio",null);
                map.put("imageurl","default");
                map.put("id",mAuth.getCurrentUser().getUid());
                mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            pd.dismiss();
                            startActivity(new Intent(RegisterActivity.this,FeedActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            Toast.makeText(RegisterActivity.this,"Kullanıcı Oluşturuldu...",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}