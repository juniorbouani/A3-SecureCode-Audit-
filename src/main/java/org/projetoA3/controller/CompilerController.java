package org.projetoA3.controller;

import org.projetoA3.ast.ProjetoNode;
import org.projetoA3.dto.ResultadoDTO;
import org.projetoA3.engine.InferenceEngine;
import org.projetoA3.excessoes.LexicalException;
import org.projetoA3.excessoes.SemanticException;
import org.projetoA3.excessoes.SyntaxException;
import org.projetoA3.lexer.Scan;
import org.projetoA3.lexer.Token;
import org.projetoA3.parser.Parser;
import org.projetoA3.semantic.SemanticAnalyzer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CompilerController {

    @PostMapping("/analisar")
    public ResponseEntity<ResultadoDTO> analisar (
        @RequestParam("regras") MultipartFile arquivoSc,
        @RequestParam("log")  MultipartFile arquivoLog
    ) {
        StringBuilder compilador = new StringBuilder();

        try {
            String code = new String(arquivoSc.getBytes());
            String logTexto = new String(arquivoLog.getBytes());

            Scan scanner = new Scan(code);
            List<Token> tokens = scanner.tokenize();
            tokens.forEach(t -> compilador.append(t.toString()).append("\n"));

            Parser parser = new Parser(tokens);
            ProjetoNode projeto = parser.parse();

            SemanticAnalyzer semantic = new SemanticAnalyzer(projeto);
            semantic.analisar();
            compilador.append("\nSEMANTICO: Análise concluída sem erros!\n");

            List<String> linhasLog = Arrays.asList(logTexto.split("\n"));
            InferenceEngine engine = new InferenceEngine(projeto);
            List<String> violacoes = engine.processar(linhasLog);

            return ResponseEntity.ok(new ResultadoDTO(compilador.toString(),violacoes));


        } catch (LexicalException | SyntaxException | SemanticException e) {
            return ResponseEntity.ok(
                    new ResultadoDTO(compilador.append("\n" + e.getMessage()).toString(), List.of())
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    new ResultadoDTO("Erro inesperado: " + e.getMessage(), List.of())
            );
        }
    }
}
