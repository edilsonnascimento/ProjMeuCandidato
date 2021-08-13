package br.utfpr.projmeucandidato;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String MODO = "MODO";
    private static final int NOVO = 1;
    private static final int ALTERAR = 2;
    private Candidato candidato;
    private Avaliacao avaliacao;
    private EditText editTextNomeCompleto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carregarCampos();
    }


    public void salvar(View view){
        tratamentoCamposTexto(view);
    }

    public static void novoCandidato(ListarCandidatosActivity activity) {
        Intent intent = new Intent(activity, MainActivity.class);

        intent.putExtra(MODO, NOVO);
        activity.startActivityForResult(intent, NOVO);
    }

    private void carregarCampos() {
        candidato = new Candidato();
        avaliacao = new Avaliacao();

        editTextNomeCompleto = findViewById(R.id.editTextNomeCompleto);
    }

    private void tratamentoCamposTexto(View view) {
        candidato.setNome(editTextNomeCompleto.getText().toString());
        String erroCampoBranco = "";
        String mensagemSucesso = "";

        if(candidato.getNome() == null || candidato.getNome().trim().isEmpty()){
            erroCampoBranco = getString(R.string.nome_completo) + " " + getString(R.string.erro_campo_em_branco);;
            Toast.makeText(this, erroCampoBranco, Toast.LENGTH_SHORT).show();
            editTextNomeCompleto.requestFocus();
            return;
        }

        mensagemSucesso = getString(R.string.campo_cadastrado_sucesso) + getString(R.string.espaco) +
                getString(R.string.nome_completo) + getString(R.string.espaco) + candidato.getNome() + getString(R.string.espaco) +
                getString(R.string.telefone) + getString(R.string.espaco) + candidato.getTelefone() + getString(R.string.espaco) +
                getString(R.string.email) + getString(R.string.espaco) + candidato.getEmail() + getString(R.string.espaco) +
                getString(R.string.justificativa) + getString(R.string.espaco) + avaliacao.getJustificativa();

        Toast.makeText(this, mensagemSucesso, Toast.LENGTH_LONG).show();
    }
}