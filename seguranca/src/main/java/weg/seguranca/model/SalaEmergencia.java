package weg.seguranca.model;

public class SalaEmergencia {
    private int id;
    private int sala_id;
    private int emergencia_id;

    public SalaEmergencia(int id, int sala_id, int emergencia_id) {
        this.id = id;
        this.sala_id = sala_id;
        this.emergencia_id = emergencia_id;
    }

    public SalaEmergencia(int sala_id, int emergencia_id) {
        this.sala_id = sala_id;
        this.emergencia_id = emergencia_id;
    }

    public int getId() {
        return id;
    }

    public int getSala_id() {
        return sala_id;
    }

    public int getEmergencia_id() {
        return emergencia_id;
    }


}
