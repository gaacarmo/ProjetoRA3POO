package projetoRA3.controller;

import projetoRA3.model.Postagem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaPostagem {
    private static final String CAMINHO_DIRETORIO = "dat";
    private static final String CAMINHO_ARQUIVO = CAMINHO_DIRETORIO + "/postagens.dat";

    public void salvar(List<Postagem> postagens) {
        try {
            File dir = new File(CAMINHO_DIRETORIO);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO))) {
                oos.writeObject(new ArrayList<>(postagens));
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar postagens: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public List<Postagem> carregar() {
        File arquivo = new File(CAMINHO_ARQUIVO);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Postagem>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar postagens: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
