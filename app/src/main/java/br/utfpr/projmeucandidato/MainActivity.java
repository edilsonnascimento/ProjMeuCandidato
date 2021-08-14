package br.utfpr.projmeucandidato;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String MODO = "MODO";
    public static final String CANDIDATO = "CANDIDATO";
    public static final int NOVO = 1;
    public static final int ALTERAR = 2;
    private Candidato candidato;
    private Avaliacao avaliacao;
    private int modo;
    private EditText editTextNomeCompleto, textTelefone, textEmail, textJustificativa;
    private CheckBox chUmaEstrela, chDuasEstrelas, chTresEstrelas, chQuatroEstrelas, chCincoEstrelas;
    private RadioGroup radioGroupSexo, radioGroupTelefone;
    private Spinner spinnerPartidos;
    private String erroCampoBranco = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carregarCampos();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null){

            modo = bundle.getInt(MODO, NOVO);

            if (modo == NOVO){
                setTitle(getString(R.string.novo_candidato));
            }else{
                candidato = (Candidato) bundle.get(CANDIDATO);
                setTitle(getString(R.string.alterar_candidato));
                carregarCamposAlterar(candidato);
            }
        }

    }

    private void carregarCamposAlterar(Candidato candidato) {
         editTextNomeCompleto.setText(candidato.getNome());

         if(candidato.getSexo().equals(Sexo.FEMININO)) {
           radioGroupSexo.check(R.id.rbFeminino);
        }else{
             radioGroupSexo.check(R.id.rbMasculino);
         }
    }


    public void salvar(View view){
        String mensagemSucesso = "";
        erroCampoBranco = "";

        tratamentoCamposTexto(view);
        tratamentoCheckBox(view);
        tratamentoRadioGroupSexo(view);
        tratamentoRadioGroupTelefone(view);
        tratamentoSpinnerPartido(view);


        if(erroCampoBranco.isEmpty()) {
            Intent intent = new Intent();
            intent.putExtra(CANDIDATO, candidato);

            setResult(Activity.RESULT_OK, intent);

            mensagemSucesso = getString(R.string.campo_cadastrado_sucesso) + getString(R.string.espaco) +
                    getString(R.string.nome_completo) + getString(R.string.espaco) + candidato.getNome() + getString(R.string.espaco) +
                    getString(R.string.telefone) + getString(R.string.espaco) + candidato.getTelefone() + getString(R.string.espaco) +
                    getString(R.string.email) + getString(R.string.espaco) + candidato.getEmail() + getString(R.string.espaco) +
                    getString(R.string.partido) + getString(R.string.espaco) + candidato.getPartido() + getString(R.string.espaco) +
                    getString(R.string.justificativa) + getString(R.string.espaco) + avaliacao.getJustificativa();

            Toast.makeText(this, mensagemSucesso, Toast.LENGTH_SHORT).show();

            finish();
        }

    }

    private void tratamentoSpinnerPartido(View view) {
        if(candidato.getPartido().isEmpty()){
            mostrarErro(getString(R.string.partido));
            spinnerPartidos.requestFocus();
        }
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
        textTelefone = findViewById(R.id.editTextTelefone);
        textEmail = findViewById(R.id.editTextEmail);
        textJustificativa = findViewById(R.id.editTextJustificativa);

        chUmaEstrela = findViewById(R.id.chUmaEstrela);
        chDuasEstrelas = findViewById(R.id.chDuasEstrelas);
        chTresEstrelas = findViewById(R.id.chTresEstrelas);
        chQuatroEstrelas = findViewById(R.id.chQuatroEstrelas);
        chCincoEstrelas = findViewById(R.id.chCincoEstrelas);

        radioGroupSexo = findViewById(R.id.radioGroupSexo);
        radioGroupTelefone = findViewById(R.id.radioGroupTelefone);

        spinnerPartidos = findViewById(R.id.spinnerPartidos);
        popularSpinnerPartido();

    }

    private void popularSpinnerPartido() {

        List<String> partidos = new ArrayList<>();

        spinnerPartidos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String partidoSelecionado = adapterView.getItemAtPosition(i).toString();
                candidato.setPartido(partidoSelecionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        partidos.add(getString(R.string.sem_partido));
        partidos.add(getString(R.string.partido_onestos));
        partidos.add(getString(R.string.partido_santos));
        partidos.add(getString(R.string.confianca));
        partidos.add(getString(R.string.agora_vai));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, partidos);

        spinnerPartidos.setAdapter(adapter);

    }


    private void tratamentoCamposTexto(View view) {
        candidato.setNome(editTextNomeCompleto.getText().toString());
        candidato.setTelefone(textTelefone.getText().toString());
        candidato.setEmail(textEmail.getText().toString());
        avaliacao.setJustificativa(textJustificativa.getText().toString());

        if(candidato.getNome() == null || candidato.getNome().trim().isEmpty()){
            mostrarErro(getString(R.string.nome_completo));
            editTextNomeCompleto.requestFocus();
            return;
        }

        if(candidato.getTelefone() == null || candidato.getTelefone().trim().isEmpty()){
            mostrarErro(getString(R.string.telefone));
            textTelefone.requestFocus();
            return;
        }

        if(candidato.getEmail() == null || candidato.getEmail().trim().isEmpty()){
            mostrarErro(getString(R.string.email));
            textEmail.requestFocus();
            return;
        }

        if(avaliacao.getJustificativa() == null || avaliacao.getJustificativa().trim().isEmpty()){
            mostrarErro(getString(R.string.justificativa));
            textJustificativa.requestFocus();
            return;
        }

    }
    private void tratamentoRadioGroupTelefone(View view) {

        switch (radioGroupTelefone.getCheckedRadioButtonId()) {

            case R.id.radioButtonParticular:
                candidato.setTipoContato(TipoContato.PARTICULAR);
                break;
            case R.id.radioButtonGabinete:
                candidato.setTipoContato(TipoContato.GABINETE);
                break;
            case R.id.radioButtonFixo:
                candidato.setTipoContato(TipoContato.FIXO);
            default:
                mostrarErro(getString(R.string.contato));
                radioGroupTelefone.requestFocus();
        }

    }

    private void tratamentoRadioGroupSexo(View view) {

        switch (radioGroupSexo.getCheckedRadioButtonId()){

            case R.id.rbFeminino :
                candidato.setSexo(Sexo.FEMININO);
                break;
            case R.id.rbMasculino:
                candidato.setSexo(Sexo.MASCULINO);
                break;
            default:
                mostrarErro(getString(R.string.sexo));
                radioGroupSexo.requestFocus();
        }
    }

    private void tratamentoCheckBox(View view) {
        int totalEstrelas = 0;

        if(chUmaEstrela.isChecked()) totalEstrelas += 1;
        if(chDuasEstrelas.isChecked()) totalEstrelas += 1;
        if(chTresEstrelas.isChecked()) totalEstrelas += 1;
        if(chQuatroEstrelas.isChecked()) totalEstrelas += 1;
        if(chCincoEstrelas.isChecked()) totalEstrelas += 1;

        avaliacao.setQuantidadeEstrelas(totalEstrelas);
        if(avaliacao.getQuantidadeEstrelas() == 0){
            mostrarErro(getString(R.string.avaliacao));
        }

    }

    private void mostrarErro(String campo){
        erroCampoBranco = getString(R.string.nenhuma_opcao_selecionada) +
                getString(R.string.espaco) + campo;
        Toast.makeText(this, erroCampoBranco, Toast.LENGTH_SHORT).show();

    }
    public void limpar(View view){

        editTextNomeCompleto.setText(null);
        textTelefone.setText(null);
        textJustificativa.setText(null);
        textEmail.setText(null);

        chUmaEstrela.setChecked(false);
        chDuasEstrelas.setChecked(false);
        chTresEstrelas.setChecked(false);
        chQuatroEstrelas.setChecked(false);
        chCincoEstrelas.setChecked(false);

        radioGroupSexo.clearCheck();
        radioGroupTelefone.clearCheck();

        erroCampoBranco = "";
        Toast.makeText(this, R.string.compos_limpos_com_sucesso, Toast.LENGTH_SHORT).show();
    }

    public static void alterarCandidato(AppCompatActivity activity, Candidato candidato){

        Intent intent = new Intent(activity, MainActivity.class);

        intent.putExtra(MODO, ALTERAR);
        intent.putExtra(CANDIDATO, candidato);

        activity.startActivityForResult(intent, ALTERAR);
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}