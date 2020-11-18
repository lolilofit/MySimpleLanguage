package main.types;

import main.CompileError;
import main.CompileFileCode;
import org.objectweb.asm.Opcodes;

public class IntType extends SimpleType {

    public static String pattern = "\\d+";

    public static String typeName = "int";

    public IntType(CompileFileCode compileFileCode) {
        super(compileFileCode);
    }

    @Override
    public SimpleType copyVariable() {
        return new IntType(compileFileCode);
    }

    int mark = -1;

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public Integer getMark() {
        return mark;
    }

    @Override
    public String getAsmTypeName() {
        return "I";
    }

    @Override
    public void addValueToPool(String src) {
        Integer value = Integer.parseInt(src);
        compileFileCode.cw.newConst(value);
        compileFileCode.methodVisitor.visitLdcInsn(value);
    }

    @Override
    public void getValueFromPool(String varName, String expectedType) throws CompileError {
        if(!expectedType.equals(typeName))
            throw new CompileError("Bad type " + varName);

        compileFileCode.methodVisitor.visitVarInsn(Opcodes.ILOAD, compileFileCode.variablesPool.get(varName).getMark());
    }

    @Override
    public void putExistingVariableToPool(String varName) {
        compileFileCode.methodVisitor.visitVarInsn(Opcodes.ISTORE, compileFileCode.variablesPool.get(varName).getMark());
    }

    @Override
    public void saveVariable(String varName) {
        compileFileCode.methodVisitor.visitVarInsn(Opcodes.ISTORE, compileFileCode.variablesCounter);
        mark = compileFileCode.variablesCounter;

        compileFileCode.variablesPool.put(varName, this);
        compileFileCode.variablesCounter++;
    }
}
