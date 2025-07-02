package com.devlu.futuresistema_cliente.controller;

import com.devlu.futuresistema_cliente.business.ClienteService;
import com.devlu.futuresistema_cliente.controller.dto.ClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * Controller para exibir relatórios de clientes.
 */
@Controller
@RequestMapping("/relatorios/clientes")
public class RelatorioClienteController {

    @Autowired
    private ClienteService clienteService;

    /**
     * Exibe o relatório de clientes na tela.
     *
     * @return ModelAndView com a lista de clientes e o nome da view Thymeleaf a ser renderizada.
     */
    @GetMapping("/tela")
    public ModelAndView exibirRelatorioTela() {
        // ALTERAÇÃO: Chamando findAll() em vez de findAllClientes()
        List<ClienteDTO> clientes = clienteService.findAll();
        ModelAndView mv = new ModelAndView("relatorioClientesTela");
        mv.addObject("clientes", clientes);
        return mv;
    }

}