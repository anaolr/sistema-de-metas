package login;

import javax.swing.*;
import colaborador.TelaPrincipalColaborador;
import conexao.Conexao;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelos.Colaborador;
import dao.ColaboradorDAO;

public class TelaLogin {
    private JFrame janelaLogin;
    private JTextField campologin;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;

    public boolean validarCampos() {
        if (campologin.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(janelaLogin, "Campo matrícula é obrigatório");
            campologin.requestFocus();
            return false;
        }

        if (campoSenha.getPassword().length == 0) {
            JOptionPane.showMessageDialog(janelaLogin, "Campo senha é obrigatório");
            campoSenha.requestFocus();
            return false;
        }
        return true;
    }

    public TelaLogin() {
        // Janela principal
        janelaLogin = new JFrame("Sistema de Metas - Login");
        janelaLogin.setSize(650, 350);
        janelaLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaLogin.setLayout(new GridLayout(1, 2)); // 2 colunas (lado a lado)
        janelaLogin.setResizable(false);

        // Painel do logo (lado esquerdo)
        JPanel painelLogo = new JPanel(new BorderLayout());
        painelLogo.setBackground(new Color(51, 102, 204)); // fundo azul corporativo

        JLabel logoLabel = new JLabel();
        ImageIcon icon = new ImageIcon("imagens/alvo.png"); // coloque seu logo na pasta do projeto
        Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(img));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        painelLogo.add(logoLabel, BorderLayout.CENTER);

        // Painel do login (lado direito)
        JPanel painelLogin = new JPanel(new GridBagLayout());
        painelLogin.setBackground(Color.WHITE);
        painelLogin.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints arrumar = new GridBagConstraints();
        arrumar.insets = new Insets(10, 10, 10, 10);
        arrumar.fill = GridBagConstraints.HORIZONTAL;
        arrumar.gridx = 0;
        arrumar.gridy = 0;
        arrumar.gridwidth = 2;

        // Título
        JLabel tituloLogin = new JLabel("Acesso ao Sistema", SwingConstants.CENTER);
        tituloLogin.setFont(new Font("Segoe UI", Font.BOLD, 20));
        painelLogin.add(tituloLogin, arrumar);

        // Matrícula
        arrumar.gridy++;
        arrumar.gridwidth = 1;
        painelLogin.add(new JLabel("Login:"), arrumar);

        arrumar.gridx = 1;
        campologin = new JTextField(15);
        campologin.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelLogin.add(campologin, arrumar);

        // Senha
        arrumar.gridy++;
        arrumar.gridx = 0;
        painelLogin.add(new JLabel("Senha:"), arrumar);

        arrumar.gridx = 1;
        campoSenha = new JPasswordField(15);
        campoSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        painelLogin.add(campoSenha, arrumar);

        // Botão Login
        arrumar.gridy++;
        arrumar.gridx = 0;
        arrumar.gridwidth = 2;
        botaoEntrar = new JButton("Entrar");
        botaoEntrar.setBackground(new Color(51, 102, 204));
        botaoEntrar.setForeground(Color.WHITE);
        botaoEntrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botaoEntrar.setFocusPainted(false);
        botaoEntrar.setPreferredSize(new Dimension(150, 35));
        painelLogin.add(botaoEntrar, arrumar);

        // Listener do botão
        // Substitua o código existente dentro do addActionListener() do botão Entrar
        // Substitua a lógica dentro do addActionListener() do botão Entrar
        botaoEntrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) { // Executa a validação básica de campos

                    String login = campologin.getText().trim();
                    String senha = new String(campoSenha.getPassword());

                    // Cria uma instância da classe DAO para autenticar o usuário
                    ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
                    Colaborador colaborador = colaboradorDAO.autenticar(login, senha);

                    if (colaborador != null) {

                        String loginLower = login.toLowerCase();
                        String departamento = colaborador.getDepartamento();

                        boolean statusColaborador = colaborador.getStatus();
                        // Redireciona para a tela correta com base no cargo do colaborador
                        if (statusColaborador == false) {
                            JOptionPane.showMessageDialog(janelaLogin, "Usuário inválido!", "Login",
                                    JOptionPane.ERROR_MESSAGE);

                        } else if (loginLower.startsWith("sup") && departamento != null
                                && departamento.equalsIgnoreCase("RH")) {
                            JOptionPane.showMessageDialog(janelaLogin, "Login bem-sucedido!");
                            new TelaEscolhaPerfil(colaborador).setVisible(true);
                            janelaLogin.dispose();
                        } else if (login.startsWith("rh")) {
                            JOptionPane.showMessageDialog(janelaLogin, "Login bem-sucedido!");
                            new TelaEscolhaPerfilRH(colaborador).setVisible(true);// Passe o objeto do colaborador
                            janelaLogin.dispose();
                        } else if (login.startsWith("sup")) {
                            JOptionPane.showMessageDialog(janelaLogin, "Login bem-sucedido!");
                            new TelaEscolhaPerfilSUP(colaborador).setVisible(true); // Passe o objeto do colaborador
                            janelaLogin.dispose();
                        } else {
                            JOptionPane.showMessageDialog(janelaLogin, "Login bem-sucedido!");
                            new TelaPrincipalColaborador(colaborador); // Passe o objeto do colaborador
                            janelaLogin.dispose();

                        }

                    } else {
                        // Autenticação falhou
                        JOptionPane.showMessageDialog(janelaLogin, "Matrícula ou senha inválida!");
                    }
                }
            }
        });

        // Adiciona os dois painéis lado a lado
        janelaLogin.add(painelLogo);
        janelaLogin.add(painelLogin);

        janelaLogin.setLocationRelativeTo(null);
        janelaLogin.setVisible(true);
    }

    public static void main(String[] args) {
        Conexao.criarTabelas();
        new TelaLogin();
    }
}
