package br.com.craftlife.minerva.home.model;

import lombok.Getter;

@Getter
public enum HomeType {

    PRIVADA("Privada"), PUBLICA("Pública"), TEMPORARIA("Temporária"), PADRAO("Padrão");

    private final String descricao;

    HomeType(String descricao) {
        this.descricao = descricao;
    }

}
