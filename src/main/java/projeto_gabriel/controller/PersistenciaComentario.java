package projeto_gabriel.controller;

import projeto_gabriel.model.Comentario;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaComentario {
    private static final String CAMINHO_ARQUIVO = "comentarios.dat";

    public void salvar(List<Comentario> comentarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO))) {
            oos.writeObject(new ArrayList<>(comentarios));
        } catch (IOException e) {
            System.err.println("Erro ao salvar comentários: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public List<Comentario> carregar() {
        File arquivo = new File(CAMINHO_ARQUIVO);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Comentario>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar comentários: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}