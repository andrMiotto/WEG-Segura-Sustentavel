package weg.seguranca.model;

public class Sala_emergencia {

    private int id;
    private int salaId;
    private int emergenciaId;

    public Sala_emergencia() {
    }

    public Sala_emergencia(Integer id, Integer salaId, Integer emergenciaId) {
        this.id = id;
        this.salaId = salaId;
        this.emergenciaId = emergenciaId;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getSalaId(){return salaId;}
    public void setSalaId(Integer salaId){this.salaId = salaId;}

    public Integer getEmergenciaId(){return emergenciaId;}
    public void setEmergenciaId(Integer emergenciaId){this.emergenciaId = emergenciaId;}
}
