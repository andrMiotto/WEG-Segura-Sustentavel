package weg.seguranca.service;

import org.springframework.stereotype.Service;

import weg.seguranca.repository.MySQLRepository;
import weg.seguranca.repository.NoSQLRepository;

@Service
public class DataReceiveService {

    private final MySQLRepository mySQLRepository;
    private final NoSQLRepository noSQLRepository;

    public DataReceiveService(MySQLRepository mySQLRepository, NoSQLRepository noSQLRepository) {
        this.mySQLRepository = mySQLRepository;
        this.noSQLRepository = noSQLRepository;
    }

    public void importData(){
        /* Aguardar a equipe reponsável pelos models e DTO*/
        /*=============
        *   Prévia
        *=============
        *
        * List <OBJETO-DOCUMENT> documents = noSQLRepository.findAll(); --- Método responsável por encontrar todos os dados do objeto especificado
        * List <OBJETO-ENTIDADE> entidadesTratadas = documents.stream()... ---- Rastrea e ttratar valores, aplicando a regra de negócio
        *
        * MySQLRepository.SaveAll(entidadesTratadas); --- Salva no MySQL os dados "tratados"
        * */


    }

    /* */
    // -
}