package com.algaworks.algafood.core.web;

import org.springframework.http.MediaType;
import static org.springframework.http.MediaType.valueOf;

public class AlgaMediaTypes {
    public static final String V1_APPLICATION_JSON_VALUE = "application/vnd.algafood.v1+json";

    public static final MediaType V1_APPLICATION_JSON = valueOf(V1_APPLICATION_JSON_VALUE);
}
