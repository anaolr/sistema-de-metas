package colaborador;

import javax.swing.*;
import login.TelaLogin;
import java.awt.*;
import java.util.ArrayList;
import dao.MetaDAO;
import modelos.Meta;
import modelos.Colaborador;
import java.time.format.DateTimeFormatter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaMinhasMetas {

    private JFrame janelaMinhasMetas;
    private JButton botaoMinhasMetas, botaoRecompensas, botaoHistorico, botaoSair;
    private JPanel painelMetas;
    private Colaborador colaboradorLogado;

    private void popularMetas() {
        MetaDAO dao = new MetaDAO();
        ArrayList<Meta> metas = dao.buscarPorColaborador(colaboradorLogado.getNome());
        painelMetas.removeAll();

        for (Meta m : metas) {
            adicionarMeta(m);
        }
        painelMetas.revalidate();
        painelMetas.repaint();
    }

    public TelaMinhasMetas(Colaborador colaborador) {
        this.colaboradorLogado = colaborador;
        janelaMinhasMetas = new JFrame("Sistema de Metas - Colaborador");
        janelaMinhasMetas.setSize(900, 500);
        janelaMinhasMetas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaMinhasMetas.setLayout(new GridLayout(1, 2));
        janelaMinhasMetas.setResizable(false);

        JPanel painelBotoes = new JPanel(new GridBagLayout());
        painelBotoes.setBackground(new Color(51, 102, 204));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints arrumar = new GridBagConstraints();
        arrumar.insets = new Insets(10, 10, 10, 10);
        arrumar.fill = GridBagConstraints.HORIZONTAL;
        arrumar.gridx = 0;
        arrumar.gridy = 0;
        arrumar.gridwidth = 2;

        JLabel rotuloColaborador = new JLabel("COLABORADOR", SwingConstants.CENTER);
        rotuloColaborador.setFont(new Font("Segoe UI", Font.BOLD, 20));
        rotuloColaborador.setForeground(Color.WHITE);
        painelBotoes.add(rotuloColaborador, arrumar);

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

        botaoMinhasMetas = new JButton("Minhas Metas");
        botaoMinhasMetas.setBackground(new Color(255, 140, 0));
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
        botaoHistorico.setBackground(botaoEscuro);
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

        botaoSair.addActionListener(e -> {
            new TelaLogin();
            janelaMinhasMetas.dispose();
        });

        botaoRecompensas.addActionListener(e -> {
            new TelaRecompensasColaborador(this.colaboradorLogado);
            janelaMinhasMetas.dispose();
        });

        botaoHistorico.addActionListener(e -> {
            new TelaHistorico(this.colaboradorLogado);
            janelaMinhasMetas.dispose();
        });

        painelMetas = new JPanel();
        painelMetas.setLayout(new BoxLayout(painelMetas, BoxLayout.Y_AXIS));
        painelMetas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        popularMetas();
        JScrollPane scrollPane = new JScrollPane(painelMetas);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        janelaMinhasMetas.add(painelBotoes);
        janelaMinhasMetas.add(scrollPane);

        janelaMinhasMetas.setLocationRelativeTo(null);
        janelaMinhasMetas.setVisible(true);
    }

    private void adicionarMeta(Meta meta) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        card.setBackground(new Color(245, 245, 245));

        JPanel painelTopo = new JPanel(new GridLayout(2, 1));
        painelTopo.setBackground(new Color(245, 245, 245));
        painelTopo.add(new JLabel(meta.getNome()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String prazoFormatado = meta.getPrazoFinal().format(formatter);
        painelTopo.add(new JLabel("Prazo: " + prazoFormatado));

        // Calcula o progresso com base nos objetivos marcados
        int concluidos = 0;
        if (meta.getObjetivos() != null && !meta.getObjetivos().isEmpty()) {
            for (String objetivo : meta.getObjetivos()) {
                if (objetivo.endsWith(" [concluido]")) {
                    concluidos++;
                }
            }
        }
        int progressoCalculado = (meta.getObjetivos() != null && !meta.getObjetivos().isEmpty())
                ? (int) ((concluidos / (double) meta.getObjetivos().size()) * 100)
                : 0;

        JProgressBar progresso = new JProgressBar(0, 100);
        progresso.setValue(progressoCalculado);
        progresso.setStringPainted(true);

        JButton botaoDetalhes = new JButton("Ver Detalhes");
        botaoDetalhes.addActionListener(e -> {
            TelaDetalhesMeta detalhesMeta = new TelaDetalhesMeta(meta, progresso);
            detalhesMeta.setVisible(true);

            detalhesMeta.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    popularMetas();
                }
            });
        });

        card.add(painelTopo, BorderLayout.NORTH);
        card.add(progresso, BorderLayout.CENTER);
        card.add(botaoDetalhes, BorderLayout.SOUTH);

        painelMetas.add(card);
        painelMetas.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    class TelaDetalhesMeta extends JFrame {
        private ArrayList<JCheckBox> checkboxes;
        private JProgressBar progresso;
        private JPanel painelObjetivos;
        private Meta meta;
        private MetaDAO dao = new MetaDAO();

        public TelaDetalhesMeta(Meta meta, JProgressBar barraProgressoPrincipal) {
            this.meta = meta;
            setTitle("Detalhes da Meta");
            setSize(500, 400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            checkboxes = new ArrayList<>();

            JPanel painel = new JPanel();
            painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

            painel.add(new JLabel("Meta: " + meta.getNome()));
            painel.add(new JLabel("Descrição: " + meta.getDescricao()));

            painelObjetivos = new JPanel();
            painelObjetivos.setLayout(new BoxLayout(painelObjetivos, BoxLayout.Y_AXIS));
            painelObjetivos.setBorder(BorderFactory.createTitledBorder("Objetivos"));

            if (meta.getObjetivos() != null) {
                for (String objetivoTexto : meta.getObjetivos()) {
                    boolean isConcluido = objetivoTexto.endsWith(" [concluido]");
                    String textoReal = isConcluido ? objetivoTexto.replace(" [concluido]", "") : objetivoTexto;
                    adicionarObjetivo(textoReal, isConcluido, barraProgressoPrincipal);
                }
            }

            JScrollPane scrollPaneObjetivos = new JScrollPane(painelObjetivos);
            scrollPaneObjetivos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            painel.add(scrollPaneObjetivos);

            JPanel painelAdicionar = new JPanel();
            JTextField campoNovoObjetivo = new JTextField(15);
            JButton botaoAdicionar = new JButton("Adicionar");
            painelAdicionar.add(campoNovoObjetivo);
            painelAdicionar.add(botaoAdicionar);
            painel.add(painelAdicionar);

            botaoAdicionar.addActionListener(e -> {
                String texto = campoNovoObjetivo.getText().trim();
                if (!texto.isEmpty()) {
                    // Adiciona o objetivo à lista em memória e ao UI
                    adicionarObjetivo(texto, false, barraProgressoPrincipal);
                    campoNovoObjetivo.setText("");

                    // Sincroniza a lista de objetivos do objeto meta e salva no banco
                    meta.getObjetivos().add(texto);
                    dao.atualizarObjetivos(meta.getId(), meta.getObjetivos());

                    atualizarProgresso(barraProgressoPrincipal);
                }
            });

            progresso = new JProgressBar(0, 100);
            progresso.setStringPainted(true);
            painel.add(new JLabel("Progresso:"));
            painel.add(progresso);

            JButton botaoConcluir = new JButton("Concluir Meta");
            botaoConcluir.addActionListener(e -> {
                if (!checkboxes.isEmpty() && progresso.getValue() == 100) {
                    if (dao.atualizarStatus(meta.getId(), "Análise")) {
                        JOptionPane.showMessageDialog(this, "🎉 Meta concluída e enviada para análise do supervisor!");
                        barraProgressoPrincipal.setValue(100);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao atualizar o status da meta.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Ainda há objetivos pendentes ou nenhum objetivo criado!");
                }
            });
            painel.add(botaoConcluir);

            add(painel, BorderLayout.CENTER);
            atualizarProgresso(barraProgressoPrincipal);
        }

        private void adicionarObjetivo(String texto, boolean isConcluido, JProgressBar barraProgressoPrincipal) {
            JPanel linhaObjetivo = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JCheckBox check = new JCheckBox(texto);
            check.setSelected(isConcluido);
            checkboxes.add(check);

            JButton botaoRemover = new JButton("Remover");
            botaoRemover.setMargin(new Insets(2, 6, 2, 6));

            linhaObjetivo.add(check);
            linhaObjetivo.add(botaoRemover);
            painelObjetivos.add(linhaObjetivo);

            check.addActionListener(e -> {
                atualizarProgresso(barraProgressoPrincipal);
            });

            botaoRemover.addActionListener(e -> {
                checkboxes.remove(check);
                painelObjetivos.remove(linhaObjetivo);

                ArrayList<String> objetivosAtualizados = new ArrayList<>();
                for (JCheckBox cb : checkboxes) {
                    objetivosAtualizados.add(cb.getText());
                }
                meta.setObjetivos(objetivosAtualizados);

                atualizarProgresso(barraProgressoPrincipal);

                painelObjetivos.revalidate();
                painelObjetivos.repaint();
            });

            painelObjetivos.revalidate();
            painelObjetivos.repaint();
        }

        private void atualizarProgresso(JProgressBar barraProgressoPrincipal) {
            int concluidos = 0;
            ArrayList<String> objetivosParaSalvar = new ArrayList<>();

            for (JCheckBox check : checkboxes) {
                if (check.isSelected()) {
                    concluidos++;
                    objetivosParaSalvar.add(check.getText() + " [concluido]");
                } else {
                    objetivosParaSalvar.add(check.getText());
                }
            }

            int percentual = (checkboxes.isEmpty()) ? 0 : (int) ((concluidos / (double) checkboxes.size()) * 100);
            progresso.setValue(percentual);
            barraProgressoPrincipal.setValue(percentual);

            dao.atualizarObjetivos(meta.getId(), objetivosParaSalvar);
        }
    }
}