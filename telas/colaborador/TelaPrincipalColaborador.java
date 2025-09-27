package colaborador;

import modelos.Colaborador;

import javax.swing.*;
import login.TelaLogin;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipalColaborador {
  // 🔹 Apenas os atributos necessários
  private JFrame janelaPrincipalColaborador;
  private JButton botaoMinhasMetas, botaoRecompensas, botaoHistorico, botaoSair;
  private Colaborador colaboradorLogado;

  public TelaPrincipalColaborador(Colaborador colaborador) {
    this.colaboradorLogado = colaborador;
    janelaPrincipalColaborador = new JFrame("Sistema de Metas - Colaborador");
    janelaPrincipalColaborador.setSize(800, 500);
    janelaPrincipalColaborador.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    janelaPrincipalColaborador.setLayout(new GridLayout(1, 2));
    janelaPrincipalColaborador.setResizable(false);

    // Painel esquerdo (logo e botões)
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

    // Botões
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
    botaoRecompensas.setBackground(botaoEscuro);
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

    botaoSair.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        new TelaLogin();
        janelaPrincipalColaborador.dispose();
      }
    });

    // Painel direito
    JPanel painelPrincipal = new JPanel(new GridBagLayout());
    painelPrincipal.setBackground(Color.WHITE);
    painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

    JLabel rotuloPrincipal = new JLabel("SEJA BEM-VINDO!", SwingConstants.CENTER);
    rotuloPrincipal.setFont(new Font("Segoe UI", Font.BOLD, 20));
    arrumar.gridx = 0;
    arrumar.gridy = 0;
    arrumar.gridwidth = 2;
    painelPrincipal.add(rotuloPrincipal, arrumar);

    // eventos - botoes
    botaoMinhasMetas.addActionListener(e -> {
      new TelaMinhasMetas(colaboradorLogado);
      janelaPrincipalColaborador.dispose();
    });

    botaoRecompensas.addActionListener(e -> {
      new TelaRecompensasColaborador(colaboradorLogado);
      janelaPrincipalColaborador.dispose();
    });

    botaoHistorico.addActionListener(e -> {
      new TelaHistorico(this.colaboradorLogado);
      janelaPrincipalColaborador.dispose();
    });

    // Adiciona os painéis
    janelaPrincipalColaborador.add(painelBotoes);
    janelaPrincipalColaborador.add(painelPrincipal);

    janelaPrincipalColaborador.setLocationRelativeTo(null);
    janelaPrincipalColaborador.setVisible(true);
  }

}
