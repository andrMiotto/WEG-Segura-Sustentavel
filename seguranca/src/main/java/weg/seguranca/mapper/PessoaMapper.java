package weg.seguranca.mapper;

import weg.seguranca.model.Pessoa;
import weg.seguranca.dto.PessoaDTO;

public class PessoaMapper {

    public static Pessoa toModel(PessoaDTO dto) {
        return new Pessoa(
                null,
                dto.getNome(),
                dto.getCadastro(),
                dto.getTipo(),
                dto.getSituacaoDeRisco(),
                dto.getSalaAtual(),
                dto.getEmergenciaAtual()
        );
    }

    public static PessoaDTO toDTO(Pessoa model) {
        return new PessoaDTO(
                model.getNome(),
                model.getCadastro(),
                model.getTipo(),
                model.getSituacaoDeRisco(),
                model.getSalaAtual(),
                model.getEmergenciaAtual()
        );
    }
}
