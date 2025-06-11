package projeto_gabriel.controller;

import projeto_gabriel.model.Comentario;
import java.util.List;

public class ComentarioController {
    private PersistenciaComentario persistencia;

    public ComentarioController() {
        this.persistencia = new PersistenciaComentario();
    }

    public void adicionarComentario(Comentario comentario) {
        List<Comentario> comentarios = persistencia.carregar();
        comentarios.add(comentario);
        persistencia.salvar(comentarios);
    }

    public List<Comentario> listarComentarios() {
        return persistencia.carregar();
    }

    public boolean atualizarComentario(Comentario comentarioAtualizado) {
        List<Comentario> comentarios = persistencia.carregar();
        for (int i = 0; i < comentarios.size(); i++) {
            if (comentarios.get(i).getId() == comentarioAtualizado.getId()) {
                comentarios.set(i, comentarioAtualizado);
                persistencia.salvar(comentarios);
                return true;
            }
        }
        return false;
    }

    public boolean removerComentario(int id) {
        List<Comentario> comentarios = persistencia.carregar();
        boolean removido = comentarios.removeIf(c -> c.getId() == id);
        if (removido) {
            persistencia.salvar(comentarios);
        }
        return removido;
    }

    public Comentario buscarComentarioPorId(int id) {
        return persistencia.carregar().stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
}