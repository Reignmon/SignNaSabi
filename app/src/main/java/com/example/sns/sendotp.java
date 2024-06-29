package com.example.sns;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.Properties;
import java.util.Random;

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

    TextView Email;
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

        Email.setText(email);
        String EM = Email.getText().toString();

        String otp = generateOTP();
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

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(sendotp.this, register.class);
                startActivity(i);
                finish();
            }
        });

        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = pinView.getText().toString();
                String encodedEmail = encodeEmail(email);
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
                            usersRef.child("password").setValue(password);
                            Toast.makeText(sendotp.this, "Correct OTP", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(sendotp.this, MainActivity.class);
                            startActivity(intent);
                            finish();
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
    }

    public static String encodeEmail(String email) {
        // Replace '.' (dot) with ',' (comma) or any other safe character
        return email.replace(".", ",");
    }

    public static String generateOTP() {
        // Define the length of the OTP
        int otpLength = 6;
        // Generate random digits
        Random random = new Random();
        StringBuilder otp = new StringBuilder(otpLength);
        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10)); // Generates digits between 0-9
        }
        return otp.toString();
    }

}