package org.projetoA3.engine;

import org.projetoA3.ast.ProjetoNode;
import org.projetoA3.ast.RegraNode;
import org.projetoA3.ast.TermoSegNode;

import java.util.List;

public class InferenceEngine {

    private final ProjetoNode projeto;

    public InferenceEngine(ProjetoNode projeto)  { this.projeto = projeto; }

    public void processar(List<String> linhasLog) {
        System.out.println("\n=== MOTOR DE INFERENCIA ===\n");

        for (int i=0; i<linhasLog.size(); i++) {
            String linhaLog = linhasLog.get(i);
            int numeroLinha = i + 1;
            boolean algumViolado = false;

            for(RegraNode regra : projeto.getRegras()) {
                if (avaliarRegra(regra, linhaLog)) {
                    System.out.println("VIOLACAO Linha " + numeroLinha +
                            " → Regra '" + regra.getNome() +
                            "' violada! Ação: " + regra.getAcao());
                    algumViolado = true;
                }
            }
            if (!algumViolado) {
                System.out.println("OK! Linha " + numeroLinha +
                        " → Nenhuma regra violada.");
            }
        }
        System.out.println("\n=== FIM ===\n");
    }

    private boolean avaliarRegra(RegraNode regra, String linhaLog) {
        List<TermoSegNode> condicoes = regra.getCondicoes();
        List<String> operadores = regra.getOperadoresLogicos();

        boolean resultado = avaliarTermo(condicoes.get(0), linhaLog);

        for (int i=0; i<operadores.size(); i++) {
            boolean proximoTermo = avaliarTermo(condicoes.get(i + 1), linhaLog);
            if (operadores.get(i).equals("AND")) {
                resultado = resultado && proximoTermo;
            } else if (operadores.get(i).equals("OR")) {
                resultado = resultado || proximoTermo;
            }
        }
        return resultado;
    }

    private boolean avaliarTermo(TermoSegNode termo, String linhaLog) {
        String chave = termo.getChave().trim();
        String operador = termo.getOperador().trim();
        String valor = termo.getValor().trim();

        String valorNoLog = extrairValor(chave, linhaLog);

        return switch (operador) {
            case "CONTAINS" -> linhaLog.toLowerCase().contains(valor.toLowerCase());
            case "EQUALS" ->  valorNoLog != null && valorNoLog.equalsIgnoreCase(valor);
            case "MATCHES" -> valorNoLog != null && valorNoLog.matches(valor);
            case "NOT" -> !linhaLog.toLowerCase().contains(valor.toLowerCase());
            default -> false;
        };
    }

    private String extrairValor(String chave, String linhaLog) {
        String chaveLog = switch (chave) {
            case "TOKEN" -> "token";
            case "SCOPE" -> "scope";
            case "USER_ROLE" -> "usuario";
            case "FILE_TYPE" ->  "file_type";
            case "PERMISSION" -> "permission";
            default -> chave.toLowerCase();
        };

        for (String parte : linhaLog.split(" ")) {
            if (parte.toLowerCase().startsWith(chaveLog + "=")) {
                return parte.split("=", 2)[1];
            }
        }
        return null;
    }
}
