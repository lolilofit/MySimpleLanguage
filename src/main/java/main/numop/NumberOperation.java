package main.numop;

import main.CompileError;
import main.CompileFileCode;

public abstract class NumberOperation {
    public String value;

    public CompileFileCode compileFileCode;

    public abstract void proceed() throws CompileError;

    public NumberOperation(String value, CompileFileCode compileFileCode) {
        this.value = value;
        this.compileFileCode = compileFileCode;
    }
}
