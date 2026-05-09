package org.projetoA3.lexer;

import org.projetoA3.excessoes.LexicalException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Scan {

    private static final Map<String, TokenType> palavrasChave = Map.ofEntries(
            Map.entry("AUDITORIA", TokenType.AUDITORIA),
            Map.entry("DEFINIR", TokenType.DEFINIR),
            Map.entry("EXECUTAR", TokenType.EXECUTAR),
            Map.entry("FIM", TokenType.FIM),
            Map.entry("REGRA", TokenType.REGRA),
            Map.entry("NIVEL", TokenType.NIVEL),
            Map.entry("SE", TokenType.SE),
            Map.entry("ACAO", TokenType.ACAO),
            Map.entry("AND", TokenType.AND),
            Map.entry("OR", TokenType.OR),
            Map.entry("BAIXA", TokenType.BAIXA),
            Map.entry("MEDIA", TokenType.MEDIA),
            Map.entry("ALTA", TokenType.ALTA),
            Map.entry("CRITICA", TokenType.CRITICA),
            Map.entry("TOKEN", TokenType.TOKEN),
            Map.entry("SCOPE", TokenType.SCOPE),
            Map.entry("USER_ROLE", TokenType.USER_ROLE),
            Map.entry("FILE_TYPE", TokenType.FILE_TYPE),
            Map.entry("PERMISSION",TokenType.PERMISSION),
            Map.entry("CONTAINS", TokenType.CONTAINS),
            Map.entry("EQUALS", TokenType.EQUALS),
            Map.entry("MATCHES", TokenType.MATCHES),
            Map.entry("NOT", TokenType.NOT),
            Map.entry("REJEITAR", TokenType.REJEITAR),
            Map.entry("NOTIFICAR", TokenType.NOTIFICAR),
            Map.entry("SOLICITAR_MFA", TokenType.SOLICITAR_MFA),
            Map.entry("LOG_ERROR", TokenType.LOG_ERROR)
    );

    private String source;
    private int posicao = 0;
    private int linha = 1;
    private int coluna = 1;

    public Scan(String source) {
        this.source = source;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (posicao < source.length()) {
            skipWhitespaceAndComments();
            if (posicao >= source.length()) break;

            //pega um cactere dentro de uma posição especifica da String
            char c = source.charAt(posicao);

            // se achar um ; cir um token na hora e avança uma posição
            if (c == ';') {
                tokens.add(new Token(TokenType.PONTO_VIRGULA, ";", linha, coluna));
                advance();
            }
            // se achou aspas significa que achou uma String, vai chamar o metodo readString() que vai ler tudo ate fechar aspas
            else if (c == '"') {
                tokens.add(readString());
            }

            else if (Character.isLetter(c) || c == '_') {
                tokens.add(readWord());
            }
            else {
                throw new LexicalException("Caractere inesperado '" + c + "'", linha, coluna);

            }


        }
        tokens.add(new Token(TokenType.EOF, "", linha, coluna));
        return tokens;
    }

    //Avança um caractere e atualiza a linha/coluna
    private char advance() {
        char c = source.charAt(posicao++);
        if (c == '\n') { linha++; coluna = 1; }
        else { coluna++; }
        return c;
    }

    private void skipWhitespaceAndComments() {
        while (posicao < source.length()) {
            char c =(source.charAt(posicao));
            if (c == '#') {
                while( posicao < source.length() && source.charAt(posicao) != '\n') advance();
            }
            else if (Character.isWhitespace(c)) {
                advance(); }
            else {
                break;
            }
        }
    }

    private Token readString() {
        int startLine = linha, startCol = coluna;
        advance();
        StringBuilder sb = new StringBuilder();
        while (posicao < source.length() && source.charAt(posicao) != '"') {
            sb.append(advance());
        }
        if (posicao >= source.length()) {
            throw new LexicalException("String não fechada na linha: ", startLine, startCol);
        }
        advance();
        return new Token(TokenType.STRING, sb.toString(), startLine, startCol);
    }


    private Token readWord() {
        int startLine = linha, startCol = coluna;
        StringBuilder sb = new StringBuilder();
        while (posicao < source.length()) {
            char c = source.charAt(posicao);
            if (Character.isLetterOrDigit(c) || c == '_') {
                sb.append(advance());
            }
            else {
                break;
            }
        }
        String word = sb.toString();
        // não achou no map retorna o identficador
        TokenType type = palavrasChave.getOrDefault(word, TokenType.IDENTIFICADOR);
        return new Token(type, word, startLine, startCol);

    }

}