# AuroraAI - Arquitetura Frontend

## Visão Geral
Frontend React/TypeScript com interface estilo VS Code, integrado com backend Spring Boot via REST API e WebSocket.

## Estrutura de Pastas

```
frontend/
├── public/
│   ├── index.html
│   ├── favicon.ico
│   └── manifest.json
├── src/
│   ├── components/              # Componentes UI
│   │   ├── common/             # Componentes reutilizáveis
│   │   │   ├── Button/
│   │   │   │   ├── Button.tsx
│   │   │   │   ├── Button.types.ts
│   │   │   │   ├── Button.test.tsx
│   │   │   │   └── index.ts
│   │   │   ├── Input/
│   │   │   ├── Modal/
│   │   │   ├── Dropdown/
│   │   │   ├── Icon/
│   │   │   ├── Badge/
│   │   │   └── Tooltip/
│   │   ├── layout/             # Layout components
│   │   │   ├── Layout/
│   │   │   ├── Header/
│   │   │   ├── Sidebar/
│   │   │   ├── StatusBar/
│   │   │   └── Panel/
│   │   ├── editor/             # Monaco Editor components
│   │   │   ├── Editor/
│   │   │   │   ├── Editor.tsx
│   │   │   │   ├── Editor.types.ts
│   │   │   │   ├── Editor.config.ts
│   │   │   │   ├── Editor.test.tsx
│   │   │   │   └── index.ts
│   │   │   ├── EditorTabs/
│   │   │   ├── EditorToolbar/
│   │   │   └── EditorMinimap/
│   │   ├── chat/               # AI Chat components
│   │   │   ├── Chat/
│   │   │   │   ├── Chat.tsx
│   │   │   │   ├── Chat.types.ts
│   │   │   │   ├── ChatMessage/
│   │   │   │   ├── ChatInput/
│   │   │   │   ├── Chat.test.tsx
│   │   │   │   └── index.ts
│   │   │   └── ChatHistory/
│   │   ├── terminal/           # Terminal components
│   │   │   ├── Terminal/
│   │   │   │   ├── Terminal.tsx
│   │   │   │   ├── Terminal.types.ts
│   │   │   │   ├── Terminal.test.tsx
│   │   │   │   └── index.ts
│   │   │   └── TerminalTabs/
│   │   └── file-explorer/      # File explorer
│   │       ├── FileExplorer/
│   │       ├── FileTree/
│   │       └── FileItem/
│   ├── pages/                  # Páginas principais
│   │   ├── Dashboard/
│   │   │   ├── Dashboard.tsx
│   │   │   ├── Dashboard.test.tsx
│   │   │   └── index.ts
│   │   ├── Settings/
│   │   │   ├── Settings.tsx
│   │   │   ├── Settings.test.tsx
│   │   │   └── index.ts
│   │   ├── Login/
│   │   │   ├── Login.tsx
│   │   │   ├── Login.test.tsx
│   │   │   └── index.ts
│   │   └── Error/
│   │       ├── Error.tsx
│   │       └── index.ts
│   ├── hooks/                  # Hooks customizados
│   │   ├── useTheme.ts
│   │   ├── useHotkeys.ts
│   │   ├── useApi.ts
│   │   ├── useLocalStorage.ts
│   │   ├── useDebounce.ts
│   │   ├── useWebSocket.ts
│   │   ├── useFileOperations.ts
│   │   └── index.ts
│   ├── services/               # Serviços API
│   │   ├── api.ts
│   │   ├── chatService.ts
│   │   ├── codeService.ts
│   │   ├── analysisService.ts
│   │   ├── fileService.ts
│   │   ├── authService.ts
│   │   └── index.ts
│   ├── store/                  # Estado global (Redux Toolkit)
│   │   ├── index.ts
│   │   ├── store.ts
│   │   ├── slices/
│   │   │   ├── themeSlice.ts
│   │   │   ├── editorSlice.ts
│   │   │   ├── chatSlice.ts
│   │   │   ├── fileSlice.ts
│   │   │   ├── uiSlice.ts
│   │   │   └── index.ts
│   │   ├── middleware/
│   │   │   ├── logger.ts
│   │   │   ├── apiMiddleware.ts
│   │   │   └── index.ts
│   │   └── selectors/
│   │       ├── themeSelectors.ts
│   │       ├── editorSelectors.ts
│   │       └── index.ts
│   ├── utils/                  # Utilitários
│   │   ├── fileUtils.ts
│   │   ├── formatUtils.ts
│   │   ├── validationUtils.ts
│   │   ├── dateUtils.ts
│   │   ├── stringUtils.ts
│   │   └── index.ts
│   ├── types/                  # Tipos TypeScript
│   │   ├── api.ts
│   │   ├── editor.ts
│   │   ├── chat.ts
│   │   ├── file.ts
│   │   ├── theme.ts
│   │   └── index.ts
│   ├── styles/                 # Estilos
│   │   ├── globals.css
│   │   ├── variables.css
│   │   ├── reset.css
│   │   ├── themes/
│   │   │   ├── dark.css
│   │   │   ├── light.css
│   │   │   ├── vs-dark.css
│   │   │   └── index.css
│   │   └── components/
│   │       ├── layout.css
│   │       ├── editor.css
│   │       └── chat.css
│   ├── assets/                 # Recursos estáticos
│   │   ├── icons/
│   │   │   ├── folder.svg
│   │   │   ├── file.svg
│   │   │   └── index.ts
│   │   ├── images/
│   │   └── fonts/
│   ├── constants/              # Constantes
│   │   ├── api.ts
│   │   ├── themes.ts
│   │   ├── shortcuts.ts
│   │   ├── fileExtensions.ts
│   │   └── index.ts
│   ├── config/                 # Configurações
│   │   ├── editor.config.ts
│   │   ├── api.config.ts
│   │   └── index.ts
│   ├── App.tsx
│   ├── App.test.tsx
│   ├── index.tsx
│   ├── index.css
│   └── react-app-env.d.ts
├── .env
├── .env.development
├── .env.production
├── package.json
├── tsconfig.json
├── tailwind.config.js
├── postcss.config.js
├── craco.config.js
├── jest.config.js
├── .eslintrc.js
└── .prettierrc
```

## Padrões Arquiteturais

### 1. Atomic Design
```
Atoms → Molecules → Organisms → Templates → Pages
```
- **Atoms:** Componentes básicos (Button, Input, Icon)
- **Molecules:** Combinações de átomos (Form, Card, Tab)
- **Organisms:** Estruturas complexas (Header, Sidebar, Editor)
- **Templates:** Layouts sem conteúdo
- **Pages:** Instâncias completas com conteúdo

### 2. State Management
```typescript
// Redux Toolkit para estado global
// React Context para estado local
// React State para UI components
```

### 3. Data Flow
```
Services Layer (API calls) 
  ↓
Store Layer (Estado global)
  ↓
Component Layer (UI rendering)
  ↓
Hook Layer (Lógica reutilizável)
```

### 4. Component Structure
```typescript
// Cada componente tem:
// - Component.tsx (implementação)
// - Component.types.ts (tipos)
// - Component.test.tsx (testes)
// - Component.config.ts (configurações)
// - index.ts (export)
```

## Stack Tecnológico

### Core
- **React 18** - UI framework
- **TypeScript** - Type safety
- **Redux Toolkit** - State management
- **React Router 6** - Routing

### Editor
- **Monaco Editor** - Code editor
- **React-Monaco-Editor** - React wrapper

### Styling
- **Tailwind CSS** - Utility-first CSS
- **Styled Components** - Component-specific styles
- **CSS Modules** - Scoped styles

### API & Data
- **Axios** - HTTP client
- **React Query** - Data fetching
- **WebSocket** - Real-time communication

### Development
- **Vite** - Build tool
- **ESLint** - Linting
- **Prettier** - Code formatting
- **Jest** - Testing
- **React Testing Library** - Component testing
- **Cypress** - E2E testing

### Performance
- **React.memo** - Memoization
- **useMemo** - Hook memoization
- **useCallback** - Callback memoization
- **Code Splitting** - Lazy loading
- **Virtual Scrolling** - Large lists

## Componentes Principais

### 1. Layout Component
```typescript
interface LayoutProps {
  children: React.ReactNode;
  theme?: 'dark' | 'light' | 'vs-dark';
}

export const Layout: React.FC<LayoutProps> = ({ children, theme }) => {
  return (
    <ThemeProvider theme={theme}>
      <div className="layout">
        <Header />
        <div className="layout-content">
          <Sidebar />
          <main className="main-content">
            {children}
          </main>
          <Chat />
        </div>
        <Terminal />
        <StatusBar />
      </div>
    </ThemeProvider>
  );
};
```

### 2. Editor Component
```typescript
interface EditorProps {
  file?: FileNode;
  readOnly?: boolean;
  language?: string;
}

export const Editor: React.FC<EditorProps> = ({ 
  file, 
  readOnly = false,
  language 
}) => {
  const { theme } = useTheme();
  const [content, setContent] = useState('');
  
  return (
    <div className="editor-container">
      <EditorTabs />
      <EditorToolbar />
      <MonacoEditor
        value={content}
        language={language}
        theme={theme}
        onChange={setContent}
        options={editorConfig}
      />
    </div>
  );
};
```

### 3. Chat Component
```typescript
interface ChatProps {
  onSendMessage?: (message: string) => void;
}

export const Chat: React.FC<ChatProps> = ({ onSendMessage }) => {
  const [messages, setMessages] = useState<ChatMessage[]>([]);
  const [input, setInput] = useState('');
  
  return (
    <div className="chat-container">
      <ChatHeader />
      <ChatMessages messages={messages} />
      <ChatInput 
        value={input}
        onChange={setInput}
        onSend={() => onSendMessage?.(input)}
      />
    </div>
  );
};
```

### 4. Terminal Component
```typescript
interface TerminalProps {
  cwd?: string;
  onCommand?: (command: string) => void;
}

export const Terminal: React.FC<TerminalProps> = ({ 
  cwd = '~',
  onCommand 
}) => {
  const [history, setHistory] = useState<string[]>([]);
  const [command, setCommand] = useState('');
  
  return (
    <div className="terminal-container">
      <TerminalTabs />
      <TerminalOutput history={history} />
      <TerminalInput 
        cwd={cwd}
        value={command}
        onChange={setCommand}
        onEnter={() => onCommand?.(command)}
      />
    </div>
  );
};
```

## Serviços API

### Base API Service
```typescript
class ApiService {
  private baseURL: string;
  private axiosInstance: AxiosInstance;
  
  constructor(baseURL: string) {
    this.baseURL = baseURL;
    this.axiosInstance = axios.create({
      baseURL,
      timeout: 10000,
    });
    
    this.setupInterceptors();
  }
  
  private setupInterceptors() {
    // Request interceptor
    this.axiosInstance.interceptors.request.use(
      (config) => {
        const token = localStorage.getItem('token');
        if (token) {
          config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
      },
      (error) => Promise.reject(error)
    );
    
    // Response interceptor
    this.axiosInstance.interceptors.response.use(
      (response) => response.data,
      (error) => {
        if (error.response?.status === 401) {
          // Handle unauthorized
        }
        return Promise.reject(error);
      }
    );
  }
  
  async get<T>(endpoint: string): Promise<T> {
    return this.axiosInstance.get(endpoint);
  }
  
  async post<T>(endpoint: string, data: any): Promise<T> {
    return this.axiosInstance.post(endpoint, data);
  }
  
  async put<T>(endpoint: string, data: any): Promise<T> {
    return this.axiosInstance.put(endpoint, data);
  }
  
  async delete<T>(endpoint: string): Promise<T> {
    return this.axiosInstance.delete(endpoint);
  }
}

export const apiService = new ApiService(
  process.env.REACT_APP_API_URL || 'http://localhost:8080/api'
);
```

### Chat Service
```typescript
export const chatService = {
  async sendMessage(message: string): Promise<ChatResponse> {
    return apiService.post('/chat/message', { message });
  },
  
  async getHistory(): Promise<ChatMessage[]> {
    return apiService.get('/chat/history');
  },
  
  async clearHistory(): Promise<void> {
    return apiService.delete('/chat/history');
  },
  
  async getConversations(): Promise<Conversation[]> {
    return apiService.get('/chat/conversations');
  },
};
```

### Code Service
```typescript
export const codeService = {
  async generateCode(prompt: string, options: CodeOptions): Promise<string> {
    return apiService.post('/code/generate', { prompt, ...options });
  },
  
  async analyzeCode(code: string): Promise<CodeAnalysis> {
    return apiService.post('/code/analyze', { code });
  },
  
  async refactorCode(code: string): Promise<string> {
    return apiService.post('/code/refactor', { code });
  },
  
  async explainCode(code: string): Promise<string> {
    return apiService.post('/code/explain', { code });
  },
};
```

### File Service
```typescript
export const fileService = {
  async getFileTree(): Promise<FileNode[]> {
    return apiService.get('/files/tree');
  },
  
  async readFile(path: string): Promise<string> {
    return apiService.get(`/files/read?path=${encodeURIComponent(path)}`);
  },
  
  async writeFile(path: string, content: string): Promise<void> {
    return apiService.post('/files/write', { path, content });
  },
  
  async deleteFile(path: string): Promise<void> {
    return apiService.delete(`/files?path=${encodeURIComponent(path)}`);
  },
  
  async createFile(path: string): Promise<void> {
    return apiService.post('/files/create', { path });
  },
};
```

## Hooks Customizados

### useTheme
```typescript
export const useTheme = () => {
  const dispatch = useAppDispatch();
  const theme = useAppSelector(state => state.theme.current);
  
  const toggleTheme = () => {
    dispatch(toggleTheme());
  };
  
  const setTheme = (newTheme: Theme) => {
    dispatch(setTheme(newTheme));
  };
  
  return { theme, toggleTheme, setTheme };
};
```

### useHotkeys
```typescript
export const useHotkeys = (shortcuts: Record<string, () => void>) => {
  useEffect(() => {
    const handleKeyDown = (e: KeyboardEvent) => {
      const key = getShortcutKey(e);
      if (shortcuts[key]) {
        e.preventDefault();
        shortcuts[key]();
      }
    };
    
    window.addEventListener('keydown', handleKeyDown);
    return () => window.removeEventListener('keydown', handleKeyDown);
  }, [shortcuts]);
};
```

### useApi
```typescript
export const useApi = <T>(
  apiCall: () => Promise<T>,
  options: {
    onSuccess?: (data: T) => void;
    onError?: (error: Error) => void;
    enabled?: boolean;
  } = {}
) => {
  const [data, setData] = useState<T | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<Error | null>(null);
  
  const execute = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const result = await apiCall();
      setData(result);
      options.onSuccess?.(result);
    } catch (err) {
      setError(err as Error);
      options.onError?.(err as Error);
    } finally {
      setLoading(false);
    }
  }, [apiCall, options]);
  
  useEffect(() => {
    if (options.enabled !== false) {
      execute();
    }
  }, [execute, options.enabled]);
  
  return { data, loading, error, execute };
};
```

### useWebSocket
```typescript
export const useWebSocket = (url: string) => {
  const [socket, setSocket] = useState<WebSocket | null>(null);
  const [connected, setConnected] = useState(false);
  const [message, setMessage] = useState<any | null>(null);
  
  useEffect(() => {
    const ws = new WebSocket(url);
    
    ws.onopen = () => setConnected(true);
    ws.onclose = () => setConnected(false);
    ws.onmessage = (event) => {
      setMessage(JSON.parse(event.data));
    };
    
    setSocket(ws);
    
    return () => ws.close();
  }, [url]);
  
  const send = useCallback((data: any) => {
    if (socket?.readyState === WebSocket.OPEN) {
      socket.send(JSON.stringify(data));
    }
  }, [socket]);
  
  return { socket, connected, message, send };
};
```

## Redux Store Structure

### Slices
```typescript
// themeSlice.ts
export const themeSlice = createSlice({
  name: 'theme',
  initialState: {
    current: 'dark' as Theme,
    fontSize: 14,
    fontFamily: 'Consolas',
  },
  reducers: {
    toggleTheme: (state) => {
      state.current = state.current === 'dark' ? 'light' : 'dark';
    },
    setTheme: (state, action: PayloadAction<Theme>) => {
      state.current = action.payload;
    },
    setFontSize: (state, action: PayloadAction<number>) => {
      state.fontSize = action.payload;
    },
  },
});

// editorSlice.ts
export const editorSlice = createSlice({
  name: 'editor',
  initialState: {
    openTabs: [] as OpenTab[],
    activeTabId: null as string | null,
    unsavedChanges: {} as Record<string, boolean>,
  },
  reducers: {
    openTab: (state, action: PayloadAction<OpenTab>) => {
      if (!state.openTabs.find(tab => tab.id === action.payload.id)) {
        state.openTabs.push(action.payload);
      }
      state.activeTabId = action.payload.id;
    },
    closeTab: (state, action: PayloadAction<string>) => {
      state.openTabs = state.openTabs.filter(tab => tab.id !== action.payload);
      if (state.activeTabId === action.payload) {
        state.activeTabId = state.openTabs[0]?.id || null;
      }
    },
    setActiveTab: (state, action: PayloadAction<string>) => {
      state.activeTabId = action.payload;
    },
    setUnsavedChanges: (state, action: PayloadAction<{ tabId: string; unsaved: boolean }>) => {
      state.unsavedChanges[action.payload.tabId] = action.payload.unsaved;
    },
  },
});

// chatSlice.ts
export const chatSlice = createSlice({
  name: 'chat',
  initialState: {
    messages: [] as ChatMessage[],
    currentConversation: null as string | null,
    conversations: [] as Conversation[],
    isTyping: false,
  },
  reducers: {
    addMessage: (state, action: PayloadAction<ChatMessage>) => {
      state.messages.push(action.payload);
    },
    setMessages: (state, action: PayloadAction<ChatMessage[]>) => {
      state.messages = action.payload;
    },
    setTyping: (state, action: PayloadAction<boolean>) => {
      state.isTyping = action.payload;
    },
    setCurrentConversation: (state, action: PayloadAction<string>) => {
      state.currentConversation = action.payload;
    },
  },
});
```

## Sistema de Temas

### Theme Configuration
```typescript
// constants/themes.ts
export const themes = {
  dark: {
    background: '#1e1e1e',
    foreground: '#cccccc',
    accent: '#007acc',
    secondary: '#252526',
    tertiary: '#2d2d30',
    border: '#3e3e42',
    success: '#4ec9b0',
    warning: '#ce9178',
    error: '#f44747',
  },
  light: {
    background: '#ffffff',
    foreground: '#333333',
    accent: '#0066cc',
    secondary: '#f3f3f3',
    tertiary: '#e8e8e8',
    border: '#e0e0e0',
    success: '#00a854',
    warning: '#faad14',
    error: '#f5222d',
  },
  vsDark: {
    background: '#000000',
    foreground: '#ffffff',
    accent: '#0080ff',
    secondary: '#0c0c0c',
    tertiary: '#1a1a1a',
    border: '#333333',
    success: '#89d185',
    warning: '#dcdcaa',
    error: '#f48771',
  },
};
```

## Atalhos de Teclado

### Keyboard Shortcuts
```typescript
// constants/shortcuts.ts
export const shortcuts = {
  'Ctrl+S': 'saveFile',
  'Ctrl+O': 'openFile',
  'Ctrl+N': 'newFile',
  'Ctrl+W': 'closeTab',
  'Ctrl+Tab': 'nextTab',
  'Ctrl+Shift+Tab': 'previousTab',
  'Ctrl+Shift+P': 'commandPalette',
  'Ctrl+Shift+F': 'searchInFiles',
  'Ctrl+G': 'goToLine',
  'Ctrl+F': 'findInFile',
  'Ctrl+H': 'replaceInFile',
  'Ctrl+`': 'toggleTerminal',
  'Ctrl+\\': 'toggleSidebar',
  'Ctrl+B': 'toggleSidebar',
  'Ctrl+Shift+M': 'toggleMaximize',
  'F11': 'toggleFullscreen',
  'Ctrl+Shift+I': 'openSettings',
  'Ctrl+Shift+T': 'reopenClosedTab',
  'Ctrl+Shift+N': 'newWindow',
  'Ctrl+Shift+W': 'closeWindow',
};
```

## Performance Optimization

### Code Splitting
```typescript
// Lazy loading components
const Editor = lazy(() => import('./components/editor/Editor'));
const Chat = lazy(() => import('./components/chat/Chat'));
const Terminal = lazy(() => import('./components/terminal/Terminal'));

// Route-based splitting
const Dashboard = lazy(() => import('./pages/Dashboard'));
const Settings = lazy(() => import('./pages/Settings'));
```

### Memoization
```typescript
// React.memo for components
export const Button = React.memo<ButtonProps>(({ children, ...props }) => {
  return <button {...props}>{children}</button>;
});

// useMemo for expensive calculations
const filteredFiles = useMemo(() => {
  return files.filter(file => file.name.includes(searchTerm));
}, [files, searchTerm]);

// useCallback for event handlers
const handleClick = useCallback(() => {
  // Handle click
}, [dependency]);
```

## Segurança

### Security Measures
```typescript
// Input sanitization
import DOMPurify from 'dompurify';

const sanitizeHTML = (html: string): string => {
  return DOMPurify.sanitize(html);
};

// CSRF protection
apiService.axiosInstance.defaults.headers.common['X-CSRF-TOKEN'] = 
  getCsrfToken();

// Content Security Policy
const cspHeaders = {
  'Content-Security-Policy': "default-src 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline';",
  'X-Content-Type-Options': 'nosniff',
  'X-Frame-Options': 'DENY',
  'X-XSS-Protection': '1; mode=block',
};
```

## Testes

### Unit Tests
```typescript
// Button.test.tsx
import { render, screen } from '@testing-library/react';
import { Button } from './Button';

describe('Button', () => {
  it('renders children', () => {
    render(<Button>Click me</Button>);
    expect(screen.getByText('Click me')).toBeInTheDocument();
  });
  
  it('calls onClick when clicked', () => {
    const handleClick = jest.fn();
    render(<Button onClick={handleClick}>Click</Button>);
    screen.getByText('Click').click();
    expect(handleClick).toHaveBeenCalled();
  });
});
```

### Integration Tests
```typescript
// Chat.test.tsx
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { Chat } from './Chat';
import { chatService } from '../../services/chatService';

jest.mock('../../services/chatService');

describe('Chat', () => {
  it('sends message on submit', async () => {
    chatService.sendMessage.mockResolvedValue({ response: 'Hello' });
    render(<Chat />);
    
    fireEvent.change(screen.getByRole('textbox'), {
      target: { value: 'Hello' }
    });
    fireEvent.click(screen.getByText('Send'));
    
    await waitFor(() => {
      expect(chatService.sendMessage).toHaveBeenCalledWith('Hello');
    });
  });
});
```

## Deploy e Build

### Scripts
```json
{
  "scripts": {
    "start": "vite",
    "build": "vite build",
    "preview": "vite preview",
    "test": "jest",
    "test:watch": "jest --watch",
    "test:coverage": "jest --coverage",
    "lint": "eslint src --ext .ts,.tsx",
    "lint:fix": "eslint src --ext .ts,.tsx --fix",
    "format": "prettier --write src/**/*.{ts,tsx,css}",
    "type-check": "tsc --noEmit"
  }
}
```

## Resumo

Esta arquitetura proporciona:
- ✅ **Escalabilidade:** Fácil adicionar novos componentes e features
- ✅ **Manutenibilidade:** Código organizado e documentado
- ✅ **Performance:** Otimizações integradas (code splitting, memoization)
- ✅ **Segurança:** Proteções contra vulnerabilidades (XSS, CSRF)
- ✅ **Testabilidade:** Código testável com testes unitários e integração
- ✅ **Type Safety:** TypeScript strict mode para evitar erros em runtime
- ✅ **Developer Experience:** Ferramentas modernas e configurações otimizadas

Pronto para implementação! 🚀
