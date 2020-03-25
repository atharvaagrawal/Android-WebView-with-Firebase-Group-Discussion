package com.blogspot.codingatharva.diplomaincomputersciecne;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.codingatharva.diplomaincomputersciecne.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import static com.blogspot.codingatharva.diplomaincomputersciecne.R.string.app_name;

public class Discuss extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Discuss";
    EditText etEmail, etPassword;
    Button btnSignIn, btnSignUp;
    TextView tvForgotPassword;
    FirebaseDatabase database;
    FirebaseAuth auth;
    User u;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);

        setUpToolbar();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        u = new User();
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);


        navigationView = findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        Intent home = new Intent(Discuss.this, MainActivity.class);
                        startActivity(home);
                        break;
                    case R.id.nav_ther:
                        Intent therr = new Intent(Discuss.this, TheoryPart.class);
                        startActivity(therr);
                        break;
                    case R.id.nav_prog:
                        Intent progg = new Intent(Discuss.this, WebPage.class);
                        startActivity(progg);
                        break;
                    case R.id.nav_privacy:
                        Intent intent1 = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://privacypoliciesplay.blogspot.com/2018/09/privacy-policy-of-official-app.html"));
                        startActivity(intent1);
                        break;
                    case R.id.nav_contact:
                        Intent intent2 = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://codingatharva.blogspot.com/p/contact-us.html"));
                        startActivity(intent2);
                        break;
                    case R.id.nav_code:
                        Intent code = new Intent(Discuss.this, CodingArea.class);
                        startActivity(code);
                        break;
                    case R.id.nav_about:
                        Intent intent3 = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://codingatharva.blogspot.com/p/about-us.html"));
                        startActivity(intent3);
                        break;
                    case R.id.nav_text:
                        Intent speech = new Intent(Discuss.this, Text.class);
                        startActivity(speech);
                        break;

                    case R.id.nav_disc:
                        Intent disc = new Intent(Discuss.this, Discuss.class);
                        startActivity(disc);
                        break;
                }
                return false;
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignIn: {
                signIn(etEmail.getText().toString(), etPassword.getText().toString());
            }
            break;
            case R.id.btnSignUp: {
                signUpDialog();
            }
            break;
            case R.id.tvForgotPassword: {
                forgotNow();
            }
            break;
        }
    }

    private void forgotNow() {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Forgot Password");
        dialog.setContentView(R.layout.dialog_forgot);
        final EditText etEmail = dialog.findViewById(R.id.etEmail);
        Button btnResetNow = dialog.findViewById(R.id.btnResetNow);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        dialog.show();
        btnResetNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.sendPasswordResetEmail(etEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Discuss.this, "Reset link sent to " + etEmail.getText().toString() + ".", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Discuss.this, "Faild to reset.", Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Discuss.this, "Faild to reset.", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void signUpDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setTitle("Sign Up");
        dialog.setContentView(R.layout.dialog_signup);
        dialog.show();
        final EditText etName = dialog.findViewById(R.id.etName);
        final EditText etEmail = dialog.findViewById(R.id.etEmail);
        final EditText etPassword = dialog.findViewById(R.id.etPassword);
        Button btnSignUp = dialog.findViewById(R.id.btnSignUp);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(dialog, etName.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString());
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void signUp(final BottomSheetDialog dialog, final String name, String email, String password) {
        Task<AuthResult> authResultTask = auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                user.sendEmailVerification();
                                Toast.makeText(Discuss.this, "Verification Link Sent to your Email",
                                        Toast.LENGTH_SHORT).show();
                            }
                            User u = new User();
                            u.setName(name);
                            u.setEmail(user.getEmail());
                            database.getReference("user").child(user.getUid()).setValue(u);

                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Discuss.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        dialog.dismiss();
                    }
                });
    }

    private void updateUI(final FirebaseUser user) {
        if (null != user) {
            database.getReference("user").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    u = dataSnapshot.getValue(User.class);
                    u.setUid(user.getUid());
                    Intent intent = new Intent(Discuss.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void signIn(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            System.out.println("Login success..........");
                            Intent intent = new Intent(Discuss.this, DashboardActivity.class);
                            startActivity(intent);
                            finish();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Discuss.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, app_name, app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setTitle("Group Discussion");
        actionBarDrawerToggle.syncState();
    }


}
