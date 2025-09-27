package supervisor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaSelecionarColaboradores extends JDialog {
    // A classe estende JDialog (janela modal que depende de um parent). Modal significa que bloqueia interação com a janela pai até ser fechada.

    private JCheckBox[] checkboxes;
    private JButton botaoConfirmar;
    private ArrayList<String> selecionados = new ArrayList<>();

    public TelaSelecionarColaboradores(JFrame parent, String[] colaboradores) {
        super(parent, "Selecionar Colaboradores", true);
        // super(parent, "Título", true) — chama construtor do JDialog com parent e define modal = true.
        setSize(300, 400);
        setLayout(new BorderLayout());

        // Painel com checkboxes
        JPanel painelCheckboxes = new JPanel(new GridLayout(0, 1));
        checkboxes = new JCheckBox[colaboradores.length];
        for (int i = 0; i < colaboradores.length; i++) {
            checkboxes[i] = new JCheckBox(colaboradores[i]);
            painelCheckboxes.add(checkboxes[i]);
        }
        // Para cada colaborador cria um JCheckBox com o nome e adiciona ao painel.

        JScrollPane scroll = new JScrollPane(painelCheckboxes);
        add(scroll, BorderLayout.CENTER);

        // Botão Confirmar
        botaoConfirmar = new JButton("Confirmar");
        botaoConfirmar.addActionListener(e -> {
            selecionados.clear();
            for (JCheckBox cb : checkboxes) {
                if (cb.isSelected()) {
                    selecionados.add(cb.getText());
                }
            }
            dispose(); // fecha a janela
        });
        add(botaoConfirmar, BorderLayout.SOUTH);

        setLocationRelativeTo(parent);
        setVisible(true);
    }

    public ArrayList<String> getSelecionados() {
        return selecionados;
    }
}
