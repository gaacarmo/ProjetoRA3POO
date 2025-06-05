package projeto_igor;

import java.io.Serializable;

public class Postagem implements Serializable {
    private static final long serialVersionUID = 2L;
    private String titulo;
    private String descricao;
    Usuario usuario;
    private boolean verificado;
    private boolean postagemAtiva;

    public Postagem(String titulo, String descricao, Usuario usuario){
        this.titulo = titulo;
        this.descricao = descricao;
        this.usuario = usuario;
        this.verificado = false;
        this.postagemAtiva = true;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public boolean isPostagemAtiva(){
        return postagemAtiva;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }


    public void setPostagemAtiva(boolean postagemAtiva){
        this.postagemAtiva = postagemAtiva;
    }
}
