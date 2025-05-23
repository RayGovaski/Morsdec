import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Style {
    // Constantes de cores
    public static final String PRIMARY_COLOR = "#6a9c78";      // Verde escuro
    public static final String SECONDARY_COLOR = "#93c7a4";    // Verde médio
    public static final String BG_COLOR = "#d3ede1";           // Verde claro
    public static final String TEXT_COLOR = "#2c463a";         // Verde escuro para o texto
    public static final String PANEL_BG_COLOR = "#e8f4ed";     // Cor de fundo para painéis
    public static final String INPUT_BG_COLOR = "#f5faf7";     // Cor de fundo para campos de entrada

    public static VBox createMainLayout() {
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: " + BG_COLOR + ";");
        return mainLayout;
    }

    public static VBox createTitlePanel() {
        return createTitlePanel("logo.png");
    }

    public static VBox createTitlePanel(String logoFileName) {
        // Título
        Label titleLabel = new Label("Tradutor de Código Morse");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.web(TEXT_COLOR));
        titleLabel.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 2);");
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setMaxWidth(Double.MAX_VALUE);

        // Logo
        ImageView logoView = null;
        try {
            Image logoImage = new Image(logoFileName);
            logoView = new ImageView(logoImage);
            logoView.setFitWidth(200);
            logoView.setFitHeight(200);
            logoView.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Erro ao carregar o logo: " + e.getMessage());
            // Cria um placeholder caso a imagem não seja encontrada
            logoView = new ImageView();
            logoView.setFitWidth(200);
            logoView.setFitHeight(200);
        }

        // Centralizar a logo
        HBox logoBox = new HBox();
        logoBox.setAlignment(Pos.CENTER);
        logoBox.getChildren().add(logoView);

        // Box do título
        VBox titleBox = new VBox(10);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().addAll(logoBox, titleLabel);

        return titleBox;
    }

    public static TextField createInputField() {
        TextField inputField = new TextField();
        inputField.setPromptText("Digite o texto ou código morse");
        inputField.setFont(Font.font("Segoe UI", 14));
        inputField.setPrefHeight(40);
        inputField.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-focus-color: " +
                PRIMARY_COLOR + "; -fx-faint-focus-color: #6a9c7822;");
        return inputField;
    }

    public static HBox createButtonPanel() {
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button encodeButton = createStyledButton("Codificar", SECONDARY_COLOR);
        Button decodeButton = createStyledButton("Decodificar", PRIMARY_COLOR);
        Button showTreeButton = createStyledButton("Árvore Binária", SECONDARY_COLOR);

        buttonBox.getChildren().addAll(encodeButton, decodeButton, showTreeButton);
        return buttonBox;
    }

    public static Button createStyledButton(String text, String baseColor) {
        Button button = new Button(text);
        button.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 14));
        button.setPrefHeight(40);
        button.setMinWidth(150);
        button.setTextFill(Color.WHITE);

        String hoverColor = adjustBrightness(baseColor, -0.1);
        String pressedColor = adjustBrightness(baseColor, -0.2);

        // CSS do botão
        String buttonStyle = "-fx-background-color: " + baseColor + "; " +
                "-fx-background-radius: 5; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 3, 0, 0, 1);";
        button.setStyle(buttonStyle);
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + hoverColor + "; -fx-background-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 5, 0, 0, 2);"));
        button.setOnMouseExited(e -> button.setStyle(buttonStyle));
        button.setOnMousePressed(e -> button.setStyle("-fx-background-color: " + pressedColor + "; -fx-background-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 2, 0, 0, 0);"));
        button.setOnMouseReleased(e -> button.setStyle(buttonStyle));
        return button;
    }

    public static Button createSmallButton(String text, String baseColor) {
        Button button = createStyledButton(text, baseColor);
        button.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        button.setPrefHeight(30);
        button.setMinWidth(100);
        return button;
    }

    public static VBox createOutputPanel() {
        VBox outputPanel = new VBox(10);
        outputPanel.setStyle("-fx-background-color: " + PANEL_BG_COLOR + "; -fx-background-radius: 5; -fx-padding: 15;");
        outputPanel.setEffect(new DropShadow(10, Color.rgb(0, 0, 0, 0.2)));

        Label resultTitleLabel = new Label("Resultado:");
        resultTitleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        resultTitleLabel.setTextFill(Color.web(TEXT_COLOR));

        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setPrefHeight(80);
        outputArea.setFont(Font.font("Segoe UI", 14));
        outputArea.setStyle("-fx-control-inner-background: " + INPUT_BG_COLOR + "; -fx-border-radius: 3; -fx-background-radius: 3; -fx-focus-color: " + PRIMARY_COLOR + "; -fx-faint-focus-color: #6a9c7822;");

        outputPanel.getChildren().addAll(resultTitleLabel, outputArea);
        return outputPanel;
    }

    public static VBox createTreePanel() {
        VBox treePanel = new VBox(10);
        treePanel.setStyle("-fx-background-color: " + PANEL_BG_COLOR + "; -fx-background-radius: 5; -fx-padding: 15;");
        treePanel.setEffect(new DropShadow(10, Color.rgb(0, 0, 0, 0.2)));
        treePanel.setVisible(false);

        // Título e controles de zoom em um painel horizontal
        HBox treeHeaderBox = new HBox(20);
        treeHeaderBox.setAlignment(Pos.CENTER_LEFT);

        Label treeTitleLabel = new Label("Visualização da Árvore Binária:");
        treeTitleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        treeTitleLabel.setTextFill(Color.web(TEXT_COLOR));

        // Adicionar controles de zoom
        Label zoomLabel = new Label("Zoom:");
        zoomLabel.setFont(Font.font("Segoe UI", 12));
        zoomLabel.setTextFill(Color.web(TEXT_COLOR));

        Slider zoomSlider = new Slider(0.5, 1.5, 1.0);
        zoomSlider.setPrefWidth(150);
        zoomSlider.setShowTickMarks(true);
        zoomSlider.setShowTickLabels(true);
        zoomSlider.setMajorTickUnit(0.25);

        treeHeaderBox.getChildren().addAll(treeTitleLabel, zoomLabel, zoomSlider);

        // Container para canvas (será adicionado na classe Main)
        StackPane canvasContainer = new StackPane();
        canvasContainer.setStyle("-fx-background-color: white;");

        // Scroll pane para permitir rolagem quando necessário
        ScrollPane canvasScrollPane = new ScrollPane(canvasContainer);
        canvasScrollPane.setPrefHeight(350);
        canvasScrollPane.setPannable(true);
        canvasScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        canvasScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        canvasScrollPane.setStyle("-fx-background-color: white; -fx-background: white;");
        canvasScrollPane.setFitToWidth(true);
        canvasScrollPane.setFitToHeight(true);

        // Adicionar os componentes ao painel da árvore
        treePanel.getChildren().addAll(treeHeaderBox, canvasScrollPane);

        return treePanel;
    }

    public static String adjustBrightness(String hexColor, double factor) {
        Color color = Color.web(hexColor);
        double red = clamp(color.getRed() + factor);
        double green = clamp(color.getGreen() + factor);
        double blue = clamp(color.getBlue() + factor);

        return String.format("#%02X%02X%02X",
                (int)(red * 255),
                (int)(green * 255),
                (int)(blue * 255));
    }

    private static double clamp(double value) {
        return Math.max(0, Math.min(1, value));
    }
}