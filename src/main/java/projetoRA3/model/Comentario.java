package projetoRA3.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Comentario implements Serializable {
    private int id;
    private String texto;
    private LocalDate data;
    private boolean aprovado;
    private int idUsuario;
    private int idPostagem;

    public Comentario(int id, String texto, LocalDate data, boolean aprovado, int idUsuario, int idPostagem) {
        this.id = id;
        this.texto = texto;
        this.data = data;
        this.aprovado = aprovado;
        this.idUsuario = idUsuario;
        this.idPostagem = idPostagem;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isAprovado() {
        return aprovado;
    }
    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdPostagem() {
        return idPostagem;
    }
    public void setIdPostagem(int idPostagem) {
        this.idPostagem = idPostagem;
    }
}