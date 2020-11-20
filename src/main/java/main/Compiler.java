package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Compiler {

    public static void main(String[] args) throws IOException, CompileError {
        File file = new File(args.length == 0 ? "src/main/resources/main.txt" : args[0]);

        CompileFileCode compiler = new CompileFileCode(
                new BufferedReader(new FileReader(file)),
                file.getName().substring(0, file.getName().length() - 4)
        );

        compiler.proceedCode();

        compiler.writeResultToFile();
    }
}
