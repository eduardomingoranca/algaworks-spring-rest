package com.algaworks.algafood.api.hostcheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

import static java.net.InetAddress.getLocalHost;

@RestController
public class HostCheckController {

    @GetMapping("/hostcheck")
    public String checkHost() throws UnknownHostException {
        // obtendo o host/ip do container que responde a requisicao
        return getLocalHost().getHostAddress()
                + " - " + getLocalHost().getHostName();
    }

}
