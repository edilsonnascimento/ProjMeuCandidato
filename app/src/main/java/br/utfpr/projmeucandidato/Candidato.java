package br.utfpr.projmeucandidato;

import java.io.Serializable;

public class Candidato implements Serializable {

    private String nome;
    private Sexo sexo;
    private TipoContato tipoContato;
    private String telefone;
    private String email;
    private String partido;
    private Avaliacao avaliacao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public TipoContato getTipoContato() {
        return tipoContato;
    }

    public void setTipoContato(TipoContato tipoContato) {
        this.tipoContato = tipoContato;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    @Override
    public String toString() {
        return  nome + " - " + partido;
    }

    public void atualizar(Candidato candidato) {
        nome = candidato.nome;
        sexo = candidato.sexo;
        tipoContato = candidato.tipoContato;
        telefone = candidato.telefone;
        email = candidato.email;
        partido = candidato.partido;
    }
}
