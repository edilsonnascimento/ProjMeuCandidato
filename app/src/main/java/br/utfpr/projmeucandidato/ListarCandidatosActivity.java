package br.utfpr.projmeucandidato;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListarCandidatosActivity extends AppCompatActivity {

    private ListView listViewCandidatos;
    private ArrayAdapter<Candidato> listAdater;
    private List<Candidato> listaCandidatos;
    private int posicaoSelecionada = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_candidatos);

        listViewCandidatos = findViewById(R.id.listViewCandidatos);

        listViewCandidatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                posicaoSelecionada = i;
                alterarCandidato();
            }
        });

        popularLista();
    }

    private void popularLista() {
        listaCandidatos = new ArrayList<>();

        listAdater = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCandidatos);

        listViewCandidatos.setAdapter(listAdater);
    }

    private void alterarCandidato() {
        Candidato candidato = listaCandidatos.get(posicaoSelecionada);
        MainActivity.alterarCandidato(this, candidato);
    }

    public void botaoSobre(View view){
        AutoriaActivity.sobre(this);
    }

    public void adicionar(View view){
        MainActivity.novoCandidato(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){

            Bundle bundle = data.getExtras();
            Candidato novoCandidato = (Candidato) bundle.get(MainActivity.CANDIDATO);

            if(requestCode == MainActivity.ALTERAR){

                Candidato candidato = listaCandidatos.get(posicaoSelecionada);
                candidato.atualizar(candidato);

                posicaoSelecionada = -1;
            }else {
                listaCandidatos.add(novoCandidato);
            }

            listAdater.notifyDataSetChanged();
        }
    }
}