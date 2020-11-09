package main;

public class Patterns {
    static String variableCreationPattern = " *new \\w+ *= *.+ *";

    static String ifPattern = " *if.+\\{ *";

    static String assignPattern = " *\\w+ *=.+ *";

    static String forPattern = " *for *new \\w+ *= *.+: *.+ *: *\\w+ *=.+ *\\{ *";

    static String whilePattern = " *while.+\\{ *";
}
