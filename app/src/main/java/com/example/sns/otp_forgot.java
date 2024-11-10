package com.example.sns;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chaos.view.PinView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

public class otp_forgot extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://capstone-f5a82-default-rtdb.firebaseio.com/");
    String otp ="";
    String email;
    TextView Email;
    private boolean backPressToExit = false;
    private static final String OTP_CHARACTERS = "0123456789";
    private static final int OTP_LENGTH = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otp_forgot);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        final Button btnverify = findViewById(R.id.btnverfiy);
        final TextView btnback = findViewById(R.id.btnback);
        final PinView pinView = findViewById(R.id.pinview);
        Email = findViewById(R.id.emaitTextview);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        Email.setText(email);
        String EM = Email.getText().toString();
        otp = generateOTP();
        sendotp obj = new sendotp();
        obj.SendOTP(EM, Integer.parseInt(otp));

        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = pinView.getText().toString();
                if (num.equals(otp)){
                    Intent i = new Intent(otp_forgot.this, change_pass.class);
                    i.putExtra("email", EM);
                    startActivity(i);
                    finish();

                }
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(otp_forgot.this, MainActivity.class));
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
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
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

}