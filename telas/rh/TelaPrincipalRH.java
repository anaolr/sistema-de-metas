package rh;

import javax.swing.*;
import login.TelaLogin;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelos.Colaborador;

public class TelaPrincipalRH {
  // 🔹 Apenas os atributos necessários
  private JFrame janelaRH;
  private JButton botaoColaborador, botaoDepartamento, botaoPremio, botaoHistorico, botaoHistoricoColaboradores,
      botaoSair;
  private Colaborador colaboradorLogado;

  public TelaPrincipalRH(Colaborador colaborador) {
    this.colaboradorLogado = colaborador;

    janelaRH = new JFrame("Sistema de Metas - RH");
    janelaRH.setSize(900, 600);
    janelaRH.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    janelaRH.setLayout(new GridLayout(1, 2));
    janelaRH.setResizable(false);

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
    JLabel rotuloRh = new JLabel("RECURSOS HUMANOS", SwingConstants.CENTER);
    rotuloRh.setFont(new Font("Segoe UI", Font.BOLD, 20));
    rotuloRh.setForeground(Color.WHITE);
    painelBotoes.add(rotuloRh, arrumar);

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

    botaoColaborador = new JButton("Cadastrar Colaborador");
    botaoColaborador.setBackground(botaoEscuro);
    botaoColaborador.setForeground(Color.WHITE);
    botaoColaborador.setFont(new Font("Segoe UI", Font.BOLD, 14));
    botaoColaborador.setFocusPainted(false);
    botaoColaborador.setPreferredSize(new Dimension(150, 35));
    painelBotoes.add(botaoColaborador, arrumar);
    arrumar.gridy++;

    botaoDepartamento = new JButton("Cadastrar Departamento");
    botaoDepartamento.setBackground(botaoEscuro);
    botaoDepartamento.setForeground(Color.WHITE);
    botaoDepartamento.setFont(new Font("Segoe UI", Font.BOLD, 14));
    botaoDepartamento.setFocusPainted(false);
    botaoDepartamento.setPreferredSize(new Dimension(150, 35));
    painelBotoes.add(botaoDepartamento, arrumar);
    arrumar.gridy++;

    botaoPremio = new JButton("Cadastrar Prêmio");
    botaoPremio.setBackground(botaoEscuro);
    botaoPremio.setForeground(Color.WHITE);
    botaoPremio.setFont(new Font("Segoe UI", Font.BOLD, 14));
    botaoPremio.setFocusPainted(false);
    botaoPremio.setPreferredSize(new Dimension(150, 35));
    painelBotoes.add(botaoPremio, arrumar);
    arrumar.gridy++;

    botaoHistorico = new JButton("Histórico de Metas");
    botaoHistorico.setBackground(botaoEscuro);
    botaoHistorico.setForeground(Color.WHITE);
    botaoHistorico.setFont(new Font("Segoe UI", Font.BOLD, 14));
    botaoHistorico.setFocusPainted(false);
    botaoHistorico.setPreferredSize(new Dimension(150, 35));
    painelBotoes.add(botaoHistorico, arrumar);
    arrumar.gridy++;

    botaoHistoricoColaboradores = new JButton("Histórico de Colaboradores");
    botaoHistoricoColaboradores.setBackground(botaoEscuro);
    botaoHistoricoColaboradores.setForeground(Color.WHITE);
    botaoHistoricoColaboradores.setFont(new Font("Segoe UI", Font.BOLD, 14));
    botaoHistoricoColaboradores.setFocusPainted(false);
    botaoHistoricoColaboradores.setPreferredSize(new Dimension(150, 35));
    painelBotoes.add(botaoHistoricoColaboradores, arrumar);
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
        janelaRH.dispose();
      }
    });

    // Painel direito
    JPanel painelPrincipal = new JPanel(new GridBagLayout());
    painelPrincipal.setBackground(Color.WHITE);
    painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

    JLabel rotuloPrincipal = new JLabel("SEJA BEM-VINDO, " + colaboradorLogado.getNome().toUpperCase() + "!",
        SwingConstants.CENTER);
    rotuloPrincipal.setFont(new Font("Segoe UI", Font.BOLD, 20));
    arrumar.gridx = 0;
    arrumar.gridy = 0;
    arrumar.gridwidth = 2;
    painelPrincipal.add(rotuloPrincipal, arrumar);

    // eventos - botoes
    botaoColaborador.addActionListener(e -> {
      new TelaCadastrarColaboradorRH();
      janelaRH.dispose();
    });

    botaoDepartamento.addActionListener(e -> {
      new TelaCadastrarDepartamento();
      janelaRH.dispose();
    });

    botaoPremio.addActionListener(e -> {
      new TelaCadastrarPremio();
      janelaRH.dispose();
    });

    botaoHistorico.addActionListener(e -> {
      new TelaHistoricoMetas();
      janelaRH.dispose();
    });

    botaoHistoricoColaboradores.addActionListener(e -> {
      new TelaHistoricoColaboradores();
      janelaRH.dispose();
    });

    // Adiciona os painéis
    janelaRH.add(painelBotoes);
    janelaRH.add(painelPrincipal);

    janelaRH.setLocationRelativeTo(null);
    janelaRH.setVisible(true);
  }

  public static void main(String[] args) {
    // new TelaPrincipalRH();
  }
}
