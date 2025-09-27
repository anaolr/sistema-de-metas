package login;

import javax.swing.*;
import rh.TelaPrincipalRH;
import supervisor.TelaPrincipalSupervisor;
import modelos.Colaborador;
import colaborador.TelaPrincipalColaborador;
import java.awt.*;

public class TelaEscolhaPerfil extends JFrame {

    public TelaEscolhaPerfil(Colaborador colaborador) {
        setTitle("Escolha de Perfil");
        setSize(450, 300); // Aumento do tamanho para melhor visualização
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        Dimension tamanhoBotao = new Dimension(280, 40);

        JLabel labelTitulo = new JLabel("Você tem múltiplos perfis. Qual deseja acessar?");
        labelTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(labelTitulo, gbc);

        // PADRONIZAÇÃO DE BOTÕES
        JButton botaoRH = new JButton("Acessar como RH");
        botaoRH.setBackground(azulPrimario);
        botaoRH.setForeground(Color.WHITE);
        botaoRH.setFont(fonteBotao);
        botaoRH.setPreferredSize(tamanhoBotao);
        painel.add(botaoRH, gbc);

        JButton botaoSupervisor = new JButton("Acessar como Supervisor");
        botaoSupervisor.setBackground(azulPrimario);
        botaoSupervisor.setForeground(Color.WHITE);
        botaoSupervisor.setFont(fonteBotao);
        botaoSupervisor.setPreferredSize(tamanhoBotao);
        painel.add(botaoSupervisor, gbc);

        JButton botaoColaborador = new JButton("Acessar como Colaborador");
        botaoColaborador.setBackground(azulPrimario);
        botaoColaborador.setForeground(Color.WHITE);
        botaoColaborador.setFont(fonteBotao);
        botaoColaborador.setPreferredSize(tamanhoBotao);
        painel.add(botaoColaborador, gbc);

        add(painel);

        // Ação do botão RH
        botaoRH.addActionListener(e -> {
            new TelaPrincipalRH(colaborador);
            dispose();
        });

        // Ação do botão Supervisor
        botaoSupervisor.addActionListener(e -> {
            new TelaPrincipalSupervisor(colaborador);
            dispose();
        });

        // Ação do novo botão Colaborador
        botaoColaborador.addActionListener(e -> {
            new TelaPrincipalColaborador(colaborador);
            dispose();
        });

        pack(); // Ajusta o tamanho da janela ao conteúdo
    }
}