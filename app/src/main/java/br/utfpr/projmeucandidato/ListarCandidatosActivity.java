package br.utfpr.projmeucandidato;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    private ActionMode actionMode;
    private int posicaoSelecionada = -1;
    private View viewSelecionada;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {

            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.main_editar_excluir_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

            switch (menuItem.getItemId()){
                case R.id.menuItemAlterar:
                     alterarCandidato();
                     actionMode.finish();
                     return true;

                case R.id.menuItemExcluir:
                    excluirCandiadato();
                    actionMode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

            if(viewSelecionada != null) viewSelecionada.setBackgroundColor(Color.TRANSPARENT);

            actionMode = null;
            viewSelecionada = null;

            listViewCandidatos.setEnabled(true);
        }
    };


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

        listViewCandidatos.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listViewCandidatos.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                        if (actionMode != null) return false;

                        posicaoSelecionada = i;

                        view.setBackgroundColor(Color.LTGRAY);

                        viewSelecionada = view;

                        listViewCandidatos.setEnabled(false);

                        actionMode = startSupportActionMode(mActionModeCallback);

                        return true;
                    }
                }
        );

        popularLista();
    }

    private void popularLista() {
        listaCandidatos = new ArrayList<>();

        listAdater = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCandidatos);

        listViewCandidatos.setAdapter(listAdater);
    }

    private void excluirCandiadato() {
        listaCandidatos.remove(posicaoSelecionada);
        listAdater.notifyDataSetChanged();
    }

    private void alterarCandidato() {
        Candidato candidato = listaCandidatos.get(posicaoSelecionada);
        MainActivity.alterarCandidato(this, candidato);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){

            Bundle bundle = data.getExtras();

            Candidato novoCandidato = (Candidato) bundle.get(MainActivity.CANDIDATO);

            if(requestCode == MainActivity.ALTERAR){

                listaCandidatos.get(posicaoSelecionada).atualizar(novoCandidato);

                posicaoSelecionada = -1;
            }else {
                listaCandidatos.add(novoCandidato);
            }

            listAdater.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listar_candidatos_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.itemMenuAdicionar:
                MainActivity.novoCandidato(this);
                return true;

            case R.id.itemMenuSobre:
                AutoriaActivity.sobre(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}