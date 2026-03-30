import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class FloodFill {
    private BufferedImage imagem;
    private Estrutura estrutura;
    private int passoFrames;
    private String pastaFrames;
    private int pixelsPintados;

    public FloodFill(BufferedImage imagem, Estrutura estrutura, int passoFrames, String pastaFrames) {
        this.imagem = imagem;
        this.estrutura = estrutura;
        this.passoFrames = passoFrames;
        this.pastaFrames = pastaFrames;
        this.pixelsPintados = 0;
    }

    public void preencher(int x, int y, int novaCor) {
        int corOriginal = imagem.getRGB(x, y);

        if (corOriginal == novaCor) {
            return;
        }

        int frame = 0;
        estrutura.adicionar(new Pixel(x, y));

        while (!estrutura.isEmpty()) {
            Pixel atual = estrutura.remover();

            int px = atual.getX();
            int py = atual.getY();

            if (px < 0 || px >= imagem.getWidth() || py < 0 || py >= imagem.getHeight()) {
                continue;
            }

            if (imagem.getRGB(px, py) != corOriginal) {
                continue;
            }

            imagem.setRGB(px, py, novaCor);
            pixelsPintados++;

            if (pixelsPintados % passoFrames == 0) {
                salvarFrame("frame_" + frame + ".png");
                frame++;
            }

            estrutura.adicionar(new Pixel(px + 1, py));
            estrutura.adicionar(new Pixel(px - 1, py));
            estrutura.adicionar(new Pixel(px, py + 1));
            estrutura.adicionar(new Pixel(px, py - 1));
        }

        salvarFrame("frame_final.png");
    }

    private void salvarFrame(String nomeArquivo) {
        try {
            ImageIO.write(imagem, "png", new File(pastaFrames + "/" + nomeArquivo));
            System.out.println("Frame salvo: " + nomeArquivo);
        } catch (Exception e) {
            System.out.println("Erro ao salvar frame: " + nomeArquivo);
            e.printStackTrace();
        }
    }

    public int getPixelsPintados() {
        return pixelsPintados;
    }

    public BufferedImage getImagem() {
        return imagem;
    }
}