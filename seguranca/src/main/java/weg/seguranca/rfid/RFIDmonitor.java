package weg.seguranca.rfid;

import java.util.HashMap;
import java.util.Map;

public class RFIDmonitor {

    private static final Map<String, LeituraInfo> leituras = new HashMap<>();
    private static final int maxRegistro = 5;
    private static final long tempo = 30000;

    public static void verificarLeitura(String tagId){
        long agora = System.currentTimeMillis();
        LeituraInfo info =  leituras.getOrDefault(tagId, new LeituraInfo(0, agora));

        if(agora - info.ultimoTempo < tempo){
            info.contador++;
        } else {
            info.contador = 1;
        }

        info.ultimoTempo = agora;
        leituras.put(tagId, info);

        if(info.contador > maxRegistro){
            throw new LeituraRFIDRepetida(tagId, info.contador, tempo);
        }
    }

    public static class LeituraInfo{
        int contador;
        long ultimoTempo;

        public LeituraInfo(int contador, long ultimoTempo){
            this.contador = contador;
            this.ultimoTempo = ultimoTempo;
        }
    }
}
