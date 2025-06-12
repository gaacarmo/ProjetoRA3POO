package projetoRA3.controller;

import projetoRA3.model.Moderador;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaModerador {
    private static final String CAMINHO_DIRETORIO = "dat";
    private static final String CAMINHO_ARQUIVO = CAMINHO_DIRETORIO + "/moderadores.dat";

    public void salvar(List<Moderador> moderadores) {
        try {
            File dir = new File(CAMINHO_DIRETORIO);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO))) {
                oos.writeObject(new ArrayList<>(moderadores));
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar moderadores: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public List<Moderador> carregar() {
        File arquivo = new File(CAMINHO_ARQUIVO);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Moderador>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar moderadores: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
