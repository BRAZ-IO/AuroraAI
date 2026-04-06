import React, { useState } from 'react';
import './Header.css';

interface MenuBarProps {
  onNewFile?: () => void;
  onOpenFile?: () => void;
  onSaveFile?: () => void;
  onUndo?: () => void;
  onRedo?: () => void;
  onCopy?: () => void;
  onPaste?: () => void;
  onFind?: () => void;
  onGoToLine?: () => void;
  onStartDebugging?: () => void;
  onOpenTerminal?: () => void;
  onAbout?: () => void;
}

export const Header: React.FC<MenuBarProps> = ({
  onNewFile,
  onOpenFile,
  onSaveFile,
  onUndo,
  onRedo,
  onCopy,
  onPaste,
  onFind,
  onGoToLine,
  onStartDebugging,
  onOpenTerminal,
  onAbout
}) => {
  const [activeMenu, setActiveMenu] = useState<string | null>(null);
  const [terminalOpen, setTerminalOpen] = useState<boolean>(false);

  const menuItems = {
    file: {
      label: 'File',
      items: [
        { label: 'New File', shortcut: 'Ctrl+N', action: onNewFile },
        { label: 'Open File...', shortcut: 'Ctrl+O', action: onOpenFile },
        { label: 'Save', shortcut: 'Ctrl+S', action: onSaveFile },
        { label: 'Save As...', shortcut: 'Ctrl+Shift+S', action: onSaveFile },
        { type: 'separator' },
        { label: 'Exit', shortcut: 'Alt+F4', action: () => window.close() }
      ]
    },
    edit: {
      label: 'Edit',
      items: [
        { label: 'Undo', shortcut: 'Ctrl+Z', action: onUndo },
        { label: 'Redo', shortcut: 'Ctrl+Y', action: onRedo },
        { type: 'separator' },
        { label: 'Cut', shortcut: 'Ctrl+X', action: () => document.execCommand('cut') },
        { label: 'Copy', shortcut: 'Ctrl+C', action: onCopy },
        { label: 'Paste', shortcut: 'Ctrl+V', action: onPaste },
        { type: 'separator' },
        { label: 'Find', shortcut: 'Ctrl+F', action: onFind },
        { label: 'Replace', shortcut: 'Ctrl+H', action: onFind }
      ]
    },
    view: {
      label: 'View',
      items: [
        { label: 'Command Palette...', shortcut: 'Ctrl+Shift+P', action: () => alert('Command Palette') },
        { label: 'Explorer', shortcut: 'Ctrl+Shift+E', action: () => setActiveMenu(null) },
        { label: 'Search', shortcut: 'Ctrl+Shift+F', action: () => setActiveMenu(null) },
        { label: 'Source Control', shortcut: 'Ctrl+Shift+G', action: () => setActiveMenu(null) },
        { label: 'Run and Debug', shortcut: 'Ctrl+Shift+D', action: () => setActiveMenu(null) },
        { label: 'Extensions', shortcut: 'Ctrl+Shift+X', action: () => alert('Extensions disabled for security') },
        { type: 'separator' },
        { label: 'Toggle Sidebar', shortcut: 'Ctrl+B', action: () => alert('Toggle Sidebar') },
        { label: 'Toggle Panel', shortcut: 'Ctrl+J', action: () => setTerminalOpen(!terminalOpen) }
      ]
    },
    selection: {
      label: 'Selection',
      items: [
        { label: 'Select All', shortcut: 'Ctrl+A', action: () => document.execCommand('selectAll') },
        { label: 'Expand Selection', shortcut: 'Shift+Alt+Right', action: () => alert('Expand Selection') },
        { label: 'Shrink Selection', shortcut: 'Shift+Alt+Left', action: () => alert('Shrink Selection') },
        { type: 'separator' },
        { label: 'Copy Line Up', shortcut: 'Alt+Shift+Up', action: () => alert('Copy Line Up') },
        { label: 'Copy Line Down', shortcut: 'Alt+Shift+Down', action: () => alert('Copy Line Down') },
        { label: 'Move Line Up', shortcut: 'Alt+Up', action: () => alert('Move Line Up') },
        { label: 'Move Line Down', shortcut: 'Alt+Down', action: () => alert('Move Line Down') }
      ]
    },
    go: {
      label: 'Go',
      items: [
        { label: 'Go to Line...', shortcut: 'Ctrl+G', action: onGoToLine },
        { label: 'Go to File...', shortcut: 'Ctrl+P', action: () => alert('Go to File') },
        { label: 'Go to Symbol in File...', shortcut: 'Ctrl+Shift+O', action: () => alert('Go to Symbol') },
        { type: 'separator' },
        { label: 'Back', shortcut: 'Alt+Left', action: () => alert('Go Back') },
        { label: 'Forward', shortcut: 'Alt+Right', action: () => alert('Go Forward') },
        { label: 'Go to Editor Group', shortcut: 'Ctrl+K Ctrl+Left', action: () => alert('Go to Editor Group') }
      ]
    },
    run: {
      label: 'Run',
      items: [
        { label: 'Start Debugging', shortcut: 'F5', action: onStartDebugging },
        { label: 'Run Without Debugging', shortcut: 'Ctrl+F5', action: () => alert('Run Without Debugging') },
        { label: 'Restart Debugging', shortcut: 'Ctrl+Shift+F5', action: () => alert('Restart Debugging') },
        { label: 'Stop Debugging', shortcut: 'Shift+F5', action: () => alert('Stop Debugging') },
        { type: 'separator' },
        { label: 'Add Configuration...', shortcut: '', action: () => alert('Add Debug Configuration') }
      ]
    },
    terminal: {
      label: 'Terminal',
      items: [
        { label: 'New Terminal', shortcut: 'Ctrl+`', action: onOpenTerminal },
        { label: 'Split Terminal', shortcut: 'Ctrl+Shift+`', action: () => alert('Split Terminal') },
        { type: 'separator' },
        { label: 'Run Active File in Terminal', shortcut: 'Ctrl+Alt+N', action: () => alert('Run Active File') },
        { label: 'Run Selected Text in Terminal', shortcut: 'Ctrl+Alt+N', action: () => alert('Run Selected Text') }
      ]
    },
    help: {
      label: 'Help',
      items: [
        { label: 'Welcome', shortcut: '', action: () => alert('Welcome to AuroraAI!') },
        { label: 'Documentation', shortcut: '', action: () => window.open('https://docs.auroraai.com', '_blank') },
        { label: 'Keyboard Shortcuts Reference', shortcut: 'Ctrl+K Ctrl+S', action: () => alert('Keyboard Shortcuts') },
        { type: 'separator' },
        { label: 'Check for Updates...', shortcut: '', action: () => alert('Checking for updates...') },
        { label: 'About AuroraAI', shortcut: '', action: onAbout }
      ]
    }
  };

  const handleMenuClick = (menuKey: string) => {
    setActiveMenu(activeMenu === menuKey ? null : menuKey);
  };

  const handleMenuItemClick = (action?: () => void) => {
    if (action) action();
    setActiveMenu(null);
  };

  const handleKeyDown = (e: KeyboardEvent) => {
    if (e.key === 'Escape') {
      setActiveMenu(null);
    }
  };

  React.useEffect(() => {
    document.addEventListener('keydown', handleKeyDown);
    return () => document.removeEventListener('keydown', handleKeyDown);
  }, []);

  return (
    <div className="vscode-header">
      <div className="header-titlebar">
        <div className="window-controls">
          <button className="window-control close" aria-label="Close"></button>
          <button className="window-control minimize" aria-label="Minimize"></button>
          <button className="window-control maximize" aria-label="Maximize"></button>
        </div>
        <div className="title">AuroraAI</div>
        <div className="header-actions">
          <button className="header-action" aria-label="Account">👤</button>
        </div>
      </div>
      
      <div className="header-menubar">
        {Object.entries(menuItems).map(([key, menu]) => (
          <div key={key} className="menu-item-container">
            <button
              className={`menu-item ${activeMenu === key ? 'active' : ''}`}
              onClick={() => handleMenuClick(key)}
            >
              {menu.label}
            </button>
            {activeMenu === key && (
              <div className="menu-dropdown">
                {menu.items.map((item, index) => {
                  if (item.type === 'separator') {
                    return <div key={index} className="menu-separator" />;
                  }
                  return (
                    <button
                      key={index}
                      className="menu-dropdown-item"
                      onClick={() => handleMenuItemClick(item.action)}
                    >
                      <span className="menu-item-label">{item.label}</span>
                      {item.shortcut && (
                        <span className="menu-item-shortcut">{item.shortcut}</span>
                      )}
                    </button>
                  );
                })}
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};
