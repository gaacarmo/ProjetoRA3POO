package projetoRA3.controller;

import projetoRA3.model.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaUsuario {
    private static final String CAMINHO_DIRETORIO = "dat";
    private static final String CAMINHO_ARQUIVO = CAMINHO_DIRETORIO + "/usuarios.dat";

    public void salvar(List<Usuario> usuarios) {
        try {
            File dir = new File(CAMINHO_DIRETORIO);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO))) {
                oos.writeObject(new ArrayList<>(usuarios));
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public List<Usuario> carregar() {
        File arquivo = new File(CAMINHO_ARQUIVO);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Usuario>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar usuários: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
