package weg.seguranca;

import weg.seguranca.service.SenderSQL;
import weg.seguranca.service.TestInflux;

public class Main {
    public static void main(String[] args) {
        TestInflux.insertTeste();
        TestInflux.recebimento();
    }
}
