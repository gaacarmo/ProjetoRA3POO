package projetoRA3.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ArquivoUtil {

    public static <T> void salvarListaEmArquivo(List<T> lista, String caminhoArquivo) {
        try {
            Path path = Paths.get(caminhoArquivo);
            if (Files.notExists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            if (Files.notExists(path)) {
                Files.createFile(path);
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
                oos.writeObject(lista);
            }

        } catch (IOException e) {
            System.err.println("Erro ao salvar no arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static <T> List<T> carregarListaDeArquivo(String caminhoArquivo) {
        Path path = Paths.get(caminhoArquivo);
        if (Files.notExists(path)) {
            return new java.util.ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar arquivo: " + e.getMessage());
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }
}

