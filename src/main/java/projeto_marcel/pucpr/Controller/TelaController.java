package projeto_marcel.pucpr.Controller;




import projeto_marcel.pucpr.Model.PersistenciaUtils;
import projeto_marcel.pucpr.Model.RepUsuarios;
import projeto_marcel.pucpr.Model.Usuario;
import projeto_marcel.pucpr.View.TelaCadastroView;
import projeto_marcel.pucpr.View.TelaExibirUsuariosView;

import java.util.ArrayList;

public class TelaController {
    public void abrirTelaCadastro() {
        TelaCadastroView tela = new TelaCadastroView(this);
        tela.mostrar();
    }

    public void cadastrarUsuario(String nome, String nomeUsuario, String curso, String email, String senha) {
        // Carrega os usuários existentes
        if (RepUsuarios.usuarios.isEmpty()) {
            RepUsuarios.usuarios = new ArrayList<>(PersistenciaUtils.carregarUsuariosDat());
        }

        // Obtém o próximo ID disponível
        int novoId = RepUsuarios.usuarios.stream()
                .mapToInt(Usuario::getId)
                .max()
                .orElse(0) + 1;

        Usuario novoUsuario = new Usuario(
                novoId, // Usa o ID calculado corretamente
                nome,
                nomeUsuario,
                curso,
                email,
                senha
        );
        RepUsuarios.usuarios.add(novoUsuario);
        PersistenciaUtils.salvarUsuarioDat();
    }

    public boolean excluirUsuario(int id) {
        // Remove o usuário da lista em memória
        boolean removido = RepUsuarios.usuarios.removeIf(usuario -> usuario.getId() == id);

        if (removido) {
            // Salva a lista atualizada
            PersistenciaUtils.salvarUsuarioDat();
        }

        return removido;
    }

    public boolean editarUsuario(int id, String nome, String nomeUsuario, String curso, String email, String senha) {
        // Procura o usuário na lista pelo ID
        for (Usuario usuario : RepUsuarios.usuarios) {
            if (usuario.getId() == id) {
                // Atualiza os dados do usuário existente
                usuario.setNome(nome);
                usuario.setNomeUsuario(nomeUsuario);
                usuario.setCurso(curso);
                usuario.setEmail(email);
                usuario.setSenha(senha);

                // Salva a lista atualizada
                PersistenciaUtils.salvarUsuarioDat();
                return true;
            }
        }
        return false;
    }

    public void abrirTelaExibirUsuarios() {
        // Carrega os dados uma vez antes de abrir a tela
        RepUsuarios.usuarios = new ArrayList<>(PersistenciaUtils.carregarUsuariosDat());
        TelaExibirUsuariosView tela = new TelaExibirUsuariosView(this);
        tela.mostrar(); //commit
    }
}