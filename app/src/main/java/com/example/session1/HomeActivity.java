package com.example.session1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<User> users;
    private ArrayAdapter arrayAdapter;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;



    Intent intent = getIntent();
    String name;
    TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        name = intent.getStringExtra("email");
        email = findViewById(R.id.email);
        email.setText("email: " + name);
        listView = findViewById(R.id.list);
        //ArrayList<User> users = new ArrayList<User>();
        //users.add(new User(intent.getStringExtra("name"),intent.getStringExtra("email"),intent.getStringExtra("password")));
        arrayAdapter = new UserArrayAdapter(this,R.layout.list_row,users);
        listView.setAdapter(arrayAdapter);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        String uid = mAuth.getUid().toString();
        database.getReference("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users = new ArrayList<>();
                for(DataSnapshot data:snapshot.getChildren())
                {
                    User user = data.getValue((User.class));
                    users.add(user);
                }
                listView = findViewById(R.id.list);
                arrayAdapter = new UserArrayAdapter(HomeActivity.this,R.layout.list_row,users);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.signout)
        {
            mAuth.signOut();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        return true;
    }

}