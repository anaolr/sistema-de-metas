package dao; // Define que a classe pertence ao pacote 'dao'.

import java.sql.Connection; // Gerencia a conexão com o banco.
import java.sql.PreparedStatement; // Prepara os comandos SQL para evitar injeção de SQL.
import java.sql.SQLException; // Trata erros.
import conexao.Conexao; // Importa nossa classe de conexão.
import modelos.Departamento; // Importa nosso modelo.
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DepartamentoDAO {

    // Método para salvar um departamento no banco de dados.
    public boolean salvar(Departamento departamento) {
        String sql = "INSERT INTO departamentos VALUES (null, ?)"; // Comando SQL. As interrogações (?) são
                                                                               // placeholders.
        Connection conn = null; // Variável da conexão.
        PreparedStatement stmt = null; // Variável para o comando preparado.

        try { // Início do bloco 'try' para executar as operações.
            conn = Conexao.conectar(); // Chama nosso método 'conectar()' para abrir a conexão.

            stmt = conn.prepareStatement(sql); // Prepara o comando SQL.
            stmt.setString(1, departamento.getNome()); // Preenche o primeiro '?' com o nome do departamento.
            // stmt.setString(2, departamento.getCodigo()); // Preenche o segundo '?' com o código.

            stmt.executeUpdate(); // Executa o comando para inserir o registro no banco.

            return true; // Retorna 'true' se deu certo.
        } catch (SQLException e) { // Captura erros de SQL.
            System.err.println("Erro ao salvar departamento: " + e.getMessage());
            return false; // Retorna 'false' se houver erro.
        } finally { // Bloco que SEMPRE será executado.
            // Fecha a conexão e o statement, liberando os recursos do banco.
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

    // O método retorna um ArrayList de objetos do tipo Departamento.
    // A lista é a forma mais comum de armazenar vários objetos em Java.
    public ArrayList<Departamento> buscarTodos() {
        // Cria uma nova lista vazia para armazenar os departamentos que serão lidos do
        // banco.
        ArrayList<Departamento> listaDepartamentos = new ArrayList<>();

        // Define o comando SQL para selecionar todos os registros da tabela
        // 'departamentos'.
        // O '*' (asterisco) significa "todas as colunas".
        String sql = "SELECT * FROM departamentos";

        // Bloco try-with-resources: garante que a conexão, o statement e o resultset
        // serão fechados automaticamente, mesmo se ocorrer um erro. Isso é uma boa
        // prática!
        try (Connection conn = Conexao.conectar(); // Chama a classe Conexao para obter a conexão com o banco.
                Statement stmt = conn.createStatement(); // Cria um objeto Statement para executar comandos SQL.
                ResultSet rs = stmt.executeQuery(sql)) { // Executa a consulta SQL e armazena o resultado em um
                                                         // ResultSet.

            // Loop que itera sobre cada linha de resultado retornada pelo banco.
            // O método 'rs.next()' move o cursor para a próxima linha e retorna 'true' se
            // houver uma.
            while (rs.next()) {
                // Cria um novo objeto Departamento a cada iteração (ou seja, para cada linha da
                // tabela).
                // Os dados são lidos do ResultSet usando os métodos 'getInt' e 'getString'.
                // O nome da coluna no banco ('id', 'nome', 'codigo') é usado para obter o valor
                // correto.
                Departamento departamento = new Departamento(
                        rs.getInt("id"),
                        rs.getString("nome")
                        );

                // Adiciona o objeto Departamento recém-criado à nossa lista.
                listaDepartamentos.add(departamento);
            }
        } catch (SQLException e) { // Captura qualquer erro que ocorra durante a comunicação com o banco.
            // Imprime o erro no console para fins de depuração.
            System.err.println("Erro ao buscar departamentos: " + e.getMessage());
        }

        // Retorna a lista completa com todos os departamentos encontrados no banco.
        return listaDepartamentos;
    }

}