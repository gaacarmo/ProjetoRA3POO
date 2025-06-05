package projeto_lucas;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaPostagem {

    private final String caminhoArquivo = "postagens.dat";
    private List<Postagem> lista = new ArrayList<>();

    public PersistenciaPostagem() {
        carregarArquivo();
    }

    public List<Postagem> listar() {
        return lista;
    }

    public void salvar(Postagem postagem) {
        remover(postagem); // evita duplicatas
        lista.add(postagem);
        salvarArquivo();
    }

    public void remover(Postagem postagem) {
        lista.removeIf(p -> p.getId() == postagem.getId());
        salvarArquivo();
    }

    public int gerarId() {
        int maior = 0;
        for (Postagem p : lista) {
            if (p.getId() > maior) {
                maior = p.getId();
            }
        }
        return maior + 1;
    }

    private void salvarArquivo() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            out.writeObject(lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarArquivo() {
        File file = new File(caminhoArquivo);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                lista = (List<Postagem>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                lista = new ArrayList<>();
            }
        }
    }
}
