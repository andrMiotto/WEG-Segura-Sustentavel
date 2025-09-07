package weg.seguranca.dto;

import weg.seguranca.model.Emergencia;
import weg.seguranca.model.Sala;

public class PessoaDTO {

    private String nome;
    private Integer cadastro;
    private String tipo;
    private Boolean situacaoDeRisco;
    private Sala salaAtual;
    private Emergencia emergenciaAtual;

    public PessoaDTO() {}

    public PessoaDTO(String nome, Integer cadastro, String tipo, Boolean situacaoDeRisco, Sala salaAtual, Emergencia emergenciaAtual) {
        this.nome = nome;
        this.cadastro = cadastro;
        this.tipo = tipo;
        this.situacaoDeRisco = situacaoDeRisco;
        this.salaAtual = salaAtual;
        this.emergenciaAtual = emergenciaAtual;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getCadastro() { return cadastro; }
    public void setCadastro(Integer cadastro) { this.cadastro = cadastro; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Boolean getSituacaoDeRisco() { return situacaoDeRisco; }
    public void setSituacaoDeRisco(Boolean situacaoDeRisco) { this.situacaoDeRisco = situacaoDeRisco; }

    public Sala getSalaAtual() { return salaAtual; }
    public void setSalaAtual(Sala salaAtual) { this.salaAtual = salaAtual; }

    public Emergencia getEmergenciaAtual() { return emergenciaAtual; }
    public void setEmergenciaAtual(Emergencia emergenciaAtual) { this.emergenciaAtual = emergenciaAtual; }
}