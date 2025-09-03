package weg.seguranca.service;

import org.springframework.stereotype.Service;

import weg.seguranca.dto.LogMovimentoDTO;
import weg.seguranca.repository.MySQLRepository;
import weg.seguranca.repository.NoSQLRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

@Service
public class DataReceiveService {

    private final MySQLRepository mySQLRepository;
    private final NoSQLRepository noSQLRepository;

    public DataReceiveService(MySQLRepository mySQLRepository, NoSQLRepository noSQLRepository) {
        this.mySQLRepository = mySQLRepository;
        this.noSQLRepository = noSQLRepository;
    }

    public void importData() {
        List<LogMovimentoDTO> logs = noSQLRepository.gettAllMoviments();

        for (LogMovimentoDTO log : logs) {
            System.out.println("[" + log.getTimestamp() + "] Sala: " + log.getSala()
                    + " | Pessoa: " + log.getPessoa()
                    + " | Movimento: " + log.getHaMovimento());

        }
    }


}