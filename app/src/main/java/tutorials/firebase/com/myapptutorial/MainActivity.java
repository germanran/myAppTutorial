package tutorials.firebase.com.myapptutorial;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private TextView signUpActivity;
    private ProgressBar progressBar;
    private TextView forgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.emailSignIn_editText);
        passwordEditText = findViewById(R.id.passwordSignIn_editText);
        signInButton = findViewById(R.id.signIn_button);
        signUpActivity = findViewById(R.id.signUp_textView);
        progressBar = findViewById(R.id.signIn_progressBar);
        forgotPassword = findViewById(R.id.forgotPassword_textView);
        mAuth = FirebaseAuth.getInstance();

        goToSignUpActivity();
        goToForgotPasswordActivity();
        userSignIn();
    }

    private void goToSignUpActivity(){
        signUpActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });
    }

    private void userSignIn(){

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboard();
                String emailStr = emailEditText.getText().toString().trim();
                String passwordStr = passwordEditText.getText().toString().trim();

                if(emailStr.isEmpty()){
                    emailEditText.setError("Please enter an Email");
                    emailEditText.requestFocus();
                    return;
                }
                else if(passwordStr.isEmpty()){
                    passwordEditText.setError("Please enter a Password");
                    passwordEditText.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressBar.setVisibility(View.GONE);

                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "User Logged-in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, AppMainActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Email or Password are incorrect", Toast.LENGTH_SHORT).show();
                            emailEditText.setText("");
                            passwordEditText.setText("");
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            startActivity(new Intent(MainActivity.this, AppMainActivity.class));
            finish();
        }

    }
    private void hideKeyboard(){
        try  { //hiding the keyboard after pressing the sign-up button
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {}
    }


    private void goToForgotPasswordActivity(){
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgotPasswordActivity.class));
            }
        });
    }


}
