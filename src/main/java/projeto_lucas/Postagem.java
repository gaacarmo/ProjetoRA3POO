package projeto_lucas;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Postagem implements Serializable {
    private int id;
    private String nome;
    private String descricao;
    private String local;
    private String cor;
    private String categoria;
    private LocalDate data;
    private LocalDateTime timestamp;
    private String imagem;
    private int tipoUsuario;
    private Usuario usuario;
    private Moderador moderador;

    public Postagem(int id, String nome, String descricao, String local, String cor, String categoria,
                    LocalDate data, LocalDateTime timestamp, String imagem, int tipoUsuario,
                    Usuario usuario, Moderador moderador) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.local = local;
        this.cor = cor;
        this.categoria = categoria;
        this.data = data;
        this.timestamp = timestamp;
        this.imagem = imagem;
        this.tipoUsuario = tipoUsuario;
        this.usuario = usuario;
        this.moderador = moderador;
    }

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

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Moderador getModerador() {
        return moderador;
    }

    public void setModerador(Moderador moderador) {
        this.moderador = moderador;
    }

    @Override
    public String toString() {
        return nome + " - " + descricao;
    }
}
