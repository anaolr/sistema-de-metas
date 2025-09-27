package colaborador;

import javax.swing.*;
import login.TelaLogin;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.MetaDAO;
import modelos.Meta;
import modelos.Colaborador;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDate;

public class TelaHistorico {

    private JFrame janelaHistorico;
    private JButton botaoMinhasMetas, botaoRecompensas, botaoHistorico, botaoSair;
    private JPanel painelHistorico;
    private Colaborador colaboradorLogado;

    // Adicione este método à classe TelaHistorico.java

    // O método não retorna nada, sua função é apenas popular a tela.
    private void popularHistorico() {
        // Cria uma instância da classe DAO para buscar os dados.
        MetaDAO dao = new MetaDAO();

        // Busca apenas as metas concluídas do colaborador logado.
        ArrayList<Meta> metasConcluidas = dao.buscarConcluidasPorColaborador(colaboradorLogado.getNome());

        // Limpa o painel antes de adicionar as novas metas.
        painelHistorico.removeAll();

        // Itera sobre a lista de metas concluídas retornada do banco.
        for (Meta m : metasConcluidas) {
            // Formata a data para o padrão 'dd/MM/yyyy'.
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataConclusao = LocalDate.now().format(formatter); // Usaremos a data atual para simular

            // Adiciona a meta concluída à tela.
            adicionarRegistro(dataConclusao, "Meta concluída", m.getDescricao());
        }
        // Repinta o painel para que as mudanças sejam visíveis.
        painelHistorico.revalidate();
        painelHistorico.repaint();
    }

    public TelaHistorico(Colaborador colaborador) {
        this.colaboradorLogado = colaborador;
        // -------------------------
        // Janela principal
        // -------------------------
        janelaHistorico = new JFrame("Sistema de Metas - Histórico do Colaborador");
        janelaHistorico.setSize(900, 500);
        janelaHistorico.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaHistorico.setLayout(new GridLayout(1, 2));
        janelaHistorico.setResizable(false);

        // -------------------------
        // Painel esquerdo (menu lateral)
        // -------------------------
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
        botaoHistorico.setBackground(new Color(255, 140, 0));
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
                janelaHistorico.dispose();
            }
        });

        // eventos - botoes
        botaoMinhasMetas.addActionListener(e -> {
            new TelaMinhasMetas(this.colaboradorLogado);
            janelaHistorico.dispose();
        });

        botaoRecompensas.addActionListener(e -> {
            new TelaRecompensasColaborador(this.colaboradorLogado);
            janelaHistorico.dispose();
        });

        // -------------------------
        // Painel direito (conteúdo histórico)
        // -------------------------
        painelHistorico = new JPanel();
        painelHistorico.setLayout(new BoxLayout(painelHistorico, BoxLayout.Y_AXIS));
        painelHistorico.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // REMOVA OU COMENTE AS LINHAS DE EXEMPLO ABAIXO
        // adicionarRegistro("01/09/2025", "Meta concluída", "Finalizou a meta 'Aumentar
        // vendas em 10%'.");
        // adicionarRegistro("05/09/2025", "Recompensa resgatada", "Baixou a recompensa
        // 'Vale-presente R$50'.");
        // adicionarRegistro("12/09/2025", "Meta concluída", "Concluiu 'Relatório de
        // produtividade'.");
        // adicionarRegistro("15/09/2025", "Recompensa resgatada", "Baixou a recompensa
        // 'Dia de folga'.");
        // adicionarRegistro("16/09/2025", "Meta iniciada", "Começou 'Atualizar cadastro
        // de clientes'.");

        popularHistorico(); // ADICIONE ESTA LINHA AQUI!

        JScrollPane scrollPane = new JScrollPane(painelHistorico);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        janelaHistorico.add(painelBotoes);
        janelaHistorico.add(scrollPane);

        janelaHistorico.setLocationRelativeTo(null);
        janelaHistorico.setVisible(true);
    }

    private void adicionarRegistro(String data, String tipo, String descricao) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        card.setBackground(new Color(245, 245, 245));
        card.setPreferredSize(new Dimension(0, 70));

        JLabel rotuloData = new JLabel(data, SwingConstants.CENTER);
        rotuloData.setPreferredSize(new Dimension(80, 70));
        rotuloData.setFont(new Font("Segoe UI", Font.BOLD, 12));

        JPanel painelInfo = new JPanel(new GridLayout(2, 1));
        painelInfo.setBackground(new Color(245, 245, 245));
        painelInfo.add(new JLabel("Tipo: " + tipo));
        painelInfo.add(new JLabel("Descrição: " + descricao));

        card.add(rotuloData, BorderLayout.WEST);
        card.add(painelInfo, BorderLayout.CENTER);

        painelHistorico.add(card);
        painelHistorico.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    public static void main(String[] args) {
        // new TelaHistorico();
    }
}