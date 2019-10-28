package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private MaterialButton buttonSignIn;
    private MaterialButton buttonSignUp;
    private MaterialButton buttonResetPass;
    private TextInputEditText editEmailConf;

    CoordinatorLayout layoutResetPass;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        buttonSignIn = findViewById(R.id.button_sign_in);
        buttonSignUp = findViewById(R.id.button_sign_up);
        buttonResetPass = findViewById(R.id.button_reset_pass);
        editEmailConf = findViewById(R.id.edit_email_reset);
        layoutResetPass =findViewById(R.id.layout_reset_pass);

        firebaseAuth = FirebaseAuth.getInstance();

        //aksi tombol buat akun baru
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                finish();
            }
        });

        buttonResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String strEmailConf = editEmailConf.getText().toString().toLowerCase();

                if (strEmailConf.equals("")){
                    snackBar(R.color.primaryColor, "Email is empty");
                } else {
                    firebaseAuth.sendPasswordResetEmail(strEmailConf)
                        .addOnCompleteListener(ResetPasswordActivity.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()){
                                    String strErrorMessage = task.getException().getMessage();
                                    snackBar(R.color.primaryColor, strErrorMessage);
                                } else {
                                    snackBar(R.color.primaryColor, "Link reset has been sent to " + strEmailConf );
                                }
                            }
                        });
                }
            }
        });


    }

    public void snackBar(int color, String message){
        Snackbar snackbar = Snackbar
                .make(layoutResetPass, message, Snackbar.LENGTH_LONG)
                .setDuration(8000);
        snackbar.show();
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(color));
    }
}
