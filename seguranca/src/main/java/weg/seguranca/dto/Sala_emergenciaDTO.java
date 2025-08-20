package weg.seguranca.dto;

public class Sala_emergenciaDTO {

    private Integer id;
    private Integer salaId;
    private Integer emergenciaId;

    public Sala_emergenciaDTO() {}

    public Sala_emergenciaDTO(Integer id, Integer salaId, Integer emergenciaId) {
        this.id = id;
        this.salaId = salaId;
        this.emergenciaId = emergenciaId;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getSalaId() { return salaId; }
    public void setSalaId(Integer salaId) { this.salaId = salaId; }

    public Integer getEmergenciaId() { return emergenciaId; }
    public void setEmergenciaId(Integer emergenciaId) { this.emergenciaId = emergenciaId; }
}

