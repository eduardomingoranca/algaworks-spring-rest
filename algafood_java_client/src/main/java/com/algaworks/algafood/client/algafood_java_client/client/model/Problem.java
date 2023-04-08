package com.algaworks.algafood.client.algafood_java_client.client.model;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Problem {
    private Integer status;
    private OffsetDateTime timestamp;
    private String userMessage;
}
