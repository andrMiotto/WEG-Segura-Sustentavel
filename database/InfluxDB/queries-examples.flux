// ========================================
// EXEMPLOS DE CONSULTAS FLUX PARA INFLUXDB
// Projeto WEG Segura - InfluxDB v2
// ========================================

// 1. DADOS DOS ÚLTIMOS 30 MINUTOS
from(bucket: "WegSegura")
  |> range(start: -30m)
  |> filter(fn: (r) => r._measurement == "logs_sensores")

// 2. DADOS DE UMA SALA ESPECÍFICA (últimas 24h)
from(bucket: "WegSegura")
  |> range(start: -24h)
  |> filter(fn: (r) => r._measurement == "logs_sensores" and r.sala == "1")

// 3. DADOS DE UMA PESSOA ESPECÍFICA
from(bucket: "WegSegura")
  |> range(start: -1h)
  |> filter(fn: (r) => r._measurement == "logs_sensores" and r.pessoa == "10")

// 4. MOVIMENTO DETECTADO NAS ÚLTIMAS 2 HORAS
from(bucket: "WegSegura")
  |> range(start: -2h)
  |> filter(fn: (r) => r._measurement == "logs_sensores" and r.ha_movimento_na_sala == true)

// 5. DADOS AGRUPADOS POR SALA (últimas 24h)
from(bucket: "WegSegura")
  |> range(start: -24h)
  |> filter(fn: (r) => r._measurement == "logs_sensores")
  |> group(columns: ["sala"])
  |> count()

// 6. MÉDIA DE MOVIMENTO POR HORA (últimas 24h)
from(bucket: "WegSegura")
  |> range(start: -24h)
  |> filter(fn: (r) => r._measurement == "logs_sensores")
  |> aggregateWindow(every: 1h, fn: mean, createEmpty: false)

// 7. CONTAGEM DE EVENTOS POR TIPO (últimas 12h)
from(bucket: "WegSegura")
  |> range(start: -12h)
  |> filter(fn: (r) => r._measurement == "logs_sensores")
  |> group(columns: ["tipo_evento"])
  |> count()

// 8. DADOS DE SALAS EM SITUAÇÃO DE RISCO
from(bucket: "WegSegura")
  |> range(start: -1h)
  |> filter(fn: (r) => r._measurement == "logs_sensores" and r.situacao_risco == true)

// 9. ÚLTIMO VALOR DE CADA SENSOR
from(bucket: "WegSegura")
  |> range(start: -1h)
  |> filter(fn: (r) => r._measurement == "logs_sensores")
  |> last()

// 10. DADOS AGRUPADOS POR BLOCO (últimas 24h)
from(bucket: "WegSegura")
  |> range(start: -24h)
  |> filter(fn: (r) => r._measurement == "logs_sensores")
  |> group(columns: ["bloco"])
  |> aggregateWindow(every: 30m, fn: mean, createEmpty: false)

// ========================================
// CONSULTAS PARA DASHBOARDS
// ========================================

// DASHBOARD: VISÃO GERAL DAS SALAS
from(bucket: "WegSegura")
  |> range(start: -1h)
  |> filter(fn: (r) => r._measurement == "logs_sensores")
  |> group(columns: ["sala"])
  |> last()
  |> sort(columns: ["sala"])

// DASHBOARD: HISTÓRICO DE MOVIMENTO
from(bucket: "WegSegura")
  |> range(start: -7d)
  |> filter(fn: (r) => r._measurement == "logs_sensores")
  |> aggregateWindow(every: 1h, fn: count, createEmpty: false)
  |> yield(name: "movimento_por_hora")

// DASHBOARD: ALERTAS EM TEMPO REAL
from(bucket: "WegSegura")
  |> range(start: -5m)
  |> filter(fn: (r) => r._measurement == "logs_sensores" and r.ha_movimento_na_sala == true)
  |> yield(name: "alertas_movimento")
