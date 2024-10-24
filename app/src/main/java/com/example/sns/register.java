package com.example.sns;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
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
    private static final int OTP_REQUEST_CODE =1 ;
    private boolean backPressToExit = false;
    private TextView mDisplayDate;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://capstone-f5a82-default-rtdb.firebaseio.com/");
    private static final String TAG = "MainActivity";

    Spinner spinnergender, spinneruser;
    static EditText fname, lname, mdname, ename, age;
    Dialog dialog;
    static EditText email;
    TextInputEditText pass, pass1;
    private String date;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    boolean isAllFieldsChecked = false;
    String firstname = "", lastname = "", middlename = "", extensionname = "", Age = "",
            bod = "", Gender = "", Disablity = "", Email = "", Password = "", Password1 = "";
    int calculatedAge = -1; // Variable to store calculated age

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

        dialog = new Dialog(register.this);
        dialog.setContentView(R.layout.policie_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.success_dialog_bg));
        dialog.setCancelable(false);

        final Button agreeBtn = dialog.findViewById(R.id.agreebtn);
        final Button cancelBtn = dialog.findViewById(R.id.cancelbtn);


        // Function for register
        btnreg.setOnClickListener(view -> {
            firstname = fname.getText().toString();
            lastname = lname.getText().toString();
            middlename = mdname.getText().toString();
            extensionname = ename.getText().toString();
            Age = age.getText().toString();
            bod = mDisplayDate.getText().toString();
            Gender = spinnergender.getSelectedItem().toString();
            Disablity = spinneruser.getSelectedItem().toString();
            Email = email.getText().toString();
            Password = pass.getText().toString();
            Password1 = pass1.getText().toString();
            boolean check = CheckAllFields(firstname, lastname, Age, Email, Password, Password1, extensionname);

            if (check) {
                if (Password.equals(Password1)) {
                    String encodedEmail = encodeEmail(Email);
                    DatabaseReference usersRef = databaseReference.child("users");

                    usersRef.child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Toast.makeText(register.this, "Email has already been taken", Toast.LENGTH_SHORT).show();
                            } else {
                                dialog.show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle potential errors
                        }
                    });
                } else {
                    Toast.makeText(register.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                }
            }
        });
        // End of function for register

        // Show activity of register
        btnlog.setOnClickListener(v -> {
            Intent i = new Intent(register.this, MainActivity.class);
            startActivity(i);
            finish();
        });
        // End of activity register code here

        agreeBtn.setOnClickListener(view -> {
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
            startActivityForResult(i, OTP_REQUEST_CODE);
            dialog.dismiss();
        });

        cancelBtn.setOnClickListener(view -> dialog.dismiss());

        // Code for datepicker
        mDisplayDate.setOnClickListener(v -> {
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
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1; // Increment month by 1 since month is zero-based
                String selectedDateStr = month + "/" + dayOfMonth + "/" + year;

                // Create a SimpleDateFormat object to parse the date
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                try {
                    Date selectedDate = sdf.parse(selectedDateStr);
                    Date currentDate = new Date();

                    // Compare the selected date with the current date
                    if (selectedDate != null && selectedDate.after(currentDate)) {
                        Toast.makeText(register.this, "The date cannot be in the future", Toast.LENGTH_LONG).show();
                        mDisplayDate.setText(""); // Clear the date field if invalid
                    } else {
                        // Set the selected date if valid
                        date = selectedDateStr;
                        mDisplayDate.setText(date);

                        // Calculate age
                        calculatedAge = calculateAge(selectedDate);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        // End of code for datepicker

        // Code for gender spinner
        String[] gender = {"Gender", "Male", "Female"};
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(gender));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.style_spinner, arrayList);
        spinnergender.setAdapter(adapter);
        // End of code for gender spinner

        // Code for user type spinner
        String[] user = {"Disability", "None", "Deaf", "Mute"};
        ArrayList<String> arrayList1 = new ArrayList<>(Arrays.asList(user));
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.style_spinner, arrayList1);
        spinneruser.setAdapter(adapter1);
        // End of code for user type spinner
    }



    // Method to calculate age
    private int calculateAge(Date birthDate) {
        Calendar birthCal = Calendar.getInstance();
        birthCal.setTime(birthDate);
        Calendar currentCal = Calendar.getInstance();

        int age = currentCal.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);
        if (currentCal.get(Calendar.DAY_OF_YEAR) < birthCal.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }

    // Code for backpress

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OTP_REQUEST_CODE) {
            // Retrieve the data from the intent
            if (resultCode == RESULT_OK) {
                String firstname1 = data.getStringExtra("firstname");
                String lastname1 = data.getStringExtra("lastname");
                String middlename1 = data.getStringExtra("middlename");
                String extensionname1 = data.getStringExtra("extensionname");
                String birthdate1 = data.getStringExtra("birthdate");
                String age1 = data.getStringExtra("age");
                String gender1 = data.getStringExtra("gender");
                String disablity1 = data.getStringExtra("disablity");
                String email1 = data.getStringExtra("email");
                String password1 = data.getStringExtra("password");

                // Populate the fields with retrieved data
                fname.setText(firstname1);
                lname.setText(lastname1);
                mdname.setText(middlename1);
                ename.setText(extensionname1);
                mDisplayDate.setText(birthdate1);
                age.setText(age1);
                spinnergender.setSelection(((ArrayAdapter<String>) spinnergender.getAdapter()).getPosition(gender1));
                spinneruser.setSelection(((ArrayAdapter<String>) spinneruser.getAdapter()).getPosition(disablity1));
                email.setText(email1);
                pass.setText(password1);
                pass1.setText(password1);
                }
            }
        }


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
        builder.setPositiveButton("Yes", (dialog, which) -> {
            backPressToExit = true;
            onBackPressed();
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);

        dialog.show();

        // Set a timer to automatically dismiss the dialog after 2 seconds
        new Handler().postDelayed(() -> {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }, 2000);
    }
    // End of code for backpress

    // Code for validation
    private boolean CheckAllFields(String firstname, String lastname, String Age, String Email, String Password, String Password1, String extensionname) {
        // Validate first name
        if (firstname.length() == 0) {
            fname.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!firstname.matches("[a-zA-Z\\s]+")) {
            fname.setError("LETTERS AND SPACES ONLY");
            return false;
        }
        // Validate last name
        if (lastname.length() == 0) {
            lname.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!lastname.matches("[a-zA-Z]+")) {
            lname.setError("ALPHABET ONLY");
            return false;
        }

        // Validate extension name
        if (extensionname.length() > 2) {
            ename.setError("SUFFIX NAME MUST BE 2 CHARACTERS ONLY");
            return false;
        }

        // Validate Date of Birth
        if (date == null || date.isEmpty()) {
            Toast.makeText(register.this, "Please input your birthdate", Toast.LENGTH_LONG).show();
            return false;
        }

        // Validate age
        if (Age.length() == 0 || Age.length() > 3) {
            age.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else {
            int enteredAge = Integer.parseInt(Age);
            if (enteredAge < 10) {
                age.setError("Age must be 10 or older");
                return false;
            }
            if (enteredAge != calculatedAge) {
                age.setError("Entered age does not match the birthdate");
                return false;
            }
        }

        // Validate gender spinner
        if (spinnergender.getSelectedItemPosition() == 0) { // Gender Spinner
            Toast.makeText(register.this, "Please select your gender", Toast.LENGTH_LONG).show();
            return false;
        }

        // Validate disability spinner
        if (spinneruser.getSelectedItemPosition() == 0) { // User Type Spinner
            Toast.makeText(register.this, "Please select your disability", Toast.LENGTH_LONG).show();
            return false;
        }

        // Validate email
        if (Email.length() == 0) {
            email.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!Email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            email.setError("ENTER VALID EMAIL");
            return false;
        }

        // Validate password
        if (Password.length() == 0) {
            Toast.makeText(register.this, "Password field cannot be empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (Password.length() < 8) {
            Toast.makeText(register.this, "Password must be at least 8 characters", Toast.LENGTH_LONG).show();
            return false;
        } else if (!containsUpperCase(Password)) {
            Toast.makeText(register.this, "Password must contain at least one uppercase letter", Toast.LENGTH_LONG).show();
            return false;
        }
        // Validate confirm password
        if (Password1.length() == 0) {
            Toast.makeText(register.this, "Confirm password field cannot be empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (Password1.length() < 8) {
            Toast.makeText(register.this, "Confirm password must be at least 8 characters", Toast.LENGTH_LONG).show();
            return false;
        } else if (!Password1.equals(Password)) {
            Toast.makeText(register.this, "Passwords do not match", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    // Helper method to check if the string contains at least one uppercase letter
    private boolean containsUpperCase(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    public static String encodeEmail(String email) {
        // Replace '.' (dot) with ',' (comma) or any other safe character
        return email.replace(".", ",");
    }
}
