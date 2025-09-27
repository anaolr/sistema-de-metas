package rh;

import javax.swing.*;
import login.TelaLogin;
import java.awt.*;
import modelos.Colaborador;
import dao.ColaboradorDAO;
import dao.DepartamentoDAO;
import modelos.Departamento;
import java.util.ArrayList;

public class TelaCadastrarColaboradorRH {
    private JFrame janelaCadastrarColaborador;
    // Removido botaoVoltar
    private JButton botaoColaborador, botaoDepartamento, botaoPremio, botaoHistorico, botaoHistoricoColaboradores,
            botaoSair, botaoSalvar, botaoLimpar, botaoNovo, botaoPesquisar, botaoAlterar, botaoExcluir;
    private JTextField campoNome, campoLogin, campoCargo, campoId;
    private JPasswordField campoSenha;
    private JComboBox<String> campoDepartamento, campoStatus;
    private DefaultComboBoxModel<String> modeloDepartamentos, modeloStatus;

    private void habilitarCampos(boolean habilitar) {
        campoNome.setEnabled(habilitar);
        campoCargo.setEnabled(habilitar);
        campoSenha.setEnabled(habilitar);
        campoStatus.setEnabled(habilitar);
        campoDepartamento.setEnabled(habilitar);
        if (habilitar) {
            campoLogin.setEnabled(true);
        }
    }

    private void configurarBotoes(String estado) {
        // Fluxo simplificado: Limpar (Reset) sempre volta ao estado 'inicial'
        switch (estado) {
            case "inicial":
                botaoNovo.setEnabled(true);
                botaoPesquisar.setEnabled(true);
                botaoSalvar.setEnabled(false);
                botaoAlterar.setEnabled(false);
                botaoExcluir.setEnabled(false);
                // Renomeado para RESET
                botaoLimpar.setEnabled(false);
                habilitarCampos(false);
                break;
            case "novo":
                botaoNovo.setEnabled(false);
                botaoPesquisar.setEnabled(false);
                botaoSalvar.setEnabled(true);
                botaoAlterar.setEnabled(false);
                botaoExcluir.setEnabled(false);
                // Renomeado para RESET
                botaoLimpar.setEnabled(true);
                habilitarCampos(true);
                limparCampos();
                break;
            case "pesquisar":
                botaoNovo.setEnabled(false);
                botaoPesquisar.setEnabled(false); // Desativa Pesquisar
                botaoSalvar.setEnabled(false);
                botaoAlterar.setEnabled(true);
                botaoExcluir.setEnabled(true);
                // Renomeado para RESET
                botaoLimpar.setEnabled(true);
                habilitarCampos(false);
                break;
            case "alterar":
                botaoNovo.setEnabled(false);
                botaoPesquisar.setEnabled(false);
                botaoSalvar.setEnabled(true);
                botaoAlterar.setEnabled(false);
                botaoExcluir.setEnabled(false);
                // Renomeado para RESET
                botaoLimpar.setEnabled(true);
                habilitarCampos(true);
                campoLogin.setEnabled(false);
                break;
        }
    }

    public boolean validarCampos() {
        String textoPrefixo = "Verifique o prefixo do usuário!\n -'rh. ' para funcionário de RH\n -'sup. ' para supervisor\n -'col. ' para colaborador";

        if (campoNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(janelaCadastrarColaborador, "Campo nome é obrigatório");
            campoNome.requestFocus();
            return false;
        }

        if (campoLogin.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(janelaCadastrarColaborador, "Campo Login é obrigatório");
            campoLogin.requestFocus();
            return false;
        }

        String login = campoLogin.getText().trim().toLowerCase();
        if (!login.startsWith("sup.") && !login.startsWith("col.") && !login.startsWith("rh.")) {
            JOptionPane.showMessageDialog(janelaCadastrarColaborador, textoPrefixo, "Prefixo de Login Inválido",
                    JOptionPane.ERROR_MESSAGE);
            campoLogin.requestFocus();
            return false;
        }

        if (campoCargo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(janelaCadastrarColaborador, "Campo cargo é obrigatório");
            campoCargo.requestFocus();
            return false;
        }

        if (campoSenha.getPassword().length == 0) {
            JOptionPane.showMessageDialog(janelaCadastrarColaborador, "Campo senha é obrigatório");
            campoSenha.requestFocus();
            return false;
        }
        if (campoStatus.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(janelaCadastrarColaborador, "Selecione um status válido");
            campoStatus.requestFocus();
            return false;
        }

        if (campoDepartamento.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(janelaCadastrarColaborador, "Selecione um departamento válido");
            campoDepartamento.requestFocus();
            return false;
        }

        return true;
    }

    public void limparCampos() {
        campoId.setText("");
        campoNome.setText("");
        campoLogin.setText("");
        campoCargo.setText("");
        campoSenha.setText("");
        campoDepartamento.setSelectedIndex(0);
        modeloStatus.setSelectedItem("Selecione o Status");
    }

    public void popularDepartamentos() {
        modeloDepartamentos.removeAllElements();
        modeloDepartamentos.addElement("Selecione o departamento");
        DepartamentoDAO dao = new DepartamentoDAO();
        ArrayList<Departamento> departamentos = dao.buscarTodos();
        for (Departamento d : departamentos) {
            modeloDepartamentos.addElement(d.getNome());
        }
    }

    public TelaCadastrarColaboradorRH() {
        // PADRONIZAÇÃO DE TAMANHO
        janelaCadastrarColaborador = new JFrame("Cadastrar colaborador - RH");
        janelaCadastrarColaborador.setSize(900, 600);
        janelaCadastrarColaborador.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaCadastrarColaborador.setLayout(new GridLayout(1, 2));
        janelaCadastrarColaborador.setResizable(false);

        // Painel Esquerdo (Botões)

        JPanel painelBotoes = new JPanel(new GridBagLayout());
        painelBotoes.setBackground(new Color(51, 102, 204));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints arrumar = new GridBagConstraints();
        arrumar.insets = new Insets(10, 10, 10, 10);
        arrumar.fill = GridBagConstraints.HORIZONTAL;
        arrumar.gridx = 0;
        arrumar.gridy = 0;
        arrumar.gridwidth = 2;

        JLabel rotuloRh = new JLabel("RECURSOS HUMANOS", SwingConstants.CENTER);
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

        // BOTÕES DE NAVEGAÇÃO
        Color botaoEscuro = new Color(30, 61, 122);

        arrumar.gridwidth = 1;
        arrumar.gridy++;

        botaoColaborador = new JButton("Cadastrar Colaborador");
        botaoColaborador.setBackground(new Color(255, 140, 0));
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

        // Painel Direito (Cadastro)

        JPanel painelCadastro = new JPanel(new GridBagLayout());
        painelCadastro.setBackground(Color.WHITE);
        painelCadastro.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        arrumar.insets = new Insets(10, 10, 10, 10);
        arrumar.fill = GridBagConstraints.HORIZONTAL;
        arrumar.gridx = 0;
        arrumar.gridy = 0;
        arrumar.gridwidth = 2;

        JLabel tituloCadastro = new JLabel("CADASTRAR COLABORADOR", SwingConstants.CENTER);
        tituloCadastro.setFont(new Font("Segoe UI", Font.BOLD, 20));
        painelCadastro.add(tituloCadastro, arrumar);

        arrumar.gridy++;
        arrumar.gridwidth = 1;
        painelCadastro.add(new JLabel("ID:"), arrumar);

        arrumar.gridx = 1;
        campoId = new JTextField(15);
        campoId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campoId.setEnabled(false);
        painelCadastro.add(campoId, arrumar);

        arrumar.gridy++;
        arrumar.gridx = 0;
        painelCadastro.add(new JLabel("Nome:"), arrumar);

        arrumar.gridx = 1;
        campoNome = new JTextField(15);
        campoNome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelCadastro.add(campoNome, arrumar);

        arrumar.gridy++;
        arrumar.gridx = 0;
        painelCadastro.add(new JLabel("Login:"), arrumar);

        arrumar.gridx = 1;
        campoLogin = new JTextField(15);
        campoLogin.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelCadastro.add(campoLogin, arrumar);

        arrumar.gridy++;
        arrumar.gridx = 0;
        painelCadastro.add(new JLabel("Cargo:"), arrumar);

        arrumar.gridx = 1;
        campoCargo = new JTextField(15);
        campoCargo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelCadastro.add(campoCargo, arrumar);

        arrumar.gridy++;
        arrumar.gridx = 0;
        painelCadastro.add(new JLabel("Senha:"), arrumar);

        arrumar.gridx = 1;
        campoSenha = new JPasswordField(15);
        campoSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelCadastro.add(campoSenha, arrumar);

        arrumar.gridy++;
        arrumar.gridx = 0;
        painelCadastro.add(new JLabel("Status:"), arrumar);

        arrumar.gridx = 1;
        String[] statusColaborador = { "Selecione o Status", "Ativo", "Inativo" };
        modeloStatus = new DefaultComboBoxModel<>(statusColaborador);
        campoStatus = new JComboBox<>(modeloStatus);
        campoStatus.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelCadastro.add(campoStatus, arrumar);

        arrumar.gridy++;
        arrumar.gridx = 0;
        painelCadastro.add(new JLabel("Departamento:"), arrumar);

        arrumar.gridx = 1;
        modeloDepartamentos = new DefaultComboBoxModel<>();
        popularDepartamentos();
        campoDepartamento = new JComboBox<>(modeloDepartamentos);
        campoDepartamento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelCadastro.add(campoDepartamento, arrumar);

        // PADRONIZAÇÃO DAS CORES DOS BOTÕES DE AÇÃO
        Color azulPrimario = new Color(51, 102, 204);
        Color amareloAlerta = new Color(255, 193, 7);
        Color vermelhoDestrutivo = new Color(220, 53, 69);
        Font fonteAcao = new Font("Segoe UI", Font.BOLD, 14);
        Dimension tamanhoAcao = new Dimension(120, 30);

        // Função utilitária para aplicar estilo
        java.util.function.BiConsumer<JButton, Color> applyStyle = (b, c) -> {
            b.setBackground(c);
            b.setForeground(Color.WHITE);
            b.setFont(fonteAcao);
            b.setFocusPainted(false);
            b.setPreferredSize(tamanhoAcao);
        };

        // Inicializa e estiliza os botões
        botaoNovo = new JButton("Novo");
        applyStyle.accept(botaoNovo, azulPrimario);

        botaoPesquisar = new JButton("Pesquisar");
        applyStyle.accept(botaoPesquisar, azulPrimario);

        botaoAlterar = new JButton("Alterar");
        applyStyle.accept(botaoAlterar, azulPrimario);

        botaoExcluir = new JButton("Desativar");
        applyStyle.accept(botaoExcluir, vermelhoDestrutivo);

        botaoSalvar = new JButton("SALVAR");
        applyStyle.accept(botaoSalvar, azulPrimario);

        // Botão Limpar RENOMEADO para RESET
        botaoLimpar = new JButton("RESET");
        botaoLimpar.setBackground(amareloAlerta);
        botaoLimpar.setForeground(Color.BLACK);
        botaoLimpar.setFont(fonteAcao);
        botaoLimpar.setFocusPainted(false);
        botaoLimpar.setPreferredSize(tamanhoAcao);

        // Painel de Ações
        // Retornado para GridLayout(2, 3) para caber 6 botões em 2 linhas, sem
        // distorção.
        JPanel painelBotoesAcao = new JPanel(new GridLayout(2, 3, 10, 10));

        painelBotoesAcao.add(botaoNovo);
        painelBotoesAcao.add(botaoPesquisar);
        painelBotoesAcao.add(botaoAlterar);
        painelBotoesAcao.add(botaoExcluir);
        painelBotoesAcao.add(botaoSalvar);
        painelBotoesAcao.add(botaoLimpar);

        arrumar.gridy++;
        arrumar.gridx = 0;
        arrumar.gridwidth = 2;
        painelCadastro.add(painelBotoesAcao, arrumar);

        janelaCadastrarColaborador.add(painelBotoes);
        janelaCadastrarColaborador.add(painelCadastro);
        janelaCadastrarColaborador.setLocationRelativeTo(null);
        janelaCadastrarColaborador.setVisible(true);

        configurarBotoes("inicial");

        // EVENTOS

        // 1. Botão NOVO
        botaoNovo.addActionListener(e -> configurarBotoes("novo"));

        // 2. Botão PESQUISAR
        botaoPesquisar.addActionListener(e -> {
            ColaboradorDAO dao = new ColaboradorDAO();
            Colaborador c = dao.buscarPorLogin(campoLogin.getText().trim());
            if (c != null) {
                campoId.setText(String.valueOf(c.getId()));
                campoNome.setText(c.getNome());
                campoCargo.setText(c.getCargo());
                campoSenha.setText(c.getSenha());
                campoStatus.setSelectedItem(c.getStatus() ? "Ativo" : "Inativo");
                campoDepartamento.setSelectedItem(c.getDepartamento());
                configurarBotoes("pesquisar");

                if (!c.getStatus()) { // Se o colaborador estiver inativo
                    int resposta = JOptionPane.showConfirmDialog(janelaCadastrarColaborador,
                            "Este colaborador está inativo. Deseja reativá-lo?", "Reativar Colaborador",
                            JOptionPane.YES_NO_OPTION);
                    if (resposta == JOptionPane.YES_OPTION) {
                        if (dao.reativar(c.getlogin())) {
                            JOptionPane.showMessageDialog(janelaCadastrarColaborador,
                                    "Colaborador reativado com sucesso!");
                            campoStatus.setSelectedItem("Ativo");
                        } else {
                            JOptionPane.showMessageDialog(janelaCadastrarColaborador, "Erro ao reativar colaborador.",
                                    "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(janelaCadastrarColaborador, "Colaborador não encontrado!");
                // Limpar e voltar ao estado inicial (RESET)
                limparCampos();
                configurarBotoes("inicial");
            }
        });

        // 3. Botão ALTERAR
        botaoAlterar.addActionListener(e -> configurarBotoes("alterar"));

        // 4. Botão DESATIVAR (Mantido como atalho)
        botaoExcluir.addActionListener(e -> {
            int resposta = JOptionPane.showConfirmDialog(janelaCadastrarColaborador,
                    "Deseja realmente desativar este colaborador?", "Confirmação de Desativação",
                    JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                ColaboradorDAO dao = new ColaboradorDAO();
                if (dao.desativar(campoLogin.getText().trim())) {
                    JOptionPane.showMessageDialog(janelaCadastrarColaborador, "Colaborador desativado com sucesso!");
                    limparCampos();
                    configurarBotoes("inicial");
                } else {
                    JOptionPane.showMessageDialog(janelaCadastrarColaborador, "Erro ao desativar colaborador.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 5. Botão SALVAR
        botaoSalvar.addActionListener(e -> {
            if (validarCampos()) {
                ColaboradorDAO dao = new ColaboradorDAO();
                Colaborador colaboradorExistente = dao.buscarPorLogin(campoLogin.getText().trim());
                boolean status = campoStatus.getSelectedItem().equals("Ativo");

                if (colaboradorExistente != null && !campoId.getText().isEmpty()
                        && Integer.parseInt(campoId.getText()) == colaboradorExistente.getId()) {
                    int resposta = JOptionPane.showConfirmDialog(janelaCadastrarColaborador,
                            "Deseja realmente alterar os dados deste colaborador?", "Confirmação",
                            JOptionPane.YES_NO_OPTION);
                    if (resposta == JOptionPane.YES_OPTION) {
                        Colaborador c = new Colaborador(campoNome.getText(), campoLogin.getText(),
                                campoCargo.getText(), new String(campoSenha.getPassword()), status,
                                (String) campoDepartamento.getSelectedItem());
                        if (dao.alterar(c)) {
                            JOptionPane.showMessageDialog(janelaCadastrarColaborador,
                                    "Colaborador alterado com sucesso!");
                            limparCampos();
                            configurarBotoes("inicial");
                        } else {
                            JOptionPane.showMessageDialog(janelaCadastrarColaborador, "Erro ao alterar colaborador.",
                                    "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    Colaborador novoColaborador = new Colaborador(campoNome.getText(), campoLogin.getText(),
                            campoCargo.getText(), new String(campoSenha.getPassword()), status,
                            (String) campoDepartamento.getSelectedItem());
                    if (dao.salvar(novoColaborador)) {
                        JOptionPane.showMessageDialog(janelaCadastrarColaborador, "Colaborador salvo com sucesso!");
                        limparCampos();
                        configurarBotoes("inicial");
                    } else {
                        JOptionPane.showMessageDialog(janelaCadastrarColaborador, "Erro ao salvar colaborador.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // 6. Botão LIMPAR (Faz o RESET COMPLETO para o estado inicial)
        botaoLimpar.addActionListener(e -> {
            limparCampos();
            configurarBotoes("inicial");
        });

        // Eventos dos Botões de Navegação
        botaoSair.addActionListener(e -> {
            new TelaLogin();
            janelaCadastrarColaborador.dispose();
        });

        botaoDepartamento.addActionListener(e -> {
            new TelaCadastrarDepartamento();
            janelaCadastrarColaborador.dispose();
        });

        botaoPremio.addActionListener(e -> {
            new TelaCadastrarPremio();
            janelaCadastrarColaborador.dispose();
        });

        botaoHistorico.addActionListener(e -> {
            new TelaHistoricoMetas();
            janelaCadastrarColaborador.dispose();
        });

        botaoHistoricoColaboradores.addActionListener(e -> {
            new TelaHistoricoColaboradores();
            janelaCadastrarColaborador.dispose();
        });
    }

    public static void main(String[] args) {
        new TelaCadastrarColaboradorRH();
    }
}
