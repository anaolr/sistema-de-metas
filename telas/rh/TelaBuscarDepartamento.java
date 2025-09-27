package rh;

import javax.swing.*;
import login.TelaLogin;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelos.Departamento;
import dao.DepartamentoDAO;

public class TelaBuscarDepartamento {
    private JFrame janelaCadastrarDepartamento;
    private JButton botaoColaborador, botaoDepartamento, botaoPremio, botaoHistorico, botaoHistoricoColaboradores, botaoSair, botaoPesquisar,botaoSalvar,
            botaoLimpar;
    private JTextField campoNome, campoCodigo;

    public boolean validarCampos() {
        if (campoNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(janelaCadastrarDepartamento, "Campo nome é obrigatório");
            campoNome.requestFocus();
            return false;
        }

        // if (campoCodigo.getText().trim().isEmpty()) {
        //     JOptionPane.showMessageDialog(janelaCadastrarDepartamento, "Campo Código é obrigatório");
        //     campoCodigo.requestFocus();
        //     return false;
        // }

        return true; // passou por todas as validações
    }

    public void limparCampos() {
        campoNome.setText("");
        campoCodigo.setText("");

    }

    public TelaBuscarDepartamento() {
        janelaCadastrarDepartamento = new JFrame("Cadastrar Departamento - RH");
        janelaCadastrarDepartamento.setSize(900, 600);
        janelaCadastrarDepartamento.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaCadastrarDepartamento.setLayout(new GridLayout(1, 2));
        janelaCadastrarDepartamento.setResizable(false);

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
        botaoColaborador.setBackground(botaoEscuro); // dourado mais escuro
        botaoColaborador.setForeground(Color.WHITE);
        botaoColaborador.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botaoColaborador.setFocusPainted(false);
        botaoColaborador.setPreferredSize(new Dimension(150, 35));
        painelBotoes.add(botaoColaborador, arrumar);
        arrumar.gridy++;

        botaoDepartamento = new JButton("Departamentos");
        botaoDepartamento.setBackground(new Color(255, 140, 0));
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
                janelaCadastrarDepartamento.dispose();

            }
        });

        // PAINEL ESQUERDO - CADASTRO
        JPanel painelCadastro = new JPanel(new GridBagLayout());
        painelCadastro.setBackground(Color.WHITE);
        painelCadastro.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        arrumar.insets = new Insets(10, 10, 10, 10);
        arrumar.fill = GridBagConstraints.HORIZONTAL;
        arrumar.gridx = 0;
        arrumar.gridy = 0;
        arrumar.gridwidth = 2;

        // Título
        JLabel tituloCadastro = new JLabel("DEPARTAMENTO", SwingConstants.CENTER);
        tituloCadastro.setFont(new Font("Segoe UI", Font.BOLD, 20));
        painelCadastro.add(tituloCadastro, arrumar);

        // Nome
        arrumar.gridy++;
        arrumar.gridwidth = 1;
        painelCadastro.add(new JLabel("Nome:"), arrumar);

        arrumar.gridx = 1;
        campoNome = new JTextField(15);
        campoNome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelCadastro.add(campoNome, arrumar);

        // CÓDIGO
        arrumar.gridy++;
        arrumar.gridx = 0;
        arrumar.gridwidth = 1;
        painelCadastro.add(new JLabel("Código:"), arrumar);

        arrumar.gridx = 1;
        campoCodigo = new JTextField(15);
        campoCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campoCodigo.setEnabled(false);
        painelCadastro.add(campoCodigo, arrumar);

        // Botão SALVAR
        arrumar.gridy++;
        arrumar.gridx = 0;
        arrumar.gridwidth = 2;
        botaoSalvar = new JButton("SALVAR");
        botaoSalvar.setBackground(new Color(51, 102, 204));
        botaoSalvar.setForeground(Color.WHITE);
        botaoSalvar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botaoSalvar.setFocusPainted(false);
        // botaoSalvar.setPreferredSize(new Dimension(150, 35));
        painelCadastro.add(botaoSalvar, arrumar);
    

        // BOTÃO LIMPAR
        arrumar.gridx = 0;
        arrumar.gridy++;
        botaoLimpar = new JButton("LIMPAR");
        botaoLimpar.setBackground(new Color(255, 193, 7)); // amarelo de alerta
        botaoLimpar.setForeground(Color.BLACK);
        botaoLimpar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botaoLimpar.setFocusPainted(false);
        botaoLimpar.setPreferredSize(new Dimension(150, 35));

        // BOTÃO PESQUISAR
        arrumar.gridy++;
        arrumar.gridx = 0;
        arrumar.gridwidth = 1;
        botaoPesquisar = new JButton("PESQUISAR");
        botaoPesquisar.setBackground(new Color(51, 102, 204));
        botaoPesquisar.setForeground(Color.WHITE);
        botaoPesquisar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botaoPesquisar.setFocusPainted(false);
        botaoPesquisar.setPreferredSize(new Dimension(150, 35));

        janelaCadastrarDepartamento.add(painelBotoes);
        janelaCadastrarDepartamento.add(painelCadastro);

        janelaCadastrarDepartamento.setLocationRelativeTo(null);
        janelaCadastrarDepartamento.setVisible(true);

        // EVENTOS
        // Substitua o código existente dentro do addActionListener() do botão salvar
        botaoSalvar.addActionListener(e -> {
            if (validarCampos()) {
                // Cria uma instância do seu modelo com os dados da tela
                Departamento novoDepartamento = new Departamento(
                        campoNome.getText().trim()
                        // campoCodigo.getText().trim()
                        );

                // Cria uma instância da classe DAO para salvar no banco de dados
                DepartamentoDAO departamentoDAO = new DepartamentoDAO();

                // Tenta salvar no banco de dados
                if (departamentoDAO.salvar(novoDepartamento)) {
                    JOptionPane.showMessageDialog(janelaCadastrarDepartamento, "Departamento salvo com sucesso!");
                    limparCampos(); // Limpa os campos após o sucesso
                } else {
                    JOptionPane.showMessageDialog(janelaCadastrarDepartamento,
                            "Erro ao salvar departamento no banco de dados.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botaoLimpar.addActionListener(e -> {
            limparCampos();
        });

        botaoColaborador.addActionListener(e -> {
            new TelaCadastrarColaboradorRH();
            janelaCadastrarDepartamento.dispose();
        });

        botaoPremio.addActionListener(e -> {
            new TelaCadastrarPremio();
            janelaCadastrarDepartamento.dispose();
        });

        botaoHistorico.addActionListener(e -> {
            new TelaHistoricoMetas();
            janelaCadastrarDepartamento.dispose();
        });

        botaoHistoricoColaboradores.addActionListener(e ->{
            new TelaHistoricoColaboradores();
            janelaCadastrarDepartamento.dispose();
          });

    }

    public static void main(String[] args) {
        new TelaCadastrarDepartamento();

    }
}

