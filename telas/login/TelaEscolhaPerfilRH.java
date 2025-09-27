package login;
import javax.swing.*;
import rh.TelaPrincipalRH;
import colaborador.TelaPrincipalColaborador;
import modelos.Colaborador;
import java.awt.*;

public class TelaEscolhaPerfilRH extends JFrame {

    public TelaEscolhaPerfilRH(Colaborador colaborador) {
        setTitle("Escolha de Perfil de Acesso");
        setSize(450, 300); // Aumento do tamanho para padronizar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Color.WHITE); // Fundo branco
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Color azulPrimario = new Color(51, 102, 204);
        Font fonteBotao = new Font("Segoe UI", Font.BOLD, 14);
        Dimension tamanhoBotao = new Dimension(300, 40);

        JLabel labelTitulo = new JLabel("Como você deseja acessar o sistema?");
        labelTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(labelTitulo, gbc);

        // PADRONIZAÇÃO DE BOTÕES
        JButton botaoRH = new JButton("Entrar como Administrador de RH");
        botaoRH.setBackground(azulPrimario);
        botaoRH.setForeground(Color.WHITE);
        botaoRH.setFont(fonteBotao);
        botaoRH.setPreferredSize(tamanhoBotao);
        painel.add(botaoRH, gbc);

        JButton botaoColaborador = new JButton("Entrar como Colaborador (Ver minhas metas)");
        botaoColaborador.setBackground(azulPrimario);
        botaoColaborador.setForeground(Color.WHITE);
        botaoColaborador.setFont(fonteBotao);
        botaoColaborador.setPreferredSize(tamanhoBotao);
        painel.add(botaoColaborador, gbc);

        add(painel);

        // Ação do botão para entrar como RH
        botaoRH.addActionListener(e -> {
            new TelaPrincipalRH(colaborador);
            dispose(); // Fecha a tela de escolha
        });

        // Ação do botão para entrar como Colaborador
        botaoColaborador.addActionListener(e -> {
            new TelaPrincipalColaborador(colaborador);
            dispose(); // Fecha a tela de escolha
        });

        pack(); // Ajusta o tamanho da janela ao conteúdo
    }
}