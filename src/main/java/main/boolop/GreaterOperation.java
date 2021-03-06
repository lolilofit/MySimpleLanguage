package main.boolop;

import main.CompileFileCode;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;

public class GreaterOperation extends BooleanOperation {
    public GreaterOperation(String value, CompileFileCode compileFileCode) {
        super(value, compileFileCode);
    }

    @Override
    public void jump(Label label, Boolean isNot) {
        if(isNot)
            compileFileCode.methodVisitor.visitJumpInsn(Opcodes.IF_ICMPGT, label);
        else
            compileFileCode.methodVisitor.visitJumpInsn(Opcodes.IF_ICMPLE, label);
    }
}
