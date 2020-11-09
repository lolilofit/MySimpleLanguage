package main.function;

import main.CompileError;
import main.CompileFileCode;
import org.objectweb.asm.Opcodes;

import java.util.regex.Pattern;

public class PrintFunction extends FunctionClass {

    public PrintFunction(CompileFileCode compileFileCode) {
        super(compileFileCode);
    }

    @Override
    public Boolean isThisFunction(String src) {
        return Pattern.matches(" *print .+ *", src);
    }

    @Override
    public void visitFunction(String src) throws CompileError {
        compileFileCode.methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        String[] values = src.split("print ");

        String value = null;
        if (values.length > 2)
            throw new CompileError("Wrong print");
        if (values.length == 1)
            value = values[0];
        if (values.length == 2)
            value = values[1];

        compileFileCode.generateNumberExpression(value);
        compileFileCode.methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
    }
}
