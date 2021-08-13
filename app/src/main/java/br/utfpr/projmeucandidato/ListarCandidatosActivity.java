package br.utfpr.projmeucandidato;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class ListarCandidatosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_candidatos);
    }

    public void botaoSobre(View view){
        AutoriaActivity.sobre(this);
    }

    public void adicionar(View view){
        MainActivity.novoCandidato(this);
    }
}