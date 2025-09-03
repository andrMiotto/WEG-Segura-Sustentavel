package weg.seguranca.model;

import java.time.LocalDateTime;

public class Emergencia {

    private Integer id;
    private String titulo;
    private String descricao;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private Boolean emAndamento;

    public Emergencia() {}

    public Emergencia(Integer id, String titulo, String descricao, LocalDateTime inicio, LocalDateTime fim, Boolean emAndamento) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.inicio = inicio;
        this.fim = fim;
        this.emAndamento = emAndamento;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDateTime getInicio() { return inicio; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }

    public LocalDateTime getFim() { return fim; }
    public void setFim(LocalDateTime fim) { this.fim = fim; }

    public Boolean getEmAndamento() { return emAndamento; }
    public void setEmAndamento(Boolean emAndamento) { this.emAndamento = emAndamento; }
}
