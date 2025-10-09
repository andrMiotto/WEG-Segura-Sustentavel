import mysql from 'mysql2/promise';
import express from 'express';
import path from 'path';
import { fileURLToPath } from 'url';
import 'dotenv/config';

const app = express()
const PORT = 3000;

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

app.use(express.static(path.join(__dirname, '../main/resources/static')));
app.use(express.static(path.join(__dirname, '../main/resources/templates')));

app.listen(PORT, () => {
  console.log(`Acesso aqui: http://localhost:${PORT}`);
});

const db = await mysql.createConnection({
    host: process.env.HOST,
    port: process.env.PORT,
    user: process.env.USER,
    password: process.env.PASSWORD,
    database: process.env.DATABASE
})

app.get('/api/salas', async(req, res) => {
    try{
        const [resultados] = await db.query(
            "SELECT id, numero, bloco, portaria, unidade, situacao_de_risco, id_emergencia_atual FROM salas"
        );

        res.json(resultados);

    }catch(err){
        console.log("Erro na execução da query do banco de dados:");
        console.log(err);
    }
})

app.get('/api/pessoas', async(req, res) => {
    try{
        const [resultados] = await db.query(
            "SELECT pessoas.id_sala_atual AS sala_id, COUNT(0) AS total_pessoas FROM pessoas JOIN salas ON pessoas.id_sala_atual = salas.id GROUP BY pessoas.id_sala_atual"
        );

        res.json(resultados);
    
    }catch(err){
        console.log("Erro na execução da query do banco de dados:");
        console.log(err);
    }
})

app.get('/api/emergencias', async(req, res) => {
    try{
        const [resultados] = await db.query(
            "SELECT id, titulo, descricao, inicio, fim, em_andamento FROM emergencias"
        );

        res.json(resultados);

    }catch(err){
        console.log("Erro na execução da query do banco de dados:");
        console.log(err);
    }
})