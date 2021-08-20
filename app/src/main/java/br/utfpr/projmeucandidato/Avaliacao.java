package br.utfpr.projmeucandidato;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Avaliacao implements Serializable {

    private LocalDateTime data;
    private Integer quantidadeEstrelas;
    private String justificativa;

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Integer getQuantidadeEstrelas() {
        return quantidadeEstrelas;
    }

    public void setQuantidadeEstrelas(Integer quantidadeEstrelas) {
        this.quantidadeEstrelas = quantidadeEstrelas;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

}
