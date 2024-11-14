package com.example.sns;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class update extends AppCompatActivity {
    private boolean backPressToExit = false;
    private TextView mDisplayDate;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://capstone-f5a82-default-rtdb.firebaseio.com/");
    private static final String TAG = "MainActivity";

    Spinner spinnergender, spinneruser;
    static EditText fname,lname,mdname,ename,age;
    static EditText email;
    TextInputEditText pass;
    private String date;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    boolean isAllFieldsChecked = false;
    Dialog loadingIndicatorDialog;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    String Decrypted = "";

    public static String getfname = "", getlname = "",getmname = "",getext = "",getbod = "",
            getage = "",getgender = "",getdisablity = "",getpassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);
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

        loadingIndicatorDialog = new Dialog(update.this);
        loadingIndicatorDialog.setContentView(R.layout.loading_dialog);
        loadingIndicatorDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingIndicatorDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.success_dialog_bg));
        loadingIndicatorDialog.setCancelable(false);

        fname = findViewById(R.id.firtsname);
        lname = findViewById(R.id.lastname);
        mdname = findViewById(R.id.middlename);
        ename = findViewById(R.id.jrname);
        age = findViewById(R.id.age);
        final Button btnreg = findViewById(R.id.btnregister);
        final TextView btnback = findViewById(R.id.btnback);
        mDisplayDate = findViewById(R.id.date);
        spinnergender = findViewById(R.id.spinner_gender);
        spinneruser = findViewById(R.id.spinner_user);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_EMAIL,null);
        String DecodedEmail = decodeEmail(name);



        if(name!=null){
            loadingIndicatorDialog.show();
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(DecodedEmail)){
                        getfname = snapshot.child(DecodedEmail).child("firstname").getValue(String.class);
                        getlname = snapshot.child(DecodedEmail).child("lastname").getValue(String.class);
                        getmname = snapshot.child(DecodedEmail).child("middlename").getValue(String.class);
                        getext = snapshot.child(DecodedEmail).child("extensioname").getValue(String.class);
                        getbod = snapshot.child(DecodedEmail).child("birthdate").getValue(String.class);
                        getage = snapshot.child(DecodedEmail).child("age").getValue(String.class);
                        getgender = snapshot.child(DecodedEmail).child("gender").getValue(String.class);
                        getdisablity = snapshot.child(DecodedEmail).child("disablity").getValue(String.class);


                        Log.d(TAG, "getmname: " + getmname);  // Log to check if value is null or empty
                        Log.d(TAG, "getext: " + getext);  // Log to check if value is null or empty

                        try {
                            Decrypted = DecryptEncrypt.decrypt(getpassword);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if(getgender.equals("Male")){
                            spinnergender.setSelection(1);
                        } else if (getgender.equals("Female")) {
                            spinnergender.setSelection(2);
                        }

                        if(getdisablity.equals("None")){
                            spinneruser.setSelection(1);
                        } else if (getdisablity.equals("Deaf")) {
                            spinneruser.setSelection(2);
                        } else if (getdisablity.equals("Mute")) {
                            spinneruser.setSelection(3);
                        }

                        fname.setText(getfname);
                        lname.setText(getlname);
                        mdname.setText(getmname != null && !getmname.isEmpty() ? getmname : "");  // Set empty if null or empty
                        ename.setText(getext != null && !getext.isEmpty() ? getext : "");  // Set empty if null or empty
                        mDisplayDate.setText(getbod);
                        age.setText(getage);
                        loadingIndicatorDialog.dismiss();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(update.this, profile.class));
                finish();
            }
        });


        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname = fname.getText().toString();
                String lastname = lname.getText().toString();
                String middlename = mdname.getText().toString();
                String extensionname = ename.getText().toString();
                String Age = age.getText().toString();
                String bod = mDisplayDate.getText().toString();
                String Gender = spinnergender.getSelectedItem().toString();
                String Disablity = spinneruser.getSelectedItem().toString();
                boolean check = CheckAllFields(firstname, lastname, Age);

                if (check) {
                    loadingIndicatorDialog.show();
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        String encodedEmail = decodeEmail(name);
                        DatabaseReference usersRef = databaseReference.child("users").child(encodedEmail);

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(encodedEmail)) {
                                // Check if middlename or extensionname is empty, and set to "" if true
                                usersRef.child("firstname").setValue(firstname);
                                usersRef.child("lastname").setValue(lastname);
                                usersRef.child("middlename").setValue(middlename.isEmpty() ? "" : middlename); // Set empty string if empty
                                usersRef.child("extensioname").setValue(extensionname.isEmpty() ? "" : extensionname); // Set empty string if empty
                                usersRef.child("age").setValue(Age);
                                usersRef.child("birthdate").setValue(bod);
                                usersRef.child("gender").setValue(Gender);
                                usersRef.child("disablity").setValue(Disablity);
                                startActivity(new Intent(update.this, profile.class));
                                finish();
                                loadingIndicatorDialog.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle any errors
                        }
                    });
                }
            }
        });




        //code for datepicker
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        update.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDateSet: date: " + year + "/" + month + "/" + dayOfMonth);

                date = month + "/" + dayOfMonth + "/" + year;
                mDisplayDate.setText(date);
            }
        };
        //end of code for datepicker

        //code for gender spinner

        String[] gender = {"Gender", "Male", "Female"};
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(gender));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.style_spinner, arrayList);
        spinnergender.setAdapter(adapter);
        //end of code for gender spinner
        //code for user type spinner

        String[] user = {"Disablity","None", "Deaf", "Mute"};
        ArrayList<String> arrayList1 = new ArrayList<>(Arrays.asList(user));
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.style_spinner, arrayList1);
        spinneruser.setAdapter(adapter1);
        //end of code for user type spinner
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



    private boolean CheckAllFields(String firstname, String lastname, String Age) {
        if (firstname.length() == 0) {
            fname.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!firstname.matches("[a-zA-Z\\s]+")) {
            fname.setError("LETTERS AND SPACES ONLY");
            return false;
        }

        if (lastname.length() == 0) {
            lname.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!lastname.matches("[a-zA-Z\\s]+")) {
            lname.setError("ALPHABET ONLY");
            return false;
        }
        
        if (Age.length() == 0) {
            age.setError("FIELD CANNOT BE EMPTY");
            return false;
        }

        // Spinner validations
        if (spinnergender.getSelectedItemPosition() == 0) { // Gender Spinner
            TextView errorText = (TextView)spinnergender.getSelectedView();
            Toast.makeText(update.this, "Please select your gender", Toast.LENGTH_LONG).show();
            return false;
        }

        if (spinneruser.getSelectedItemPosition() == 0) { // User Type Spinner
            TextView errorText = (TextView)spinneruser.getSelectedView();
            Toast.makeText(update.this, "Please select your disablity", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
    //end of code for validation

    private boolean containsUpperCase(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    public static String decodeEmail(String email) {
        // Replace '.' (dot) with ',' (comma) or any other safe character
        return email.replace(".", ",");
    }
}