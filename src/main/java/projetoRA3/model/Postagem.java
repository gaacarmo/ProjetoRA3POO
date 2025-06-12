package projetoRA3.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Postagem implements Serializable {
    private int id;
    private String nome;
    private String descricao;
    private String local;
    private String cor;
    private String categoria;
    private LocalDate data;
    private long timestamp;
    private String imagem;
    private String tipoUsuario;
    private int idUsuario;
    private int idAdmin;

    public Postagem(int id, String nome, String descricao, String local, String cor, String categoria,
                    LocalDate data, long timestamp, String imagem, String tipoUsuario, int idUsuario, int idAdmin) {
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
        this.idUsuario = idUsuario;
        this.idAdmin = idAdmin;
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

    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getImagem() {
        return imagem;
    }
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdAdmin() {
        return idAdmin;
    }
    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }
}
