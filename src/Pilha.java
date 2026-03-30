public class Pilha implements Estrutura {
    private Node topo;

    public Pilha() {
        topo = null;
    }

    @Override
    public void adicionar(Pixel pixel) {
        Node novo = new Node(pixel);
        novo.setProximo(topo);
        topo = novo;
    }

    @Override
    public Pixel remover() {
        if (isEmpty()) {
            return null;
        }

        Pixel valor = topo.getElemento();
        topo = topo.getProximo();
        return valor;
    }

    @Override
    public boolean isEmpty() {
        return topo == null;
    }
}