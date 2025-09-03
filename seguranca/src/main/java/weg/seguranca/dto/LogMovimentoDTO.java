package weg.seguranca.dto;

import java.time.Instant;

public class LogMovimentoDTO {

    private String sala;
    private String pessoa;
    private Boolean haMovimento;
    private Instant timestamp;

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getPessoa() {
        return pessoa;
    }

    public void setPessoa(String pessoa) {
        this.pessoa = pessoa;
    }

    public Boolean getHaMovimento() {
        return haMovimento;
    }

    public void setHaMovimento(Boolean haMovimento) {
        this.haMovimento = haMovimento;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
