    package projeto_marcel;

    import java.io.*;

    public class PersistenciaUtils {
        public static void salvarUsuarioDat() {
            int ultimoId = obterUltimoId(); // busca o último ID salvo

            try (PrintWriter gravador = new PrintWriter(new FileWriter("usuarios.dat", true))) {
                for (Usuario usuario : RepUsuarios.usuarios) {
                    ultimoId++; // add  +1 no id para do novo usuario
                    usuario.setId(ultimoId); // coloca o id novo
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

        private static int obterUltimoId() {
            int ultimoId = 0;

            try (BufferedReader leitor = new BufferedReader(new FileReader("usuarios.dat"))) {
                String linha;
                while ((linha = leitor.readLine()) != null) {
                    String[] partes = linha.split("\\|");
                    if (partes.length > 0) {
                        int id = Integer.parseInt(partes[0]);//ele vai pegar a primeira parte
                        if (id > ultimoId) {
                            ultimoId = id;
                        }
                    }
                }
            } catch (IOException | NumberFormatException e) {
                // ignora se estiver vazio
            }

            return ultimoId;
        }
    }


