package org.projetoA3.parser;

import org.projetoA3.ast.ProjetoNode;
import org.projetoA3.ast.RegraNode;
import org.projetoA3.excessoes.SyntaxException;
import org.projetoA3.lexer.Token;
import org.projetoA3.lexer.TokenType;

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
        if(t.getType() != type) {
            throw new SyntaxException("Esperava: " + type + " mas encontrou '"
                    + t.getValor() + "'", t.getLinha(),  t.getColuna());
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
    }

}
