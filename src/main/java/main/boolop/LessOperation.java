package main.boolop;

import main.CompileFileCode;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;

public class LessOperation extends BooleanOperation {
    public LessOperation(String value, CompileFileCode compileFileCode) {
        super(value, compileFileCode);
    }

    @Override
    public void jump(Label label, Boolean isNot) {
        if (isNot)
            compileFileCode.methodVisitor.visitJumpInsn(Opcodes.IF_ICMPLT, label);
        else
            compileFileCode.methodVisitor.visitJumpInsn(Opcodes.IF_ICMPGE, label);
    }
}
