import React, { useState } from 'react';
import { Sidebar } from '../Sidebar/Sidebar';
import { AppRoutes } from '../../../routes/AppRoutes';
import { Header } from '../Header/Header';
import '../Layout.css';

interface LayoutProps {
  isAuthenticated?: boolean;
}

export const Layout: React.FC<LayoutProps> = ({ 
  isAuthenticated = false 
}) => {
  const [isSidebarCollapsed, setIsSidebarCollapsed] = useState(false);
  const [activePanel, setActivePanel] = useState<string>('explorer');

  const handleSidebarToggle = () => {
    setIsSidebarCollapsed(!isSidebarCollapsed);
  };

  // Header menu callbacks
  const handleNewFile = () => {
    // Trigger new file creation in sidebar
    setActivePanel('explorer');
    alert('New file created!');
  };

  const handleOpenFile = () => {
    alert('Open file dialog');
  };

  const handleSaveFile = () => {
    alert('File saved!');
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

  return (
    <div className="vscode-layout">
      <Sidebar 
        isCollapsed={isSidebarCollapsed}
        onToggle={handleSidebarToggle}
      />
      
      <div className="vscode-main">
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
        <div className="vscode-content">
          <AppRoutes isAuthenticated={isAuthenticated} />
        </div>
      </div>
    </div>
  );
};
