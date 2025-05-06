public class MorseNode {
    String character;
    MorseNode dot;   // esquerda
    MorseNode dash;  // direita

    public MorseNode(String character) {
        this.character = character;
    }
}
