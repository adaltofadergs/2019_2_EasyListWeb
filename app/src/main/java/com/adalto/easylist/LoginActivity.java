package com.adalto.easylist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etSenha;
    private Button btnEntrar, btnCadastro;

    private FirebaseAuth auth;
    private FirebaseUser usuario;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etSenha = findViewById(R.id.etSenha);
        btnCadastro = findViewById(R.id.btnCadastro);
        btnEntrar = findViewById(R.id.btnEntrar);

        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                  usuario = auth.getCurrentUser();
                  if (usuario != null ){
                      Intent intent = new Intent(LoginActivity.this,
                              MainActivity.class);
                      startActivity(intent);
                  }else {
                      Toast.makeText(LoginActivity.this,
                              "Erro ao fazer login", Toast.LENGTH_LONG ).show();
                  }
            }
        };


        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logar();
            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criarUsuario();
            }
        });

    }

    private void criarUsuario(){
        String email = etEmail.getText().toString();
        String senha = etSenha.getText().toString();

        if( ! email.isEmpty() ){


            auth.createUserWithEmailAndPassword(email, senha).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            usuario = auth.getCurrentUser();

                        }
                    });
        }
    }

    private void logar(){
        String email = etEmail.getText().toString();
        String senha = etSenha.getText().toString();
        if (  ! email.isEmpty() && !senha.isEmpty() ){
       //     auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email, senha).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if( ! task.isSuccessful() ){
                                Toast.makeText(LoginActivity.this,
                                        "Erro ao logar",
                                        Toast.LENGTH_LONG).show();
                            }else {

                            }
                        }
                    });
        }
    }


}
