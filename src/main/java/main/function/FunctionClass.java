package main.function;

import main.CompileError;
import main.CompileFileCode;

public abstract class FunctionClass {
    CompileFileCode compileFileCode;

    public abstract Boolean isThisFunction(String src);

    public abstract void visitFunction(String src) throws CompileError;

    public FunctionClass(
            CompileFileCode compileFileCode
    ) {
        this.compileFileCode = compileFileCode;
    }
}
