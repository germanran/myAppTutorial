package tutorials.firebase.com.myapptutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class AppMainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView signOutLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);

        mAuth = FirebaseAuth.getInstance();
        signOutLink = findViewById(R.id.signOut_textView);
        signOut();
    }

    private void signOut(){
        signOutLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(AppMainActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
