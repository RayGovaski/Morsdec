import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

public class MorseApp extends Application {

    // Componentes da interface
    private Canvas treeCanvas;
    private MorseBST morseTree;
    private double zoomFactor = 1.0;
    private TextField inputField;
    private TextArea outputArea;
    private VBox treePanel;
    private StackPane canvasContainer;
    private ScrollPane canvasScrollPane;
    private Slider zoomSlider;

    // Nome dos arquivos de logo
    private static final String LOGO_FILE = "logo.png";
    private static final String ICON_FILE = "logo2.png";

    // Classe interna para a árvore binária de busca para código Morse
    static class MorseBST {
        private Node root;

        static class Node {
            char letter;
            Node left, right; // ponto à esquerda, traço à direita

            Node(char letter) {
                this.letter = letter;
                this.left = null;
                this.right = null;
            }
        }

        public MorseBST() {
            root = new Node(' '); // Raiz vazia
            buildTree();
        }

        private void buildTree() {
            // Letras
            insert('A', ".-");
            insert('B', "-...");
            insert('C', "-.-.");
            insert('D', "-..");
            insert('E', ".");
            insert('F', "..-.");
            insert('G', "--.");
            insert('H', "....");
            insert('I', "..");
            insert('J', ".---");
            insert('K', "-.-");
            insert('L', ".-..");
            insert('M', "--");
            insert('N', "-.");
            insert('O', "---");
            insert('P', ".--.");
            insert('Q', "--.-");
            insert('R', ".-.");
            insert('S', "...");
            insert('T', "-");
            insert('U', "..-");
            insert('V', "...-");
            insert('W', ".--");
            insert('X', "-..-");
            insert('Y', "-.--");
            insert('Z', "--..");

            // Números
            insert('1', ".----");
            insert('2', "..---");
            insert('3', "...--");
            insert('4', "....-");
            insert('5', ".....");
            insert('6', "-....");
            insert('7', "--...");
            insert('8', "---..");
            insert('9', "----.");
            insert('0', "-----");
        }

        public void insert(char letter, String morseCode) {
            Node current = root;

            // Percorre o código morse para encontrar o local de inserção
            for (int i = 0; i < morseCode.length(); i++) {
                char symbol = morseCode.charAt(i);

                if (symbol == '.') { // Ponto vai para a esquerda
                    if (current.left == null) {
                        current.left = new Node(' ');
                    }
                    current = current.left;
                } else if (symbol == '-') { // Traço vai para a direita
                    if (current.right == null) {
                        current.right = new Node(' ');
                    }
                    current = current.right;
                }
            }

            // Associa o caractere ao nó final
            current.letter = letter;
        }

        public String decode(String morseText) {
            StringBuilder result = new StringBuilder();
            String[] words = morseText.split(" / ");

            // Processa cada palavra
            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                String[] letters = word.trim().split(" ");

                // Decodifica cada letra
                for (int j = 0; j < letters.length; j++) {
                    String morseChar = letters[j];
                    if (!morseChar.isEmpty()) {
                        result.append(decodeLetter(morseChar));
                    }
                }

                // Adiciona espaço entre palavras
                if (i < words.length - 1) {
                    result.append(' ');
                }
            }

            return result.toString();
        }

        private char decodeLetter(String morseChar) {
            Node current = root;

            // Navega na árvore seguindo o código morse
            for (int i = 0; i < morseChar.length(); i++) {
                char symbol = morseChar.charAt(i);

                if (symbol == '.') {
                    current = current.left;
                } else if (symbol == '-') {
                    current = current.right;
                }

                if (current == null) {
                    return '?'; // Caractere não encontrado
                }
            }

            return current.letter;
        }

        public String encode(String text) {
            StringBuilder result = new StringBuilder();
            text = text.toUpperCase();

            // Processa cada caractere do texto
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);

                if (c == ' ') {
                    result.append("/ ");
                } else {
                    String morse = encodeChar(c);
                    result.append(morse).append(" ");
                }
            }

            return result.toString().trim();
        }

        private String encodeChar(char c) {
            // Implementação simples sem HashMap
            // Para cada caractere, verifica qual é seu código morse correspondente
            switch (c) {
                case 'A': return ".-";
                case 'B': return "-...";
                case 'C': return "-.-.";
                case 'D': return "-..";
                case 'E': return ".";
                case 'F': return "..-.";
                case 'G': return "--.";
                case 'H': return "....";
                case 'I': return "..";
                case 'J': return ".---";
                case 'K': return "-.-";
                case 'L': return ".-..";
                case 'M': return "--";
                case 'N': return "-.";
                case 'O': return "---";
                case 'P': return ".--.";
                case 'Q': return "--.-";
                case 'R': return ".-.";
                case 'S': return "...";
                case 'T': return "-";
                case 'U': return "..-";
                case 'V': return "...-";
                case 'W': return ".--";
                case 'X': return "-..-";
                case 'Y': return "-.--";
                case 'Z': return "--..";
                case '1': return ".----";
                case '2': return "..---";
                case '3': return "...--";
                case '4': return "....-";
                case '5': return ".....";
                case '6': return "-....";
                case '7': return "--...";
                case '8': return "---..";
                case '9': return "----.";
                case '0': return "-----";
                default: return "?";
            }
        }

        public int getHeight() {
            return getHeight(root);
        }

        private int getHeight(Node node) {
            if (node == null) {
                return 0;
            }
            return 1 + Math.max(getHeight(node.left), getHeight(node.right));
        }

        public Node getRoot() {
            return root;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        morseTree = new MorseBST();

        VBox mainLayout = Style.createMainLayout();
        setupUI(mainLayout);
        setupEventHandlers();
        setupPrimaryStage(primaryStage, mainLayout);
    }

    private void setupUI(VBox mainLayout) {
        VBox titleBox = Style.createTitlePanel(LOGO_FILE); // Passa o nome do arquivo do logo

        inputField = Style.createInputField();// Campo entrada

        HBox buttonBox = createButtonsWithHandlers();// Painel botoes

        VBox outputPanel = Style.createOutputPanel(); // Painel saida
        outputArea = (TextArea) outputPanel.getChildren().get(1);

        treePanel = Style.createTreePanel();// Painel arvore
        setupTreePanelComponents();

        // Adiciona os componentes ao layout principal
        mainLayout.getChildren().addAll(titleBox, inputField, buttonBox, outputPanel, treePanel);
    }

    private void setupTreePanelComponents() {
        //ScrollPane árvore
        canvasScrollPane = (ScrollPane) treePanel.getChildren().get(1);

        // container do canvas
        canvasContainer = (StackPane) canvasScrollPane.getContent();

        // Cria canvas para arvore e adiciona ao container
        treeCanvas = new Canvas(600, 400);
        canvasContainer.getChildren().add(treeCanvas);

        // slider de zoom painel
        HBox treeHeaderBox = (HBox) treePanel.getChildren().get(0);
        zoomSlider = (Slider) treeHeaderBox.getChildren().get(2);
    }

    private HBox createButtonsWithHandlers() {
        HBox buttonBox = Style.createButtonPanel();

        //botoes do painel
        Button encodeButton = (Button) buttonBox.getChildren().get(0);
        Button decodeButton = (Button) buttonBox.getChildren().get(1);
        Button showTreeButton = (Button) buttonBox.getChildren().get(2);

        //eventos dos botoes
        encodeButton.setOnAction(e -> encodeAction());
        decodeButton.setOnAction(e -> decodeAction());
        showTreeButton.setOnAction(e -> showTreeAction());

        return buttonBox;
    }

    private void setupEventHandlers() {
        // Manipulador de zoom
        zoomSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                zoomFactor = newValue.doubleValue();
                drawMorseTree(treeCanvas, morseTree);
            }
        });
    }

    private void setupPrimaryStage(Stage primaryStage, VBox mainLayout) {
        Scene scene = new Scene(mainLayout, 650, 800);
        scene.getRoot().setStyle("-fx-focus-color: " + Style.PRIMARY_COLOR + "; -fx-faint-focus-color: #6a9c7822;");

        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (treePanel.isVisible()) {
                canvasScrollPane.setPrefWidth(newVal.doubleValue() - 60);
                drawMorseTree(treeCanvas, morseTree);
            }
        });

        // Definindo o ícone
        try {
            Image appIcon = new Image(ICON_FILE);
            primaryStage.getIcons().add(appIcon);
        } catch (Exception e) {
            System.err.println("Não foi possível carregar o ícone da aplicação: " + e.getMessage());
        }

        // Titulo janela
        primaryStage.setTitle("Morsdec - Tradutor de código morse");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void encodeAction() {
        String input = inputField.getText().trim();
        String result = morseTree.encode(input);
        outputArea.setText(result);
    }

    private void decodeAction() {
        String input = inputField.getText().trim();
        String result = morseTree.decode(input);
        outputArea.setText(result);
    }

    private void showTreeAction() {
        // Mostra o painel da arvore
        treePanel.setVisible(true);

        // Resetar zoom
        zoomSlider.setValue(1.0);
        zoomFactor = 1.0;

        // Desenha a árvore no canvas
        drawMorseTree(treeCanvas, morseTree);
    }

    private void drawMorseTree(Canvas canvas, MorseBST morseTree) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Calcula dimensoes para a arvore
        int treeHeight = morseTree.getHeight();

        // Calcula tamanho da árvore
        double nodeSpacingHorizontal = 75 * zoomFactor; // Espaçamento horizontal
        double nodeSpacingVertical = 80 * zoomFactor;   // Espaçamento vertical

        double canvasWidth = Math.max(600, Math.pow(2, treeHeight) * nodeSpacingHorizontal / 2);
        double canvasHeight = 80 + treeHeight * nodeSpacingVertical;

        // Atualiza dimensoes do canvas
        canvas.setWidth(canvasWidth);
        canvas.setHeight(canvasHeight);

        // Limpa o canvas
        gc.clearRect(0, 0, canvasWidth, canvasHeight);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);

        // Desenha a arvore com espaçamento
        drawNode(gc, morseTree.getRoot(), canvasWidth / 2, 60, canvasWidth / 4);

        // Adiciona informaçao no topo
        gc.setFill(Color.web(Style.TEXT_COLOR));
        gc.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        gc.fillText("• = ponto (esquerda)     — = traço (direita)", canvasWidth / 2 - 140, 30);
    }

    private void drawNode(GraphicsContext gc, MorseBST.Node node, double x, double y, double xOffset) {
        if (node == null) return;

        // Tamanho nó ajustado pelo zoom
        int nodeSize = (int)(30 * zoomFactor);

        // círculo do nó
        gc.setFill(Color.web(Style.INPUT_BG_COLOR));
        gc.setStroke(Color.web(Style.PRIMARY_COLOR));
        gc.setLineWidth(1.5);
        gc.fillOval(x - nodeSize/2, y - nodeSize/2, nodeSize, nodeSize);
        gc.strokeOval(x - nodeSize/2, y - nodeSize/2, nodeSize, nodeSize);

        // Desenha o texto do nó
        gc.setFill(Color.web(Style.TEXT_COLOR));
        gc.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14 * zoomFactor));
        String display = node.letter == ' ' ? "•" : String.valueOf(node.letter);
        double textWidth = display.length() * 5 * zoomFactor;
        gc.fillText(display, x - textWidth/2, y + 6 * zoomFactor);

        // Espaçamento vertical entre níveis - ajustado pelo zoom
        double verticalGap = 80 * zoomFactor;

        // Desenha as conexões para os filhos
        if (node.left != null) {
            double newX = x - xOffset;
            double newY = y + verticalGap;

            // Desenha a linha para o filho esquerdo (ponto)
            gc.setStroke(Color.web(Style.SECONDARY_COLOR));
            gc.setLineWidth(1.5);
            gc.strokeLine(x - nodeSize/4, y + nodeSize/3, newX, newY - nodeSize/2);

            // Desenha o símbolo do ponto
            gc.setFill(Color.web(Style.TEXT_COLOR));
            gc.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18 * zoomFactor));
            gc.fillText("•", (x + newX) / 2 - 10 * zoomFactor, (y + nodeSize/3 + newY - nodeSize/2) / 2);

            // Desenha o filho esquerdo
            drawNode(gc, node.left, newX, newY, xOffset / 2);
        }

        if (node.right != null) {
            double newX = x + xOffset;
            double newY = y + verticalGap;

            // Desenha a linha para o filho direito (traço)
            gc.setStroke(Color.web(Style.SECONDARY_COLOR));
            gc.setLineWidth(1.5);
            gc.strokeLine(x + nodeSize/4, y + nodeSize/3, newX, newY - nodeSize/2);

            // Desenha o símbolo do traço
            gc.setFill(Color.web(Style.TEXT_COLOR));
            gc.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18 * zoomFactor));
            gc.fillText("—", (x + newX) / 2 - 10 * zoomFactor, (y + nodeSize/3 + newY - nodeSize/2) / 2);

            // Desenha o filho direito
            drawNode(gc, node.right, newX, newY, xOffset / 2);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}