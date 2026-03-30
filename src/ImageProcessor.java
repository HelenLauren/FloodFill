import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageProcessor {

    public BufferedImage carregarImagem(String caminho) throws Exception {
        BufferedImage imagemOriginal = ImageIO.read(new File(caminho));

        if (imagemOriginal == null) {
            return null;
        }

        BufferedImage imagemRGB = new BufferedImage(
                imagemOriginal.getWidth(),
                imagemOriginal.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g2d = imagemRGB.createGraphics();
        g2d.drawImage(imagemOriginal, 0, 0, null);
        g2d.dispose();

        return imagemRGB;
    }

    public void salvarImagem(BufferedImage imagem, String caminho) throws Exception {
        ImageIO.write(imagem, "png", new File(caminho));
    }
}