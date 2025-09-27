package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import modelos.Meta;
import conexao.Conexao;
import java.time.LocalDate;

public class MetaDAO {

    public boolean salvar(Meta meta) {
        String sql = "INSERT INTO metas (nome, descricao, prazo_final, status, colaboradores, objetivos) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexao.conectar();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, meta.getNome());
            stmt.setString(2, meta.getDescricao());
            stmt.setDate(3, Date.valueOf(meta.getPrazoFinal()));
            stmt.setString(4, "Pendente");
            stmt.setString(5, String.join(", ", meta.getColaboradores()));
            stmt.setString(6, String.join(", ", meta.getObjetivos()));
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar meta: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

    public ArrayList<Meta> buscarTodos() {
        ArrayList<Meta> listaMetas = new ArrayList<>();
        String sql = "SELECT * FROM metas";

        try (Connection conn = Conexao.conectar();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String colaboradoresString = rs.getString("colaboradores");
                ArrayList<String> colaboradores = new ArrayList<>(Arrays.asList(colaboradoresString.split(", ")));

                LocalDate prazoFinal = rs.getDate("prazo_final").toLocalDate();

                String objetivosString = rs.getString("objetivos");
                ArrayList<String> objetivos = new ArrayList<>();
                if (objetivosString != null && !objetivosString.isEmpty()) {
                    objetivos = new ArrayList<>(Arrays.asList(objetivosString.split(", ")));
                }

                Meta meta = new Meta(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        prazoFinal,
                        rs.getString("status"),
                        colaboradores,
                        objetivos);

                listaMetas.add(meta);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar metas: " + e.getMessage());
        }
        return listaMetas;
    }

    // O método retorna um ArrayList de metas com status 'Pendente'.
    public ArrayList<Meta> buscarMetasParaConclusao() {
        ArrayList<Meta> listaMetas = new ArrayList<>();
        String sql = "SELECT * FROM metas WHERE status = 'Análise'";

        try (Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String colaboradoresString = rs.getString("colaboradores");
                ArrayList<String> colaboradores = new ArrayList<>(Arrays.asList(colaboradoresString.split(", ")));

                LocalDate prazoFinal = rs.getDate("prazo_final").toLocalDate();

                String objetivosString = rs.getString("objetivos");
                ArrayList<String> objetivos = new ArrayList<>();
                if (objetivosString != null && !objetivosString.isEmpty()) {
                    objetivos = new ArrayList<>(Arrays.asList(objetivosString.split(", ")));
                }

                Meta meta = new Meta(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        prazoFinal,
                        rs.getString("status"),
                        colaboradores,
                        objetivos);
                listaMetas.add(meta);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar metas para conclusão: " + e.getMessage());
        }
        return listaMetas;
    }

    // O método atualiza o status de uma meta com base no ID.
    public boolean atualizarStatus(int idMeta, String novoStatus) {
        String sql = "UPDATE metas SET status = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexao.conectar();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, novoStatus);
            stmt.setInt(2, idMeta);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o status da meta: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

    // O método retorna um ArrayList de metas de um colaborador específico.
    public ArrayList<Meta> buscarPorColaborador(String nomeColaborador) {
        ArrayList<Meta> listaMetas = new ArrayList<>();
        // **CORREÇÃO AQUI:** Adiciona a cláusula de filtro para o status
        String sql = "SELECT * FROM metas WHERE status = ? AND colaboradores LIKE ?";

        try (Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            // **CORREÇÃO AQUI:** Define o status "Pendente" para o primeiro parâmetro
            stmt.setString(1, "Pendente");
            // O segundo '?' recebe o nome do colaborador.
            stmt.setString(2, "%" + nomeColaborador + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String colaboradoresString = rs.getString("colaboradores");
                    ArrayList<String> colaboradores = new ArrayList<>(Arrays.asList(colaboradoresString.split(", ")));

                    LocalDate prazoFinal = rs.getDate("prazo_final").toLocalDate();

                    String objetivosString = rs.getString("objetivos");
                    ArrayList<String> objetivos = new ArrayList<>();
                    if (objetivosString != null && !objetivosString.isEmpty()) {
                        objetivos = new ArrayList<>(Arrays.asList(objetivosString.split(", ")));
                    }

                    Meta meta = new Meta(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("descricao"),
                            prazoFinal,
                            rs.getString("status"),
                            colaboradores,
                            objetivos);

                    listaMetas.add(meta);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar metas por colaborador: " + e.getMessage());
        }
        return listaMetas;
    }

    // Adicione o método para buscar por ID, necessário para a atualização da tela
    // principal
    public Meta buscarPorId(int id) {
        String sql = "SELECT * FROM metas WHERE id = ?";
        Meta meta = null;

        try (Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String colaboradoresString = rs.getString("colaboradores");
                    ArrayList<String> colaboradores = new ArrayList<>();
                    if (colaboradoresString != null && !colaboradoresString.isEmpty()) {
                        colaboradores.add(colaboradoresString);
                    }

                    String objetivosString = rs.getString("objetivos");
                    ArrayList<String> objetivos = new ArrayList<>();
                    if (objetivosString != null && !objetivosString.isEmpty()) {
                        objetivos = new ArrayList<>(Arrays.asList(objetivosString.split(", ")));
                    }

                    meta = new Meta(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("descricao"),
                            rs.getDate("prazo_final").toLocalDate(),
                            rs.getString("status"),
                            colaboradores,
                            objetivos);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar meta por ID: " + e.getMessage());
        }
        return meta;
    }

    // Adicione o método para atualizar a lista de objetivos no banco
    public boolean atualizarObjetivos(int idMeta, ArrayList<String> objetivos) {
        String sql = "UPDATE metas SET objetivos = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexao.conectar();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, String.join(", ", objetivos));
            stmt.setInt(2, idMeta);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar os objetivos da meta: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

    // O método retorna um ArrayList de metas concluídas de um colaborador
    // específico.
    public ArrayList<Meta> buscarConcluidasPorColaborador(String nomeColaborador) {
        ArrayList<Meta> listaMetas = new ArrayList<>();
        String sql = "SELECT * FROM metas WHERE status = 'Concluída' AND colaboradores LIKE ?";

        try (Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nomeColaborador + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String colaboradoresString = rs.getString("colaboradores");
                    ArrayList<String> colaboradores = new ArrayList<>(Arrays.asList(colaboradoresString.split(", ")));

                    LocalDate prazoFinal = rs.getDate("prazo_final").toLocalDate();

                    String objetivosString = rs.getString("objetivos");
                    ArrayList<String> objetivos = new ArrayList<>();
                    if (objetivosString != null && !objetivosString.isEmpty()) {
                        objetivos = new ArrayList<>(Arrays.asList(objetivosString.split(", ")));
                    }

                    Meta meta = new Meta(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("descricao"),
                            prazoFinal,
                            rs.getString("status"),
                            colaboradores,
                            objetivos);

                    listaMetas.add(meta);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar metas concluídas: " + e.getMessage());
        }
        return listaMetas;
    }

}