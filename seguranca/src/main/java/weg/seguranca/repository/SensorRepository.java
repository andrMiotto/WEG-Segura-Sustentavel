package weg.seguranca.repository;

import com.firexguard.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    Sensor findBySensorId(String sensorId);
}
