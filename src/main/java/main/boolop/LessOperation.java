package main.boolop;

import main.CompileFileCode;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;

public class LessOperation extends BooleanOperation {
    public LessOperation(String value, CompileFileCode compileFileCode) {
        super(value, compileFileCode);
    }

    @Override
    public void jump(Label label) {
        compileFileCode.methodVisitor.visitJumpInsn(Opcodes.IF_ICMPGE, label);
    }
}
