package modelos; // Define que a classe pertence ao pacote 'modelos'.

public class Departamento {
    private int id; // Atributo que representa o ID no banco de dados.
    private String nome; // Atributo que representa o nome.
   // private String codigo; // Atributo que representa o código.

    // Construtor para criar um novo departamento.
    public Departamento(String nome) {
        this.nome = nome;
        // this.codigo = codigo;
    }

    // Construtor para carregar um departamento que já existe no banco.
    public Departamento(int id, String nome/*, String codigo*/) {
        this.id = id;
        this.nome = nome;
        /*this.codigo = codigo;*/
    }

    // Métodos 'get' para obter o valor de cada atributo.
    public int getId() {
        return id;
    }

    // Métodos 'set' para definir o valor de cada atributo.
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // public String getCodigo() {
    //     return codigo;
    // }

    // public void setCodigo(String codigo) {
    //     this.codigo = codigo;
    // }
}