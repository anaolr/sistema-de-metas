package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane; // <<-- NOVA IMPORTAÇÃO NECESSÁRIA

public class Conexao {

    // URL para conexão com o banco 'sistema_metas' (Usado para criar as tabelas)
    // ATUALIZE ESTAS VARIÁVEIS NA VERSÃO FINAL!
    private static final String URL_DB = "jdbc:mysql://localhost:3306/sistema_metas";

    // URL para conexão APENAS com o servidor (Usado para criar o banco de dados)
    private static final String URL_SERVER = "jdbc:mysql://localhost:3306/";

    private static final String USUARIO = "root";
    private static final String SENHA = "123456"; // <<-- SENHA PADRÃO DE DESENVOLVIMENTO

    // NOVO MÉTODO: Conecta ao servidor e cria o banco 'sistema_metas' se ele não
    // existir
    public static boolean criarDatabase() {

        // <<-- CHECAGEM E ALERTA DE SEGURANÇA -->>
        // Se a senha ou a porta padrão (3303) for detectada, emita um aviso crítico.
        if (SENHA.equals("123456") || URL_DB.contains("3306")) {
            JOptionPane.showMessageDialog(null,
                    "ATENÇÃO: Você está usando as configurações de desenvolvimento (Porta 3306 / Senha 123456).\n" +
                            "POR FAVOR, FECHE O PROGRAMA e altere as variáveis USUARIO, SENHA e URL_DB/URL_SERVER em conexao/Conexao.java\n"
                            +
                            "para as credenciais de produção do cliente.",
                    "AVISO DE CONFIGURAÇÃO CRÍTICA!",
                    JOptionPane.WARNING_MESSAGE);
        }
        // <<-- FIM DO ALERTA -->>

        try (Connection conn = DriverManager.getConnection(URL_SERVER, USUARIO, SENHA);
                Statement stmt = conn.createStatement()) {

            // O comando que cria o banco de dados
            String sql = "CREATE DATABASE IF NOT EXISTS sistema_metas";
            stmt.execute(sql);
            System.out.println("Base de dados 'sistema_metas' verificada/criada com sucesso.");
            return true;

        } catch (SQLException e) {
            System.err.println(
                    "Erro ao criar o banco de dados 'sistema_metas'. Verifique se o MySQL/MariaDB está em execução: "
                            + e.getMessage());
            return false;
        }
    }

    public static Connection conectar() {
        // O método conectar agora tenta conectar à base de dados que acabamos de criar.
        Connection conn = null;
        try {
            // Usa a URL com o nome do DB para o restante das operações
            conn = DriverManager.getConnection(URL_DB, USUARIO, SENHA);
            System.out.println("Conexão com o banco de dados MySQL estabelecida.");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return conn;
    }

    public static void criarTabelas() {
        // PASSO CRÍTICO: Garantir que o banco de dados exista antes de criar as tabelas
        // dentro dele.
        if (!criarDatabase()) {
            System.err.println("Não foi possível continuar: Falha ao criar a base de dados.");
            return;
        }

        // --- 1. COMANDOS DE CRIAÇÃO DE ESTRUTURA (TABELAS) ---
        String sqlDepartamentos = "CREATE TABLE IF NOT EXISTS departamentos ("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "nome VARCHAR(255) NOT NULL UNIQUE"
                + ");";

        String sqlColaboradores = "CREATE TABLE IF NOT EXISTS colaboradores ("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "nome VARCHAR(255) NOT NULL,"
                + "login VARCHAR(255) NOT NULL UNIQUE,"
                + "cargo VARCHAR(255) NOT NULL,"
                + "senha VARCHAR(255) NOT NULL,"
                + "status BOOLEAN NOT NULL DEFAULT TRUE, "
                + "departamento VARCHAR(255) NOT NULL"
                + ");";

        String sqlMetas = "CREATE TABLE IF NOT EXISTS metas ("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "nome VARCHAR(255) NOT NULL,"
                + "descricao TEXT NOT NULL,"
                + "prazo_final DATE NOT NULL,"
                + "status VARCHAR(50) NOT NULL,"
                + "colaboradores TEXT NOT NULL,"
                + "objetivos TEXT"
                + ");";

        String sqlPremios = "CREATE TABLE IF NOT EXISTS premios ("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "nome VARCHAR(255) NOT NULL,"
                + "descricao TEXT NOT NULL,"
                + "id_colaborador INT NOT NULL,"
                + "caminho_imagem VARCHAR(255) NOT NULL"
                + ");";

        String sqlColaboradoresExcluidos = "CREATE TABLE IF NOT EXISTS colaboradores_excluidos ("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "nome VARCHAR(255) NOT NULL,"
                + "login VARCHAR(255) NOT NULL,"
                + "cargo VARCHAR(255) NOT NULL,"
                + "departamento VARCHAR(255) NOT NULL,"
                + "data_exclusao TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                + ");";

        String sqlTriggerDesativacao = "CREATE TRIGGER IF NOT EXISTS after_colaborador_deactivate "
                + "AFTER UPDATE ON colaboradores "
                + "FOR EACH ROW "
                + "BEGIN "
                + "IF OLD.status = TRUE AND NEW.status = FALSE THEN "
                + "INSERT INTO colaboradores_excluidos(nome, login, cargo, departamento) "
                + "VALUES(OLD.nome, OLD.login, OLD.cargo, OLD.departamento); "
                + "END IF; "
                + "END;";

        String sqlTriggerReativacao = "CREATE TRIGGER IF NOT EXISTS after_colaborador_reactivate "
                + "AFTER UPDATE ON colaboradores "
                + "FOR EACH ROW "
                + "BEGIN "
                + "IF OLD.status = FALSE AND NEW.status = TRUE THEN "
                + "DELETE FROM colaboradores_excluidos WHERE login = OLD.login; "
                + "END IF; "
                + "END;";

        String sqlDeptoRH = "INSERT IGNORE INTO departamentos VALUES(null, 'RH')";
        String sqlSuperUserRH = "INSERT IGNORE INTO colaboradores VALUES(null,'root','rh.root','rh','1234',true,'rh')";

        try (Connection conn = conectar(); Statement stmt = conn.createStatement()) {

            // Bloco 1: CRIAÇÃO DE TABELAS
            stmt.execute(sqlDepartamentos);
            System.out.println("Tabela 'departamentos' criada ou já existe.");

            stmt.execute(sqlColaboradores);
            System.out.println("Tabela 'colaboradores' criada ou já existe.");

            stmt.execute(sqlMetas);
            System.out.println("Tabela 'metas' criada ou já existe.");

            stmt.execute(sqlPremios);
            System.out.println("Tabela 'premios' criada ou já existe.");

            stmt.execute(sqlColaboradoresExcluidos);
            System.out.println("Tabela 'colaboradores_excluidos' criada ou já existe.");

            // Bloco 2: TRIGGERS
            stmt.execute("DROP TRIGGER IF EXISTS before_colaborador_delete");
            stmt.execute("DROP TRIGGER IF EXISTS after_colaborador_update");
            stmt.execute(sqlTriggerDesativacao);
            System.out.println("Trigger 'after_colaborador_deactivate' criado ou já existe.");

            stmt.execute(sqlTriggerReativacao);
            System.out.println("Trigger 'after_colaborador_reactivate' criado ou já existe.");

            // Bloco 3: INSERÇÃO INICIAL
            stmt.execute(sqlDeptoRH);
            System.out.println("DEPARTAMENTO RH CRIADO");

            stmt.execute(sqlSuperUserRH);
            System.out.println("USUARIO ROOT CRIADO");

        } catch (SQLException e) {
            System.err.println("Erro ao criar as tabelas: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        criarTabelas();
    }
}