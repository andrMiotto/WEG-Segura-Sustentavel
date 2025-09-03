package weg.seguranca.dto;

public class PessoaDTO {

    private String nome;
    private Integer cadastro;
    private String tipo;
    private Boolean situacaoDeRisco;
    private Integer idSalaAtual;
    private Integer idEmergenciaAtual;

    public PessoaDTO() {}

    public PessoaDTO(String nome, Integer cadastro, String tipo, Boolean situacaoDeRisco, Integer idSalaAtual, Integer idEmergenciaAtual) {
        this.nome = nome;
        this.cadastro = cadastro;
        this.tipo = tipo;
        this.situacaoDeRisco = situacaoDeRisco;
        this.idSalaAtual = idSalaAtual;
        this.idEmergenciaAtual = idEmergenciaAtual;
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

    public Integer getIdSalaAtual() { return idSalaAtual; }
    public void setIdSalaAtual(Integer idSalaAtual) { this.idSalaAtual = idSalaAtual; }

    public Integer getIdEmergenciaAtual() { return idEmergenciaAtual; }
    public void setIdEmergenciaAtual(Integer idEmergenciaAtual) { this.idEmergenciaAtual = idEmergenciaAtual; }
}