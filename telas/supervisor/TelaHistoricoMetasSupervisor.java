package supervisor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import login.TelaLogin;
import java.awt.*;
import dao.MetaDAO;
import modelos.Colaborador;
import modelos.Meta;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

public class TelaHistoricoMetasSupervisor {

    private JFrame janelaHistoricoMetasSupervisor;
    private JButton botaoColaborador, botaoCriarMeta, botaoConclusaoMeta, botaoHistorico, botaoSair;
    private JTable tabelaMetas;
    private DefaultTableModel modelo;
    private Colaborador supervisorLogado;

    public void popularTabela() {
        modelo.setRowCount(0);
        MetaDAO dao = new MetaDAO();
        ArrayList<Meta> metas = dao.buscarTodos();

        for (Meta m : metas) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String prazoFormatado = m.getPrazoFinal().format(formatter);
            String colaboradoresString = String.join(", ", m.getColaboradores());
            Object[] linha = {
                    m.getNome(),
                    prazoFormatado,
                    m.getStatus(),
                    colaboradoresString
            };
            modelo.addRow(linha);
        }
    }

    public TelaHistoricoMetasSupervisor(Colaborador supervisor) {
        this.supervisorLogado = supervisor;

        janelaHistoricoMetasSupervisor = new JFrame("Histórico de Metas - Supervisor");
        janelaHistoricoMetasSupervisor.setSize(900, 500);
        janelaHistoricoMetasSupervisor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaHistoricoMetasSupervisor.setLayout(new GridLayout(1, 2));
        janelaHistoricoMetasSupervisor.setResizable(false);

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
        botaoHistorico.setBackground(new Color(255, 140, 0));
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

        String[] colunas = { "Meta", "Prazo Final", "Status", "Colaboradores" };

        modelo = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaMetas = new JTable(modelo);
        tabelaMetas.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(tabelaMetas);
        painelDireito.add(scroll, BorderLayout.CENTER);

        popularTabela();

        janelaHistoricoMetasSupervisor.add(painelBotoes);
        janelaHistoricoMetasSupervisor.add(painelDireito);
        janelaHistoricoMetasSupervisor.setLocationRelativeTo(null);
        janelaHistoricoMetasSupervisor.setVisible(true);

        botaoSair.addActionListener(e -> {
            new TelaLogin();
            janelaHistoricoMetasSupervisor.dispose();
        });

        botaoColaborador.addActionListener(e -> {
            new TelaColaboradores(supervisorLogado);
            janelaHistoricoMetasSupervisor.dispose();
        });

        botaoCriarMeta.addActionListener(e -> {
            new TelaCriarMeta(supervisorLogado);
            janelaHistoricoMetasSupervisor.dispose();
        });

        botaoConclusaoMeta.addActionListener(e -> {
            new TelaConclusaoMetas(supervisorLogado);
            janelaHistoricoMetasSupervisor.dispose();
        });
    }
}