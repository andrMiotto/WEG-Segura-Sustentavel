package weg.seguranca.mapper;

import weg.seguranca.dto.EmergenciaDTO;
import weg.seguranca.model.Emergencia;

public class EmergenciaMapper {

    public static Emergencia toModel(EmergenciaDTO dto){
        return new Emergencia(
                null,
                dto.getTitulo(),
                dto.getDescricao(),
                dto.getInicio(),
                dto.getFim(),
                dto.getEmAndamento()
        );
    }

    public static EmergenciaDTO toDTO(Emergencia model){
        return new EmergenciaDTO(
                model.getTitulo(),
                model.getDescricao(),
                model.getInicio(),
                model.getFim(),
                model.getEmAndamento()
        );
    }
}
