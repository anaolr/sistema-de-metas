package supervisor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import login.TelaLogin;
import java.awt.*;

import dao.ColaboradorDAO;
import modelos.Colaborador;
import java.util.ArrayList;

public class TelaColaboradores {
    private JFrame janelaSupervisor;
    private JButton botaoColaborador, botaoCriarMeta, botaoConclusaoMeta, botaoHistorico, botaoSair;
    private JTable tabelaColaboradores;
    private DefaultTableModel modelo;
    private Colaborador supervisorLogado;

    public void popularTabela() {
        modelo.setRowCount(0);
        ColaboradorDAO dao = new ColaboradorDAO();
        ArrayList<Colaborador> colaboradores = dao.buscarAtivosPorDepartamento(supervisorLogado.getDepartamento());

        for (Colaborador c : colaboradores) {
            Object[] linha = {
                    c.getNome(),
                    c.getCargo(),
                    c.getStatus() ? "Ativo" : "Inativo"
            };
            modelo.addRow(linha);
        }
    }

    public TelaColaboradores(Colaborador supervisor) {
        this.supervisorLogado = supervisor;
        janelaSupervisor = new JFrame("Sistema de Metas - Supervisor");
        janelaSupervisor.setSize(900, 500);
        janelaSupervisor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaSupervisor.setLayout(new GridLayout(1, 2));
        janelaSupervisor.setResizable(false);

        JPanel painelBotoes = new JPanel(new GridBagLayout());
        painelBotoes.setBackground(new Color(51, 102, 204));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints arrumar = new GridBagConstraints();
        arrumar.insets = new Insets(10, 10, 10, 10);
        arrumar.fill = GridBagConstraints.HORIZONTAL;
        arrumar.gridx = 0;
        arrumar.gridy = 0;
        arrumar.gridwidth = 2;

        JLabel rotuloRh = new JLabel("SUPERVISOR", SwingConstants.CENTER);
        rotuloRh.setFont(new Font("Segoe UI", Font.BOLD, 20));
        rotuloRh.setForeground(Color.WHITE);
        painelBotoes.add(rotuloRh, arrumar);

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

        Color botaoEscuro = new Color(30, 61, 122);
        arrumar.gridwidth = 2;
        arrumar.gridy++;

        botaoColaborador = new JButton("Colaboradores do Departamento");
        botaoColaborador.setBackground(new Color(255, 140, 0));
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

        JPanel painelDireito = new JPanel(new BorderLayout());
        painelDireito.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painelDireito.setBackground(Color.WHITE);

        String[] colunas = { "Nome", "Cargo", "Status" };
        modelo = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaColaboradores = new JTable(modelo);
        tabelaColaboradores.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(tabelaColaboradores);
        painelDireito.add(scroll, BorderLayout.CENTER);

        popularTabela();

        // Eventos dos botões
        botaoSair.addActionListener(e -> {
            new TelaLogin();
            janelaSupervisor.dispose();
        });

        botaoCriarMeta.addActionListener(e -> {
            new TelaCriarMeta(supervisorLogado);
            janelaSupervisor.dispose();
        });

        botaoHistorico.addActionListener(e -> {
            new TelaHistoricoMetasSupervisor(supervisorLogado);
            janelaSupervisor.dispose();
        });

        botaoConclusaoMeta.addActionListener(e -> {
            new TelaConclusaoMetas(supervisorLogado);
            janelaSupervisor.dispose();
        });

        janelaSupervisor.add(painelBotoes);
        janelaSupervisor.add(painelDireito);
        janelaSupervisor.setLocationRelativeTo(null);
        janelaSupervisor.setVisible(true);
    }
}