package weg.seguranca.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sensorId;
    private String type;
    private double value;
    private String status;

    @ManyToOne
    private Device device;
}
