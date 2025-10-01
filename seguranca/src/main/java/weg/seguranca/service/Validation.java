package weg.seguranca.service;

import weg.seguranca.dao.EmergenciaDao;
import weg.seguranca.model.Emergencia;
import weg.seguranca.util.MySQLDatabase;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class Validation {

    public static void validarTemperaturaEUmidade(Emergencia e, double temperatura, double umidade) throws SQLException {
        if (temperatura > 30 || umidade < 40 || umidade > 70 ){
            EmergenciaDao.salvar(e);
        }
    }
}
