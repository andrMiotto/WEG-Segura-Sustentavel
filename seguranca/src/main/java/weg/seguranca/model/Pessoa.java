package weg.seguranca.model;

public class Pessoa {

    private Integer id;
    private String nome;
    private Integer cadastro;
    private String tipo;
    private int situacao_de_risco;
    private Integer sala_atual;
    private Integer emergencia_atual;

    public Pessoa(Integer id, String nome, Integer cadastro, String tipo, int situacao_de_risco, Integer sala_atual, Integer emergencia_atual) {
        this.id = id;
        this.nome = nome;
        this.cadastro = cadastro;
        this.tipo = tipo;
        this.situacao_de_risco = situacao_de_risco;
        this.sala_atual = sala_atual;
        this.emergencia_atual = emergencia_atual;
    }

    public Pessoa(String nome, Integer cadastro, String tipo, int situacao_de_risco, Integer sala_atual, Integer emergencia_atual) {
        this.nome = nome;
        this.cadastro = cadastro;
        this.tipo = tipo;
        this.situacao_de_risco = situacao_de_risco;
        this.sala_atual = sala_atual;
        this.emergencia_atual = emergencia_atual;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getCadastro() {
        return cadastro;
    }

    public String getTipo() {
        return tipo;
    }

    public int getSituacao_de_risco() {
        return situacao_de_risco;
    }

    public Integer getSala_atual() {
        return sala_atual;
    }

    public Integer getEmergencia_atual() {
        return emergencia_atual;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCadastro(Integer cadastro) {
        this.cadastro = cadastro;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setSituacao_de_risco(int situacao_de_risco) {
        this.situacao_de_risco = situacao_de_risco;
    }

    public void setSala_atual(Integer sala_atual) {
        this.sala_atual = sala_atual;
    }

    public void setEmergencia_atual(Integer emergencia_atual) {
        this.emergencia_atual = emergencia_atual;
    }


}
