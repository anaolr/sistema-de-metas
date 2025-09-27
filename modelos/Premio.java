package modelos;

public class Premio {
    private int id;
    private String nome;
    private String descricao;
    private String caminhoImagem; // O caminho do arquivo da imagem ou ícone
    private int idColaborador;

    // Construtor para criar um novo prêmio
    public Premio(String nome, String descricao, String caminhoImagem, int idColaborador) {
        this.nome = nome;
        this.descricao = descricao;
        this.caminhoImagem = caminhoImagem;
        this.idColaborador = idColaborador;
    }

    // Construtor para carregar um prêmio que já existe no banco
    public Premio(int id, String nome, String descricao, String caminhoImagem, int idColaborador) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.caminhoImagem = caminhoImagem;
        this.idColaborador = idColaborador;
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

    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

}