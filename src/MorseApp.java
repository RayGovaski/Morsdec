import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.geometry.Insets;


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
        // Inicializa a árvore de código Morse
        morseTree = new MorseBST();

        // Configuração do layout principal
        VBox mainLayout = createMainLayout();

        // Configuração dos componentes da UI
        setupUI(mainLayout);

        // Configuração dos eventos
        setupEventHandlers();

        // Configuração da janela principal
        setupPrimaryStage(primaryStage, mainLayout);
    }


    private VBox createMainLayout() {
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: #f5f5f5;");
        return mainLayout;
    }

    private void setupUI(VBox mainLayout) {
        // Painel de título
        VBox titleBox = createTitlePanel();

        // Campo de entrada
        inputField = createInputField();

        // Painel de botões
        HBox buttonBox = createButtonsWithHandlers();

        // Painel de saída
        VBox outputPanel = createOutputPanel();
        outputArea = (TextArea) outputPanel.getChildren().get(1);

        // Painel da árvore
        treePanel = createTreePanel();
        setupTreePanelComponents();

        // Adiciona os componentes ao layout principal
        mainLayout.getChildren().addAll(titleBox, inputField, buttonBox, outputPanel, treePanel);
    }


    private VBox createTitlePanel() {
        VBox titleBox = new VBox(10);
        titleBox.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Morsdec");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        titleLabel.setStyle("-fx-text-fill: #2979ff;");

        Label subtitleLabel = new Label("Tradutor de Código Morse");
        subtitleLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));
        subtitleLabel.setStyle("-fx-text-fill: #455a64;");

        titleBox.getChildren().addAll(titleLabel, subtitleLabel);
        return titleBox;
    }

    private TextField createInputField() {
        TextField inputField = new TextField();
        inputField.setPromptText("Digite o texto ou código morse...");
        inputField.setFont(Font.font("Segoe UI", 14));
        inputField.setPrefHeight(40);
        inputField.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #2979ff;" +
                        "-fx-border-radius: 4px;" +
                        "-fx-padding: 8px;"
        );
        return inputField;
    }

    private VBox createOutputPanel() {
        VBox outputPanel = new VBox(10);

        Label outputLabel = new Label("Resultado");
        outputLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        outputLabel.setStyle("-fx-text-fill: #455a64;");

        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setPrefHeight(120);
        outputArea.setFont(Font.font("Courier New", 14));
        outputArea.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #455a64;" +
                        "-fx-border-radius: 4px;"
        );

        outputPanel.getChildren().addAll(outputLabel, outputArea);
        return outputPanel;
    }


    private VBox createTreePanel() {
        VBox treePanel = new VBox(10);
        treePanel.setVisible(false); // Inicialmente oculto

        // Cabeçalho com título e slider de zoom
        HBox treeHeader = new HBox(15);
        treeHeader.setAlignment(Pos.CENTER_LEFT);

        Label treeLabel = new Label("Árvore de Código Morse");
        treeLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        treeLabel.setStyle("-fx-text-fill: #455a64;");

        Label zoomLabel = new Label("Zoom:");
        zoomLabel.setFont(Font.font("Segoe UI", 14));

        Slider zoomSlider = new Slider(0.5, 2.0, 1.0);
        zoomSlider.setPrefWidth(150);
        zoomSlider.setShowTickMarks(true);
        zoomSlider.setShowTickLabels(true);
        zoomSlider.setMajorTickUnit(0.5);

        treeHeader.getChildren().addAll(treeLabel, zoomLabel, zoomSlider);

        // ScrollPane para o canvas da árvore
        ScrollPane canvasScrollPane = new ScrollPane();
        canvasScrollPane.setPrefHeight(350);
        canvasScrollPane.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #455a64;" +
                        "-fx-border-radius: 4px;"
        );

        // Container para o canvas (será adicionado no código principal)
        StackPane canvasContainer = new StackPane();
        canvasContainer.setStyle("-fx-background-color: white;");
        canvasScrollPane.setContent(canvasContainer);

        treePanel.getChildren().addAll(treeHeader, canvasScrollPane);
        return treePanel;
    }


    private void setupTreePanelComponents() {
        // Obtém o ScrollPane da árvore
        canvasScrollPane = (ScrollPane) treePanel.getChildren().get(1);

        // Obtém o container do canvas
        canvasContainer = (StackPane) canvasScrollPane.getContent();

        // Cria o canvas para a árvore e adiciona ao container
        treeCanvas = new Canvas(600, 400);
        canvasContainer.getChildren().add(treeCanvas);

        // Obtém o slider de zoom do painel de cabeçalho
        HBox treeHeaderBox = (HBox) treePanel.getChildren().get(0);
        zoomSlider = (Slider) treeHeaderBox.getChildren().get(2);
    }


    private HBox createButtonsWithHandlers() {
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button encodeButton = createStyledButton("Codificar", "#455a64");
        Button decodeButton = createStyledButton("Decodificar", "#2979ff");
        Button showTreeButton = createStyledButton("Árvore Binária", "#455a64");

        // Configura os eventos dos botões
        encodeButton.setOnAction(e -> encodeAction());
        decodeButton.setOnAction(e -> decodeAction());
        showTreeButton.setOnAction(e -> showTreeAction());

        buttonBox.getChildren().addAll(encodeButton, decodeButton, showTreeButton);
        return buttonBox;
    }


    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        button.setPrefHeight(40);
        button.setMinWidth(120);
        button.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-border-radius: 4px;" +
                        "-fx-background-radius: 4px;" +
                        "-fx-cursor: hand;"
        );

        // Efeito hover
        button.setOnMouseEntered(e -> {
            button.setStyle(
                    "-fx-background-color: derive(" + color + ", 20%);" +
                            "-fx-text-fill: white;" +
                            "-fx-border-radius: 4px;" +
                            "-fx-background-radius: 4px;" +
                            "-fx-cursor: hand;"
            );
        });

        // Restaura o estilo original ao sair
        button.setOnMouseExited(e -> {
            button.setStyle(
                    "-fx-background-color: " + color + ";" +
                            "-fx-text-fill: white;" +
                            "-fx-border-radius: 4px;" +
                            "-fx-background-radius: 4px;" +
                            "-fx-cursor: hand;"
            );
        });

        return button;
    }

    private void setupEventHandlers() {
        // Manipulador do slider de zoom
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
        scene.getRoot().setStyle("-fx-focus-color: #2979ff; -fx-faint-focus-color: #6a9c7822;");

        // Adicionar eventos de redimensionamento
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (treePanel.isVisible()) {
                canvasScrollPane.setPrefWidth(newVal.doubleValue() - 60);
                drawMorseTree(treeCanvas, morseTree);
            }
        });

        // Título da janela
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
        // Mostra o painel da árvore
        treePanel.setVisible(true);

        // Resetar zoom
        zoomSlider.setValue(1.0);
        zoomFactor = 1.0;

        // Desenha a árvore no canvas
        drawMorseTree(treeCanvas, morseTree);
    }

    private void drawMorseTree(Canvas canvas, MorseBST morseTree) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Calcula as dimensões necessárias para a árvore
        int treeHeight = morseTree.getHeight();

        // Calcula tamanhos base da árvore (mais compactos)
        double nodeSpacingHorizontal = 75 * zoomFactor; // Espaçamento horizontal reduzido
        double nodeSpacingVertical = 80 * zoomFactor;   // Espaçamento vertical reduzido

        double canvasWidth = Math.max(600, Math.pow(2, treeHeight) * nodeSpacingHorizontal / 2);
        double canvasHeight = 80 + treeHeight * nodeSpacingVertical;

        // Atualiza as dimensões do canvas
        canvas.setWidth(canvasWidth);
        canvas.setHeight(canvasHeight);

        // Limpa o canvas
        gc.clearRect(0, 0, canvasWidth, canvasHeight);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);

        // Desenha a árvore com espaçamento adequado
        drawNode(gc, morseTree.getRoot(), canvasWidth / 2, 60, canvasWidth / 4);

        // Adiciona uma informação no topo
        gc.setFill(Color.web("#263238"));
        gc.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        gc.fillText("• = ponto (esquerda)     — = traço (direita)", canvasWidth / 2 - 140, 30);
    }

    private void drawNode(GraphicsContext gc, MorseBST.Node node, double x, double y, double xOffset) {
        if (node == null) return;

        // Tamanho do nó ajustado pelo zoom
        int nodeSize = (int)(30 * zoomFactor);

        // Desenha o círculo do nó
        gc.setFill(Color.web("#f5f5f5"));
        gc.setStroke(Color.web("#2979ff"));
        gc.setLineWidth(1.5);
        gc.fillOval(x - nodeSize/2, y - nodeSize/2, nodeSize, nodeSize);
        gc.strokeOval(x - nodeSize/2, y - nodeSize/2, nodeSize, nodeSize);

        // Desenha o texto do nó
        gc.setFill(Color.web("#263238"));
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
            gc.setStroke(Color.web("#455a64"));
            gc.setLineWidth(1.5);
            gc.strokeLine(x - nodeSize/4, y + nodeSize/3, newX, newY - nodeSize/2);

            // Desenha o símbolo do ponto
            gc.setFill(Color.web("#263238"));
            gc.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18 * zoomFactor));
            gc.fillText("•", (x + newX) / 2 - 10 * zoomFactor, (y + nodeSize/3 + newY - nodeSize/2) / 2);

            // Desenha o filho esquerdo
            drawNode(gc, node.left, newX, newY, xOffset / 2);
        }

        if (node.right != null) {
            double newX = x + xOffset;
            double newY = y + verticalGap;

            // Desenha a linha para o filho direito (traço)
            gc.setStroke(Color.web("#455a64"));
            gc.setLineWidth(1.5);
            gc.strokeLine(x + nodeSize/4, y + nodeSize/3, newX, newY - nodeSize/2);

            // Desenha o símbolo do traço
            gc.setFill(Color.web("#263238"));
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