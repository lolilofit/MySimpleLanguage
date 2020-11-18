package main.types;

import main.CompileError;
import main.CompileFileCode;

public abstract class SimpleType {
    public static String pattern = "";

    CompileFileCode compileFileCode;

    SimpleType(CompileFileCode compileFileCode) {
        this.compileFileCode = compileFileCode;
    }

    public abstract SimpleType copyVariable();

    public abstract String getTypeName();

    public abstract Integer getMark();

    public abstract String getAsmTypeName();

    public abstract void addValueToPool(String src);

    public abstract void getValueFromPool(String varName, String expectedType) throws CompileError;

    public abstract void putExistingVariableToPool(String varName);

    public abstract void saveVariable(String varName);
}
