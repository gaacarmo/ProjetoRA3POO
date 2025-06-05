package RA3.projeto_igor;

import java.io.Serializable;

public class Moderador implements Serializable {
    private static final long serialVersionUID = 1L;
    private int idModerador; // acabei nem usando id
    private String nomeModerador;
    private String senhaModerador;

    public Moderador(int idModerador, String nomeModerador, String senhaModerador){
        this.idModerador = idModerador;
        this.nomeModerador = nomeModerador;
        this.senhaModerador = senhaModerador;
    }

    public String getNomeModerador() {
        return nomeModerador;
    }

    public String getSenhaModerador() {
        return senhaModerador;
    }

    public int getIdModerador() {
        return idModerador;
    }

    public void setNomeModerador(String nomeModerador) {
        this.nomeModerador = nomeModerador;
    }

    public void setSenhaModerador(String senhaModerador) {
        this.senhaModerador = senhaModerador;
    }

    public void setIdModerador(int idModerador) {
        this.idModerador = idModerador;
    }

    public void excluirUsuario(Usuario usuario){
        usuario.setUsuarioAtivo(false);
        System.out.println("Usuário: " + usuario.getNomeUsuario() + " excluído");
    }

    public void adicionarVerificado(Postagem postagem){
        postagem.setVerificado(true);
        System.out.println("A postagem foi verificada");
    }

    public void excluirPostagem(Postagem postagem){
        postagem.setPostagemAtiva(false);
        System.out.println("A postagem foi excluída com sucesso");
    }

    public void reativarUsuario(Usuario usuario){
        usuario.setUsuarioAtivo(true);
        System.out.println("O usuário foi reativado com sucesso");
    }

    public void reativarPostagem(Postagem postagem){
        postagem.setPostagemAtiva(true);
        System.out.println("A postagem foi reativada com sucesso");
    }

    public void retirarVerificado(Postagem postagem){
        postagem.setVerificado(false);
        System.out.println("A postagem teve seu verificado retirado com sucesso");
    }


}