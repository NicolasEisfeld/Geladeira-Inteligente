package dev.nicolas.MagicFridgeAI.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.core.ParameterizedTypeReference;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class ChatService {

    @Value("${openai.api.key:}")
    private String apiKey;

    @Value("${openai.api.url:}")
    private String apiUrl;

    private final WebClient webClient = WebClient.create();

    public String enviarMensagem(String mensagem) {
        if (apiKey == null || apiKey.isBlank()) {
            loadEnvFile();
        }

        if (apiKey == null || apiKey.isBlank()) {
            return "Chave da API OpenAI nao configurada. Defina a variavel de ambiente OPENAI_API_KEY ou configure openai.api.key (ou adicione OPENAI_API_KEY no arquivo .env).";
        }
        if (apiUrl == null || apiUrl.isBlank()) {
            return "URL da API OpenAI nao configurada (openai.api.url).";
        }

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o-mini");
        requestBody.put("messages", List.of(Map.of("role", "user", "content", mensagem)));

        try {
            Map<String, Object> response = webClient.post()
                    .uri(apiUrl)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();

            if (response == null) return null;

            Object choicesObj = response.get("choices");
            if (!(choicesObj instanceof List)) return null;
            List<?> choices = (List<?>) choicesObj;
            if (choices.isEmpty()) return null;
            Object first = choices.get(0);
            if (!(first instanceof Map)) return null;
            Object messageObj = ((Map<?, ?>) first).get("message");
            if (!(messageObj instanceof Map)) return null;
            Object content = ((Map<?, ?>) messageObj).get("content");
            return content != null ? content.toString() : null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Erro ao chamar a API de geracao: " + ex.getMessage();
        }
    }

    // Tenta ler arquivo .env na raiz do projeto e carregar o OPENAI_API_KEY
    private void loadEnvFile() {
        try {
            Path envPath = Path.of(".env");
            if (!Files.exists(envPath)) return;
            List<String> lines = Files.readAllLines(envPath, StandardCharsets.UTF_8);
            for (String line : lines) {
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#")) continue;
                int eq = trimmed.indexOf('=');
                if (eq <= 0) continue;
                String key = trimmed.substring(0, eq).trim();
                String val = trimmed.substring(eq + 1).trim();
                // remover aspas se houver
                if ((val.startsWith("\"") && val.endsWith("\"")) || (val.startsWith("'") && val.endsWith("'"))) {
                    val = val.substring(1, val.length() - 1);
                }
                if ("OPENAI_API_KEY".equals(key) && (apiKey == null || apiKey.isBlank())) {
                    apiKey = val;
                    // tambem define a variavel de sistema para que outras partes possam usar se necessario
                    if (val != null && !val.isBlank()) System.setProperty("OPENAI_API_KEY", val);
                    break;
                }
            }
        } catch (Exception e) {
            // não queremos interromper; apenas logar no stdout/stderr
            System.err.println("Falha ao ler .env: " + e.getMessage());
        }
    }
}
