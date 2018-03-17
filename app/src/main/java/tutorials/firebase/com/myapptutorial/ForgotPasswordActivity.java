package tutorials.firebase.com.myapptutorial;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailForSendingNewPasswordEditText;
    private Button sendPasswordToEmailButton;
    private TextView goToSignInActivityTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();
        emailForSendingNewPasswordEditText = findViewById(R.id.emailReset_editText);
        sendPasswordToEmailButton = findViewById(R.id.sendPassword_button);
        goToSignInActivityTextView = findViewById(R.id.signInActivityFromForgotPassword_textView);

        sendResetPasswordToEmail();
        goToSignInActivity();
    }

    public void sendResetPasswordToEmail(){
        sendPasswordToEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = emailForSendingNewPasswordEditText.getText().toString().trim();

                if(emailAddress.isEmpty()){
                    emailForSendingNewPasswordEditText.setError("Please Enter your Email");
                    emailForSendingNewPasswordEditText.requestFocus();
                    return;
                }

                mAuth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Password was sent to your Email", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Unable to send Password", Toast.LENGTH_SHORT).show();
                                    emailForSendingNewPasswordEditText.setText("");
                                }
                            }
                        });
            }
        });
    }

    public void goToSignInActivity(){
        goToSignInActivityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
