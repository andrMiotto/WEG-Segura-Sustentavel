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


        model.addAttribute("sala", 211);
        model.addAttribute("pessoa", 10);
        model.addAttribute("ha_movimento_na_sala", true);

        return "index";
    }

}
