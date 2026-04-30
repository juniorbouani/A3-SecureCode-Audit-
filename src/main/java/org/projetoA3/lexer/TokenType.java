package org.projetoA3.lexer;

public enum TokenType {

    // palavras chaves
    AUDITORIA, DEFINIR, EXECUTAR, FIM,
    REGRA, NIVEL, SE, ACAO,

    //Prioridades
    BAIXA, MEDIA, ALTA, CRITICA,

    // Chaves de condiçao
    TOKEN, SCOPE, USER_ROLE, FILE_TYPE, PERMISSION,

    //Operadores Logicos
    AND, OR,

    //Operadores de comparação
    CONTAINS, EQUALS, MATCHES, NOT,

    //Ações
    REIJETAR, NOTIFICAR, SOLICITAR_MFA, LOG_ERROR,

    // Genericos

    IDENTIFICARDOR,
    STRING,
    PONTO_VIRGULA




}
