package main.types;

import main.CompileError;
import main.CompileFileCode;
import org.objectweb.asm.Opcodes;

public class StringType extends SimpleType {
    public static String typeName = "str";

    public static String pattern = " *\".*\" *";

    int mark = -1;

    public StringType(CompileFileCode compileFileCode) {
        super(compileFileCode);
    }

    @Override
    public SimpleType copyVariable() {
        return new StringType(compileFileCode);
    }

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
        return "Ljava/lang/String;";
    }

    @Override
    public void addValueToPool(String src) {
        compileFileCode.cw.newConst(src);
        compileFileCode.methodVisitor.visitLdcInsn(src);
    }

    @Override
    public void getValueFromPool(String varName, String expectedType) throws CompileError {
        if(!expectedType.equals(typeName))
            throw new CompileError("Bad type " + varName);

        compileFileCode.methodVisitor.visitVarInsn(Opcodes.ALOAD, compileFileCode.variablesPool.get(varName).getMark());
    }

    @Override
    public void putExistingVariableToPool(String varName) {
        compileFileCode.methodVisitor.visitVarInsn(Opcodes.ASTORE, compileFileCode.variablesPool.get(varName).getMark());
    }

    @Override
    public void saveVariable(String varName) {
        compileFileCode.methodVisitor.visitVarInsn(Opcodes.ASTORE, compileFileCode.variablesCounter);
        mark = compileFileCode.variablesCounter;

        compileFileCode.variablesPool.put(varName, this);
        compileFileCode.variablesCounter++;
    }
}
