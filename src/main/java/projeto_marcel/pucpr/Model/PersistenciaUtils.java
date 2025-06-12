package projeto_marcel.pucpr.Model;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaUtils {
    public static void salvarUsuarioDat() {
        try (PrintWriter gravador = new PrintWriter(new FileWriter("usuarios.dat"))) { // Sem append
            for (Usuario usuario : RepUsuarios.usuarios) {
                gravador.println(usuario.getId() + "|" +
                        usuario.getNome() + "|" +
                        usuario.getNomeUsuario() + "|" +
                        usuario.getCurso() + "|" +
                        usuario.getEmail() + "|" +
                        usuario.getSenha());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Usuario> carregarUsuariosDat() {
        List<Usuario> usuarios = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader("usuarios.dat"))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                try {
                    String[] partes = linha.split("\\|");
                    if (partes.length == 6) {
                        Usuario usuario = new Usuario(
                                Integer.parseInt(partes[0]),
                                partes[1],
                                partes[2],
                                partes[3],
                                partes[4],
                                partes[5]
                        );
                        usuarios.add(usuario);
                    } else {
                        System.err.println("Linha mal formatada: " + linha);
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao processar linha: " + linha);
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo usuarios.dat não encontrado - criando novo.");
        } catch (IOException e) {
            System.err.println("Erro de IO ao ler arquivo: " + e.getMessage());
            e.printStackTrace();
        }

        return usuarios;
    }

    private static int obterUltimoId() {
        List<Usuario> usuarios = carregarUsuariosDat();
        return usuarios.stream().mapToInt(Usuario::getId).max().orElse(0);
    }

}