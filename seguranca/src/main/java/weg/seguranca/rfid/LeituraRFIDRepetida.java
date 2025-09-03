package weg.seguranca.rfid;

public class LeituraRFIDRepetida extends RuntimeException{
    public LeituraRFIDRepetida(String tagId, int repeticoes, long intervalo){
        super("Leitura repetida do RFID [" + tagId + "] detectada " + repeticoes + " vezes em " + intervalo + " ms.");
    }
}
