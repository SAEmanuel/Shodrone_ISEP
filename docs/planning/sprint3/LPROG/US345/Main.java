import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Uso: java Main <input-file>");
            return;
        }

        try {
            String code = new String(Files.readAllBytes(Paths.get(args[0])));

            DroneLanguagePlugin plugin = new DroneOnePlugin();
            ValidationResult result = plugin.analyzeProgram(code);

            System.out.println("Validação: " + (result.isValid() ? "Sucesso" : "Falhou"));
            System.out.println("Mensagem: " + result.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
