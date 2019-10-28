package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class SignInActivity extends AppCompatActivity {

    private MaterialButton buttonSignIn;
    private MaterialButton buttonSignUp;
    private MaterialButton buttonResetPass;
    private TextInputEditText editEmail;
    private TextInputEditText editPassword;

    private FirebaseAuth firebaseAuth;

    CoordinatorLayout layoutSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        buttonSignIn = findViewById(R.id.button_sign_in);
        buttonSignUp = findViewById(R.id.button_sign_up);
        buttonResetPass = findViewById(R.id.button_reset_pass);
        editEmail = findViewById(R.id.edit_email_in);
        editPassword = findViewById(R.id.edit_password_in);

        layoutSignIn = findViewById(R.id.layout_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();


        //cek apakah ada akun yang masih masuk
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        //aksi tombol reset password
        buttonResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
                finish();
            }
        });

        //aksi tombol buat akun baru
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });

        //aksi tombol sign in
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strEmail = editEmail.getText().toString().toLowerCase();
                String strPassword = editPassword.getText().toString();

                if (strEmail.equals("") || strPassword.equals("") ){
                    snackBar(R.color.primaryColor, "Email or Password is empty");
                } else {
                    firebaseAuth.signInWithEmailAndPassword(strEmail, strPassword)
                            .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()){
                                        String strErrorMessage = task.getException().getMessage();
                                        snackBar(R.color.primaryColor, strErrorMessage);
                                    } else {
                                        Intent intentSuccessLogin = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intentSuccessLogin);
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
                .make(layoutSignIn, message, Snackbar.LENGTH_LONG)
                .setDuration(8000);
        snackbar.show();
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(color));
    }
}
