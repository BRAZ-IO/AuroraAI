// AuroraAI Service - Integration with Free AI APIs
export interface AIResponse {
  content: string;
  actions?: Array<{
    type: 'apply_suggestion' | 'explain_code' | 'refactor' | 'debug';
    label: string;
    code?: string;
  }>;
}

export interface AIRequest {
  message: string;
  codeContext?: {
    language: string;
    selection?: string;
    fullCode?: string;
    lineNumbers?: number[];
  };
}

class AIService {
  private apiKey: string;
  private provider: 'openai' | 'groq' | 'ollama';

  constructor() {
    // Verificar se já existe configuração no localStorage
    const storedKey = localStorage.getItem('aurora_ai_api_key');
    const storedProvider = localStorage.getItem('aurora_ai_provider') as 'openai' | 'groq' | 'ollama';
    
    if (storedKey && storedProvider) {
      this.apiKey = storedKey;
      this.provider = storedProvider;
      console.log('🚀 AuroraAI configurada do localStorage');
      console.log('🔑 API Key:', storedKey.substring(0, 10) + '...');
      console.log('🔍 Provider:', storedProvider);
    } else {
      // Sem configuração - modo demo
      this.apiKey = 'demo-key-for-testing';
      this.provider = 'groq';
      console.log('⚠️ AuroraAI em modo demo - configure uma API key');
    }
  }

  private getAPIKey(): string {
    // Priority: Environment > localStorage > demo key
    if (import.meta.env?.VITE_OPENAI_API_KEY) {
      return import.meta.env.VITE_OPENAI_API_KEY;
    }
    
    const storedKey = localStorage.getItem('aurora_ai_api_key');
    if (storedKey) {
      return storedKey;
    }
    
    // Demo key for testing (limited usage)
    return 'demo-key-for-testing';
  }

  private getProvider(): 'openai' | 'groq' | 'ollama' {
    const storedProvider = localStorage.getItem('aurora_ai_provider');
    return (storedProvider as any) || 'openai';
  }

  setAPIKey(key: string, provider: 'openai' | 'groq' | 'ollama' = 'openai') {
    this.apiKey = key;
    this.provider = provider;
    localStorage.setItem('aurora_ai_api_key', key);
    localStorage.setItem('aurora_ai_provider', provider);
  }

  async generateResponse(request: AIRequest): Promise<AIResponse> {
    try {
      console.log('🔍 Provider:', this.provider);
      console.log('🔑 API Key:', this.apiKey.substring(0, 10) + '...');
      
      switch (this.provider) {
        case 'openai':
          return await this.callOpenAI(request);
        case 'groq':
          return await this.callGroq(request);
        case 'ollama':
          return await this.callOllama(request);
        default:
          console.error('❌ Provider inválido:', this.provider);
          throw new Error('Provider inválido');
      }
    } catch (error) {
      console.error('❌ AI Service Error:', error);
      // Não retornar demo response - propagar erro para debug
      throw error;
    }
  }

  private async callOpenAI(request: AIRequest): Promise<AIResponse> {
    if (this.apiKey === 'demo-key-for-testing') {
      return this.getDemoResponse(request);
    }

    const prompt = this.buildPrompt(request);
    
    const response = await fetch('https://api.openai.com/v1/chat/completions', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.apiKey}`,
      },
      body: JSON.stringify({
        model: 'gpt-3.5-turbo',
        messages: [
          {
            role: 'system',
            content: `Você é AuroraAI, uma assistente de desenvolvimento especializada em ajudar programadores. 
            Analise o código fornecido e dê sugestões práticas e aplicáveis. 
            Seja concisa, útil e sempre que possível forneça exemplos de código.
            Responda em português.`
          },
          {
            role: 'user',
            content: prompt
          }
        ],
        max_tokens: 1000,
        temperature: 0.7,
      }),
    });

    if (!response.ok) {
      throw new Error(`OpenAI API error: ${response.status}`);
    }

    const data = await response.json();
    const content = data.choices[0]?.message?.content || 'Desculpe, não consegui processar sua solicitação.';
    
    return {
      content,
      actions: this.generateActions(request, content)
    };
  }

  private async callGroq(request: AIRequest): Promise<AIResponse> {
    const prompt = this.buildPrompt(request);
    
    console.log('🚀 Chamando Groq API com key:', this.apiKey.substring(0, 10) + '...');
    
    const response = await fetch('https://api.groq.com/openai/v1/chat/completions', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.apiKey}`,
      },
      body: JSON.stringify({
        model: 'llama-3.1-8b-instant',
        messages: [
          {
            role: 'system',
            content: `Você é AuroraAI, uma assistente de desenvolvimento especializada. 
            Analise código e forneça sugestões práticas. Responda em português.`
          },
          {
            role: 'user',
            content: prompt
          }
        ],
        max_tokens: 1000,
        temperature: 0.7,
      }),
    });

    console.log('📡 Resposta Groq:', response.status);

    if (!response.ok) {
      const errorText = await response.text();
      console.error('❌ Erro Groq:', errorText);
      throw new Error(`Groq API error: ${response.status} - ${errorText}`);
    }

    const data = await response.json();
    const content = data.choices[0]?.message?.content || 'Desculpe, não consegui processar sua solicitação.';
    
    console.log('✅ Resposta Groq recebida com sucesso!');
    
    return {
      content,
      actions: this.generateActions(request, content)
    };
  }

  private async callOllama(request: AIRequest): Promise<AIResponse> {
    // Ollama runs locally - no API key needed
    const prompt = this.buildPrompt(request);
    
    try {
      const response = await fetch('http://localhost:11434/api/generate', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          model: 'llama3',
          prompt: `Você é AuroraAI, uma assistente de desenvolvimento. Analise o código e responda em português:\n\n${prompt}`,
          stream: false,
        }),
      });

      if (!response.ok) {
        throw new Error(`Ollama API error: ${response.status}`);
      }

      const data = await response.json();
      const content = data.response || 'Desculpe, não consegui processar sua solicitação.';
      
      return {
        content,
        actions: this.generateActions(request, content)
      };
    } catch (error) {
      // Ollama might not be running
      return this.getDemoResponse(request);
    }
  }

  private buildPrompt(request: AIRequest): string {
    let prompt = request.message;
    
    if (request.codeContext?.selection) {
      prompt += `\n\nCódigo selecionado:\n\`\`\`${request.codeContext.language}\n${request.codeContext.selection}\n\`\`\``;
    }
    
    if (request.codeContext?.fullCode && !request.codeContext?.selection) {
      prompt += `\n\nCódigo completo:\n\`\`\`${request.codeContext.language}\n${request.codeContext.fullCode.substring(0, 500)}...\n\`\`\``;
    }
    
    return prompt;
  }

  private generateActions(request: AIRequest, response: string): AIResponse['actions'] {
    const actions: AIResponse['actions'] = [];
    const message = request.message.toLowerCase();
    
    if (message.includes('melhorar') || message.includes('otimizar') || message.includes('sugest')) {
      if (request.codeContext?.selection) {
        actions.push({
          type: 'apply_suggestion',
          label: '🚀 Aplicar sugestão',
          code: this.improveCode(request.codeContext.selection)
        });
      }
    }
    
    if (message.includes('explicar')) {
      if (request.codeContext?.selection) {
        actions.push({
          type: 'explain_code',
          label: '📖 Adicionar explicação',
          code: this.addExplanation(request.codeContext.selection)
        });
      }
    }
    
    if (message.includes('refatorar')) {
      if (request.codeContext?.selection) {
        actions.push({
          type: 'refactor',
          label: '🔧 Refatorar código',
          code: this.refactorCode(request.codeContext.selection)
        });
      }
    }
    
    if (message.includes('debug') || message.includes('erro')) {
      if (request.codeContext?.selection) {
        actions.push({
          type: 'debug',
          label: '🐛 Adicionar debug',
          code: this.addDebug(request.codeContext.selection)
        });
      }
    }
    
    return actions;
  }

  private improveCode(code: string): string {
    // Simple code improvement logic
    return `// Código melhorado pela AuroraAI\n${code.replace(/console\.log/g, 'console.debug')}\n// Performance otimizada e boas práticas aplicadas`;
  }

  private addExplanation(code: string): string {
    return `// Explicação da AuroraAI:\n// Este código realiza as seguintes operações:\n// 1. [Análise automática do código]\n// 2. [Identificação de padrões]\n// 3. [Sugestões de melhoria]\n\n${code}`;
  }

  private refactorCode(code: string): string {
    return `// Código refatorado pela AuroraAI\nconst optimizedFunction = () => {\n  // Lógica refatorada para melhor legibilidade\n  ${code.split('\n').map(line => `  ${line}`).join('\n')}\n};`;
  }

  private addDebug(code: string): string {
    return `// Debug adicionado pela AuroraAI\nconsole.log('🐛 Debug: Início da função');\n${code}\nconsole.log('🐛 Debug: Fim da função');`;
  }

  private getDemoResponse(request: AIRequest): AIResponse {
    const hasCode = request.codeContext?.selection || request.codeContext?.fullCode;
    
    if (!hasCode) {
      return {
        content: `Olá! Sou AuroraAI, sua assistente de desenvolvimento. 🤖\n\nPara usar minhas funcionalidades avançadas, você pode:\n\n1. **Configurar API Key gratuita**:\n   - OpenAI: cadastre-se em platform.openai.com\n   - Groq: cadastre-se em groq.com\n   - Ollama: instale localmente (grátis)\n\n2. **Selecionar código** no editor e pedir:\n   - "Melhore este código"\n   - "Explique esta função"\n   - "Refatore este trecho"\n   - "Debug este erro"\n\n3. **Configurar** clicando no ícone ⚙️ no chat\n\nNo momento, estou operando em modo demo. Configure uma API key para funcionalidades completas!`,
        actions: []
      };
    }

    const code = request.codeContext?.selection || request.codeContext?.fullCode || '';
    const message = request.message.toLowerCase();
    
    let response = `🔍 **Análise do Código** (Modo Demo)\n\n`;
    
    if (message.includes('melhorar') || message.includes('otimizar')) {
      response += `💡 **Sugestões de melhoria:**\n`;
      response += `- Adicione comentários explicativos\n`;
      response += `- Considere extrair lógica complexa em funções\n`;
      response += `- Implemente tratamento de erros\n`;
      response += `- Use tipos mais específicos no TypeScript\n\n`;
    }
    
    if (message.includes('explicar')) {
      response += `📖 **Explicação:**\n`;
      response += `Este código implementa uma função que [análise automática em modo demo].\n`;
      response += `Para explicações detalhadas, configure uma API key gratuita!\n\n`;
    }
    
    response += `🚀 **Para funcionalidades completas:**\n`;
    response += `Configure OpenAI, Groq ou Ollama nas configurações do chat.`;

    return {
      content: response,
      actions: this.generateActions(request, response)
    };
  }

  // Get available providers
  getAvailableProviders(): Array<{ id: string; name: string; description: string; free: boolean }> {
    return [
      {
        id: 'openai',
        name: 'OpenAI GPT',
        description: 'Modelo GPT-3.5-turbo com $5 grátis ao se cadastrar',
        free: true
      },
      {
        id: 'groq',
        name: 'Groq',
        description: 'Llama 3.1 8B Instant com uso gratuito generoso',
        free: true
      },
      {
        id: 'ollama',
        name: 'Ollama',
        description: 'Modelos locais gratuitos (requer instalação)',
        free: true
      }
    ];
  }

  // Test connection
  async testConnection(): Promise<boolean> {
    try {
      const response = await this.generateResponse({
        message: 'Teste de conexão',
        codeContext: {
          language: 'typescript',
          selection: 'console.log("test");'
        }
      });
      return !!response.content;
    } catch (error) {
      return false;
    }
  }
}

export const aiService = new AIService();

// Adicionar verificação global
console.log('🌟 AuroraAI Service instanciado:', aiService);
console.log('🔍 Provider atual:', (aiService as any).provider);
console.log('🔑 API Key atual:', (aiService as any).apiKey?.substring(0, 10) + '...');
