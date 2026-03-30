public class Node {
    private Pixel elemento;
    private Node proximo;

    public Node(Pixel elemento) {
        this.elemento = elemento;
        this.proximo = null;
    }

    public Pixel getElemento() {
        return elemento;
    }

    public Node getProximo() {
        return proximo;
    }

    public void setProximo(Node proximo) {
        this.proximo = proximo;
    }
}