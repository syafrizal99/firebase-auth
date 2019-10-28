package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {

    private MaterialButton buttonSignIn;
    private MaterialButton buttonSignUp;
    private MaterialButton buttonResetPass;
    private TextInputEditText editEmail;
    private TextInputEditText editFullName;
    private TextInputEditText editPassword;
    private TextInputEditText editPasswordConf;

    private FirebaseAuth firebaseAuth;

    CoordinatorLayout layoutSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        buttonSignIn = findViewById(R.id.button_sign_in);
        buttonSignUp = findViewById(R.id.button_sign_up);
        editEmail = findViewById(R.id.text_email_up);
        buttonResetPass = findViewById(R.id.button_reset_pass);
        editFullName = findViewById(R.id.text_full_name_up);
        editPassword = findViewById(R.id.text_password_up_1);
        editPasswordConf = findViewById(R.id.text_password_up_2);
        layoutSignUp = findViewById(R.id.layout_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                finish();
            }
        });

        //aksi tombol reset password
        buttonResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
                finish();
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strEmail = editEmail.getText().toString().toLowerCase();
                final String strFullName = editFullName.getText().toString().toUpperCase();
                String strPassword = editPassword.getText().toString();
                String strPasswordConf = editPasswordConf.getText().toString();

                if (strEmail.equals("") || strPassword.equals("") ||
                        strPasswordConf.equals("") || strFullName.equals("") ){
                    snackBar(R.color.primaryColor, "Email or Password is empty");
                }
                else if (!strPassword.equals(strPasswordConf)){
                    snackBar(R.color.primaryColor, "Password not match!");
                }
                else{
                    firebaseAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()){
                                        String strErrorMessage = task.getException().getMessage();
                                        snackBar(R.color.primaryColor,strErrorMessage);
                                    } else {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(strFullName).build();
                                        user.updateProfile(profileUpdates);

                                        startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                                        finish();
                                    }
                                }
                            });
                }
            }
        });

    }
    public void snackBar(int color, String message){
        Snackbar snackbar = Snackbar
                .make(layoutSignUp, message, Snackbar.LENGTH_LONG)
                .setDuration(8000);
        snackbar.show();
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(color));
    }
}
