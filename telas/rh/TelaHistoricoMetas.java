package rh;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.MetaDAO;
import modelos.Meta;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

import login.TelaLogin;

import java.awt.*;

public class TelaHistoricoMetas {

    private JFrame janelaHistoricoMetas;
    private JButton botaoColaborador, botaoDepartamento, botaoPremio, botaoHistorico,botaoHistoricoColaboradores, botaoSair;
    private JTable tabelaMetas;
    private DefaultTableModel modelo;

    // Adicione este método à classe TelaHistoricoMetas.java

// O método não retorna nada, sua função é apenas popular a tabela.
public void popularTabela() {
    // Apaga os dados atuais para evitar duplicidade.
    modelo.setRowCount(0);

    // Cria uma instância da classe DAO para buscar os dados.
    MetaDAO dao = new MetaDAO();

    // Busca todas as metas do banco de dados usando o método que criamos.
    ArrayList<Meta> metas = dao.buscarTodos();

    // Itera sobre a lista de metas retornada do banco.
    for (Meta m : metas) {
        // Formata a data para o padrão 'dd/MM/yyyy'.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String prazoFormatado = m.getPrazoFinal().format(formatter);

        // Converte a lista de colaboradores para uma string separada por vírgula.
        String colaboradoresString = String.join(", ", m.getColaboradores());

        // Cria um novo array de objetos para representar uma linha da tabela.
        Object[] linha = {
            m.getNome(), // Coluna 1: Nome da meta.
            "Departamento", // Futuramente, você pode buscar o departamento da meta.
            prazoFormatado, // Coluna 2: Prazo final formatado.
            m.getStatus(), // Coluna 3: Status da meta.
            colaboradoresString // Coluna 4: Colaboradores da meta.
        };

        // Adiciona a nova linha com os dados da meta à tabela.
        modelo.addRow(linha);
    }
}
    

    // Construtor da tela
    public TelaHistoricoMetas() {
        // ======== JANELA PRINCIPAL ========
        janelaHistoricoMetas = new JFrame("Histórico de Metas - RH");
        janelaHistoricoMetas.setSize(900, 600);
        janelaHistoricoMetas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaHistoricoMetas.setLayout(new GridLayout(1, 2)); // Painel esquerdo e direito
        janelaHistoricoMetas.setResizable(false);

        // ======== PAINEL ESQUERDO - BOTÕES ========
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
        botaoColaborador.setBackground(botaoEscuro); // dourado mais escuro
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
        botaoHistorico.setBackground(new Color(255, 140, 0));
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

        botaoSair.addActionListener(e -> {
            new TelaLogin();
            janelaHistoricoMetas.dispose();

        });

        // ======== PAINEL DIREITO - TABELA ========
        JPanel painelDireito = new JPanel(new BorderLayout());
        painelDireito.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painelDireito.setBackground(Color.WHITE);

        /// ...
    // Colunas da tabela
    String[] colunas = { "Meta", "Departamento", "Prazo Final", "Status", "Colaboradores" };

    // Modelo da tabela (não editável)
    DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    // ADICIONE ESTA LINHA para que o método popularTabela() possa acessá-lo.
    this.modelo = modelo;

    // NÃO ADICIONE AS LINHAS DE EXEMPLO, pois vamos preencher com dados reais.
    // modelo.addRow(new Object[] { "Meta A", "RH", "20/09/2025", "Em andamento", "Ana, Bruno" });
    // modelo.addRow(new Object[] { "Meta B", "Financeiro", "15/09/2025", "Concluída", "Carlos" });
    // modelo.addRow(new Object[] { "Meta C", "TI", "10/09/2025", "Atrasada", "Daniela, Eduardo" });

    tabelaMetas = new JTable(modelo);
    tabelaMetas.setFillsViewportHeight(true);

    JScrollPane scroll = new JScrollPane(tabelaMetas);
    painelDireito.add(scroll, BorderLayout.CENTER);

    // ADICIONE ESTA LINHA PARA POPULAR A TABELA COM OS DADOS DO BANCO.
    popularTabela();
// ...

        /*
         * tabelaMetas.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
         * public void mouseMoved(java.awt.event.MouseEvent e) {
         * int row = tabelaMetas.rowAtPoint(e.getPoint());
         * int col = tabelaMetas.columnAtPoint(e.getPoint());
         * Object value = tabelaMetas.getValueAt(row, col);
         * tabelaMetas.setToolTipText(value != null ? value.toString() : null);
         * }
         * });
         */

        // ======== ADICIONA PAINÉIS À JANELA ========
        janelaHistoricoMetas.add(painelBotoes);
        janelaHistoricoMetas.add(painelDireito);

        janelaHistoricoMetas.setLocationRelativeTo(null);
        janelaHistoricoMetas.setVisible(true);

        // ======== EVENTOS DOS BOTÕES ========

        botaoColaborador.addActionListener(e -> {
            new TelaCadastrarColaboradorRH();
            janelaHistoricoMetas.dispose();
        });
        botaoDepartamento.addActionListener(e -> {
            new TelaCadastrarDepartamento();
            janelaHistoricoMetas.dispose();
        });
        botaoPremio.addActionListener(e -> {
            new TelaCadastrarPremio();
            janelaHistoricoMetas.dispose();
        });

        botaoHistoricoColaboradores.addActionListener(e ->{
            new TelaHistoricoColaboradores();
            janelaHistoricoMetas.dispose();
          });
    }

    public static void main(String[] args) {
        new TelaHistoricoMetas();
    }
}
