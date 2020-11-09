package main;

import main.boolop.BooleanOperation;
import main.boolop.EqualsOperation;
import main.boolop.GreaterOperation;
import main.boolop.LessOperation;
import main.function.FunctionClass;
import main.function.PrintFunction;
import main.numop.NumberOperation;
import main.numop.PlusOperation;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static main.Patterns.*;

public class CompileFileCode {
    Map<String, Integer> variablesPool = new HashMap<>();

    public ClassWriter cw;

    public MethodVisitor methodVisitor;

    int variablesCounter = 1;

    BufferedReader in;

    List<NumberOperation> numberOperations = new ArrayList<>();

    List<BooleanOperation> booleanOperation = new ArrayList<>();

    List<FunctionClass> functions = new ArrayList<>();

    public CompileFileCode(BufferedReader in) {
        generateBeginning();

        this.in = in;

        numberOperations.add(new PlusOperation("+", this));

        booleanOperation.add(new EqualsOperation("=", this));
        booleanOperation.add(new LessOperation("<", this));
        booleanOperation.add(new GreaterOperation(">", this));

        functions.add(new PrintFunction(this));
    }

    private void createInteger(String src) {
        Integer value = Integer.parseInt(src);
        cw.newConst(value);
        methodVisitor.visitLdcInsn(value);
    }

    private void loadVariable(Integer m) {
        methodVisitor.visitVarInsn(Opcodes.ILOAD, m);
    }

    public void parseSimpleValue(String src) throws CompileError {
        if (Pattern.matches("\\d+", src))
            createInteger(src);
        else {
            if(variablesPool.containsKey(src))
                loadVariable(variablesPool.get(src));
            else
                throw new CompileError("Bad value");
        }
    }
    public void generateNumberExpression(String src) throws CompileError {
        src = src.replace(" ", "");
        if(src.equals(""))
            throw  new CompileError("Empty token");

        int prevIndex = 0;
        int nearestIndex = -1;
        int curIndex = -1;

        NumberOperation lastOperation = null;
        NumberOperation currentOperation = null;

        while(prevIndex < src.length()) {
            nearestIndex = -1;
            for (NumberOperation s : numberOperations) {
                curIndex = src.indexOf(s.value, prevIndex);
                if ((curIndex < nearestIndex || nearestIndex == -1) && curIndex != -1) {
                    nearestIndex = curIndex;
                    currentOperation = s;
                }
            }
            if(nearestIndex == -1) {
                parseSimpleValue(src.substring(prevIndex));
                if(lastOperation != null)
                    lastOperation.proceed();
            }
            else {
                parseSimpleValue(src.substring(prevIndex, nearestIndex));
                if(lastOperation != null)
                    lastOperation.proceed();
                lastOperation = currentOperation;
            }
            prevIndex = nearestIndex == -1 ? src.length() : nearestIndex + 1;
        }
    }

    private void calkIntValue(String src) throws CompileError {
        System.out.println("Int!");
        String[] tokens = src.split("=");

        if (tokens.length != 2)
            throw new CompileError("Should be one =");

        tokens[0] = tokens[0].replace(" ", "");
        tokens[0] = tokens[0].replace("new", "");

        generateNumberExpression(tokens[1]);

        methodVisitor.visitVarInsn(Opcodes.ISTORE, variablesCounter);
        variablesPool.put(tokens[0], variablesCounter);
        variablesCounter++;
    }

    public void assignVariable(String src) throws CompileError {
        String[] tokens = src.split("=");

        if (tokens.length != 2)
            throw new CompileError("Should be one =");

        tokens[0] = tokens[0].replace(" ", "");
        generateNumberExpression(tokens[1]);

        if(!variablesPool.containsKey(tokens[0]))
            throw new CompileError("no such variable " + tokens[0]);

        methodVisitor.visitVarInsn(Opcodes.ISTORE, variablesPool.get(tokens[0]));
    }

    private void proceedSubBlock(String src, Label label) throws CompileError, IOException {
        int ind;

        for(BooleanOperation op : booleanOperation) {
            ind = src.indexOf(op.value);
            if (ind != -1) {
                if (src.indexOf(op.value, ind + 1) != -1)
                    throw new CompileError("Bad if: " + src);
                generateNumberExpression(src.substring(0, ind));
                generateNumberExpression(src.substring(ind + 1));

                op.jump(label);
                proceedCode();
                return;
            }
        }
    }

    public void proceedIf(String src) throws CompileError, IOException {
        Label ifLabel = new Label();

        src = src.replace(" ", "");
        src = src.substring(2, src.length() - 1);

        proceedSubBlock(src, ifLabel);
        methodVisitor.visitLabel(ifLabel);
    }

    public void proceedFor(String src) throws CompileError, IOException {
        Label forLabel = new Label();
        Label beginLabel = new Label();

        src = src.substring(3, src.length() - 1);

        String[] tokens = src.split(":");

        if(tokens.length != 3)
            throw  new CompileError("Wrong for");
        calkIntValue(tokens[0]);

        methodVisitor.visitLabel(beginLabel);
        proceedSubBlock(tokens[1], forLabel);
        assignVariable(tokens[2]);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, beginLabel);
        methodVisitor.visitLabel(forLabel);
    }

    public void proceedWhile(String src) throws IOException, CompileError {
        Label whileLabel = new Label();
        Label beginLabel = new Label();

        src = src.substring(5, src.length() - 1);

        methodVisitor.visitLabel(beginLabel);
        proceedSubBlock(src, whileLabel);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, beginLabel);
        methodVisitor.visitLabel(whileLabel);
    }

    public void generateBeginning() {
        cw = new ClassWriter(ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, "Main", null, "java/lang/Object", null);

        methodVisitor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        methodVisitor.visitInsn(Opcodes.RETURN);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();

        methodVisitor = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        methodVisitor.visitCode();
    }

    public void proceedCode() throws CompileError, IOException {
        boolean flag;

        while (true) {
            flag = true;
            String line = in.readLine();

            if (line == null || line.equals("}"))
                return;

            line = line.trim();

            if (line.isEmpty())
                continue;

            if (Pattern.matches(variableCreationPattern, line)) {
                calkIntValue(line);
                flag = false;
            }
            if(Pattern.matches(assignPattern, line)) {
                assignVariable(line);
                flag = false;
            }
            if(Pattern.matches(ifPattern, line)) {
                proceedIf(line);
                flag = false;
            }
            if(Pattern.matches(forPattern, line)) {
                proceedFor(line);
                flag = false;
            }
            if(Pattern.matches(whilePattern, line)) {
                proceedWhile(line);
                flag = false;
            }
            for (FunctionClass function : functions) {
                if (function.isThisFunction(line)) {
                    function.visitFunction(line);
                    flag = false;
                    break;
                }
            }
            if (flag)
                throw new CompileError("Bad construction: " + line);
        }
    }

    public void writeResultToFile() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("Main.class");
        methodVisitor.visitInsn(Opcodes.RETURN);
        methodVisitor.visitMaxs(1, 3);
        cw.visitEnd();
        fileOutputStream.write(cw.toByteArray());
    }
}
