package weg.seguranca.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pessoas")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private Integer cadastro;
    private String tipo;
    private Boolean situacaoDeRisco;
    private Sala salaAtual;
    private Emergencia emergenciaAtual;

    public Pessoa() {}

    public Pessoa(Integer id, String nome, Integer cadastro, String tipo, Boolean situacaoDeRisco, Sala salaAtual, Emergencia idEmergenciaAtual) {
        this.id = id;
        this.nome = nome;
        this.cadastro = cadastro;
        this.tipo = tipo;
        this.situacaoDeRisco = situacaoDeRisco;
        this.salaAtual = salaAtual;
        this.emergenciaAtual = emergenciaAtual;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

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