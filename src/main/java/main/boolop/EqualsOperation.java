package main.boolop;

import main.CompileFileCode;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;

public class EqualsOperation extends BooleanOperation{
    public EqualsOperation(String value, CompileFileCode compileFileCode) {
        super(value, compileFileCode);
    }

    @Override
    public void jump(Label label, Boolean isNot) {
        if(isNot)
            compileFileCode.methodVisitor.visitJumpInsn(Opcodes.IF_ICMPEQ, label);
        else
            compileFileCode.methodVisitor.visitJumpInsn(Opcodes.IF_ICMPNE, label);
    }
}
