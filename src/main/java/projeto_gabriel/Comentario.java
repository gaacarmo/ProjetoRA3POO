package projeto_gabriel;

import java.io.Serializable;
import java.time.LocalDate;

public class Comentario implements Serializable {
    private int id;
    private String conteudo;
    private LocalDate data;
    private boolean privado;
    private int idUsuario;
    private int idPostagem;

    public Comentario(int id, String conteudo, LocalDate data, boolean privado, int idUsuario, int idPostagem) {
        this.id = id;
        this.conteudo = conteudo;
        this.data = data;
        this.privado = privado;
        this.idUsuario = idUsuario;
        this.idPostagem = idPostagem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isPrivado() {
        return privado;
    }

    public void setPrivado(boolean privado) {
        this.privado = privado;
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

    @Override
    public String toString() {
        return "Comentário{" +
                "id=" + id +
                ", conteudo='" + conteudo + '\'' +
                ", data=" + data +
                ", privado=" + privado +
                ", idUsuario=" + idUsuario +
                ", idPostagem=" + idPostagem +
                '}';
    }
}
