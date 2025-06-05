package projeto_igor;

import java.io.Serializable;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 3L;
    private String nomeUsuario;
    private boolean usuarioAtivo;

    public Usuario(String nomeUsuario){
        this.nomeUsuario = nomeUsuario;
        this.usuarioAtivo = true;
    }

    public String getNomeUsuario(){
        return nomeUsuario;
    }

    public boolean isUsuarioAtivo() {
        return usuarioAtivo;
    }

    public void setNomeUsuario(String nomeUsuario){
        this.nomeUsuario = nomeUsuario;
    }

    public void setUsuarioAtivo(boolean usuarioAtivo){
        this.usuarioAtivo = usuarioAtivo;
    }
}
