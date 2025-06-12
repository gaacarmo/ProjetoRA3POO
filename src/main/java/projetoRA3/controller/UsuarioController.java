package projetoRA3.controller;

import projetoRA3.model.Usuario;
import java.util.List;

public class UsuarioController {
    private PersistenciaUsuario persistencia;

    public UsuarioController() {
        this.persistencia = new PersistenciaUsuario();
    }

    public void adicionarUsuario(Usuario usuario) {
        List<Usuario> usuarios = persistencia.carregar();
        usuarios.add(usuario);
        persistencia.salvar(usuarios);
    }

    public List<Usuario> listarUsuarios() {
        return persistencia.carregar();
    }

    public boolean atualizarUsuario(Usuario usuarioAtualizado) {
        List<Usuario> usuarios = persistencia.carregar();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == usuarioAtualizado.getId()) {
                usuarios.set(i, usuarioAtualizado);
                persistencia.salvar(usuarios);
                return true;
            }
        }
        return false;
    }

    public boolean removerUsuario(int id) {
        List<Usuario> usuarios = persistencia.carregar();
        boolean removido = usuarios.removeIf(u -> u.getId() == id);
        if (removido) {
            persistencia.salvar(usuarios);
        }
        return removido;
    }

    public Usuario buscarUsuarioPorId(int id) {
        return persistencia.carregar().stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }
}