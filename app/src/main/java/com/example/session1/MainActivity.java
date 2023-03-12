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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText email, password;
    Button signin, signup;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
        signin.setOnClickListener(this);
        signup.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if(v == signin)
        {
            email.setText(email.getText().toString());
            password.setText(email.getText().toString());
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("email",email.getText().toString());
            startActivity(intent);
        }
        if(v == signup)
        {
            Toast.makeText(this,"Signup is under construction", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        }
    }
    public void sign_in(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent i = new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(i);
                } else
                {
                    Toast.makeText(MainActivity.this,"Authentication failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}