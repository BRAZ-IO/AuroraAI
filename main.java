import java.util.*;

public class Main {
    
    // ArrayList para armazenar mensagens do chat
    private static List<Map<String, Object>> chatHistory = new ArrayList<>();
    private static List<Map<String, String>> knowledgeBase = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        // Inicializa a base de conhecimento
        initializeKnowledgeBase();
        
        System.out.println("🌟 AuroraAI iniciada com sucesso!");
        System.out.println("📊 Base de conhecimento: " + knowledgeBase.size() + " tópicos");
        System.out.println("💬 Histórico de chat: " + chatHistory.size() + " mensagens");
        System.out.println("\n=== AuroraAI - Seu Assistente Inteligente ===");
        System.out.println("Digite 'sair' para encerrar\n");
        
        // Loop principal do chat
        runChatLoop();
    }
    
    private static void initializeKnowledgeBase() {
        // Algoritmo inicial - Base de conhecimento simples
        knowledgeBase.add(Map.of("topic", "aprendizado", "response", "Posso ajudar você a aprender qualquer assunto! Me diga o que quer estudar."));
        knowledgeBase.add(Map.of("topic", "produtividade", "response", "Vamos organizar suas tarefas e aumentar sua produtividade!"));
        knowledgeBase.add(Map.of("topic", "criatividade", "response", "Modo criatividade ativado! 🎨 Vamos criar algo incrível juntos!"));
        knowledgeBase.add(Map.of("topic", "problemas", "response", "Analisando problemas e encontrando soluções inteligentes..."));
        knowledgeBase.add(Map.of("topic", "saudacao", "response", "Olá! Sou AuroraAI, seu assistente inteligente. Como posso ajudar?"));
        knowledgeBase.add(Map.of("topic", "ajuda", "response", "Posso ajudar com aprendizado, produtividade, criatividade e resolução de problemas!"));
    }
    
    private static void runChatLoop() {
        while (true) {
            System.out.print("\nVocê: ");
            String message = scanner.nextLine().trim();
            
            if (message.equalsIgnoreCase("sair")) {
                System.out.println("\n🌟 AuroraAI: Até logo! Volte sempre que precisar de ajuda!");
                break;
            }
            
            if (message.isEmpty()) {
                continue;
            }
            
            // Algoritmo inicial de processamento
            String response = processMessage(message);
            
            // Armazena no ArrayList
            Map<String, Object> chatEntry = new HashMap<>();
            chatEntry.put("userId", "user_console");
            chatEntry.put("message", message);
            chatEntry.put("response", response);
            chatEntry.put("timestamp", new Date());
            chatEntry.put("messageId", UUID.randomUUID().toString());
            
            chatHistory.add(chatEntry);
            
            // Limita o histórico a 1000 mensagens
            if (chatHistory.size() > 1000) {
                chatHistory.remove(0);
            }
            
            // Exibe resposta
            System.out.println("\n🤖 AuroraAI: " + response);
            System.out.println("💾 Mensagem #" + chatHistory.size() + " armazenada");
        }
        
        // Exibe estatísticas finais
        showStatistics();
    }
    
    private static String processMessage(String message) {
        message = message.toLowerCase().trim();
        
        // Comandos especiais
        if (message.equals("status")) {
            return "📊 Status:\n" +
                   "- Mensagens: " + chatHistory.size() + "\n" +
                   "- Tópicos: " + knowledgeBase.size() + "\n" +
                   "- Memória usada: " + (chatHistory.size() * 100) + " bytes aprox.";
        }
        
        if (message.equals("historico")) {
            return showRecentHistory();
        }
        
        if (message.equals("aprender")) {
            return learnNewTopic();
        }
        
        // Algoritmo simples de busca na base de conhecimento
        for (Map<String, String> knowledge : knowledgeBase) {
            if (message.contains(knowledge.get("topic"))) {
                return knowledge.get("response");
            }
        }
        
        // Respostas específicas
        if (message.contains("oi") || message.contains("ola")) {
            return "Olá! 👋 Sou AuroraAI, pronta para ajudar você!";
        }
        
        if (message.contains("tudo bem")) {
            return "Estou ótima! Obrigado por perguntar. Como posso ajudar você hoje?";
        }
        
        if (message.contains("adeus") || message.contains("tchau")) {
            return "Até logo! 🌟 Volte sempre que precisar de ajuda!";
        }
        
        // Resposta padrão inteligente
        return "Hmm, interessante! Pode me dar mais detalhes sobre '" + message + "'? " +
               "Estou aqui para ajudar com aprendizado, produtividade, criatividade ou problemas.";
    }
    
    private static String showRecentHistory() {
        if (chatHistory.isEmpty()) {
            return "Nenhuma mensagem no histórico ainda.";
        }
        
        StringBuilder sb = new StringBuilder("📜 Histórico recente:\n");
        int start = Math.max(0, chatHistory.size() - 5);
        
        for (int i = start; i < chatHistory.size(); i++) {
            Map<String, Object> entry = chatHistory.get(i);
            sb.append(i + 1).append(". Você: ").append(entry.get("message")).append("\n");
            sb.append("   AuroraAI: ").append(entry.get("response")).append("\n\n");
        }
        
        return sb.toString();
    }
    
    private static String learnNewTopic() {
        System.out.print("📚 Digite o novo tópico: ");
        String topic = scanner.nextLine().trim();
        
        System.out.print("💬 Digite a resposta para '" + topic + "': ");
        String response = scanner.nextLine().trim();
        
        // Adiciona novo conhecimento ao ArrayList
        knowledgeBase.add(Map.of("topic", topic, "response", response));
        
        return "✅ Aprendi! Agora conheço sobre '" + topic + "'. Total de tópicos: " + knowledgeBase.size();
    }
    
    private static void showStatistics() {
        System.out.println("\n=== Estatísticas Finais ===");
        System.out.println("💬 Total de mensagens: " + chatHistory.size());
        System.out.println("📚 Total de tópicos: " + knowledgeBase.size());
        System.out.println("⏱️  Sessão encerrada");
        
        // Conta palavras mais usadas
        Map<String, Integer> wordCount = new HashMap<>();
        for (Map<String, Object> entry : chatHistory) {
            String message = (String) entry.get("message");
            String[] words = message.toLowerCase().split("\\s+");
            for (String word : words) {
                if (word.length() > 3) {
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }
            }
        }
        
        System.out.println("🔤 Palavras mais usadas:");
        wordCount.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(5)
            .forEach(entry -> System.out.println("   - " + entry.getKey() + ": " + entry.getValue() + "x"));
    }
}