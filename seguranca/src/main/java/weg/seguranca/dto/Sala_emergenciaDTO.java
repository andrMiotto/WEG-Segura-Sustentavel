package weg.seguranca.dto;

import weg.seguranca.model.Emergencia;
import weg.seguranca.model.Sala;

public class Sala_emergenciaDTO {

    private int id;
    private Sala sala;
    private Emergencia emergencia;

    public Sala_emergenciaDTO() {}

    public Sala_emergenciaDTO(Integer id, Sala sala, Emergencia emergencia) {
        this.id = id;
        this.sala = sala;
        this.emergencia = emergencia;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Sala getSala() { return sala; }
    public void setSala(Integer salaId) { this.sala = sala; }

    public Emergencia getEmergencia() { return emergencia; }
    public void setEmergencia(Integer emergenciaId) { this.emergencia = emergencia; }
}

