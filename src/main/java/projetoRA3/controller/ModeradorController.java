package projetoRA3.controller;

import projetoRA3.model.Moderador;
import java.util.List;

public class ModeradorController {
    private PersistenciaModerador persistencia;

    public ModeradorController() {
        this.persistencia = new PersistenciaModerador();
    }

    public void adicionarModerador(Moderador moderador) {
        List<Moderador> moderadores = persistencia.carregar();
        moderadores.add(moderador);
        persistencia.salvar(moderadores);
    }

    public List<Moderador> listarModeradores() {
        return persistencia.carregar();
    }

    public boolean atualizarModerador(Moderador moderadorAtualizado) {
        List<Moderador> moderadores = persistencia.carregar();
        for (int i = 0; i < moderadores.size(); i++) {
            if (moderadores.get(i).getId() == moderadorAtualizado.getId()) {
                moderadores.set(i, moderadorAtualizado);
                persistencia.salvar(moderadores);
                return true;
            }
        }
        return false;
    }

    public boolean removerModerador(int id) {
        List<Moderador> moderadores = persistencia.carregar();
        boolean removido = moderadores.removeIf(m -> m.getId() == id);
        if (removido) {
            persistencia.salvar(moderadores);
        }
        return removido;
    }

    public Moderador buscarModeradorPorId(int id) {
        return persistencia.carregar().stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
