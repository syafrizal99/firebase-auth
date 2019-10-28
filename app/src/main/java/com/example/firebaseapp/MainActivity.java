package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private MaterialButton buttonSignOut;
    private MaterialButton buttonChangePass;
    private FirebaseAuth firebaseAuth;
    private TextView textEmailUser;
    private TextView textDisplayNameUser;
    private TextInputEditText editNewPassword;
    private TextInputEditText editNewPasswordConf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonSignOut = findViewById(R.id.button_sign_out);
        buttonChangePass = findViewById(R.id.button_change_pass);
        textEmailUser = findViewById(R.id.text_email_user);
        textDisplayNameUser = findViewById(R.id.text_display_name);
        editNewPassword = findViewById(R.id.edit_new_password);
        editNewPasswordConf = findViewById(R.id.edit_new_password_conf);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textDisplayNameUser.setText(user.getDisplayName());
        textEmailUser.setText(user.getEmail());

        buttonChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    LayoutInflater inflater = MainActivity.this.getLayoutInflater();

                    builder.setView(inflater.inflate(R.layout.dialog_change_pass, null))
                            .setTitle("Change Password")
                            // Add action buttons
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {

                                    String strNewPassword = editNewPassword.getText().toString();
                                    String strNewPasswordConf = editNewPassword.getText().toString();
                                    Toast.makeText(MainActivity.this,
                                            "new password is "+ strNewPassword, Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton("Cancel", null )
                            .show();


                }
//                new MaterialAlertDialogBuilder(MainActivity.this)
//                        .setTitle("Title")
//                        .setMessage("Message")
//                        .setPositiveButton("Confirm", null)
//                        .setNegativeButton("Cancel", null)
//                        .show();
        });

        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.signOut();

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(MainActivity.this, SignInActivity.class));
                    finish();
                }
            }
        });

    }
}
