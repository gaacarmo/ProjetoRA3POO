package projeto_igor;

import java.util.List;

// CLasse para testar se as alterações feitas na interface estão funcionando
public class Teste {
    public static void main(String[] args) {
        List<Postagem> postagens = Persistencia.carregarPostagens();
        for (Postagem p : postagens) { // imprime todas as postagens salvas
            System.out.println("Título: " + p.getTitulo());
            System.out.println("Selo de verificado: " + p.isVerificado());
            System.out.println("Postagem ativa: : " + p.isPostagemAtiva());
            System.out.println("Autor: " + p.getUsuario().getNomeUsuario());
            System.out.println("------");
        }

        List<Usuario> usuarios = Persistencia.carregarUsuarios();
        for (Usuario u : usuarios) { // Imprime todas os usuários salvos
            System.out.println("Nome do usuário: " + u.getNomeUsuario());
            System.out.println("Usuário ativo: " + u.isUsuarioAtivo());
            System.out.println("------");
        }
    }
}