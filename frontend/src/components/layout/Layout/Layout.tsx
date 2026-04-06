import React, { useState } from 'react';
import { Sidebar } from '../Sidebar/Sidebar';
import { AppRoutes } from '../../../routes/AppRoutes';
import { Header } from '../Header/Header';
import { Editor } from '../Editor/Editor';
import '../Layout.css';

interface LayoutProps {
  isAuthenticated?: boolean;
}

export const Layout: React.FC<LayoutProps> = ({ 
  isAuthenticated = false 
}) => {
  const [isSidebarCollapsed, setIsSidebarCollapsed] = useState(false);
  const [activePanel, setActivePanel] = useState<string>('explorer');
  const [editorContent, setEditorContent] = useState<string>('// AuroraAI IDE - Professional Development Environment\n\n// Welcome to AuroraAI!\n// This is a secure AI-powered development environment\n// inspired by VS Code\n\nfunction main() {\n  console.log("Hello, AuroraAI!");\n  return "Welcome to AuroraAI IDE!";\n}');

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

  const handleEditorChange = (content: string) => {
    setEditorContent(content);
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
          />
        </div>
      </div>
    </div>
  );
};
