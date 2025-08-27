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
    private Integer idEmergenciaAtual;

    public Sala() {}

    public Sala(Integer id, Integer numero, String bloco, Integer portaria, String unidade, Boolean situacaoDeRisco, Integer idEmergenciaAtual) {
        this.id = id;
        this.numero = numero;
        this.bloco = bloco;
        this.portaria = portaria;
        this.unidade = unidade;
        this.situacaoDeRisco = situacaoDeRisco;
        this.idEmergenciaAtual = idEmergenciaAtual;
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

    public Integer getIdEmergenciaAtual() { return idEmergenciaAtual; }
    public void setIdEmergenciaAtual(Integer idEmergenciaAtual) { this.idEmergenciaAtual = idEmergenciaAtual; }
}