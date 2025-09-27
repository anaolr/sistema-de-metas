package supervisor;

import javax.swing.*;
import login.TelaLogin;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelos.Colaborador;

public class TelaPrincipalSupervisor {
    private JFrame janelaSupervisor;
    private JButton botaoColaborador, botaoCriarMeta, botaoConclusaoMeta, botaoHistorico, botaoSair;
    private Colaborador colaboradorLogado;

    public TelaPrincipalSupervisor(Colaborador colaborador) {
        this.colaboradorLogado = colaborador;
        janelaSupervisor = new JFrame("Sistema de Metas - Supervisor");
        janelaSupervisor.setSize(800, 500);
        janelaSupervisor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaSupervisor.setLayout(new GridLayout(1, 2));
        janelaSupervisor.setResizable(false);

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
        JLabel rotuloRh = new JLabel("SUPERVISOR", SwingConstants.CENTER);
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

        botaoColaborador = new JButton("Colaboradores do Departamento");
        botaoColaborador.setBackground(botaoEscuro);
        botaoColaborador.setForeground(Color.WHITE);
        botaoColaborador.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botaoColaborador.setFocusPainted(false);
        botaoColaborador.setPreferredSize(new Dimension(150, 35));
        painelBotoes.add(botaoColaborador, arrumar);
        arrumar.gridy++;

        botaoCriarMeta = new JButton("Criar Meta");
        botaoCriarMeta.setBackground(botaoEscuro);
        botaoCriarMeta.setForeground(Color.WHITE);
        botaoCriarMeta.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botaoCriarMeta.setFocusPainted(false);
        botaoCriarMeta.setPreferredSize(new Dimension(150, 35));
        painelBotoes.add(botaoCriarMeta, arrumar);
        arrumar.gridy++;

        botaoHistorico = new JButton("Histórico de Metas");
        botaoHistorico.setBackground(botaoEscuro);
        botaoHistorico.setForeground(Color.WHITE);
        botaoHistorico.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botaoHistorico.setFocusPainted(false);
        botaoHistorico.setPreferredSize(new Dimension(150, 35));
        painelBotoes.add(botaoHistorico, arrumar);
        arrumar.gridy++;

        botaoConclusaoMeta = new JButton("Conclusão de Metas");
        botaoConclusaoMeta.setBackground(botaoEscuro);
        botaoConclusaoMeta.setForeground(Color.WHITE);
        botaoConclusaoMeta.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botaoConclusaoMeta.setFocusPainted(false);
        botaoConclusaoMeta.setPreferredSize(new Dimension(150, 35));
        painelBotoes.add(botaoConclusaoMeta, arrumar);
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
                janelaSupervisor.dispose();
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
        botaoColaborador.addActionListener(e -> {
            new TelaColaboradores(colaboradorLogado);
            janelaSupervisor.dispose();
        });

        botaoCriarMeta.addActionListener(e -> {
            new TelaCriarMeta(colaboradorLogado);
            janelaSupervisor.dispose();
        });

        botaoHistorico.addActionListener(e -> {
            new TelaHistoricoMetasSupervisor(colaboradorLogado);
            janelaSupervisor.dispose();
        });

        botaoConclusaoMeta.addActionListener(e -> {
            new TelaConclusaoMetas(colaboradorLogado);
            janelaSupervisor.dispose();
        });

        // Adiciona os painéis
        janelaSupervisor.add(painelBotoes);
        janelaSupervisor.add(painelPrincipal);

        janelaSupervisor.setLocationRelativeTo(null);
        janelaSupervisor.setVisible(true);
    }
}