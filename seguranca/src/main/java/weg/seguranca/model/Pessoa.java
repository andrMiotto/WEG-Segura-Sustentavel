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
    private Integer idSalaAtual;
    private Integer idEmergenciaAtual;

    public Pessoa() {}

    public Pessoa(Integer id, String nome, Integer cadastro, String tipo, Boolean situacaoDeRisco, Integer idSalaAtual, Integer idEmergenciaAtual) {
        this.id = id;
        this.nome = nome;
        this.cadastro = cadastro;
        this.tipo = tipo;
        this.situacaoDeRisco = situacaoDeRisco;
        this.idSalaAtual = idSalaAtual;
        this.idEmergenciaAtual = idEmergenciaAtual;
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

    public Integer getIdSalaAtual() { return idSalaAtual; }
    public void setIdSalaAtual(Integer idSalaAtual) { this.idSalaAtual = idSalaAtual; }

    public Integer getIdEmergenciaAtual() { return idEmergenciaAtual; }
    public void setIdEmergenciaAtual(Integer idEmergenciaAtual) { this.idEmergenciaAtual = idEmergenciaAtual; }
}