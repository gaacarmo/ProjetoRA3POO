package projeto_marcel.pucpr.Model;

public class Usuario {
    private int id;
    private String nome;
    private String nomeUsuario;
    private String curso;
    private String email;
    private String senha;

    public Usuario(int id, String nome, String nomeUsuario, String curso, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.nomeUsuario = nomeUsuario;
        this.curso = curso;
        this.email = email;
        this.senha = senha;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}