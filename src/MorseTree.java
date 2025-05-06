import java.util.HashMap;
import java.util.Map;

public class MorseTree {
    private MorseNode root;
    private final Map<String, String> morseTable = new HashMap<>();

    public MorseTree() {
        root = new MorseNode("");
        initializeMorseTable();
        buildTree();
    }

    // Método getter para acessar a raiz da árvore
    public MorseNode getRoot() {
        return root;
    }

    private void initializeMorseTable() {
        morseTable.put("A", ".-");    morseTable.put("B", "-...");
        morseTable.put("C", "-.-.");  morseTable.put("D", "-..");
        morseTable.put("E", ".");     morseTable.put("F", "..-.");
        morseTable.put("G", "--.");   morseTable.put("H", "....");
        morseTable.put("I", "..");    morseTable.put("J", ".---");
        morseTable.put("K", "-.-");   morseTable.put("L", ".-..");
        morseTable.put("M", "--");    morseTable.put("N", "-.");
        morseTable.put("O", "---");   morseTable.put("P", ".--.");
        morseTable.put("Q", "--.-");  morseTable.put("R", ".-.");
        morseTable.put("S", "...");   morseTable.put("T", "-");
        morseTable.put("U", "..-");   morseTable.put("V", "...-");
        morseTable.put("W", ".--");   morseTable.put("X", "-..-");
        morseTable.put("Y", "-.--");  morseTable.put("Z", "--..");
        morseTable.put("1", ".----"); morseTable.put("2", "..---");
        morseTable.put("3", "...--"); morseTable.put("4", "....-");
        morseTable.put("5", "....."); morseTable.put("6", "-....");
        morseTable.put("7", "--..."); morseTable.put("8", "---..");
        morseTable.put("9", "----."); morseTable.put("0", "-----");
    }

    private void buildTree() {
        for (Map.Entry<String, String> entry : morseTable.entrySet()) {
            insert(entry.getValue(), entry.getKey());
        }
    }

    private void insert(String code, String character) {
        MorseNode current = root;
        for (char c : code.toCharArray()) {
            if (c == '.') {
                if (current.dot == null) current.dot = new MorseNode("");
                current = current.dot;
            } else if (c == '-') {
                if (current.dash == null) current.dash = new MorseNode("");
                current = current.dash;
            }
        }
        current.character = character;
    }

    public String decode(String morseText) {
        StringBuilder result = new StringBuilder();
        String[] words = morseText.split(" / ");
        for (String word : words) {
            for (String letter : word.trim().split(" ")) {
                result.append(decodeLetter(letter));
            }
            result.append(" ");
        }
        return result.toString().trim();
    }

    private String decodeLetter(String code) {
        MorseNode current = root;
        for (char c : code.toCharArray()) {
            if (c == '.') {
                current = current.dot;
            } else if (c == '-') {
                current = current.dash;
            }
            if (current == null) return "?";
        }
        return current.character;
    }

    public String encode(String text) {
        StringBuilder morseCode = new StringBuilder();
        text = text.toUpperCase();

        for (char c : text.toCharArray()) {
            if (c == ' ') {
                morseCode.append("/ ");
            } else if (morseTable.containsKey(String.valueOf(c))) {
                morseCode.append(morseTable.get(String.valueOf(c))).append(" ");
            } else {
                morseCode.append("? "); // caractere desconhecido
            }
        }

        return morseCode.toString().trim();
    }

    // Método para imprimir a árvore (mantido para compatibilidade)
    public void printTree() {
        System.out.println("Árvore Morse:");
        System.out.println("(Raiz)");
        printTreeNode(root, "", true);
    }

    // Método auxiliar para imprimir cada nó
    private void printTreeNode(MorseNode node, String prefix, boolean isTail) {
        if (node == null) return;

        String nodeValue = node.character.isEmpty() ? "(vazio)" : node.character;
        System.out.println(prefix + (isTail ? "└── " : "├── ") + nodeValue);

        String newPrefix = prefix + (isTail ? "    " : "│   ");

        boolean dashIsLast = (node.dot == null);

        if (node.dot != null) {
            printTreeNode(node.dot, newPrefix, dashIsLast);
        }

        if (node.dash != null) {
            printTreeNode(node.dash, newPrefix, true);
        }
    }
}