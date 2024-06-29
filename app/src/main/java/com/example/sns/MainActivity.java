package com.example.sns;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://capstone-f5a82-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        final TextView btnreg = findViewById(R.id.signup);
        final Button btnlogin = findViewById(R.id.btnlogin);
        final TextInputEditText email = findViewById(R.id.txtusername);
        final TextInputEditText password = findViewById(R.id.txtpassword);
        // show activity of register
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, register.class);
                startActivity(i);
            }
        });
        // end of activity register code here

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                String encodedEmail = encodeEmail(Email);

                if(Email.isEmpty() ){
                    email.setError("ENTER your email");
                } else if (Password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter your password", Toast.LENGTH_SHORT).show();
                } else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(encodedEmail)){
                                final String getPassword = snapshot.child(encodedEmail).child("password").getValue(String.class);
                                if(getPassword.equals(Password)){
                                    Toast.makeText(MainActivity.this, "Successful Login", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this,dashboard.class));
                                    finish();
                                }else{
                                    Toast.makeText(MainActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(MainActivity.this, "Incorrect email", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


            }
        });
    }

    public static String encodeEmail(String email) {
        // Replace '.' (dot) with ',' (comma) or any other safe character
        return email.replace(".", ",");
    }

}