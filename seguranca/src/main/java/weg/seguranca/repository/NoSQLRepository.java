package weg.seguranca.repository;

import org.springframework.stereotype.Repository;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import weg.seguranca.dto.LogMovimentoDTO;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class NoSQLRepository {

    private static final String url = "http://localhost:8086";
    private static final char[] token = "nU8725HIFJQYxLu0dbOKyVuNjQfrBaZf0bSi6pakaVNkG3BbygOEzSjtTRJ9sZ1JtdHfCZ9YXRPlWCbIQgHr0g==".toCharArray();
    private static final String org = "WegSegura";
    private static final String bucket = "WegSegura";

    public Connection testConnection() {
        try (InfluxDBClient client = InfluxDBClientFactory.create(url, token, org, bucket)) {
            System.out.println("✅ Conexão InfluxDB estabelecida com sucesso!");
        } catch (Exception e) {
            System.err.println("❌ Erro de conexão InfluxDB: " + e.getMessage());
        }
        return null;
    }

    public void insertData() {
        InfluxDBClient client = InfluxDBClientFactory.create(url, token, org, bucket);
        WriteApiBlocking writeApi = client.getWriteApiBlocking();

        try {
            Point ponto5 = Point.measurement("logs_sensores")
                    .addTag("sala", "2")
                    .addTag("pessoa", "sigma")
                    .addTag("quem leu é sigma boy", "69")
                    .addField("ha_movimento_na_sala", true)
                    .time(System.currentTimeMillis(), WritePrecision.MS);

            writeApi.writePoint(ponto5);
            System.out.println("Dados inseridos com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao inserir dados: " + e.getMessage());
        } finally {
            client.close();
        }
    }

    // falta verificar as tables do influx, aguardando team IOT. Não está 100% funcional pois depende das row do DB
    public List<LogMovimentoDTO> gettAllMoviments(){
        List<LogMovimentoDTO> movimentos = new ArrayList<>();
        try{
            InfluxDBClient influxDatabase = InfluxDBClientFactory.create(url, token, org, bucket);
            String query = "from(bucket: \"WegSegura\") |> range(start: -1h) |> filter(fn: (r) => r._measurement == \"logs_sensores\")";
            var tables = influxDatabase.getQueryApi().query(query);
            for(var table : tables){
                for(var record : table.getRecords()){
                    LogMovimentoDTO movimento = new LogMovimentoDTO();
                    movimento.setSala((record.getValueByKey("sala")).toString());
                    movimento.setPessoa((record.getValueByKey("pessoa")).toString());
                    movimento.setHaMovimento((Boolean) record.getValueByKey("ha_movimento_na_sala"));
                    movimento.setTimestamp(record.getTime());
                    movimentos.add(movimento);
                }
            }
        } catch (Exception e){
            System.err.println("Erro ao buscar dados: " + e.getMessage());
        }

        return movimentos;
    }


}
