package com.example.sns;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class update extends AppCompatActivity {

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

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    String Decrypted = "";

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

        fname = findViewById(R.id.firtsname);
        lname = findViewById(R.id.lastname);
        mdname = findViewById(R.id.middlename);
        ename = findViewById(R.id.jrname);
        age = findViewById(R.id.age);
        email = findViewById(R.id.txtemail);
        pass = findViewById(R.id.txtpass);
        final Button btnreg = findViewById(R.id.btnregister);
        mDisplayDate = findViewById(R.id.date);
        spinnergender = findViewById(R.id.spinner_gender);
        spinneruser = findViewById(R.id.spinner_user);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_EMAIL,null);
        String DecodedEmail = decodeEmail(name);

        if(name!=null){
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(DecodedEmail)){
                        final String getfname = snapshot.child(DecodedEmail).child("firstname").getValue(String.class);
                        final String getlname = snapshot.child(DecodedEmail).child("lastname").getValue(String.class);
                        final String getmname = snapshot.child(DecodedEmail).child("middlename").getValue(String.class);
                        final String getext = snapshot.child(DecodedEmail).child("extensionname").getValue(String.class);
                        final String getbod = snapshot.child(DecodedEmail).child("birthdate").getValue(String.class);
                        final String getage = snapshot.child(DecodedEmail).child("age").getValue(String.class);
                        final String getgender = snapshot.child(DecodedEmail).child("gender").getValue(String.class);
                        final String getdisablity = snapshot.child(DecodedEmail).child("disablity").getValue(String.class);
                        final String getpassword = snapshot.child(DecodedEmail).child("password").getValue(String.class);
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
                        mdname.setText(getmname);
                        ename.setText(getext);
                        mDisplayDate.setText(getbod);
                        age.setText(getage);
                        email.setText(name);
                        pass.setText(Decrypted);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


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
                String Email = email.getText().toString();
                String Password = pass.getText().toString();
                boolean check = CheckAllFields(firstname,lastname,Age,Email,Password);

                if(check == true){

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


    private boolean CheckAllFields(String firstname, String lastname, String Age, String Email, String Password) {
        if (firstname.length() == 0) {
            fname.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!firstname.matches("[a-zA-Z]+")) {
            fname.setError("ALPHABET ONLY");
            return false;
        }

        if (lastname.length() == 0) {
            lname.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!lastname.matches("[a-zA-Z]+")) {
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

        if (Email.length() == 0) {
            email.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!Email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            email.setError("ENTER VALID EMAIL");
            return false;
        }

        if (Password.length() == 0) {
            Toast.makeText(update.this, "Password field cannot be empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (Password.length() < 8) {
            Toast.makeText(update.this, "Password must be minimum 8 characters", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
    //end of code for validation

    public static String decodeEmail(String email) {
        // Replace '.' (dot) with ',' (comma) or any other safe character
        return email.replace(".", ",");
    }

}