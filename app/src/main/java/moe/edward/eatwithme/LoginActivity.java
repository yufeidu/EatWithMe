package moe.edward.eatwithme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                EditText editText = (EditText)findViewById(R.id.editText);
                String email = editText.getText().toString();
                sendEmail(email);
            }
        });
    }

    public void sendEmail(String email){
        Random random = new Random();
        int rand = random.nextInt(8999) + 1000;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc882");
        intent.putExtra(intent.EXTRA_EMAIL, email);
        intent.putExtra(intent.EXTRA_SUBJECT, "Confirmation Code - EatWithMe App");
        intent.putExtra(intent.EXTRA_TEXT, "Your confirmation code is: " + rand + ". Please use this" +
                " code to successfully register.");
        try{
            startActivity(Intent.createChooser(intent, "Sending the email"));

        } catch (android.content.ActivityNotFoundException e){
            Toast.makeText(LoginActivity.this, "Couldn't send the email", Toast.LENGTH_SHORT).show();
        }
    }
}
