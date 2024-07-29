package com.example.sns;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class change_pass extends AppCompatActivity {
    String email= "";
    TextInputEditText password,password1;
    boolean backPressToExit = false;
    Dialog loadingIndicatorDialog;
    String Password="",Password1="",encodedEmail="";
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://capstone-f5a82-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_pass);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        password = findViewById(R.id.txtpass);
        password1 = findViewById(R.id.txtpass1);
        final Button btnChange = findViewById(R.id.btnchange);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        loadingIndicatorDialog = new Dialog(change_pass.this);
        loadingIndicatorDialog.setContentView(R.layout.loading_dialog);
        loadingIndicatorDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingIndicatorDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.success_dialog_bg));
        loadingIndicatorDialog.setCancelable(false);


        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Password = password.getText().toString();
                Password1 = password1.getText().toString();
                encodedEmail = encodeEmail(email);


                String encryptedText;
                try {
                    encryptedText = DecryptEncrypt.encrypt(Password1);
                } catch (NoSuchPaddingException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                } catch (InvalidAlgorithmParameterException e) {
                    throw new RuntimeException(e);
                } catch (InvalidKeyException e) {
                    throw new RuntimeException(e);
                } catch (IllegalBlockSizeException e) {
                    throw new RuntimeException(e);
                } catch (BadPaddingException e) {
                    throw new RuntimeException(e);
                }

                loadingIndicatorDialog.show();

                if (Password.isEmpty()) {
                    password.setError("ENTER YOUR NEW PASSWORD");
                    loadingIndicatorDialog.dismiss();
                } else if (Password1.isEmpty()) {
                    password1.setError("ENTER YOUR CONFIRM PASSWORD");
                    loadingIndicatorDialog.dismiss();
                } else if (!Password.equals(Password1)) {
                    Toast.makeText(change_pass.this, "PASSWORDS DON'T MATCH", Toast.LENGTH_SHORT).show();
                    loadingIndicatorDialog.dismiss();
                } else if (Password.length() < 8) {
                    password.setError("PASSWORD MUST BE MINIMUM 8 CHARACTERS");
                    loadingIndicatorDialog.dismiss();
                } else {
                    DatabaseReference usersRef = databaseReference.child("users").child(encodedEmail);
                    usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                // Update the password
                                usersRef.child("password").setValue(encryptedText)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(change_pass.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(change_pass.this,MainActivity.class));
                                                loadingIndicatorDialog.dismiss();
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                loadingIndicatorDialog.dismiss();
                                                Toast.makeText(change_pass.this, "Failed to update password. Please try again.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Toast.makeText(change_pass.this, "User not found", Toast.LENGTH_SHORT).show();
                                loadingIndicatorDialog.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(change_pass.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            loadingIndicatorDialog.dismiss();
                        }
                    });
                }
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

    public static String encodeEmail(String email) {
        // Replace '.' (dot) with ',' (comma) or any other safe character
        return email.replace(".", ",");
    }
}