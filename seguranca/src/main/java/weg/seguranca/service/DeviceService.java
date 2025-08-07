package weg.seguranca.service;

import com.firexguard.model.Device;
import com.firexguard.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public Device registerDevice(Device device) {
        return deviceRepository.save(device);
    }

    public Device getDeviceById(String deviceId) {
        return deviceRepository.findByDeviceId(deviceId);
    }
}
