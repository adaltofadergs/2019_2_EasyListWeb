package com.adalto.easylist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormularioActivity extends AppCompatActivity {

    private EditText etNome, etQuantidade;
    private Button btnSalvar;
    private String acao;
    private int idProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        etNome = (EditText) findViewById(R.id.etNome);
        etQuantidade = (EditText) findViewById(R.id.etQuantidade);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);

        acao = getIntent().getExtras().getString("acao");
        if ( acao.equals("editar")){
            idProduto = getIntent().getExtras().getInt("idProduto");
            carregarFormulario(idProduto);
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });
    }

    private void salvar(){
        String nome = etNome.getText().toString();
        String qtd = etQuantidade.getText().toString();

        if( nome.isEmpty() ){
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setIcon( android.R.drawable.ic_dialog_alert);
            alerta.setTitle("Atenção!");
            alerta.setMessage("Você deve informar o nome do produto.");
            alerta.setPositiveButton("OK", null);
            alerta.show();
        }else {
            Produto prod = new Produto();
            prod.setNome( nome );

            if (qtd.isEmpty()){
                prod.setQuantidade(0);
            }else {
                qtd = qtd.replace(",",".");
                prod.setQuantidade( Double.valueOf( qtd ) );
            }
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference();
            reference.child("produtos").push().setValue(prod);

            finish();

        }
    }

    private void carregarFormulario(int idProduto){


    }

}
