package modelos;

import java.time.LocalDate;
import java.util.ArrayList;

public class Meta {
    private int id;
    private String nome;
    private String descricao;
    private LocalDate prazoFinal;
    private String status;
    private ArrayList<String> colaboradores;
    private ArrayList<String> objetivos; // Adicionado para armazenar os objetivos

    // Construtor para criar uma nova meta
    public Meta(String nome, String descricao, LocalDate prazoFinal, String status, ArrayList<String> colaboradores) {
        this.nome = nome;
        this.descricao = descricao;
        this.prazoFinal = prazoFinal;
        this.status = status;
        this.colaboradores = colaboradores;
        this.objetivos = new ArrayList<>(); // Inicializa a lista
    }

    // Construtor para carregar uma meta que já existe no banco
    public Meta(int id, String nome, String descricao, LocalDate prazoFinal, String status,
            ArrayList<String> colaboradores, ArrayList<String> objetivos) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.prazoFinal = prazoFinal;
        this.status = status;
        this.colaboradores = colaboradores;
        this.objetivos = objetivos;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getPrazoFinal() {
        return prazoFinal;
    }

    public void setPrazoFinal(LocalDate prazoFinal) {
        this.prazoFinal = prazoFinal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(ArrayList<String> colaboradores) {
        this.colaboradores = colaboradores;
    }

    // Getter e Setter para a nova lista de objetivos
    public ArrayList<String> getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(ArrayList<String> objetivos) {
        this.objetivos = objetivos;
    }
}