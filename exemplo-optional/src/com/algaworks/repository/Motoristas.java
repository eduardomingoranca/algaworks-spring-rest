package com.algaworks.repository;

import com.algaworks.model.Caminhao;
import com.algaworks.model.Motorista;
import com.algaworks.model.Seguro;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Motoristas {
    private final Map<String, Optional<Motorista>> motoristas = new HashMap<>();

    public Motoristas() {
        Seguro seguro = new Seguro("Parcial - nao cobre roubo", new BigDecimal("5000"));
        Caminhao caminhao = new Caminhao("Mercedes Atron", Optional.ofNullable(seguro));
        Optional<Motorista> motoristaJuan = Optional.of(new Motorista("Juan", 40, Optional.ofNullable(caminhao)));

        Optional<Motorista> motoristaJose = Optional.of(new Motorista("Jose", 25, Optional.ofNullable(null)));

        motoristas.put("Juan", motoristaJuan);
        motoristas.put("Jose", motoristaJose);
    }

    public Optional<Motorista> porNome(String nome) {
        return motoristas.get(nome);
    }
}
