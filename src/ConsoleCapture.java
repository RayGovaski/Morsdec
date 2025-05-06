//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//
///**
// * Classe utilitária para capturar a saída do console e redirecionar para um string
// */
//public class ConsoleCapture {
//    private final ByteArrayOutputStream baos;
//    private final PrintStream original;
//    private final PrintStream ps;
//
//    public ConsoleCapture() {
//        this.baos = new ByteArrayOutputStream();
//        this.ps = new PrintStream(baos);
//        this.original = System.out;
//        System.setOut(ps);
//    }
//
//    public String getOutput() {
//        ps.flush();
//        return baos.toString();
//    }
//
//    public void close() {
//        System.setOut(original);
//    }
//}