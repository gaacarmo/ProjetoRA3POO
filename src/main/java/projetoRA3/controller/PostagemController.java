package projetoRA3.controller;

import projetoRA3.model.Postagem;
import java.util.List;

public class PostagemController {
    private PersistenciaPostagem persistencia;

    public PostagemController() {
        this.persistencia = new PersistenciaPostagem();
    }

    public void adicionarPostagem(Postagem postagem) {
        List<Postagem> postagens = persistencia.carregar();
        postagens.add(postagem);
        persistencia.salvar(postagens);
    }

    public List<Postagem> listarPostagens() {
        return persistencia.carregar();
    }

    public boolean atualizarPostagem(Postagem postagemAtualizada) {
        List<Postagem> postagens = persistencia.carregar();
        for (int i = 0; i < postagens.size(); i++) {
            if (postagens.get(i).getId() == postagemAtualizada.getId()) {
                postagens.set(i, postagemAtualizada);
                persistencia.salvar(postagens);
                return true;
            }
        }
        return false;
    }

    public boolean removerPostagem(int id) {
        List<Postagem> postagens = persistencia.carregar();
        boolean removido = postagens.removeIf(p -> p.getId() == id);
        if (removido) {
            persistencia.salvar(postagens);
        }
        return removido;
    }

    public Postagem buscarPostagemPorId(int id) {
        return persistencia.carregar().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
