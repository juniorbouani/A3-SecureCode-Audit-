package org.projetoA3.semantic;

import org.projetoA3.ast.ProjetoNode;
import org.projetoA3.ast.RegraNode;
import org.projetoA3.excessoes.SemanticException;

import java.util.HashSet;
import java.util.Set;

public class SemanticAnalyzer {

    private final ProjetoNode projeto;
    private final Set<String> nomesUsados = new HashSet<>();


    public SemanticAnalyzer(ProjetoNode projeto) {
        this.projeto = projeto;
    }

    public void analisar() {
        for (RegraNode regra : projeto.getRegras()) {
            validarNomeDuplicado(regra);
            validarCondicoes(regra);
            validarNivelAcao(regra);

        }
        System.out.println("SEMANTICO: Analise concluida sem erros!");
    }

    private void validarNomeDuplicado(RegraNode regra) {
        if (nomesUsados.contains(regra.getNome())) {
            throw new SemanticException(
                    "Regra duplicada: '" + regra.getNome() + "' já foi denifinida anteriormente."
            );
        }
        nomesUsados.add(regra.getNome());

    }

    private void validarCondicoes(RegraNode regra) {
        if ( regra.getCondicoes().isEmpty()) {
            throw new SemanticException(
                    "Regra '" + regra.getNome() + "' não possui nenhuma condição."
            );
        }

    }

    private void validarNivelAcao(RegraNode regra) {
        String nivel = regra.getNivel();
        String acao = regra.getAcao();

        switch (nivel) {
            case "BAIXA" -> {
                if (acao.equals("REJEITAR") || acao.equals("SOLICITAR_MFA")) {
                    throw new SemanticException("Regra '" + regra.getNome() + "': nível BAIXA não pode ter ação " + acao +
                            ". " + "Use NOTIFICAR ou LOG_ERROR."
                    );
                }

            }
            case "MEDIA" -> {
                if (acao.equals("REJEITAR")) {
                    throw new SemanticException("Regra '" + regra.getNome() + "': nível MEDIA não pode ter ação REJEITAR" +
                            "Use NOTIFICAR, LOG_ERROR ou SOLICITAR_MFA");

                }
            }
        }
    }
}
