package main;

public class CompileError extends Exception {
    private String message;

    public CompileError(String message) {this.message = message;}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
