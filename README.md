# 📡 Tradutor de Código Morse

## 📌 Descrição do Projeto

**Tradutor de Código Morse** é uma aplicação desktop que permite aos usuários codificar texto em código Morse e decodificar código Morse de volta para texto de forma intuitiva e visual.

---

## 🧩 Funcionalidades Principais

- **Codificação de Texto para Morse**  
  Converte instantaneamente texto normal em código Morse usando o padrão internacional.

- **Decodificação de Morse para Texto**  
  Transforma sequências de pontos (`.`) e traços (`-`) de volta para caracteres alfanuméricos.

- **Visualização da Árvore Binária**  
  Exibe uma representação interativa da estrutura de árvore binária usada na decodificação:
  - Navegação visual pelo caminho de pontos (esquerda) e traços (direita)
  - Controle de zoom para visualização detalhada
  - Interface intuitiva com rolagem e opção de reset da visualização

---

## 🛠️ Tecnologias Utilizadas

- **Java** - Linguagem de programação principal
- **JavaFX** - Framework para desenvolvimento da interface gráfica
- **Estrutura de Dados** - Implementação prática de árvore binária para decodificação eficiente

---

## ▶️ Como Rodar o Projeto

1. Certifique-se de ter o **Java JDK 11+** e o **JavaFX** instalados em seu sistema.

2. Clone este repositório:

3. Compile o projeto (exemplo via linha de comando):
javac --module-path /caminho/para/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -d bin src/*.java

4. Execute a aplicação:
java --module-path /caminho/para/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -cp bin Main

5. Os arquivos de recursos (logo.png e logo2.png) devem estar no classpath
---

## 👨‍💻 Desenvolvedor

- Ray Govaski
- Luana Akemi Sakurada
- Thais Oliveira Amaral

---

## 🎓 Valor Educacional

Esta aplicação serve como uma ferramenta educacional para:

- Entender como o código Morse é estruturado
- Visualizar a implementação prática de árvores binárias
- Aprender princípios de decodificação eficiente

---

## 🔍 Recursos Adicionais

- Implementação de **árvore binária completa** para o alfabeto Morse (letras e números)
- Interface com **feedback visual** durante a interação

---

## ⚠️ Nota Final

Este projeto foi desenvolvido como uma **demonstração de habilidades técnicas**, com foco em:

- **Programação orientada a objetos**
- **Estruturas de dados** (árvore binária)
- **Interfaces gráficas com JavaFX**
