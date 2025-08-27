package weg.seguranca.model;

import jakarta.persistence.*;

@Entity
@Table(name = "salas")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer numero;
    private String bloco;
    private Integer portaria;
    private String unidade;
    private Boolean situacaoDeRisco;

    @OneToMany
    private Emergencia emergenciaAtual;

    public Sala() {}

    public Sala(Integer id, Integer numero, String bloco, Integer portaria, String unidade, Boolean situacaoDeRisco, Emergencia emergenciaAtual) {
        this.id = id;
        this.numero = numero;
        this.bloco = bloco;
        this.portaria = portaria;
        this.unidade = unidade;
        this.situacaoDeRisco = situacaoDeRisco;
        this.emergenciaAtual = emergenciaAtual;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public String getBloco() { return bloco; }
    public void setBloco(String bloco) { this.bloco = bloco; }

    public Integer getPortaria() { return portaria; }
    public void setPortaria(Integer portaria) { this.portaria = portaria; }

    public String getUnidade() { return unidade; }
    public void setUnidade(String unidade) { this.unidade = unidade; }

    public Boolean getSituacaoDeRisco() { return situacaoDeRisco; }
    public void setSituacaoDeRisco(Boolean situacaoDeRisco) { this.situacaoDeRisco = situacaoDeRisco; }

    public Emergencia getEmergenciaAtual() { return emergenciaAtual; }
    public void setEmergenciaAtual(Emergencia emergenciaAtual) { this.emergenciaAtual = emergenciaAtual; }
}