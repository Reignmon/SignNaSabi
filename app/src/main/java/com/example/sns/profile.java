package com.example.sns;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class profile extends AppCompatActivity {
    private boolean backPressToExit = false;
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://capstone-f5a82-default-rtdb.firebaseio.com/");
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        final Button btnLogout = findViewById(R.id.btnlogout);
        final TextView emailTxt = findViewById(R.id.txtEmail);
        final TextView Emailtxt = findViewById(R.id.emailtxt);
        final TextView fullName = findViewById(R.id.fullname);
        final TextView bod = findViewById(R.id.bod);
        final TextView age = findViewById(R.id.age);
        final TextView gender = findViewById(R.id.gender);
        final TextView disablity = findViewById(R.id.disablity);
        final TextView fullname = findViewById(R.id.name);
        final TextView BtnBack = findViewById(R.id.btnback);
        final TextView btnEdit = findViewById(R.id.btnedit);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_EMAIL,null);
        String DecodedEmail = decodeEmail(name);

        if(name!=null){
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(DecodedEmail)){

                        String getmname="";
                        String getext = "";
                        final String getfname = snapshot.child(DecodedEmail).child("firstname").getValue(String.class);
                        final String getlname = snapshot.child(DecodedEmail).child("lastname").getValue(String.class);
                        getmname = snapshot.child(DecodedEmail).child("middlename").getValue(String.class);
                        getext = snapshot.child(DecodedEmail).child("extensionname").getValue(String.class);
                        final String getbod = snapshot.child(DecodedEmail).child("birthdate").getValue(String.class);
                        final String getage = snapshot.child(DecodedEmail).child("age").getValue(String.class);
                        final String getgender = snapshot.child(DecodedEmail).child("gender").getValue(String.class);
                        final String getdisablity = snapshot.child(DecodedEmail).child("disablity").getValue(String.class);

                        getmname = (getmname != null) ? getmname : "";
                        getext = (getext != null) ? getext : "";


                        fullname.setText(getfname + " " + getmname + " " + getlname);
                        fullName.setText(getfname + " " + getmname + " " + getlname+ " " + getext);
                        bod.setText(getbod);
                        age.setText(getage);
                        gender.setText(getgender);
                        disablity.setText(getdisablity);
                        emailTxt.setText(name);
                        Emailtxt.setText(name);

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(profile.this,MainActivity.class));
                finish();
            }
        });

        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(profile.this,dashboard.class));
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(profile.this,update.class));
                finish();
            }
        });
    }

    //code for backpress
    @Override
    public void onBackPressed() {
        if (backPressToExit) {
            super.onBackPressed();
            return;
        }

        // Show confirmation dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                backPressToExit = true;
                onBackPressed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);

        dialog.show();

        // Set a timer to automatically dismiss the dialog after 2 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 2000);
    }
//end of code for backpress




    public static String decodeEmail(String email) {
        // Replace '.' (dot) with ',' (comma) or any other safe character
        return email.replace(".", ",");
    }
}