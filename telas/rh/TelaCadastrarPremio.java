package rh;

import javax.swing.*;
import login.TelaLogin;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import modelos.Colaborador;
import dao.ColaboradorDAO;
import modelos.Premio;
import dao.PremioDAO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TelaCadastrarPremio {
    private JFrame janelaCadastrarPremio;
    private JButton botaoColaborador, botaoDepartamento, botaoPremio, botaoHistorico, botaoHistoricoColaboradores,
            botaoSair, botaoSalvar,
            botaoLimpar;
    private JTextField campoNome, campoDescricao;
    private java.io.File arquivoSelecionado;
    private JLabel labelArquivo;

    private JComboBox<String> comboBoxColaboradores;
    private List<Colaborador> listaColaboradores;

    public boolean validarCampos() {
        if (campoNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(janelaCadastrarPremio, "Campo nome é obrigatório");
            campoNome.requestFocus();
            return false;
        }

        if (campoDescricao.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(janelaCadastrarPremio, "Campo Descrição é obrigatório");
            campoDescricao.requestFocus();
            return false;
        }

        if (arquivoSelecionado == null) {
            JOptionPane.showMessageDialog(janelaCadastrarPremio, "Selecione um arquivo para upload");
            return false;
        }

        if (comboBoxColaboradores.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(janelaCadastrarPremio, "Selecione um colaborador para atribuir o prêmio.");
            return false;
        }

        return true;
    }

    public void limparCampos() {
        campoNome.setText("");
        campoDescricao.setText("");

        arquivoSelecionado = null;
        labelArquivo.setText("Nenhum arquivo selecionado");

        comboBoxColaboradores.setSelectedIndex(0);
    }

    public TelaCadastrarPremio() {
        janelaCadastrarPremio = new JFrame("Cadastrar Prêmio - RH");
        janelaCadastrarPremio.setSize(900, 600);
        janelaCadastrarPremio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaCadastrarPremio.setLayout(new GridLayout(1, 2));
        janelaCadastrarPremio.setResizable(false);

        // PAINEL ESQUERDO - BOTOES
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
        botaoPremio.setBackground(new Color(255, 140, 0));
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
                janelaCadastrarPremio.dispose();
            }
        });

        ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
        listaColaboradores = colaboradorDAO.buscarTodos();

        comboBoxColaboradores = new JComboBox<>();
        comboBoxColaboradores.addItem("Selecione um Colaborador");

        for (Colaborador c : listaColaboradores) {
            comboBoxColaboradores.addItem(c.getNome());
        }

        // PAINEL DIREITO - CADASTRO
        JPanel painelCadastro = new JPanel(new GridBagLayout());
        painelCadastro.setBackground(Color.WHITE);
        painelCadastro.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        arrumar = new GridBagConstraints();
        arrumar.insets = new Insets(10, 10, 10, 10);
        arrumar.fill = GridBagConstraints.HORIZONTAL;
        arrumar.gridx = 0;
        arrumar.gridy = 0;
        arrumar.gridwidth = 2;

        // Título
        JLabel tituloCadastro = new JLabel("CADASTRAR PRÊMIO", SwingConstants.CENTER);
        tituloCadastro.setFont(new Font("Segoe UI", Font.BOLD, 20));
        painelCadastro.add(tituloCadastro, arrumar);

        // Nome
        arrumar.gridy++;
        arrumar.gridwidth = 1;
        arrumar.gridx = 0;
        painelCadastro.add(new JLabel("Nome:"), arrumar);

        arrumar.gridx = 1;
        campoNome = new JTextField(15);
        campoNome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelCadastro.add(campoNome, arrumar);

        // Descrição
        arrumar.gridy++;
        arrumar.gridx = 0;
        painelCadastro.add(new JLabel("Descrição:"), arrumar);

        arrumar.gridx = 1;
        campoDescricao = new JTextField(15);
        campoDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelCadastro.add(campoDescricao, arrumar);

        // Rótulo da foto/arquivo
        arrumar.gridy++;
        arrumar.gridx = 0;
        painelCadastro.add(new JLabel("Foto / Arquivo:"), arrumar);

        // Botão escolher arquivo
        arrumar.gridx = 1;
        JButton botaoUpload = new JButton("Selecionar arquivo");
        painelCadastro.add(botaoUpload, arrumar);

        // Rótulo para mostrar nome do arquivo
        arrumar.gridy++;
        arrumar.gridx = 1;
        labelArquivo = new JLabel("Nenhum arquivo selecionado");
        painelCadastro.add(labelArquivo, arrumar);

        // Colaborador: Rótulo
        arrumar.gridy++;
        arrumar.gridx = 0;
        painelCadastro.add(new JLabel("Atribuir a:"), arrumar);

        // Colaborador: ComboBox
        arrumar.gridx = 1;
        painelCadastro.add(comboBoxColaboradores, arrumar);

        // Botão SALVAR
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

        // BOTÃO LIMPAR
        arrumar.gridx = 1;
        botaoLimpar = new JButton("LIMPAR");
        botaoLimpar.setBackground(new Color(255, 193, 7));
        botaoLimpar.setForeground(Color.BLACK);
        botaoLimpar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botaoLimpar.setFocusPainted(false);
        botaoLimpar.setPreferredSize(new Dimension(150, 35));
        painelCadastro.add(botaoLimpar, arrumar);

        janelaCadastrarPremio.add(painelBotoes);
        janelaCadastrarPremio.add(painelCadastro);

        janelaCadastrarPremio.setLocationRelativeTo(null);
        janelaCadastrarPremio.setVisible(true);

        // EVENTOS
        botaoUpload.addActionListener(e -> {
            JFileChooser seletor = new JFileChooser();
            int resultado = seletor.showOpenDialog(janelaCadastrarPremio);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                arquivoSelecionado = seletor.getSelectedFile();

                String nomeCompleto = arquivoSelecionado.getName();
                String nomeExibicao;
                int limite = 30;

                if (nomeCompleto.length() > limite) {
                    nomeExibicao = nomeCompleto.substring(0, limite) + " ...";
                } else {
                    nomeExibicao = nomeCompleto;
                }

                labelArquivo.setText(nomeExibicao);
                System.out.println("Arquivo escolhido: " + arquivoSelecionado.getAbsolutePath());
            }
        });

        // LÓGICA DO BOTÃO SALVAR (AGORA COM CÓPIA SIMPLES)
        botaoSalvar.addActionListener(e -> {
            if (validarCampos()) {

                String caminhoRelativo = null;

                try {
                    // 1. Lógica para copiar o arquivo para a pasta do projeto (imagens/premios/)
                    String nomeArquivo = arquivoSelecionado.getName();
                    caminhoRelativo = "imagens/premios/" + nomeArquivo; // Caminho relativo

                    // Cria o diretório de destino, se não existir
                    java.io.File diretorioDestino = new java.io.File("imagens/premios");
                    if (!diretorioDestino.exists()) {
                        diretorioDestino.mkdirs();
                    }

                    // Copiar o arquivo da origem (local absoluto) para o destino (local relativo)
                    Files.copy(
                            arquivoSelecionado.toPath(),
                            Paths.get(caminhoRelativo),
                            StandardCopyOption.REPLACE_EXISTING);

                    // 2. Continua com a lógica de salvamento para o banco de dados
                    int indiceSelecionado = comboBoxColaboradores.getSelectedIndex();
                    Colaborador colaboradorSelecionado = listaColaboradores.get(indiceSelecionado - 1);
                    int idColaborador = colaboradorSelecionado.getId();

                    Premio novoPremio = new Premio(
                            campoNome.getText().trim(),
                            campoDescricao.getText().trim(),
                            caminhoRelativo, // <<--- SALVA O CAMINHO RELATIVO
                            idColaborador);

                    PremioDAO premioDAO = new PremioDAO();

                    if (premioDAO.salvar(novoPremio)) {
                        JOptionPane.showMessageDialog(janelaCadastrarPremio,
                                "Prêmio cadastrado e atribuído a " + colaboradorSelecionado.getNome() + "!");
                        limparCampos();
                    } else {
                        JOptionPane.showMessageDialog(janelaCadastrarPremio,
                                "Erro ao salvar o prêmio no banco de dados. Verifique a conexão e o DAO.",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(janelaCadastrarPremio,
                            "Erro ao salvar o arquivo localmente. Detalhes: " + ex.getMessage(),
                            "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(janelaCadastrarPremio,
                            "Erro inesperado durante o cadastro: " + ex.getMessage(), "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botaoLimpar.addActionListener(e -> {
            limparCampos();
        });

        botaoColaborador.addActionListener(e -> {
            new TelaCadastrarColaboradorRH();
            janelaCadastrarPremio.dispose();
        });

        botaoDepartamento.addActionListener(e -> {
            new TelaCadastrarDepartamento();
            janelaCadastrarPremio.dispose();
        });

        botaoHistorico.addActionListener(e -> {
            new TelaHistoricoMetas();
            janelaCadastrarPremio.dispose();
        });

        botaoHistoricoColaboradores.addActionListener(e -> {
            new TelaHistoricoColaboradores();
            janelaCadastrarPremio.dispose();
        });

    }

    public static void main(String[] args) {
        new TelaCadastrarPremio();
    }
}