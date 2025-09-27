package modelos;

import java.util.ArrayList;

public class Colaborador {
    private int id;
    private String nome;
    private String login;
    private String cargo;
    private String senha;
    private String departamento; // Vamos assumir que é o nome do departamento por enquanto
    private boolean status;


    private ArrayList<String> colaboradores;

    // Construtor para criar um novo colaborador
    public Colaborador(String nome, String login, String cargo, String senha,boolean status, String departamento) {
        this.nome = nome;
        this.login = login;
        this.cargo = cargo;
        this.senha = senha;
        this.status = status;
        this.departamento = departamento;
        
    }

    // Construtor para carregar um colaborador que já existe no banco
    public Colaborador(int id, String nome, String login, String cargo, String senha, String departamento, boolean status) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.cargo = cargo;
        this.senha = senha;
        this.departamento = departamento;
        this.status = status;
    }

    public Colaborador(int id, String nome, String login, String cargo, String departamento) {
        // ... atribuições ...
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getlogin() {
        return login;
    }

    public void setlogin(String login) {
        this.login = login;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

     public ArrayList<String> getColaboradores() {
        return colaboradores;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}