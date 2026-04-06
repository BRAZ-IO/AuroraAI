import React, { useState } from 'react';
import { aiService } from '../../../services/aiService';
import './AIConfig.css';

interface AIConfigProps {
  onClose: () => void;
  onConfigured: () => void;
}

export const AIConfig: React.FC<AIConfigProps> = ({ onClose, onConfigured }) => {
  const [selectedProvider, setSelectedProvider] = useState<'openai' | 'groq' | 'ollama'>('openai');
  const [apiKey, setApiKey] = useState('');
  const [isTesting, setIsTesting] = useState(false);
  const [testResult, setTestResult] = useState<{ success: boolean; message: string } | null>(null);

  const providers = aiService.getAvailableProviders();

  const handleTest = async () => {
    if (!apiKey && selectedProvider !== 'ollama') {
      setTestResult({ success: false, message: 'Por favor, insira uma API key' });
      return;
    }

    setIsTesting(true);
    setTestResult(null);

    try {
      aiService.setAPIKey(apiKey, selectedProvider);
      const success = await aiService.testConnection();
      
      if (success) {
        setTestResult({ success: true, message: '✅ Conexão bem-sucedida!' });
        setTimeout(() => {
          onConfigured();
          onClose();
        }, 1500);
      } else {
        setTestResult({ success: false, message: '❌ Falha na conexão. Verifique sua API key.' });
      }
    } catch (error) {
      setTestResult({ success: false, message: '❌ Erro ao testar conexão.' });
    } finally {
      setIsTesting(false);
    }
  };

  const getProviderInfo = (providerId: string) => {
    return providers.find(p => p.id === providerId);
  };

  return (
    <div className="ai-config-overlay">
      <div className="ai-config-modal">
        <div className="ai-config-header">
          <h3>⚙️ Configurar AuroraAI</h3>
          <button onClick={onClose} className="close-button">✕</button>
        </div>

        <div className="ai-config-content">
          <div className="provider-selection">
            <h4>Escolha o provedor de IA:</h4>
            <div className="providers-grid">
              {providers.map((provider) => (
                <div
                  key={provider.id}
                  className={`provider-card ${selectedProvider === provider.id ? 'selected' : ''}`}
                  onClick={() => setSelectedProvider(provider.id as any)}
                >
                  <div className="provider-header">
                    <h5>{provider.name}</h5>
                    {provider.free && <span className="free-badge">GRÁTIS</span>}
                  </div>
                  <p>{provider.description}</p>
                </div>
              ))}
            </div>
          </div>

          {selectedProvider !== 'ollama' && (
            <div className="api-key-input">
              <h4>API Key:</h4>
              <input
                type="password"
                value={apiKey}
                onChange={(e) => setApiKey(e.target.value)}
                placeholder="Insira sua API key"
                className="api-key-field"
              />
              <div className="api-key-help">
                {selectedProvider === 'openai' && (
                  <small>
                    💡 Obtenha sua key em: <a href="https://platform.openai.com/api-keys" target="_blank" rel="noopener noreferrer">platform.openai.com</a>
                    <br />Novos usuários recebem $5 grátis!
                  </small>
                )}
                {selectedProvider === 'groq' && (
                  <small>
                    💡 Obtenha sua key em: <a href="https://console.groq.com/keys" target="_blank" rel="noopener noreferrer">console.groq.com</a>
                    <br />Uso gratuito com Llama 3.1 8B Instant!
                  </small>
                )}
              </div>
            </div>
          )}

          {selectedProvider === 'ollama' && (
            <div className="ollama-info">
              <h4>🦙 Ollama - Modelo Local</h4>
              <p>Ollama executa modelos localmente na sua máquina.</p>
              <div className="installation-steps">
                <h5>Passos para instalação:</h5>
                <ol>
                  <li>Baixe Ollama em <a href="https://ollama.ai" target="_blank" rel="noopener noreferrer">ollama.ai</a></li>
                  <li>Instale o modelo: <code>ollama pull llama3.1</code></li>
                  <li>Inicie o servidor: <code>ollama serve</code></li>
                  <li>Teste a conexão aqui</li>
                </ol>
              </div>
            </div>
          )}

          {testResult && (
            <div className={`test-result ${testResult.success ? 'success' : 'error'}`}>
              {testResult.message}
            </div>
          )}

          <div className="config-actions">
            <button onClick={onClose} className="cancel-button">
              Cancelar
            </button>
            <button
              onClick={handleTest}
              disabled={isTesting || (!apiKey && selectedProvider !== 'ollama')}
              className="test-button"
            >
              {isTesting ? '🔄 Testando...' : '🧪 Testar Conexão'}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
