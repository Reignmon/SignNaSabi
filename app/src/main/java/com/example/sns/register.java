package com.example.sns;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class register extends AppCompatActivity {
    private TextView mDisplayDate;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://capstone-f5a82-default-rtdb.firebaseio.com/");
    private static final String TAG = "MainActivity";

    Spinner spinnergender, spinneruser;
    static EditText fname,lname,mdname,ename,age;
    static EditText email;
    TextInputEditText pass,pass1;
    private String date;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    boolean isAllFieldsChecked = false;
    int num =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final TextView btnlog = findViewById(R.id.Login);
        fname = findViewById(R.id.firtsname);
        lname = findViewById(R.id.lastname);
        mdname = findViewById(R.id.middlename);
        ename = findViewById(R.id.jrname);
        age = findViewById(R.id.age);
        email = findViewById(R.id.txtemail);
        pass = findViewById(R.id.txtpass);
        pass1 = findViewById(R.id.txtpass1);
        final Button btnreg = findViewById(R.id.btnregister);
        mDisplayDate = findViewById(R.id.date);
        spinnergender = findViewById(R.id.spinner_gender);
        spinneruser = findViewById(R.id.spinner_user);


        // function for register
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
                String Password1 = pass1.getText().toString();
                boolean check = CheckAllFields(firstname,lastname,Age,Email,Password,Password1);

                if(check == true){
                    if(Password.equals(Password1)){
                        String encodedEmail = encodeEmail(Email);

                        DatabaseReference usersRef = databaseReference.child("users");

                        usersRef.child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    Toast.makeText(register.this, "Email have been already taken", Toast.LENGTH_SHORT).show();
                                }else{
                                    Intent i = new Intent(register.this, sendotp.class);
                                    i.putExtra("firstname", firstname);
                                    i.putExtra("lastname", lastname);
                                    i.putExtra("middlename", middlename);
                                    i.putExtra("extensionname", extensionname);
                                    i.putExtra("birthdate", bod);
                                    i.putExtra("age", Age);
                                    i.putExtra("gender", Gender);
                                    i.putExtra("disablity", Disablity);
                                    i.putExtra("email", Email);
                                    i.putExtra("password", Password);
                                    startActivity(i);
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }else{
                        Toast.makeText(register.this, "Password not match", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        // end of function for register

        // show activity of register
        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(register.this, MainActivity.class);
                startActivity(i);
            }
        });
        // end of activity register code here


        //code for datepicker
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        register.this,
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

    //code for validation
    private boolean CheckAllFields(String firstname, String lastname, String Age, String Email, String Password, String Password1) {
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

        // Validate Date of Birth
        if (date == null || date.isEmpty()) {
            Toast.makeText(register.this, "Please input your birthdate", Toast.LENGTH_LONG).show();
            return false;
        } /*else {
            // Perform date validation here (assuming date is in a format that can be compared)
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            Date selectedDate;
            try {
                selectedDate = sdf.parse(date); // Parse the selected date
                Date currentDate = new Date(); // Get current date
                if (selectedDate.before(currentDate)) {
                    Toast.makeText(register.this, "Date cannot be in future", Toast.LENGTH_LONG).show();
                    return false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return false; // Handle parsing exception if needed
            }
        }*/

        if (Age.length() == 0) {
            age.setError("FIELD CANNOT BE EMPTY");
            return false;
        }

        // Spinner validations
        if (spinnergender.getSelectedItemPosition() == 0) { // Gender Spinner
            TextView errorText = (TextView)spinnergender.getSelectedView();
            Toast.makeText(register.this, "Please select your gender", Toast.LENGTH_LONG).show();
            return false;
        }

        if (spinneruser.getSelectedItemPosition() == 0) { // User Type Spinner
            TextView errorText = (TextView)spinneruser.getSelectedView();
            Toast.makeText(register.this, "Please select your disablity", Toast.LENGTH_LONG).show();
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
            Toast.makeText(register.this, "Password field cannot be empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (Password.length() < 8) {
            Toast.makeText(register.this, "Password must be minimum 8 characters", Toast.LENGTH_LONG).show();
            return false;
        }

        if (Password1.length() == 0) {
            Toast.makeText(register.this, "Password field cannot be empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (Password1.length() < 8) {
            Toast.makeText(register.this, "Password must be minimum 8 characters", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    //end of code for validation

    public static String encodeEmail(String email) {
        // Replace '.' (dot) with ',' (comma) or any other safe character
        return email.replace(".", ",");
    }

}