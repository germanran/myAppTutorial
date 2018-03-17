package tutorials.firebase.com.myapptutorial;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
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

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button signUpButton;
    private TextView signInActivity;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.emailSignUp_editText);
        passwordEditText = findViewById(R.id.passwordSignUp_editText);
        confirmPasswordEditText = findViewById(R.id.confirmEmailSignUp_editText);
        signUpButton = findViewById(R.id.signUp_button);
        signInActivity = findViewById(R.id.signIn_textView);
        progressBar = findViewById(R.id.signUp_progressBar);

        goToSignInActivity();
        userSignUp();
    }

    private void goToSignInActivity(){
        signInActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    public boolean checkUserInput(String email, String password, String confirmPassword){

        boolean isInputValid = false;

        if(email.isEmpty()){
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();

        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Please enter valid mail");
            emailEditText.requestFocus();
        }
        else if(password.isEmpty()){
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
        }
        else if(password.length() < 6){
            passwordEditText.setError("At least 6 characters");
            passwordEditText.requestFocus();
        }
        else if(!password.equals(confirmPassword)){
            passwordEditText.setError("The passwords don't match");
            passwordEditText.requestFocus();
        }
        else {
            isInputValid = true;
        }

        return isInputValid;
    }

    private void userSignUp(){

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean userInputValid;

                hideKeyboard();

                String emailStr = emailEditText.getText().toString().trim();
                final String passwordStr = passwordEditText.getText().toString().trim();
                String confirmPasswordStr = confirmPasswordEditText.getText().toString().trim();


                userInputValid = checkUserInput(emailStr, passwordStr, confirmPasswordStr);
                if(!userInputValid){
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressBar.setVisibility(View.GONE);

                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, AppMainActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Unable to Register", Toast.LENGTH_SHORT).show();
                            emailEditText.setText("");
                            passwordEditText.setText("");
                            confirmPasswordEditText.setText("");
                        }
                    }
                });

            }
        });
    }

    private void hideKeyboard(){
        try  { //hiding the keyboard after pressing the sign-up button
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {}
    }

}
