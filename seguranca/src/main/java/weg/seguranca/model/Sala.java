package weg.seguranca.model;

public class Sala {

    private int id;
    private int numero;
    private String bloco;
    private int portaria;
    private String unidade;
    private boolean situacao_risco;
    private int id_emergencia_atual;

    public Sala(int id, int numero, String bloco, int portaria, String unidade, boolean situacao_risco, int id_emergencia_atual) {
        this.id = id;
        this.numero = numero;
        this.bloco = bloco;
        this.portaria = portaria;
        this.unidade = unidade;
        this.situacao_risco = situacao_risco;
        this.id_emergencia_atual = id_emergencia_atual;
    }

    public Sala(int numero, String bloco, int portaria, String unidade, boolean situacao_risco, int id_emergencia_atual) {
        this.numero = numero;
        this.bloco = bloco;
        this.portaria = portaria;
        this.unidade = unidade;
        this.situacao_risco = situacao_risco;
        this.id_emergencia_atual = id_emergencia_atual;
    }

    public int getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public String getBloco() {
        return bloco;
    }

    public int getPortaria() {
        return portaria;
    }

    public String getUnidade() {
        return unidade;
    }

    public boolean isSituacao_risco() {
        return situacao_risco;
    }

    public int getId_emergencia_atual() {
        return id_emergencia_atual;
    }
}