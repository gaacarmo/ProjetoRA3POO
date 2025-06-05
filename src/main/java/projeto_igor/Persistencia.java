package projeto_igor;


import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Persistencia { // fiz a persistência de objetos da forma que fui entendendo que seria, pesquisei na internet e fiz consultas com IA
    private static final String ARQUIVO_POSTAGENS = "postagens.dat";
    private static final String ARQUIVO_USUARIOS = "usuarios.dat";

    // Salva a lista de postagens em arquivo .dat
    public static void salvarPostagens(List<Postagem> postagens) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQUIVO_POSTAGENS))) {
            out.writeObject(postagens);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Carrega a lista de postagens do arquivo
    public static List<Postagem> carregarPostagens() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ARQUIVO_POSTAGENS))) {
            return (List<Postagem>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>(); // Retorna lista vazia se o arquivo não existir
        }
    }

    // Salva a lista de usuários em arquivo .dat
    public static void salvarUsuario(List<Usuario> usuarios){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQUIVO_USUARIOS))) {
            out.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Usuario> carregarUsuarios(){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ARQUIVO_USUARIOS))) {
            return (List<Usuario>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>(); // Retorna lista vazia se o arquivo não existir
        }
    }
}