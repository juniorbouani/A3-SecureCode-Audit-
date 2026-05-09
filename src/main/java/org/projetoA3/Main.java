package org.projetoA3;

import org.projetoA3.ast.ProjetoNode;
import org.projetoA3.excessoes.FileException;
import org.projetoA3.excessoes.LexicalException;
import org.projetoA3.excessoes.SyntaxException;
import org.projetoA3.lexer.Scan;
import org.projetoA3.lexer.Token;
import org.projetoA3.parser.Parser;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main (String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Digite o caminho do arquivo: ");
        String caminho = scan.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {

            StringBuilder sb = new StringBuilder();

            String linha;
            while ((linha = br.readLine()) != null) {
                sb.append(linha).append("\n");
            }
            String code = sb.toString();

            Scan scanner = new Scan(code);
            List<Token> tokens = scanner.tokenize();
            tokens.forEach(System.out::println);

            Parser parser = new Parser(tokens);
            ProjetoNode projeto = parser.parse();
            System.out.println(projeto);
        }
        catch (LexicalException e ) {
            System.err.println(e.getMessage());
        }
        catch (SyntaxException e) {
            System.err.println(e.getMessage());
        }
        catch (FileNotFoundException e) {
            throw new FileException("Arquivo não encotrado: " + caminho);
        }
        catch (IOException e) {
            throw new FileException("Falha ao ler o arquivo: " + e.getMessage());
        }
        catch (SecurityException e ) {
            throw new FileException("Sem permissão para ler: " + caminho);
        }
        catch (FileException e) {
            System.err.println(e.getMessage());
        }
    }
}
