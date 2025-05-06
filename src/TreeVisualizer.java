/*import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TreeVisualizer extends Application {

    // Classe Node para a árvore binária de busca
    static class Node {
        char letter;
        String morseCode;
        Node left;
        Node right;

        Node(char letter, String morseCode) {
            this.letter = letter;
            this.morseCode = morseCode;
            this.left = null;
            this.right = null;
        }
    }

    // Classe da árvore binária de busca
    static class MorseBST {
        private Node root;

        public MorseBST() {
            root = null;
        }

        public void insert(char letter, String morseCode) {
            root = insertRecursive(root, letter, morseCode, 0);
        }

        private Node insertRecursive(Node current, char letter, String morseCode, int index) {
            if (index == morseCode.length()) {
                return new Node(letter, morseCode);
            }

            if (current == null) {
                current = new Node(' ', "");
            }

            char symbol = morseCode.charAt(index);
            if (symbol == '.') {
                current.left = insertRecursive(current.left, letter, morseCode, index + 1);
            } else if (symbol == '-') {
                current.right = insertRecursive(current.right, letter, morseCode, index + 1);
            }

            return current;
        }

        // Calcula a altura da árvore
        public int getHeight() {
            return getHeight(root);
        }

        private int getHeight(Node node) {
            if (node == null) {
                return 0;
            }
            return 1 + Math.max(getHeight(node.left), getHeight(node.right));
        }

        public void drawTree(Canvas canvas) {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            drawNode(gc, root, canvas.getWidth() / 2, 40, canvas.getWidth() / 4, 1);
        }

        private void drawNode(GraphicsContext gc, Node node, double x, double y, double xOffset, int level) {
            if (node == null) return;

            // Desenha círculo
            gc.strokeOval(x - 15, y - 15, 30, 30);

            // Letra dentro do círculo
            gc.strokeText(String.valueOf(node.letter == ' ' ? ' ' : node.letter), x - 5, y + 5);

            if (node.left != null) {
                double newX = x - xOffset;
                double newY = y + 120;
                gc.strokeLine(x, y + 15, newX, newY - 15);
                drawNode(gc, node.left, newX, newY, xOffset / 2, level + 1);
            }

            if (node.right != null) {
                double newX = x + xOffset;
                double newY = y + 120;
                gc.strokeLine(x, y + 15, newX, newY - 15);
                drawNode(gc, node.right, newX, newY, xOffset / 2, level + 1);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Visualizador de Árvore Morse");

        MorseBST bst = new MorseBST();

        // Inserindo algumas letras como exemplo
        bst.insert('E', ".");
        bst.insert('T', "-");
        bst.insert('I', "..");
        bst.insert('A', ".-");
        bst.insert('N', "-.");
        bst.insert('M', "--");
        bst.insert('S', "...");
        bst.insert('O', "---");

        // Inicialização de janela
        int height = bst.getHeight();
        int canvasHeight = 100 + height * 100;
        int canvasWidth = (int) Math.pow(2, height) * 40;

        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        bst.drawTree(canvas);

        Group root = new Group();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root, canvasWidth, canvasHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}*/
