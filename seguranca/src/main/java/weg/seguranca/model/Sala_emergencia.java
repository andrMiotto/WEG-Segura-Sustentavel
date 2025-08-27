package weg.seguranca.model;

import jakarta.persistence.*;

@Entity
@Table(name = "salas_emergencias")
public class Sala_emergencia {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private Sala sala;

    @OneToMany
    private Emergencia emergencia;

    public Sala_emergencia() {
    }

    public Sala_emergencia(Integer id, Sala sala, Emergencia emergencia) {
        this.id = id;
        this.sala = sala;
        this.emergencia = emergencia;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Sala getSala(){return sala;}
    public void setSalaId(Sala salaId){this.sala = sala;}

    public Emergencia getEmergencia(){return emergencia;}
    public void setEmergenciaId(Emergencia emergenciaId){this.emergencia = emergencia;}
}
