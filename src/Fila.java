public class Fila implements Estrutura {
    private Node primeiro;
    private Node ultimo;

    public Fila() {
        primeiro = null;
        ultimo = null;
    }

    @Override
    public void adicionar(Pixel pixel) {
        Node novo = new Node(pixel);

        if (isEmpty()) {
            primeiro = novo;
            ultimo = novo;
        } else {
            ultimo.setProximo(novo);
            ultimo = novo;
        }
    }

    @Override
    public Pixel remover() {
        if (isEmpty()) {
            return null;
        }

        Pixel valor = primeiro.getElemento();
        primeiro = primeiro.getProximo();

        if (primeiro == null) {
            ultimo = null;
        }

        return valor;
    }

    @Override
    public boolean isEmpty() {
        return primeiro == null;
    }
}