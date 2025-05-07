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


//Classe principal

public class Main extends Application {

    private Canvas treeCanvas;
    private MorseTree morseTree;
    private double zoomFactor = 1.0;

    // Componentes precisam ser acessados
    private TextField inputField;
    private TextArea outputArea;
    private VBox treePanel;
    private StackPane canvasContainer;
    private ScrollPane canvasScrollPane;
    private Slider zoomSlider;

    @Override
    public void start(Stage primaryStage) {
        // Inicializa a árvore de código Morse
        morseTree = new MorseTree();
        VBox mainLayout = Style.createMainLayout();// Configuração do layout principal
        setupUI(mainLayout);  // Criação dos componentes principais da UI
        setupEventHandlers();// Configuração de eventos
        setupPrimaryStage(primaryStage, mainLayout);// Configuração da janela principal
    }


    // Configura todos os componentes da UI

    private void setupUI(VBox mainLayout) {

        VBox titleBox = Style.createTitlePanel();// Título e logo
        inputField = Style.createInputField(); // Campo de entrada
        HBox buttonBox = createButtonsWithHandlers();// Painel de botões
        VBox outputPanel = Style.createOutputPanel();// Painel de saída
        outputArea = (TextArea) outputPanel.getChildren().get(1);
        treePanel = Style.createTreePanel();// Painel da árvore
        setupTreePanelComponents();

        // Adiciona os componentes a tela
        mainLayout.getChildren().addAll(titleBox, inputField, buttonBox, outputPanel, treePanel);
    }


    // Configura os componentes específicos do painel da árvore

    private void setupTreePanelComponents() {

        canvasScrollPane = (ScrollPane) treePanel.getChildren().get(1);// Obtém o ScrollPane da árvore
        canvasContainer = (StackPane) canvasScrollPane.getContent();// Obtém o container do canvas
        treeCanvas = new Canvas(600, 400);// Cria o canvas para a árvore e adiciona ao container
        canvasContainer.getChildren().add(treeCanvas);
        HBox treeHeaderBox = (HBox) treePanel.getChildren().get(0);// Obtém o slider de zoom do painel de cabeçalho
        zoomSlider = (Slider) treeHeaderBox.getChildren().get(2);
    }


    // Cria os botões com seus manipuladores de evento

    private HBox createButtonsWithHandlers() {
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(javafx.geometry.Pos.CENTER);

        Button encodeButton = Style.createStyledButton("Codificar", Style.SECONDARY_COLOR);
        Button decodeButton = Style.createStyledButton("Decodificar", Style.PRIMARY_COLOR);
        Button showTreeButton = Style.createStyledButton("Árvore Binária", Style.SECONDARY_COLOR);

        // Configura os eventos dos botões
        encodeButton.setOnAction(e -> encodeAction());
        decodeButton.setOnAction(e -> decodeAction());
        showTreeButton.setOnAction(e -> showTreeAction());

        buttonBox.getChildren().addAll(encodeButton, decodeButton, showTreeButton);
        return buttonBox;
    }


    // Configura todos os manipuladores de eventos

    private void setupEventHandlers() {
        zoomSlider.valueProperty().addListener(new ChangeListener<Number>() {// Manipulador do slider de zoom
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                zoomFactor = newValue.doubleValue();
                drawMorseTree(treeCanvas, morseTree);
            }
        });
    }

    // Configura a janela principal

    private void setupPrimaryStage(Stage primaryStage, VBox mainLayout) {
        Scene scene = new Scene(mainLayout, 650, 800);
        scene.getRoot().setStyle("-fx-focus-color: " + Style.PRIMARY_COLOR + "; -fx-faint-focus-color: #6a9c7822;");

        // Adicionar eventos de redimensionamento
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (treePanel.isVisible()) {
                canvasScrollPane.setPrefWidth(newVal.doubleValue() - 60);
                drawMorseTree(treeCanvas, morseTree);
            }
        });




        // Ícone da aplicação
        Image appIcon = new Image("logo2.png");
        primaryStage.getIcons().add(appIcon);

        // Título da janela
        primaryStage.setTitle("Morsdec - Tradutor de código morse");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    // Ação para o botão de codificação

    private void encodeAction() {
        String input = inputField.getText().trim();
        String result = morseTree.encode(input);
        outputArea.setText(result);
    }


    // Ação para o botão de decodificação

    private void decodeAction() {
        String input = inputField.getText().trim();
        String result = morseTree.decode(input);
        outputArea.setText(result);
    }


    // Ação para mostrar a árvore binária

    private void showTreeAction() {
        // Mostra o painel da árvore
        treePanel.setVisible(true);

        // Resetar zoom
        zoomSlider.setValue(1.0);
        zoomFactor = 1.0;

        // Desenha a árvore no canvas
        drawMorseTree(treeCanvas, morseTree);
    }


    // Desenha a árvore Morse no canvas

    private void drawMorseTree(Canvas canvas, MorseTree morseTree) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Calcula as dimensões necessárias para a árvore
        int treeHeight = calculateTreeHeight(morseTree.getRoot());

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
        gc.setFill(Color.web(Style.TEXT_COLOR));
        gc.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        gc.fillText("• = ponto (esquerda)     — = traço (direita)", canvasWidth / 2 - 140, 30);
    }


    // Calcula a altura da árvore

    private int calculateTreeHeight(MorseNode node) {
        if (node == null) return 0;
        return 1 + Math.max(calculateTreeHeight(node.dot), calculateTreeHeight(node.dash));
    }


    // Desenha um nó da árvore e seus filhos

    private void drawNode(GraphicsContext gc, MorseNode node, double x, double y, double xOffset) {
        if (node == null) return;

        // Tamanho do nó ajustado pelo zoom
        int nodeSize = (int)(30 * zoomFactor);

        // Desenha o círculo do nó
        gc.setFill(Color.web(Style.BG_COLOR));
        gc.setStroke(Color.web(Style.PRIMARY_COLOR));
        gc.setLineWidth(1.5);
        gc.fillOval(x - nodeSize/2, y - nodeSize/2, nodeSize, nodeSize);
        gc.strokeOval(x - nodeSize/2, y - nodeSize/2, nodeSize, nodeSize);

        // Desenha o texto do nó
        gc.setFill(Color.web(Style.TEXT_COLOR));
        gc.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14 * zoomFactor));
        String display = node.character.isEmpty() ? "•" : node.character;
        double textWidth = display.length() * 5 * zoomFactor;
        gc.fillText(display, x - textWidth/2, y + 6 * zoomFactor);

        // Espaçamento vertical entre níveis - ajustado pelo zoom
        double verticalGap = 80 * zoomFactor;

        // Desenha as conexões para os filhos
        if (node.dot != null) {
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
            drawNode(gc, node.dot, newX, newY, xOffset / 2);
        }

        if (node.dash != null) {
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
            drawNode(gc, node.dash, newX, newY, xOffset / 2);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}