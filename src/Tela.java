import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Tela extends JFrame {
    private JLabel labelImagem;
    private JButton botaoPilha;
    private JButton botaoFila;

    public Tela() {
        setTitle("Flood Fill - Pilha ou Fila");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        labelImagem = new JLabel();
        labelImagem.setHorizontalAlignment(SwingConstants.CENTER);
        add(labelImagem, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();

        botaoPilha = new JButton("Executar com Pilha");
        botaoFila = new JButton("Executar com Fila");

        painelBotoes.add(botaoPilha);
        painelBotoes.add(botaoFila);

        add(painelBotoes, BorderLayout.SOUTH);

        botaoPilha.addActionListener(e -> executarFloodFill(true));
        botaoFila.addActionListener(e -> executarFloodFill(false));

        setVisible(true);
    }

    private void executarFloodFill(boolean usarPilha) {
        new Thread(() -> {
            try {
                botaoPilha.setEnabled(false);
                botaoFila.setEnabled(false);

                ImageProcessor imageProcessor = new ImageProcessor();
                BufferedImage imagem = imageProcessor.carregarImagem("coracao.png");

                if (imagem == null) {
                    JOptionPane.showMessageDialog(this, "Não foi possível carregar a imagem.");
                    return;
                }

                Estrutura estrutura;
                String pastaFrames;
                String resultadoFinal;
                int novaCor;

                if (usarPilha) {
                    estrutura = new Pilha();
                    pastaFrames = "frames_pilha";
                    resultadoFinal = "resultado_pilha.png";
                    novaCor = Color.RED.getRGB();
                } else {
                    estrutura = new Fila();
                    pastaFrames = "frames_fila";
                    resultadoFinal = "resultado_fila.png";
                    novaCor = Color.BLUE.getRGB();
                }

                File pasta = new File(pastaFrames);
                if (!pasta.exists()) {
                    pasta.mkdir();
                }

                limparPastaFrames(pasta);

                FloodFill floodFill = new FloodFill(imagem, estrutura, 500, pastaFrames);
                floodFill.preencher(300, 300, novaCor);

                imageProcessor.salvarImagem(floodFill.getImagem(), resultadoFinal);

                animar(pastaFrames, 100);

                JOptionPane.showMessageDialog(this,
                        "Pixels pintados: " + floodFill.getPixelsPintados() +
                                "\nImagem salva em: " + resultadoFinal);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro na execução.");
                e.printStackTrace();
            } finally {
                botaoPilha.setEnabled(true);
                botaoFila.setEnabled(true);
            }
        }).start();
    }

    private void limparPastaFrames(File pasta) {
        File[] arquivos = pasta.listFiles();
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                arquivo.delete();
            }
        }
    }

    public void mostrarImagem(String caminho) {
        ImageIcon icon = new ImageIcon(caminho);
        labelImagem.setIcon(icon);
        labelImagem.repaint();
    }

    public void animar(String pastaFrames, int delay) {
        try {
            int i = 0;

            while (true) {
                File arquivo = new File(pastaFrames + "/frame_" + i + ".png");

                if (!arquivo.exists()) {
                    break;
                }

                mostrarImagem(arquivo.getPath());
                Thread.sleep(delay);
                i++;
            }

            File frameFinal = new File(pastaFrames + "/frame_final.png");
            if (frameFinal.exists()) {
                mostrarImagem(frameFinal.getPath());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao exibir animação.");
            e.printStackTrace();
        }
    }
}