package weg.seguranca.mapper;

import weg.seguranca.dto.Sala_emergenciaDTO;
import weg.seguranca.model.Sala_emergencia;

public class Sala_emergenciaMapper {

    public static Sala_emergenciaDTO toDTO(Sala_emergencia model) {
        if (model == null) return null;
        return new Sala_emergenciaDTO(
                model.getId(),
                model.getSala(),
                model.getEmergencia()
        );
    }

    public static Sala_emergencia toModel(Sala_emergenciaDTO dto) {
        if (dto == null) return null;
        return new Sala_emergencia(
                dto.getId(),
                dto.getSala(),
                dto.getEmergencia()
        );
    }

}
