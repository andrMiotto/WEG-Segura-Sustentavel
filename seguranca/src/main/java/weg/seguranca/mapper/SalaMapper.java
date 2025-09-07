package weg.seguranca.mapper;

import weg.seguranca.dto.SalaDTO;
import weg.seguranca.model.Sala;

public class SalaMapper {

    public static Sala toModel(SalaDTO dto) {
        return new Sala(
                null,
                dto.getNumero(),
                dto.getBloco(),
                dto.getPortaria(),
                dto.getUnidade(),
                dto.getSituacaoDeRisco(),
                dto.getEmergenciaAtual()
        );
    }

    public static SalaDTO toDTO(Sala model) {
        return new SalaDTO(
                model.getNumero(),
                model.getBloco(),
                model.getPortaria(),
                model.getUnidade(),
                model.getSituacaoDeRisco(),
                model.getEmergenciaAtual()
        );
    }
}