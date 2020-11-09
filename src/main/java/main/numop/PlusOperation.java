package main.numop;

import main.CompileError;
import main.CompileFileCode;
import org.objectweb.asm.Opcodes;

public class PlusOperation extends NumberOperation {
    public PlusOperation(String value, CompileFileCode compileFileCode) {
        super(value, compileFileCode);
    }

    @Override
    public void proceed() throws CompileError {
        compileFileCode.methodVisitor.visitInsn(Opcodes.IADD);
    }
}
