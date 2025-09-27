package colaborador;

import javax.swing.*;
import login.TelaLogin;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import dao.PremioDAO;
import modelos.Colaborador;
import modelos.Premio;
import java.util.List;
import java.net.URL;
import java.io.File;

public class TelaRecompensasColaborador {

    private JFrame janelaRecompensasColaborador;
    private JButton botaoMinhasMetas, botaoRecompensas, botaoHistorico, botaoSair;
    private JPanel painelRecompensas;
    private Colaborador colaboradorLogado;

    private void popularPremios() {
        PremioDAO dao = new PremioDAO();

        List<Premio> premios = dao.buscarPremiosPorColaborador(this.colaboradorLogado.getId());

        painelRecompensas.removeAll();

        for (Premio p : premios) {
            adicionarRecompensa(p);
        }
        painelRecompensas.revalidate();
        painelRecompensas.repaint();
    }

    public TelaRecompensasColaborador(Colaborador colaborador) {
        this.colaboradorLogado = colaborador;
        // -------------------------
        // Janela principal
        // -------------------------
        janelaRecompensasColaborador = new JFrame("Sistema de Metas - Colaborador");
        janelaRecompensasColaborador.setSize(900, 500);
        janelaRecompensasColaborador.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaRecompensasColaborador.setLayout(new GridLayout(1, 2));
        janelaRecompensasColaborador.setResizable(false);

        // Painel esquerdo (menu lateral)
        JPanel painelBotoes = new JPanel(new GridBagLayout());
        painelBotoes.setBackground(new Color(51, 102, 204));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints arrumar = new GridBagConstraints();
        arrumar.insets = new Insets(10, 10, 10, 10);
        arrumar.fill = GridBagConstraints.HORIZONTAL;
        arrumar.gridx = 0;
        arrumar.gridy = 0;
        arrumar.gridwidth = 2;

        // Título
        JLabel rotuloColaborador = new JLabel("COLABORADOR", SwingConstants.CENTER);
        rotuloColaborador.setFont(new Font("Segoe UI", Font.BOLD, 20));
        rotuloColaborador.setForeground(Color.WHITE);
        painelBotoes.add(rotuloColaborador, arrumar);

        // Imagem
        arrumar.gridy++;
        JLabel rotuloLogo = new JLabel();
        ImageIcon icon = new ImageIcon("imagens/pessoa.png");
        if (icon.getIconWidth() > 0) {
            Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            rotuloLogo.setIcon(new ImageIcon(img));
        } else {
            rotuloLogo.setText("Logo");
            rotuloLogo.setForeground(Color.WHITE);
        }
        rotuloLogo.setHorizontalAlignment(SwingConstants.CENTER);
        painelBotoes.add(rotuloLogo, arrumar);

        // Botões do menu
        Color botaoEscuro = new Color(30, 61, 122);
        arrumar.gridwidth = 1;
        arrumar.gridy++;

        botaoMinhasMetas = new JButton("Minhas Metas");
        botaoMinhasMetas.setBackground(botaoEscuro);
        botaoMinhasMetas.setForeground(Color.WHITE);
        botaoMinhasMetas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botaoMinhasMetas.setFocusPainted(false);
        botaoMinhasMetas.setPreferredSize(new Dimension(150, 35));
        painelBotoes.add(botaoMinhasMetas, arrumar);
        arrumar.gridy++;

        botaoRecompensas = new JButton("Recompensas");
        botaoRecompensas.setBackground(new Color(255, 140, 0));
        botaoRecompensas.setForeground(Color.WHITE);
        botaoRecompensas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botaoRecompensas.setFocusPainted(false);
        botaoRecompensas.setPreferredSize(new Dimension(150, 35));
        painelBotoes.add(botaoRecompensas, arrumar);
        arrumar.gridy++;

        botaoHistorico = new JButton("Histórico");
        botaoHistorico.setBackground(botaoEscuro);
        botaoHistorico.setForeground(Color.WHITE);
        botaoHistorico.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botaoHistorico.setFocusPainted(false);
        botaoHistorico.setPreferredSize(new Dimension(150, 35));
        painelBotoes.add(botaoHistorico, arrumar);
        arrumar.gridy++;

        botaoSair = new JButton("SAIR");
        botaoSair.setBackground(new Color(220, 53, 69));
        botaoSair.setForeground(Color.WHITE);
        botaoSair.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botaoSair.setFocusPainted(false);
        botaoSair.setPreferredSize(new Dimension(150, 35));
        painelBotoes.add(botaoSair, arrumar);

        botaoSair.addActionListener(e -> {
            new TelaLogin();
            janelaRecompensasColaborador.dispose();
        });

        botaoMinhasMetas.addActionListener(e -> {
            new TelaMinhasMetas(this.colaboradorLogado);
            janelaRecompensasColaborador.dispose();
        });

        botaoHistorico.addActionListener(e -> {
            new TelaHistorico(this.colaboradorLogado);
            janelaRecompensasColaborador.dispose();
        });

        // -------------------------
        // Painel direito (recompensas)
        // -------------------------
        painelRecompensas = new JPanel();
        painelRecompensas.setLayout(new BoxLayout(painelRecompensas, BoxLayout.Y_AXIS));
        painelRecompensas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        popularPremios();

        JScrollPane scrollPane = new JScrollPane(painelRecompensas);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Monta layout
        janelaRecompensasColaborador.add(painelBotoes);
        janelaRecompensasColaborador.add(scrollPane);

        janelaRecompensasColaborador.setLocationRelativeTo(null);
        janelaRecompensasColaborador.setVisible(true);
    }

    // -------------------------
    // Método para criar cards de recompensas (COM AJUSTE DE TAMANHO)
    // -------------------------
    private void adicionarRecompensa(Premio premio) {
        String nome = premio.getNome();
        String descricao = premio.getDescricao();
        String caminhoImagem = premio.getCaminhoImagem();

        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        card.setBackground(new Color(250, 250, 250));

        // --- LÓGICA DE CARREGAMENTO E REDIMENSIONAMENTO ---
        JLabel rotuloImagem = new JLabel();
        String caminhoParaExibicao = caminhoImagem;

        // 1. Lógica de Placeholder para PDF
        boolean isDocument = caminhoImagem != null && caminhoImagem.toLowerCase().endsWith(".pdf");
        if (isDocument) {
            caminhoParaExibicao = "imagens/alvo.png";
        }

        ImageIcon icon = null;

        try {
            File imagemFile = Paths.get(caminhoParaExibicao).toFile();

            // 1. Tenta carregar do sistema de arquivos (para JPGs/PNGs copiados em runtime)
            if (imagemFile.exists() && imagemFile.canRead()) {
                // TÉCNICA MAIS PRIMITIVA: Ler em bytes.
                byte[] imagemBytes = Files.readAllBytes(imagemFile.toPath());

                if (imagemBytes.length > 0) {
                    icon = new ImageIcon(imagemBytes);
                }
            }

            // 2. Fallback para ClassLoader (para recursos internos/estáticos)
            if (icon == null && caminhoParaExibicao.startsWith("imagens/")) {
                URL url = getClass().getClassLoader().getResource(caminhoParaExibicao);
                if (url != null) {
                    byte[] urlBytes = url.openStream().readAllBytes();
                    if (urlBytes.length > 0) {
                        icon = new ImageIcon(urlBytes);
                    }
                }
            }

        } catch (Exception e) {
            // Em caso de falha, o ícone é nulo.
            System.err.println(
                    "Falha ao carregar imagem (Bytes): " + caminhoParaExibicao + " Detalhes: " + e.getMessage());
            icon = null;
        }

        // 3. Verifica, REDIMENSIONA e atribui o ícone
        if (icon != null && icon.getIconWidth() > 0) {
            // <<-- CORREÇÃO DE TAMANHO AQUI: Ajuste o valor 100 se necessário -->>
            int tamanhoDesejado = 100;
            Image imgRedimensionada = icon.getImage().getScaledInstance(tamanhoDesejado, tamanhoDesejado,
                    Image.SCALE_SMOOTH);
            rotuloImagem.setIcon(new ImageIcon(imgRedimensionada));
        } else {
            // Se falhar, mostra o texto
            rotuloImagem.setText(isDocument ? "[Documento]" : "[Imagem]");
        }
        // --- FIM DA LÓGICA DE CARREGAMENTO ---

        // Nome e descrição
        JPanel painelTexto = new JPanel(new GridLayout(2, 1));
        painelTexto.setBackground(new Color(250, 250, 250));
        painelTexto.add(new JLabel(nome));
        painelTexto.add(new JLabel(descricao));

        // Botão de resgate
        JButton botaoResgatar = new JButton("Resgatar");
        botaoResgatar.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salvar recompensa: " + nome);

            // Sugere o nome do arquivo, usando o caminhoImagem original para determinar a
            // extensão
            String extensao = "";
            int lastDot = caminhoImagem.lastIndexOf('.');
            if (lastDot > 0) {
                extensao = caminhoImagem.substring(lastDot);
            }
            fileChooser.setSelectedFile(new java.io.File(nome.replaceAll(" ", "_") + extensao));

            int userSelection = fileChooser.showSaveDialog(janelaRecompensasColaborador);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                java.io.File arquivoDestino = fileChooser.getSelectedFile();

                try {
                    // Cópia do arquivo (funciona com o caminho relativo)
                    Files.copy(
                            Paths.get(caminhoImagem),
                            arquivoDestino.toPath(),
                            StandardCopyOption.REPLACE_EXISTING);

                    JOptionPane.showMessageDialog(janelaRecompensasColaborador,
                            "🎉 Arquivo da recompensa salvo em: " + arquivoDestino.getAbsolutePath());

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(janelaRecompensasColaborador,
                            "Erro ao salvar a recompensa:\n" + ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        card.add(rotuloImagem, BorderLayout.WEST);
        card.add(painelTexto, BorderLayout.CENTER);
        card.add(botaoResgatar, BorderLayout.EAST);

        painelRecompensas.add(card);
        painelRecompensas.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    // -------------------------
    // Main
    // -------------------------
    public static void main(String[] args) {
        // new TelaRecompensasColaborador();
    }
}