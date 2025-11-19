package com.backendLevelup.Backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Blog {
    private Long id;
    private Usuario usuario;
    private String TituloBlog;
    private String CuerpoBlog;
}
