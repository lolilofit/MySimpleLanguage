package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Compiler {

    public static void main(String[] args) throws IOException, CompileError {
        CompileFileCode compiler = new CompileFileCode(
                new BufferedReader(new FileReader(args.length == 0 ? "src/main/resources/main.txt" : args[0]))
        );

        compiler.proceedCode();

        compiler.writeResultToFile();
    }
}
