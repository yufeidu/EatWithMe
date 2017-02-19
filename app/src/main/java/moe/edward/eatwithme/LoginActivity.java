package moe.edward.eatwithme;

import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Random;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = (Button)findViewById(R.id.register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                EditText editText = (EditText)findViewById(R.id.editText);
//                String email = editText.getText().toString();
//                sendEmail(email);
                Intent intent = new Intent(LoginActivity.this, PlacesSelection.class);
                startActivity(intent);

            }
        });
    }

    /*public void sendEmail(String email){
        Random random = new Random();
        int rand = random.nextInt(8999) + 1000;

        try {
            GMailSender sender = new GMailSender("muhammedalibnc@gmail.com", "password");
            sender.sendMail("This is Subject",
                    "Use this code: " + rand,
                    "muhammedalibnc@gmail.com",
                    email);
            Toast.makeText(LoginActivity.this, "Sent", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }


    }*/
}
