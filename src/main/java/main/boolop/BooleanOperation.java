package main.boolop;

import main.CompileFileCode;
import org.objectweb.asm.Label;

public abstract class BooleanOperation {
   public String value;

   public CompileFileCode compileFileCode;

   public BooleanOperation(String value, CompileFileCode compileFileCode) {
       this.value = value;
       this.compileFileCode = compileFileCode;
   }

   public abstract void jump(Label label);
}
