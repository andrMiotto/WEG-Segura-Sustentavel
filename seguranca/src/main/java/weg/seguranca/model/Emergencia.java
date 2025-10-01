package weg.seguranca.model;

import java.time.LocalDate;

public class Emergencia {

    private int id;
    private String titulo;
    private String descricao;
    private LocalDate inicio;
    private LocalDate fim;

    public Emergencia(int id, String titulo, String descricao, LocalDate inicio, LocalDate fim) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.inicio = inicio;
        this.fim = fim;
    }

    public Emergencia(String titulo, String descricao, LocalDate inicio, LocalDate fim) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.inicio = inicio;
        this.fim = fim;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFim() {
        return fim;
    }
}
