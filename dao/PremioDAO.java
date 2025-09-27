package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelos.Premio;
import conexao.Conexao;

public class PremioDAO {

    public boolean salvar(Premio premio) {
        String sql = "INSERT INTO premios (nome, descricao, caminho_imagem, id_colaborador) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexao.conectar();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, premio.getNome());
            stmt.setString(2, premio.getDescricao());
            stmt.setString(3, premio.getCaminhoImagem());
            stmt.setInt(4, premio.getIdColaborador());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar prêmio: " + e.getMessage());
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

    // Adicione este método à classe PremioDAO.java

    public List<Premio> buscarPremiosPorColaborador(int idColaborador) {
        List<Premio> listaPremios = new ArrayList<>();
        // SQL Alterado para filtrar pelo id_colaborador
        String sql = "SELECT * FROM premios WHERE id_colaborador = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            stmt = conn.prepareStatement(sql);
            // NOVO: Define o filtro para o ID do colaborador
            stmt.setInt(1, idColaborador);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Premio premio = new Premio(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("caminho_imagem"),
                        // NOVO: Pega o id_colaborador do banco
                        rs.getInt("id_colaborador"));
                listaPremios.add(premio);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar prêmios: " + e.getMessage());
        }
        return listaPremios;
    }
}