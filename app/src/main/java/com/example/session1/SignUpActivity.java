package com.example.session1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name, email, password;
    Button submit;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public void onClick(View v) {
        if(v == submit)
        {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("name", name.toString());
            intent.putExtra("email", email.toString());
            intent.putExtra("password", password.toString());
            startActivity(intent);
        }
    }

    public void create_user(String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    String uid = mAuth.getCurrentUser().getUid();
                    User user = new User(name.getText().toString(),email,password);
                    database.getReference("Users").child(uid).setValue(user);
                    Intent i = new Intent(SignUpActivity.this,HomeActivity.class);
                    startActivity(i);
                } else
                {
                    Toast.makeText(SignUpActivity.this,"Authentication failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}