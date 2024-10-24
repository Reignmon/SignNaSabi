package com.example.sns;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chaos.view.PinView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class sendotp extends AppCompatActivity {
    private boolean backPressToExit = false;
    private static final String OTP_CHARACTERS = "0123456789";
    private static final int OTP_LENGTH = 6;
    int countdownTime = 60;
    Dialog dialog,loadingIndicatorDialog;
    TextView Email;
    String otp ="";
    TextView resendOTP;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://capstone-f5a82-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sendotp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        final TextView btnBack = findViewById(R.id.btnback);
        final Button btnverify = findViewById(R.id.btnregister);
        final PinView pinView = findViewById(R.id.pinview);
        resendOTP = findViewById(R.id.resend);

        Email = findViewById(R.id.email);
        Intent intent = getIntent();
        final String firstname = intent.getStringExtra("firstname");
        final String lastname = intent.getStringExtra("lastname");
        final String middlename = intent.getStringExtra("middlename");
        final String extensionname = intent.getStringExtra("extensionname");
        final String birthdate = intent.getStringExtra("birthdate");
        final String age = intent.getStringExtra("age");
        final String gender = intent.getStringExtra("gender");
        final String disablity = intent.getStringExtra("disablity");
        final String email = intent.getStringExtra("email");
        final String password = intent.getStringExtra("password");

        dialog = new Dialog(sendotp.this);
        dialog.setContentView(R.layout.sucess_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.success_dialog_bg));
        dialog.setCancelable(false);
        final Button okayBtn = dialog.findViewById(R.id.okaybtn);

        loadingIndicatorDialog = new Dialog(sendotp.this);
        loadingIndicatorDialog.setContentView(R.layout.loading_dialog);
        loadingIndicatorDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingIndicatorDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.success_dialog_bg));
        loadingIndicatorDialog.setCancelable(false);

        startCountdown(60 * 1000); // 60 seconds



        String encryptedText;
        try {
            encryptedText = DecryptEncrypt.encrypt(password);
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
        Email.setText(email);
        String EM = Email.getText().toString();
        otp = generateOTP();
        sendotp obj = new sendotp();
        obj.SendOTP(EM, Integer.parseInt(otp));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("firstname", firstname);
                resultIntent.putExtra("lastname", lastname);
                resultIntent.putExtra("middlename", middlename);
                resultIntent.putExtra("extensionname", extensionname);
                resultIntent.putExtra("birthdate", birthdate);
                resultIntent.putExtra("age", age);
                resultIntent.putExtra("gender", gender);
                resultIntent.putExtra("disablity", disablity);
                resultIntent.putExtra("email", email);
                resultIntent.putExtra("password", password);

                setResult(RESULT_OK, resultIntent); // Set the result for this activity
                finish(); // Close this activity and return to the previous one
            }
        });

        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp = generateOTP();
                obj.SendOTP(EM, Integer.parseInt(otp));
                startCountdown(60 * 1000); // 60 seconds
            }
        });

        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = pinView.getText().toString();
                String encodedEmail = encodeEmail(email);
                loadingIndicatorDialog.show();
                if (num.equals(otp)){
                    DatabaseReference usersRef = databaseReference.child("users");
                    usersRef.child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            DatabaseReference usersRef = databaseReference.child("users").child(encodedEmail);

                            usersRef.child("firstname").setValue(firstname);
                            usersRef.child("lastname").setValue(lastname);
                            usersRef.child("middlename").setValue(middlename);
                            usersRef.child("extensioname").setValue(extensionname);
                            usersRef.child("birthdate").setValue(birthdate);
                            usersRef.child("age").setValue(age);
                            usersRef.child("gender").setValue(gender);
                            usersRef.child("disablity").setValue(disablity);
                            usersRef.child("email").setValue(email);
                            usersRef.child("password").setValue(encryptedText);
                            loadingIndicatorDialog.dismiss();
                            dialog.show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    Toast.makeText(sendotp.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });

        okayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sendotp.this, MainActivity.class);
                startActivity(intent);
                finish();
                dialog.dismiss();
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
    //code for send otp message
    public void SendOTP(String EM, int otp){
        try {

            String senderEmail = "reignmondizon93@gmail.com";
            String recieverEmail = EM ;
            String passwordSenderEmail = "tjwr ufiw ejfd zghg";
            String stringHost = "smtp.gmail.com";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", stringHost);
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.auth", "true");

            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, passwordSenderEmail);
                }
            });
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recieverEmail));

            mimeMessage.setSubject("Subject: Android App email");
            mimeMessage.setText("Hello Programmer, \n\n Heres your OTP "+otp);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    //end of code for send otp message

    public static String generateOTP() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder(OTP_LENGTH);

        for (int i = 0; i < OTP_LENGTH; i++) {
            int index = random.nextInt(OTP_CHARACTERS.length());
            otp.append(OTP_CHARACTERS.charAt(index));
        }

        return otp.toString();
    }

    private void startCountdown(long millisInFuture) {
        new CountDownTimer(millisInFuture, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // Calculate minutes and seconds
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;

                // Update the TextView
                resendOTP.setText(String.format("%02d",seconds));
                resendOTP.setEnabled(false);
            }

            @Override
            public void onFinish() {
                // When the timer finishes
                resendOTP.setText("Resend");
                resendOTP.setEnabled(true);
            }
        }.start();
    }

}