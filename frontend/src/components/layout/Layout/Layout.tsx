import React, { useState } from 'react';
import { Sidebar } from '../Sidebar/Sidebar';
import { AppRoutes } from '../../../routes/AppRoutes';
import { Header } from '../Header/Header';
import { Editor } from '../Editor/Editor';
import { ChatPanel } from '../ChatPanel/ChatPanel';
import '../Layout.css';

interface LayoutProps {
  isAuthenticated?: boolean;
}

export const Layout: React.FC<LayoutProps> = ({ 
  isAuthenticated = false 
}) => {
  const [isSidebarCollapsed, setIsSidebarCollapsed] = useState(false);
  const [activePanel, setActivePanel] = useState<string>('explorer');
  const [isChatPanelOpen, setIsChatPanelOpen] = useState(false);
  const [editorContent, setEditorContent] = useState<string>('// AuroraAI IDE - Professional Development Environment\n\n// Welcome to AuroraAI!\n// This is a secure AI-powered development environment\n\nfunction main() {\n  console.log("Hello, AuroraAI!");\n  return "Welcome to AuroraAI IDE!";\n}');
  const [selectedCode, setSelectedCode] = useState<string>('');
  const [cursorPosition, setCursorPosition] = useState<{ line: number; column: number }>({ line: 1, column: 1 });

  const handleSidebarToggle = () => {
    setIsSidebarCollapsed(!isSidebarCollapsed);
  };

  // Header menu callbacks
  const handleNewFile = () => {
    // Trigger new file creation in sidebar
    setActivePanel('explorer');
    setEditorContent('// New file created in AuroraAI IDE\n// File name: new-file.ts\n\nexport const newFile = () => {\n  console.log("New file created");\n};');
  };

  const handleOpenFile = () => {
    alert('Open file dialog');
  };

  const handleSaveFile = () => {
    alert('File saved successfully!');
  };

  const handleUndo = () => {
    document.execCommand('undo');
  };

  const handleRedo = () => {
    document.execCommand('redo');
  };

  const handleCopy = () => {
    document.execCommand('copy');
  };

  const handlePaste = () => {
    document.execCommand('paste');
  };

  const handleFind = () => {
    setActivePanel('search');
  };

  const handleGoToLine = () => {
    const lineNumber = prompt('Go to line number:');
    if (lineNumber) {
      alert(`Going to line ${lineNumber}`);
    }
  };

  const handleStartDebugging = () => {
    setActivePanel('debug');
    alert('Starting debugging session...');
  };

  const handleOpenTerminal = () => {
    alert('Opening terminal...');
  };

  const handleAbout = () => {
    alert('AuroraAI v1.0.0\nA secure AI-powered development environment\n\n© 2024 AuroraAI Technologies');
  };

  const handleChatPanelToggle = () => {
    setIsChatPanelOpen(!isChatPanelOpen);
  };

  const handleCodeAction = (action: string, code: string) => {
    // Aplicar ação da AuroraAI no editor
    switch (action) {
      case 'apply_suggestion':
        setEditorContent(code);
        break;
      case 'refactor':
        setEditorContent(code);
        break;
      case 'explain_code':
        // Adicionar comentário explicativo
        const commentedCode = `// Explicação da AuroraAI:\n// ${code}\n\n${code}`;
        setEditorContent(commentedCode);
        break;
      case 'debug':
        // Adicionar debug logs
        const debuggedCode = code.replace(/console\.log/g, 'console.debug');
        setEditorContent(debuggedCode);
        break;
      default:
        setEditorContent(code);
    }
  };

  const handleEditorChange = (content: string) => {
    setEditorContent(content);
  };

  const handleSelectionChange = (selection: string, start: number, end: number) => {
    setSelectedCode(selection);
  };

  const handleCursorChange = (position: { line: number; column: number }) => {
    setCursorPosition(position);
  };

  const handleFileSelect = (filePath: string) => {
    console.log('File selected:', filePath);
    
    // Simulate loading file content dynamically
    const loadFileContent = async (path: string) => {
      // Simulate file loading - in real app this would fetch from server
      const fileExtensions: { [key: string]: string } = {
        '.tsx': '// TypeScript React Component\n// Click to edit this file\n\nexport const Component = () => {\n  return <div>Component</div>;\n};',
        '.ts': '// TypeScript File\n// Click to edit this file\n\nexport function main() {\n  console.log("Hello, AuroraAI!");\n}',
        '.json': '// JSON Configuration File\n// Click to edit this file\n\n{\n  "name": "project",\n  "version": "1.0.0"\n}',
        '.md': '// Markdown File\n# Project Documentation\n\nClick to edit this file.',
        '.css': '/* CSS Stylesheet */\n/* Click to edit this file */\n\n.container {\n  display: flex;\n}'
      };
      
      const extension = path.split('.').pop();
      const baseContent = fileExtensions[`.${extension}`] || '// Text File\n// Click to edit this file';
      
      return `// File: ${path}\n// Loaded dynamically\n\n${baseContent}`;
    };
    
    loadFileContent(filePath).then(content => {
      console.log('Content loaded for:', filePath);
      setEditorContent(content);
    });
  };

  return (
    <div className="vscode-layout">
      <Header 
        onNewFile={handleNewFile}
        onOpenFile={handleOpenFile}
        onSaveFile={handleSaveFile}
        onUndo={handleUndo}
        onRedo={handleRedo}
        onCopy={handleCopy}
        onPaste={handlePaste}
        onFind={handleFind}
        onGoToLine={handleGoToLine}
        onStartDebugging={handleStartDebugging}
        onOpenTerminal={handleOpenTerminal}
        onAbout={handleAbout}
      />
      
      <div className="vscode-main">
        <Sidebar 
          isCollapsed={isSidebarCollapsed}
          onToggle={handleSidebarToggle}
          onFileSelect={handleFileSelect}
        />
        
        <div className="vscode-content">
          <Editor 
            content={editorContent}
            language="typescript"
            theme="dark"
            onContentChange={handleEditorChange}
            onSave={handleSaveFile}
            fontSize={14}
            wordWrap={true}
            onSelectionChange={handleSelectionChange}
            onCursorChange={handleCursorChange}
          />
        </div>
      </div>
      
      <ChatPanel 
        isOpen={isChatPanelOpen}
        onToggle={handleChatPanelToggle}
        editorContent={editorContent}
        editorSelection={selectedCode}
        cursorPosition={cursorPosition}
        onCodeAction={handleCodeAction}
      />
    </div>
  );
};
