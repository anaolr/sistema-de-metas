package supervisor;

import javax.swing.*;
import login.TelaLogin;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import modelos.Meta;
import dao.MetaDAO;
import java.util.Date;
import dao.ColaboradorDAO;
import modelos.Colaborador;

public class TelaCriarMeta {

    private JFrame janelaCriarMeta;
    private JButton botaoColaborador, botaoCriarMeta, botaoConclusaoMeta, botaoHistorico, botaoSair, botaoSalvar,
            botaoLimpar;
    private JTextField campoNome;
    private JTextArea campoDescricao;
    private JSpinner campoData;
    private ArrayList<String> colaboradoresSelecionados;
    private Colaborador supervisorLogado;

    private void limparCampos() {
        campoNome.setText("");
        campoDescricao.setText("");
        campoData.setValue(new Date());
        colaboradoresSelecionados.clear();
    }

    private boolean validarCampos() {
        if (campoNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(janelaCriarMeta, "O campo Nome é obrigatório!", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            campoNome.requestFocus();
            return false;
        }

        if (campoDescricao.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(janelaCriarMeta, "O campo Descrição é obrigatório!", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            campoDescricao.requestFocus();
            return false;
        }

        Date dataSelecionada = (Date) campoData.getValue();
        LocalDate dataLocal = dataSelecionada.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate hoje = LocalDate.now();

        if (dataLocal.isBefore(hoje)) {
            JOptionPane.showMessageDialog(janelaCriarMeta, "A data deve ser hoje ou futura!", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            campoData.requestFocus();
            return false;
        }

        if (colaboradoresSelecionados.isEmpty()) {
            JOptionPane.showMessageDialog(janelaCriarMeta,
                    "Selecione pelo menos um colaborador!",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    public TelaCriarMeta(Colaborador supervisor) {
        this.supervisorLogado = supervisor;

        janelaCriarMeta = new JFrame("Sistema de Metas - Supervisor");
        janelaCriarMeta.setSize(800, 500);
        janelaCriarMeta.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaCriarMeta.setLayout(new GridLayout(1, 2));
        janelaCriarMeta.setResizable(false);

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
        botaoCriarMeta.setBackground(new Color(255, 140, 0));
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

        JPanel painelCadastro = new JPanel(new GridBagLayout());
        painelCadastro.setBackground(Color.WHITE);
        painelCadastro.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        arrumar.insets = new Insets(10, 10, 10, 10);
        arrumar.fill = GridBagConstraints.HORIZONTAL;
        arrumar.gridx = 0;
        arrumar.gridy = 0;
        arrumar.gridwidth = 2;

        JLabel tituloCadastro = new JLabel("CRIAR META", SwingConstants.CENTER);
        tituloCadastro.setFont(new Font("Segoe UI", Font.BOLD, 20));
        painelCadastro.add(tituloCadastro, arrumar);

        arrumar.gridy++;
        arrumar.gridwidth = 1;
        painelCadastro.add(new JLabel("Nome:"), arrumar);

        arrumar.gridx = 1;
        campoNome = new JTextField(15);
        campoNome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelCadastro.add(campoNome, arrumar);

        arrumar.gridy++;
        arrumar.gridx = 0;
        arrumar.gridwidth = 1;
        painelCadastro.add(new JLabel("Descrição:"), arrumar);

        arrumar.gridx = 1;
        arrumar.gridwidth = 2;
        arrumar.weightx = 1.0;
        arrumar.weighty = 1.0;
        arrumar.fill = GridBagConstraints.BOTH;

        campoDescricao = new JTextArea();
        campoDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campoDescricao.setLineWrap(true);
        campoDescricao.setWrapStyleWord(true);

        JScrollPane scrollDescricao = new JScrollPane(campoDescricao);
        painelCadastro.add(scrollDescricao, arrumar);

        arrumar.weightx = 0;
        arrumar.weighty = 0;

        arrumar.gridy++;
        arrumar.gridx = 0;
        arrumar.gridwidth = 1;
        painelCadastro.add(new JLabel("Prazo final:"), arrumar);

        arrumar.gridx = 1;
        campoData = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(campoData, "dd/MM/yyyy");
        campoData.setEditor(editor);
        campoData.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelCadastro.add(campoData, arrumar);

        arrumar.gridy++;
        arrumar.gridx = 0;
        painelCadastro.add(new JLabel("Colaborador:"), arrumar);

        colaboradoresSelecionados = new ArrayList<>();
        JButton botaoSelecionarColaboradores = new JButton("Selecionar Colaboradores");
        arrumar.gridx = 1;
        painelCadastro.add(botaoSelecionarColaboradores, arrumar);

        arrumar.gridy++;
        arrumar.gridx = 0;
        arrumar.gridwidth = 1;
        botaoSalvar = new JButton("SALVAR");
        botaoSalvar.setBackground(new Color(51, 102, 204));
        botaoSalvar.setForeground(Color.WHITE);
        botaoSalvar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botaoSalvar.setFocusPainted(false);
        botaoSalvar.setPreferredSize(new Dimension(150, 35));
        painelCadastro.add(botaoSalvar, arrumar);

        arrumar.gridx = 1;
        botaoLimpar = new JButton("LIMPAR");
        botaoLimpar.setBackground(new Color(255, 193, 7));
        botaoLimpar.setForeground(Color.BLACK);
        botaoLimpar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botaoLimpar.setFocusPainted(false);
        botaoLimpar.setPreferredSize(new Dimension(150, 35));
        painelCadastro.add(botaoLimpar, arrumar);

        // Eventos
        botaoSair.addActionListener(e -> {
            new TelaLogin();
            janelaCriarMeta.dispose();
        });

        botaoSelecionarColaboradores.addActionListener(e -> {
            ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
            ArrayList<Colaborador> listaColaboradores = colaboradorDAO.buscarAtivosPorDepartamento(supervisorLogado.getDepartamento());
            
            String[] nomesColaboradores = new String[listaColaboradores.size()];
            for (int i = 0; i < listaColaboradores.size(); i++) {
                nomesColaboradores[i] = listaColaboradores.get(i).getNome();
            }

            TelaSelecionarColaboradores tela = new TelaSelecionarColaboradores(janelaCriarMeta, nomesColaboradores);
            colaboradoresSelecionados = tela.getSelecionados();

            if (!colaboradoresSelecionados.isEmpty()) {
                JOptionPane.showMessageDialog(janelaCriarMeta,
                        "Selecionados: " + String.join(", ", colaboradoresSelecionados));
            }
        });

        botaoSalvar.addActionListener(e -> {
            if (validarCampos()) {
                Date dataSelecionada = (Date) campoData.getValue();
                LocalDate prazoFinal = dataSelecionada.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                Meta novaMeta = new Meta(
                        campoNome.getText().trim(),
                        campoDescricao.getText().trim(),
                        prazoFinal,
                        "Em andamento",
                        colaboradoresSelecionados);
                MetaDAO metaDAO = new MetaDAO();
                if (metaDAO.salvar(novaMeta)) {
                    JOptionPane.showMessageDialog(janelaCriarMeta, "Meta salva com sucesso!");
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(janelaCriarMeta,
                            "Erro ao salvar a meta no banco de dados.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botaoLimpar.addActionListener(e -> limparCampos());

        botaoColaborador.addActionListener(e -> {
            new TelaColaboradores(supervisorLogado);
            janelaCriarMeta.dispose();
        });

        botaoHistorico.addActionListener(e -> {
            new TelaHistoricoMetasSupervisor(supervisorLogado);
            janelaCriarMeta.dispose();
        });

        botaoConclusaoMeta.addActionListener(e -> {
            new TelaConclusaoMetas(supervisorLogado);
            janelaCriarMeta.dispose();
        });

        janelaCriarMeta.add(painelBotoes);
        janelaCriarMeta.add(painelCadastro);
        janelaCriarMeta.setLocationRelativeTo(null);
        janelaCriarMeta.setVisible(true);
    }
}