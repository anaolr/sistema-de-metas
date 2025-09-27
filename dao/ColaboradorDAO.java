package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import conexao.Conexao;
import modelos.Colaborador;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ColaboradorDAO {

    public boolean salvar(Colaborador colaborador) {
        String sql = "INSERT INTO colaboradores (nome, login, cargo, senha, status, departamento) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, colaborador.getNome());
            stmt.setString(2, colaborador.getlogin());
            stmt.setString(3, colaborador.getCargo());
            stmt.setString(4, colaborador.getSenha());
            stmt.setBoolean(5, colaborador.getStatus());
            stmt.setString(6, colaborador.getDepartamento());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar colaborador: " + e.getMessage());
            return false;
        }
    }

    public Colaborador buscarPorLogin(String login) {
        String sql = "SELECT * FROM colaboradores WHERE login = ?";
        Colaborador colaborador = null;
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    colaborador = new Colaborador(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("login"),
                            rs.getString("cargo"),
                            rs.getString("senha"),
                            rs.getString("departamento"),
                            rs.getBoolean("status"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar colaborador por login: " + e.getMessage());
        }
        return colaborador;
    }

    public boolean alterar(Colaborador colaborador) {
        String sql = "UPDATE colaboradores SET nome = ?, cargo = ?, senha = ?, status = ?, departamento = ? WHERE login = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, colaborador.getNome());
            stmt.setString(2, colaborador.getCargo());
            stmt.setString(3, colaborador.getSenha());
            stmt.setBoolean(4, colaborador.getStatus());
            stmt.setString(5, colaborador.getDepartamento());
            stmt.setString(6, colaborador.getlogin());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao alterar colaborador: " + e.getMessage());
            return false;
        }
    }

    public boolean desativar(String login) {
        String sql = "UPDATE colaboradores SET status = false WHERE login = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao desativar colaborador: " + e.getMessage());
            return false;
        }
    }

    public boolean reativar(String login) {
        String sql = "UPDATE colaboradores SET status = true WHERE login = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao reativar colaborador: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Colaborador> buscarAtivosPorDepartamento(String departamento) {
        ArrayList<Colaborador> listaColaboradores = new ArrayList<>();
        String sql = "SELECT * FROM colaboradores WHERE departamento = ? AND status = true";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, departamento);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Colaborador colaborador = new Colaborador(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("login"),
                            rs.getString("cargo"),
                            rs.getString("senha"),
                            rs.getString("departamento"),
                            rs.getBoolean("status")
                    );
                    listaColaboradores.add(colaborador);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar colaboradores por departamento: " + e.getMessage());
        }
        return listaColaboradores;
    }

    public ArrayList<Colaborador> buscarTodos() {
        ArrayList<Colaborador> listaColaboradores = new ArrayList<>();
        String sql = "SELECT * FROM colaboradores";
        try (Connection conn = Conexao.conectar();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Colaborador colaborador = new Colaborador(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("login"),
                        rs.getString("cargo"),
                        rs.getString("senha"),
                        rs.getString("departamento"),
                        rs.getBoolean("status")
                );
                listaColaboradores.add(colaborador);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar colaboradores: " + e.getMessage());
        }
        return listaColaboradores;
    }

    public Colaborador autenticar(String login, String senha) {
        String sql = "SELECT * FROM colaboradores WHERE login = ? AND senha = ?";
        Colaborador colaborador = null;
        try (Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, senha);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    colaborador = new Colaborador(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("login"),
                            rs.getString("cargo"),
                            rs.getString("senha"),
                            rs.getString("departamento"),
                            rs.getBoolean("status"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro durante a autenticação: " + e.getMessage());
        }
        return colaborador;
    }

    public ArrayList<Colaborador> buscarTodosRH() {
        ArrayList<Colaborador> listaColaboradores = new ArrayList<>();
        String sql = "SELECT id, nome, login, cargo, departamento FROM colaboradores";
        try (Connection conn = Conexao.conectar(); 
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Colaborador colaborador = new Colaborador(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("login"),
                        rs.getString("cargo"),
                        rs.getString("departamento")
                );
                listaColaboradores.add(colaborador);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar colaboradores: " + e.getMessage());
            e.printStackTrace();
        }
        return listaColaboradores;
    }
}