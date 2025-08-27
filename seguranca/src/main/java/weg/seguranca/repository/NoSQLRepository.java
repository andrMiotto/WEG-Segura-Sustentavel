package weg.seguranca.repository;

import org.springframework.stereotype.Repository;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;

@Repository
public class NoSQLRepository {

    private static final String url = "http://localhost:8086";
    private static final char[] token = "nU8725HIFJQYxLu0dbOKyVuNjQfrBaZf0bSi6pakaVNkG3BbygOEzSjtTRJ9sZ1JtdHfCZ9YXRPlWCbIQgHr0g==".toCharArray();
    private static final String org = "WegSegura";
    private static final String bucket = "WegSegura";

    public void testConnection() {
        try (InfluxDBClient client = InfluxDBClientFactory.create(url, token, org, bucket)) {
            System.out.println("✅ Conexão InfluxDB estabelecida com sucesso!");
        } catch (Exception e) {
            System.err.println("❌ Erro de conexão InfluxDB: " + e.getMessage());
        }
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
}
