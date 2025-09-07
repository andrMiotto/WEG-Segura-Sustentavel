package weg.seguranca.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import weg.seguranca.service.DataReceiveService;

@Controller
@AllArgsConstructor
@RequestMapping("/dashboards")
public class DataController {

    private DataReceiveService dataReceiveService;

    @GetMapping
    public String controllerPage(Model model){

        // métodos de tratamento de dados


        // Endpoint pra pegar dados
        @GetMapping("/getData")
        public String receberDadosDoNoSQL() {

            //nome do método que voces criarem para pegar os dados do NOSQL substitua aqui
            dataReceiveService.pegarDado();
            return "Dados do NOSQL transferidos para ca";
        }

        // Endpoint pra processar
        @PostMapping("/dataProcessing")
        public String tratarDados() {

            //nome do método que voces criarem para tratar dados substitua aqui
            dataReceiveService.tratarDados();
            return "Tratamento de dados feito com sucesso!";
        }


        // Endpoint pra enviar pro MYSQL
        @PostMapping("/setData")
        public String tratarDados() {

            //nome do método que voces criarem para tratar dados substitua aqui
            dataReceiveService.enviarDado();
            return "Envio de dados para o MYSQL feito com sucesso!";
        }

    }
    }

}
