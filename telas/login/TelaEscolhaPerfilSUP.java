package login;
import javax.swing.*;
import supervisor.TelaPrincipalSupervisor;
import modelos.Colaborador;
import colaborador.TelaPrincipalColaborador;
import java.awt.*;

public class TelaEscolhaPerfilSUP extends JFrame {

    public TelaEscolhaPerfilSUP(Colaborador colaborador) {
        setTitle("Escolha de Perfil");
        setSize(450, 300); // Padronizado para um tamanho melhor
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Color.WHITE); // Fundo branco padronizado
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Color azulPrimario = new Color(51, 102, 204);
        Font fonteBotao = new Font("Segoe UI", Font.BOLD, 14);
        Dimension tamanhoBotao = new Dimension(280, 40); // Padronização de dimensão

        JLabel labelTitulo = new JLabel("Você tem múltiplos perfis. Qual deseja acessar?");
        labelTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(labelTitulo, gbc);

        // PADRONIZAÇÃO DE BOTÕES

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

        // Ação do botão Supervisor
        botaoSupervisor.addActionListener(e -> {
            new TelaPrincipalSupervisor(colaborador);
            dispose(); // Fecha a tela de escolha
        });

        // Ação do novo botão Colaborador
        botaoColaborador.addActionListener(e -> {
            new TelaPrincipalColaborador(colaborador);
            dispose(); // Fecha a tela de escolha
        });

        // Ajusta o tamanho da janela ao conteúdo
        pack();
    }
}