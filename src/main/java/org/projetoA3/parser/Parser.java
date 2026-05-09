package org.projetoA3.parser;

import org.projetoA3.ast.ProjetoNode;
import org.projetoA3.ast.RegraNode;
import org.projetoA3.ast.TermoSegNode;
import org.projetoA3.excessoes.SyntaxException;
import org.projetoA3.lexer.Token;
import org.projetoA3.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    private Token peek() {
        return tokens.get(pos);
    }

    private Token consume() {
        return tokens.get(pos++);
    }

    private Token expect(TokenType type) {
        Token t = peek();
        if (t.getType() != type) {
            throw new SyntaxException("Esperava: " + type + " mas encontrou '"
                    + t.getValor() + "'", t.getLinha(), t.getColuna());
        }
        return consume();
    }

    public ProjetoNode parse() {
        return parseProjeto();
    }

    private ProjetoNode parseProjeto() {
        expect(TokenType.AUDITORIA);
        String nome = expect(TokenType.IDENTIFICADOR).getValor();
        expect(TokenType.DEFINIR);
        List<RegraNode> regras = parseRegras();
        expect(TokenType.EXECUTAR);
        expect(TokenType.FIM);
        return new ProjetoNode(nome, regras);
    }

    private List<RegraNode> parseRegras() {
        List<RegraNode> regras = new ArrayList<>();
        regras.add(parseRegraUnitaria());
        while (peek().getType() == TokenType.REGRA) {
            regras.add(parseRegraUnitaria());
        }
        return regras;

    }

    private RegraNode parseRegraUnitaria() {
        expect(TokenType.REGRA);
        String nome = expect(TokenType.IDENTIFICADOR).getValor();
        expect(TokenType.NIVEL);
        String nivel = parsePrioridade();
        expect(TokenType.SE);
        List<TermoSegNode> condicoes = new ArrayList<>();
        List<String> operadores = new ArrayList<>();
        condicoes.add(parseTermoSeg());

        while (peek().getType() == TokenType.AND || peek().getType() == TokenType.OR) {
            operadores.add((consume().getValor()));
            condicoes.add(parseTermoSeg());
        }
        expect(TokenType.ACAO);
        String acao = parseResultado();
        expect(TokenType.PONTO_VIRGULA);
        return new RegraNode(nome, nivel, condicoes, operadores, acao);

    }

    // <prioridade> ::= "BAIXA" | "MEDIA" | "ALTA" | "CRITICA"
    private String parsePrioridade() {
        Token t = peek();
        if (t.getType() == TokenType.BAIXA || t.getType() == TokenType.MEDIA ||
                t.getType() == TokenType.ALTA || t.getType() == TokenType.CRITICA) {
            return consume().getValor();
        }
        throw new SyntaxException("Prioridade invalida '" + t.getValor() + "'. Use: BAIXA, MEDIA, ALTA ou CRITICA",
                t.getLinha(), t.getColuna());
    }

    private TermoSegNode parseTermoSeg() {
        String chave = parseChave();
        String operador = parseOperador();
        String valor = expect(TokenType.STRING).getValor();
        return new TermoSegNode(chave, operador, valor);
    }

    private String parseChave() {
        Token t = peek();
        if (t.getType() == TokenType.TOKEN || t.getType() == TokenType.SCOPE ||
            t.getType() == TokenType.USER_ROLE || t.getType() == TokenType.FILE_TYPE ||
            t.getType() == TokenType.PERMISSION) {
            return consume().getValor();
        }
        throw new SyntaxException("Chave inválida '" + t.getValor() + ". Use: TOKEN, SCOPE, USER_ROLE, FILE_TYPE, PERMISSION",
                t.getLinha(), t.getColuna());

    }

    private String parseOperador() {
        Token t = peek();
        if(t.getType() == TokenType.CONTAINS || t.getType() == TokenType.EQUALS ||
        t.getType() == TokenType.MATCHES || t.getType() == TokenType.NOT) {
            return consume().getValor();
        }
        throw new SyntaxException("Operador inválido '" + t.getValor() + "'. Use: CONTAINS, EQUALS, MATCHES ou NOT",
                t.getLinha(), t.getColuna());
    }

    private String parseResultado() {
        Token t = peek();
        if (t.getType() == TokenType.REJEITAR || t.getType() == TokenType.NOTIFICAR ||
        t.getType() == TokenType.SOLICITAR_MFA || t.getType() == TokenType.LOG_ERROR) {
            return consume().getValor();
        }
        throw new SyntaxException("Ação inválida '" + t.getValor() + "'. Use: REJIETAR, NOTIFICAR, SOLICITAR_MFA ou LOG_ERROR",
                t.getLinha(), t.getColuna());




    }
}