package supervisor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import login.TelaLogin;
import java.awt.*;
import dao.MetaDAO;
import modelos.Colaborador;
import modelos.Meta;
import java.util.ArrayList;

public class TelaConclusaoMetas {

    private JFrame janelaConclusaoMetas;
    private JButton botaoColaborador, botaoCriarMeta, botaoConclusaoMeta, botaoHistorico, botaoSair;
    private JTable tabelaMetas;
    private DefaultTableModel modelo;
    private Colaborador supervisorLogado;

    public void popularTabela() {
        modelo.setRowCount(0);
        MetaDAO dao = new MetaDAO();
        ArrayList<Meta> metas = dao.buscarMetasParaConclusao();
        for (Meta m : metas) {
            modelo.addRow(new Object[] { m.getNome(), m.getStatus(), m.getId() });
        }
    }

    public TelaConclusaoMetas(Colaborador supervisor) {
        this.supervisorLogado = supervisor;

        janelaConclusaoMetas = new JFrame("Conclusão de Metas - Supervisor");
        janelaConclusaoMetas.setSize(900, 500);
        janelaConclusaoMetas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaConclusaoMetas.setLayout(new GridLayout(1, 2));
        janelaConclusaoMetas.setResizable(false);

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
        botaoHistorico.setBackground(botaoEscuro);
        botaoHistorico.setForeground(Color.WHITE);
        botaoHistorico.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botaoHistorico.setFocusPainted(false);
        botaoHistorico.setPreferredSize(new Dimension(150, 35));
        painelBotoes.add(botaoHistorico, arrumar);
        arrumar.gridy++;

        botaoConclusaoMeta = new JButton("Conclusão de Metas");
        botaoConclusaoMeta.setBackground(new Color(255, 140, 0));
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

        String[] colunas = { "Meta", "Status", "ID" };
        modelo = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaMetas = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabelaMetas);
        painelDireito.add(scroll, BorderLayout.CENTER);
        popularTabela();

        JPanel painelAcoes = new JPanel();
        JButton botaoConfirmar = new JButton("Confirmar");
        JButton botaoRecusar = new JButton("Recusar");
        painelAcoes.add(botaoConfirmar);
        painelAcoes.add(botaoRecusar);
        painelDireito.add(painelAcoes, BorderLayout.SOUTH);

        botaoConfirmar.addActionListener(e -> {
            int linha = tabelaMetas.getSelectedRow();
            if (linha != -1) {
                int idMeta = (int) modelo.getValueAt(linha, 2);
                MetaDAO dao = new MetaDAO();
                if (dao.atualizarStatus(idMeta, "Concluída")) {
                    JOptionPane.showMessageDialog(janelaConclusaoMetas, "Meta confirmada!");
                    popularTabela();
                } else {
                    JOptionPane.showMessageDialog(janelaConclusaoMetas, "Erro ao confirmar a meta.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(janelaConclusaoMetas, "Selecione uma meta primeiro!");
            }
        });

        botaoRecusar.addActionListener(e -> {
            int linha = tabelaMetas.getSelectedRow();
            if (linha != -1) {
                int idMeta = (int) modelo.getValueAt(linha, 2);
                MetaDAO dao = new MetaDAO();
                if (dao.atualizarStatus(idMeta, "Pendente")) {
                    JOptionPane.showMessageDialog(janelaConclusaoMetas, "Meta recusada!");
                    popularTabela();
                } else {
                    JOptionPane.showMessageDialog(janelaConclusaoMetas, "Erro ao recusar a meta.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(janelaConclusaoMetas, "Selecione uma meta primeiro!");
            }
        });

        janelaConclusaoMetas.add(painelBotoes);
        janelaConclusaoMetas.add(painelDireito);
        janelaConclusaoMetas.setLocationRelativeTo(null);
        janelaConclusaoMetas.setVisible(true);

        botaoSair.addActionListener(e -> {
            new TelaLogin();
            janelaConclusaoMetas.dispose();
        });

        botaoColaborador.addActionListener(e -> {
            new TelaColaboradores(supervisorLogado);
            janelaConclusaoMetas.dispose();
        });

        botaoCriarMeta.addActionListener(e -> {
            new TelaCriarMeta(supervisorLogado);
            janelaConclusaoMetas.dispose();
        });

        botaoHistorico.addActionListener(e -> {
            new TelaHistoricoMetasSupervisor(supervisorLogado);
            janelaConclusaoMetas.dispose();
        });
    }
}