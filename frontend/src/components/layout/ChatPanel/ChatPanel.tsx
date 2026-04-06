import React, { useState, useRef, useEffect } from 'react';
import { aiService, AIRequest } from '../../../services/aiService';
import { AIConfig } from './AIConfig';
import './ChatPanel.css';

interface Message {
  id: string;
  role: 'user' | 'assistant';
  content: string;
  timestamp: Date;
  codeContext?: {
    language: string;
    selection?: string;
    fullCode?: string;
    lineNumbers?: number[];
  };
  actions?: Array<{
    type: 'apply_suggestion' | 'explain_code' | 'refactor' | 'debug';
    label: string;
    code?: string;
  }>;
}

interface ChatPanelProps {
  isOpen?: boolean;
  onToggle?: () => void;
  editorContent?: string;
  editorSelection?: string;
  cursorPosition?: { line: number; column: number };
  onCodeAction?: (action: string, code: string) => void;
}

export const ChatPanel: React.FC<ChatPanelProps> = ({
  isOpen = false,
  onToggle,
  editorContent = '',
  editorSelection = '',
  cursorPosition = { line: 1, column: 1 },
  onCodeAction
}) => {
  const [messages, setMessages] = useState<Message[]>([
    {
      id: '1',
      role: 'assistant',
      content: '🚀 **AuroraAI configurada com Groq!**\n\nOlá! Sou AuroraAI, sua assistente de desenvolvimento com IA real. Posso ajudar você com:\n\n💬 Discutir seu código\n🔍 Analisar e sugerir melhorias\n🐛 Debugar problemas\n🔧 Refatorar código\n📝 Explicar conceitos\n\nSelecione um trecho do código ou me diga o que precisa!',
      timestamp: new Date()
    }
  ]);
  const [inputValue, setInputValue] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [showConfig, setShowConfig] = useState(false);
  const messagesEndRef = useRef<HTMLDivElement>(null);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  // Testar conexão com AuroraAI ao montar
  useEffect(() => {
    const testAuroraAI = async () => {
      try {
        const isConnected = await aiService.testConnection();
        if (isConnected) {
          console.log('✅ AuroraAI Groq conectada com sucesso!');
        }
      } catch (error) {
        console.log('⚠️ Teste de conexão AuroraAI:', error);
      }
    };
    
    testAuroraAI();
  }, []);

  const analyzeCode = (code: string, userQuery: string): string => {
    // Simulação de análise de código pela AuroraAI
    const responses = [
      `Analisando seu código: "${code.substring(0, 50)}..."\n\n🔍 **Análise:**\n- O código está bem estruturado\n- Boas práticas de nomenclatura\n- Performance otimizada\n\n💡 **Sugestões:**\n- Considere adicionar comentários para melhor documentação\n- Pode extrair parte da lógica para funções menores\n- Adicione tratamento de erros\n\n🚀 **Melhoria:**\n\`\`\`typescript\n// Versão otimizada\n${code.replace(/console\.log/g, '// Log:').substring(0, 100)}...\n\`\`\``,
      
      `Seu código está ótimo! Algumas observações:\n\n✅ **Pontos positivos:**\n- Sintaxe correta\n- Boa legibilidade\n- Segue padrões TypeScript\n\n🎯 **Oportunidades:**\n- Adicione type hints mais específicos\n- Considere usar async/await para operações assíncronas\n- Implemente testes unitários\n\n📚 **Recomendação:**\nEstude SOLID principles para melhorar ainda mais seu código!`,
      
      `Interessante! Seu código "${code.substring(0, 30)}..." tem potencial.\n\n🔧 **Refatoração sugerida:**\n\`\`\`typescript\n// Versão melhorada\nconst optimizedFunction = (params: any) => {\n  // Implementação otimizada\n  return result;\n};\n\`\`\`\n\n📖 **Conceitos relacionados:**\n- Clean Code\n- Design Patterns\n- Performance Optimization`
    ];
    
    return responses[Math.floor(Math.random() * responses.length)];
  };

  const generateCodeActions = (code: string, query: string) => {
    const actions = [];
    
    if (query.toLowerCase().includes('melhorar') || query.toLowerCase().includes('otimizar')) {
      actions.push({
        type: 'apply_suggestion' as const,
        label: '🚀 Aplicar sugestão',
        code: `// Código otimizado pela AuroraAI\n${code.replace(/console\.log/g, '// Log otimizado')}`
      });
    }
    
    if (query.toLowerCase().includes('explicar')) {
      actions.push({
        type: 'explain_code' as const,
        label: '📖 Explicar detalhadamente',
        code: code
      });
    }
    
    if (query.toLowerCase().includes('refatorar')) {
      actions.push({
        type: 'refactor' as const,
        label: '🔧 Refatorar código',
        code: `// Código refatorado\nconst refactoredFunction = () => {\n  // Nova implementação\n};`
      });
    }
    
    return actions;
  };

  const handleSendMessage = async () => {
    if (!inputValue.trim() || isLoading) return;

    const userMessage: Message = {
      id: Date.now().toString(),
      role: 'user',
      content: inputValue,
      timestamp: new Date(),
      codeContext: {
        language: 'typescript',
        selection: editorSelection || editorContent.substring(0, 200),
        fullCode: editorContent,
        lineNumbers: cursorPosition ? [cursorPosition.line] : undefined
      }
    };

    setMessages(prev => [...prev, userMessage]);
    setInputValue('');
    setIsLoading(true);

    try {
      // Usar AIService real
      console.log('🚀 Enviando requisição para AuroraAI...');
      console.log('📝 Mensagem:', inputValue);
      console.log('💻 Código:', editorSelection ? 'selecionado' : 'completo');
      
      const aiRequest: AIRequest = {
        message: inputValue,
        codeContext: {
          language: 'typescript',
          selection: editorSelection || undefined,
          fullCode: editorContent,
          lineNumbers: cursorPosition ? [cursorPosition.line] : undefined
        }
      };

      const aiResponse = await aiService.generateResponse(aiRequest);
      
      console.log('✅ Resposta AuroraAI recebida:', aiResponse.content.substring(0, 100) + '...');

      const assistantMessage: Message = {
        id: (Date.now() + 1).toString(),
        role: 'assistant',
        content: aiResponse.content,
        timestamp: new Date(),
        actions: aiResponse.actions
      };

      setMessages(prev => [...prev, assistantMessage]);
    } catch (error) {
      console.error('❌ Erro AuroraAI:', error);
      
      // Fallback para resposta simulada
      const fallbackMessage: Message = {
        id: (Date.now() + 1).toString(),
        role: 'assistant',
        content: `❌ **Erro na conexão com AuroraAI**\n\n${error}\n\n🔧 **Soluções:**\n1. Verifique sua API key Groq\n2. Confirme conexão com internet\n3. Tente novamente\n\n💡 **Dica:** Abra o console (F12) para ver detalhes do erro.`,
        timestamp: new Date()
      };
      
      setMessages(prev => [...prev, fallbackMessage]);
    } finally {
      setIsLoading(false);
    }
  };

  const handleActionClick = (action: any, messageId: string) => {
    if (onCodeAction && action.code) {
      onCodeAction(action.type, action.code);
      
      // Adicionar mensagem de confirmação
      const confirmationMessage: Message = {
        id: (Date.now() + 2).toString(),
        role: 'assistant',
        content: `✅ **${action.label}** aplicada com sucesso!\n\nO código foi atualizado no editor. Revise as alterações e me diga se precisa de mais ajustes.`,
        timestamp: new Date()
      };
      
      setMessages(prev => [...prev, confirmationMessage]);
    }
  };

  const handleKeyPress = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      handleSendMessage();
    }
  };

  if (!isOpen) {
    return (
      <button 
        className="chat-panel-toggle"
        onClick={onToggle}
        title="Abrir Chat com AuroraAI"
      >
        💬
      </button>
    );
  }

  return (
    <div className="chat-panel">
      <div className="chat-panel-header">
        <div className="chat-panel-title">
          <span className="chat-icon">💬</span>
          <span>AuroraAI Chat</span>
          {editorSelection && <span className="code-indicator">📝</span>}
        </div>
        <div className="chat-panel-controls">
          <button 
            className="config-button"
            onClick={() => setShowConfig(true)}
            title="Configurar AuroraAI"
          >
            ⚙️
          </button>
          <button 
            className="chat-panel-close"
            onClick={onToggle}
            title="Fechar Chat"
          >
            ✕
          </button>
        </div>
      </div>

      <div className="chat-panel-messages">
        {messages.map((message) => (
          <div key={message.id} className={`message ${message.role}`}>
            <div className="message-avatar">
              {message.role === 'user' ? '👤' : '🤖'}
            </div>
            <div className="message-content">
              <div className="message-text">
                {message.content}
                {message.codeContext?.selection && (
                  <div className="code-context">
                    <small><strong>Código em discussão:</strong></small>
                    <pre><code>{message.codeContext.selection.substring(0, 150)}...</code></pre>
                  </div>
                )}
              </div>
              
              {message.actions && message.actions.length > 0 && (
                <div className="message-actions">
                  {message.actions.map((action, index) => (
                    <button
                      key={index}
                      className="action-button"
                      onClick={() => handleActionClick(action, message.id)}
                    >
                      {action.label}
                    </button>
                  ))}
                </div>
              )}
              
              <div className="message-time">
                {message.timestamp.toLocaleTimeString()}
              </div>
            </div>
          </div>
        ))}
        {isLoading && (
          <div className="message assistant">
            <div className="message-avatar">🤖</div>
            <div className="message-content">
              <div className="message-text">
                <span className="typing-indicator">Analisando código...</span>
              </div>
            </div>
          </div>
        )}
        <div ref={messagesEndRef} />
      </div>

      <div className="chat-panel-input">
        <textarea
          value={inputValue}
          onChange={(e) => setInputValue(e.target.value)}
          onKeyPress={handleKeyPress}
          placeholder={editorSelection ? "Discutir este código..." : "Digite sua mensagem..."}
          className="chat-input"
          rows={1}
          disabled={isLoading}
        />
        <button 
          onClick={handleSendMessage}
          disabled={!inputValue.trim() || isLoading}
          className="chat-send-button"
          title="Enviar mensagem (Enter)"
        >
          {isLoading ? '⏳' : '➤'}
        </button>
      </div>
      
      {showConfig && (
        <AIConfig 
          onClose={() => setShowConfig(false)}
          onConfigured={() => {
            setShowConfig(false);
            // Adicionar mensagem de sucesso
            const successMessage: Message = {
              id: Date.now().toString(),
              role: 'assistant',
              content: '✅ **AuroraAI configurada com sucesso!**\n\nAgora você pode:\n- Analisar código com IA real\n- Obter sugestões inteligentes\n- Discutir implementações\n- Receber ajuda especializada\n\nExperimente selecionar um trecho do código e pedir ajuda!',
              timestamp: new Date()
            };
            setMessages(prev => [...prev, successMessage]);
          }}
        />
      )}
    </div>
  );
};
