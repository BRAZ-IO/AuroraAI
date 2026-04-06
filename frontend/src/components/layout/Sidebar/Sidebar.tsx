import React, { useState } from 'react';
import { useLocation, Link } from 'react-router-dom';
import './Sidebar.css';

interface SidebarItem {
  id: string;
  icon: string;
  label: string;
  path?: string;
}

interface FileNode {
  name: string;
  type: 'file' | 'folder';
  path: string;
  children?: FileNode[];
  isExpanded?: boolean;
}

interface SidebarProps {
  isCollapsed?: boolean;
  onToggle?: () => void;
}

export type { SidebarProps };

export const Sidebar: React.FC<SidebarProps> = ({ 
  isCollapsed = false,
  onToggle
}) => {
  const location = useLocation();
  const [activePanel, setActivePanel] = useState<string>('explorer');
  const [searchQuery, setSearchQuery] = useState<string>('');
  const [expandedFolders, setExpandedFolders] = useState<Set<string>>(new Set(['src']));
  const [fileTree, setFileTree] = useState<FileNode[]>([
    {
      name: 'src',
      type: 'folder',
      path: 'src',
      isExpanded: expandedFolders.has('src'),
      children: [
        {
          name: 'App.tsx',
          type: 'file',
          path: 'src/App.tsx'
        },
        {
          name: 'index.tsx',
          type: 'file',
          path: 'src/index.tsx'
        },
        {
          name: 'components',
          type: 'folder',
          path: 'src/components',
          isExpanded: expandedFolders.has('src/components'),
          children: [
            {
              name: 'Header.tsx',
              type: 'file',
              path: 'src/components/Header.tsx'
            },
            {
              name: 'Layout.tsx',
              type: 'file',
              path: 'src/components/Layout.tsx'
            },
            {
              name: 'Sidebar',
              type: 'folder',
              path: 'src/components/Sidebar',
              isExpanded: expandedFolders.has('src/components/Sidebar'),
              children: [
                {
                  name: 'Sidebar.tsx',
                  type: 'file',
                  path: 'src/components/Sidebar/Sidebar.tsx'
                },
                {
                  name: 'Sidebar.css',
                  type: 'file',
                  path: 'src/components/Sidebar/Sidebar.css'
                }
              ]
            }
          ]
        },
        {
          name: 'pages',
          type: 'folder',
          path: 'src/pages',
          isExpanded: expandedFolders.has('src/pages'),
          children: [
            {
              name: 'Dashboard.tsx',
              type: 'file',
              path: 'src/pages/Dashboard.tsx'
            },
            {
              name: 'Settings.tsx',
              type: 'file',
              path: 'src/pages/Settings.tsx'
            }
          ]
        }
      ]
    },
    {
      name: 'package.json',
      type: 'file',
      path: 'package.json'
    },
    {
      name: 'README.md',
      type: 'file',
      path: 'README.md'
    },
    {
      name: 'tsconfig.json',
      type: 'file',
      path: 'tsconfig.json'
    }
  ]);
  const [isCreatingFile, setIsCreatingFile] = useState<boolean>(false);
  const [newFileName, setNewFileName] = useState<string>('');
  const [newFilePath, setNewFilePath] = useState<string>('');
  const [contextMenu, setContextMenu] = useState<{ x: number; y: number; node: FileNode } | null>(null);
  const [isEditingFile, setIsEditingFile] = useState<boolean>(false);
  const [editingFile, setEditingFile] = useState<{ path: string; name: string } | null>(null);

  const activityItems: SidebarItem[] = [
    {
      id: 'explorer',
      icon: '📁',
      label: 'Explorer',
      path: '/'
    },
    {
      id: 'search',
      icon: '🔍',
      label: 'Search',
      path: '/search'
    },
    {
      id: 'git',
      icon: '📊',
      label: 'Source Control',
      path: '/git'
    },
    {
      id: 'debug',
      icon: '🐛',
      label: 'Run and Debug',
      path: '/debug'
    }
  ];

  const isActive = (path?: string) => {
    return path && location.pathname === path;
  };

  const handleActivityClick = (itemId: string) => {
    setActivePanel(itemId);
  };

  const toggleFolder = (folderPath: string) => {
    setExpandedFolders(prev => {
      const newSet = new Set(prev);
      if (newSet.has(folderPath)) {
        newSet.delete(folderPath);
      } else {
        newSet.add(folderPath);
      }
      return newSet;
    });

    // Update file tree expanded state
    const updateExpanded = (nodes: FileNode[]): FileNode[] => {
      return nodes.map(node => {
        if (node.path === folderPath) {
          return { ...node, isExpanded: !node.isExpanded };
        }
        if (node.children) {
          return { ...node, children: updateExpanded(node.children) };
        }
        return node;
      });
    };
    setFileTree(updateExpanded(fileTree));
  };

  const createNewFile = () => {
    setIsCreatingFile(true);
    setNewFileName('');
    setNewFilePath('src'); // Default to src folder
  };

  const handleFileCreation = () => {
    if (newFileName.trim()) {
      const fullPath = `${newFilePath}/${newFileName}`;
      
      // Find the parent folder and add the new file
      const addFileToTree = (nodes: FileNode[]): FileNode[] => {
        return nodes.map(node => {
          if (node.path === newFilePath && node.type === 'folder') {
            const newFile: FileNode = {
              name: newFileName,
              type: 'file',
              path: fullPath
            };
            return {
              ...node,
              children: [...(node.children || []), newFile]
            };
          }
          if (node.children) {
            return { ...node, children: addFileToTree(node.children) };
          }
          return node;
        });
      };

      setFileTree(addFileToTree(fileTree));
      setIsCreatingFile(false);
      setNewFileName('');
      setNewFilePath('');
    }
  };

  const cancelFileCreation = () => {
    setIsCreatingFile(false);
    setNewFileName('');
    setNewFilePath('');
  };

  const showContextMenu = (e: React.MouseEvent, node: FileNode) => {
    e.preventDefault();
    e.stopPropagation();
    setContextMenu({ x: e.clientX, y: e.clientY, node });
  };

  const hideContextMenu = () => {
    setContextMenu(null);
  };

  const startEditingFile = (node: FileNode) => {
    if (node.type === 'file') {
      setIsEditingFile(true);
      setEditingFile({ path: node.path, name: node.name });
      hideContextMenu();
    }
  };

  const handleFileEdit = () => {
    if (editingFile && editingFile.name.trim()) {
      const updateFileInTree = (nodes: FileNode[]): FileNode[] => {
        return nodes.map(node => {
          if (node.path === editingFile.path) {
            const newPath = node.path.replace(/\/[^\/]+$/, `/${editingFile.name}`);
            return { ...node, name: editingFile.name, path: newPath };
          }
          if (node.children) {
            return { ...node, children: updateFileInTree(node.children) };
          }
          return node;
        });
      };
      setFileTree(updateFileInTree(fileTree));
      setIsEditingFile(false);
      setEditingFile(null);
    }
  };

  const cancelFileEdit = () => {
    setIsEditingFile(false);
    setEditingFile(null);
  };

  const deleteFile = (node: FileNode) => {
    if (window.confirm(`Delete ${node.name}?`)) {
      const deleteFileFromTree = (nodes: FileNode[]): FileNode[] => {
        return nodes.filter(n => n.path !== node.path).map(n => {
          if (n.children) {
            return { ...n, children: deleteFileFromTree(n.children) };
          }
          return n;
        });
      };
      setFileTree(deleteFileFromTree(fileTree));
      hideContextMenu();
    }
  };

  // Git operations
  const stageFile = (file: string) => {
    setGitStatus(prev => ({
      ...prev,
      modified: prev.modified.filter(f => f !== file),
      staged: [...prev.staged, file]
    }));
  };

  const unstageFile = (file: string) => {
    setGitStatus(prev => ({
      ...prev,
      staged: prev.staged.filter(f => f !== file),
      modified: [...prev.modified, file]
    }));
  };

  const commitChanges = () => {
    if (commitMessage.trim() && gitStatus.staged.length > 0) {
      // Simulate commit
      setTimeout(() => {
        setGitStatus(prev => ({
          modified: prev.modified,
          untracked: prev.untracked,
          staged: []
        }));
        setCommitMessage('');
        setIsCommitting(false);
        alert(`Committed ${gitStatus.staged.length} files: "${commitMessage}"`);
      }, 1000);
      setIsCommitting(true);
    }
  };

  const syncChanges = () => {
    // Simulate sync
    alert('Syncing with remote repository...');
    setTimeout(() => {
      alert('Sync completed successfully!');
    }, 1500);
  };

  const createBranch = () => {
    const branchName = prompt('Enter new branch name:');
    if (branchName && branchName.trim()) {
      setCurrentBranch(branchName);
      alert(`Switched to branch: ${branchName}`);
    }
  };

  // Debug operations
  const [debugConfig, setDebugConfig] = useState('chrome');
  const [isDebugging, setIsDebugging] = useState<boolean>(false);
  const [debugVariables, setDebugVariables] = useState<any[]>([]);

  const startDebugging = () => {
    setIsDebugging(true);
    setDebugVariables([
      { name: 'user', value: 'John Doe', type: 'string' },
      { name: 'isLoggedIn', value: true, type: 'boolean' },
      { name: 'count', value: 42, type: 'number' }
    ]);
    alert(`Starting debug session with ${debugConfig}...`);
  };

  const stopDebugging = () => {
    setIsDebugging(false);
    setDebugVariables([]);
    alert('Debug session stopped');
  };

  const addDebugConfig = () => {
    const configName = prompt('Enter debug configuration name:');
    if (configName && configName.trim()) {
      alert(`Added debug configuration: ${configName}`);
    }
  };

  // Mock search results (simulated search)
  const searchResults = searchQuery ? [
    {
      file: 'src/components/Sidebar/Sidebar.tsx',
      line: 15,
      content: `const activityItems: SidebarItem[] = [`,
      matches: 1
    },
    {
      file: 'src/components/Layout/Layout.tsx',
      line: 8,
      content: `import { Sidebar } from '../Sidebar/Sidebar';`,
      matches: 1
    },
    {
      file: 'src/App.tsx',
      line: 12,
      content: `<Sidebar isCollapsed={isSidebarCollapsed} onToggle={toggleSidebar} />`,
      matches: 1
    }
  ].filter(result => 
    result.file.toLowerCase().includes(searchQuery.toLowerCase()) ||
    result.content.toLowerCase().includes(searchQuery.toLowerCase())
  ) : [];

  // Mock Git status (simulated real Git operations)
  const [gitStatus, setGitStatus] = useState({
    modified: ['src/components/Sidebar/Sidebar.tsx', 'src/App.tsx'],
    untracked: ['src/pages/NewPage.tsx'],
    staged: ['package.json']
  });
  const [currentBranch, setCurrentBranch] = useState<string>('main');
  const [isCommitting, setIsCommitting] = useState<boolean>(false);
  const [commitMessage, setCommitMessage] = useState<string>('');

  // Render file tree recursively
  const renderFileTree = (nodes: FileNode[], depth: number = 0) => {
    return nodes.map((node) => (
      <div key={node.path}>
        {isEditingFile && editingFile?.path === node.path ? (
          <div className="new-file-input">
            <span className="tree-icon">📘</span>
            <input
              type="text"
              className="file-name-input"
              value={editingFile.name}
              onChange={(e) => setEditingFile({ ...editingFile, name: e.target.value })}
              onKeyDown={(e) => {
                if (e.key === 'Enter') {
                  handleFileEdit();
                } else if (e.key === 'Escape') {
                  cancelFileEdit();
                }
              }}
              autoFocus
            />
            <div className="file-input-actions">
              <button className="file-input-btn confirm" onClick={handleFileEdit}>✓</button>
              <button className="file-input-btn cancel" onClick={cancelFileEdit}>✗</button>
            </div>
          </div>
        ) : (
          <div 
            className={`tree-item ${node.type === 'folder' && node.isExpanded ? 'expanded' : ''}`}
            style={{ paddingLeft: `${16 + depth * 16}px` }}
            onClick={() => node.type === 'folder' && toggleFolder(node.path)}
            onContextMenu={(e) => node.type === 'file' && showContextMenu(e, node)}
          >
            <span className="tree-icon">
              {node.type === 'folder' ? (node.isExpanded ? '📂' : '📁') : '📘'}
            </span>
            <span className="tree-label">{node.name}</span>
          </div>
        )}
        {node.type === 'folder' && node.isExpanded && node.children && (
          <div className="tree-children">
            {renderFileTree(node.children, depth + 1)}
          </div>
        )}
      </div>
    ));
  };

  return (
    <div className={`sidebar ${isCollapsed ? 'collapsed' : ''}`} onClick={hideContextMenu}>
      {/* Main Content - Activity + Panel Side by Side */}
      <div className="sidebar-main">
        {/* Activity Bar - Left Side */}
        <div className="activity-bar">
          {activityItems.map((item) => (
            <button
              key={item.id}
              className={`activity-item ${isActive(item.path) || activePanel === item.id ? 'active' : ''}`}
              title={item.label}
              onClick={() => handleActivityClick(item.id)}
            >
              <span className="activity-icon">{item.icon}</span>
            </button>
          ))}
          
          {/* Toggle Button at Bottom */}
          <button 
            className="toggle-btn"
            onClick={onToggle}
            title={isCollapsed ? 'Expand Sidebar' : 'Collapse Sidebar'}
          >
            {isCollapsed ? '▶' : '◀'}
          </button>
        </div>

        {/* Dynamic Panel - Right Side */}
        {!isCollapsed && (
          <>
            {/* Explorer Panel */}
            {activePanel === 'explorer' && (
              <div className="explorer-panel">
                <div className="panel-header">
                  <span className="panel-title">EXPLORER</span>
                  <div className="panel-actions">
                    <button className="panel-action" title="New File" onClick={createNewFile}>📄</button>
                    <button className="panel-action" title="New Folder">📁</button>
                    <button className="panel-action" title="Refresh">🔄</button>
                    <button className="panel-action" title="Collapse All">📂</button>
                  </div>
                </div>
                <div className="file-tree">
                  {isCreatingFile && (
                    <div className="new-file-input">
                      <span className="tree-icon">📘</span>
                      <input
                        type="text"
                        className="file-name-input"
                        placeholder="Filename..."
                        value={newFileName}
                        onChange={(e) => setNewFileName(e.target.value)}
                        onKeyDown={(e) => {
                          if (e.key === 'Enter') {
                            handleFileCreation();
                          } else if (e.key === 'Escape') {
                            cancelFileCreation();
                          }
                        }}
                        autoFocus
                      />
                      <div className="file-input-actions">
                        <button className="file-input-btn confirm" onClick={handleFileCreation}>✓</button>
                        <button className="file-input-btn cancel" onClick={cancelFileCreation}>✗</button>
                      </div>
                    </div>
                  )}
                  {renderFileTree(fileTree)}
                </div>
              </div>
            )}

            {/* Search Panel */}
            {activePanel === 'search' && (
              <div className="search-panel">
                <div className="panel-header">
                  <span className="panel-title">SEARCH</span>
                  <div className="panel-actions">
                    <button className="panel-action" title="Clear Search" onClick={() => setSearchQuery('')}>🗑️</button>
                    <button className="panel-action" title="Collapse All">📂</button>
                  </div>
                </div>
                <div className="search-container">
                  <div className="search-input-container">
                    <span className="search-icon">🔍</span>
                    <input
                      type="text"
                      className="search-input"
                      placeholder="Search"
                      value={searchQuery}
                      onChange={(e) => setSearchQuery(e.target.value)}
                      autoFocus
                    />
                  </div>
                  <div className="search-options">
                    <label className="search-option">
                      <input type="checkbox" className="search-checkbox" />
                      <span>Case Sensitive</span>
                    </label>
                    <label className="search-option">
                      <input type="checkbox" className="search-checkbox" />
                      <span>Whole Word</span>
                    </label>
                    <label className="search-option">
                      <input type="checkbox" className="search-checkbox" />
                      <span>Regex</span>
                    </label>
                  </div>
                </div>
                <div className="search-results">
                  {searchQuery && searchResults.length > 0 ? (
                    <>
                      <div className="results-header">
                        {searchResults.length} results in {new Set(searchResults.map(r => r.file)).size} files
                      </div>
                      {searchResults.map((result, index) => (
                        <div key={index} className="search-result-item">
                          <div className="result-file">
                            <span className="file-icon">📘</span>
                            <span className="file-path">{result.file}</span>
                            <span className="line-number">:{result.line}</span>
                          </div>
                          <div className="result-content">
                            {result.content}
                          </div>
                        </div>
                      ))}
                    </>
                  ) : searchQuery ? (
                    <div className="no-results">
                      No results found for "{searchQuery}"
                    </div>
                  ) : (
                    <div className="search-placeholder">
                      Type to search in files
                    </div>
                  )}
                </div>
              </div>
            )}

            {/* Source Control Panel */}
            {activePanel === 'git' && (
              <div className="panel-placeholder">
                <div className="panel-header">
                  <span className="panel-title">SOURCE CONTROL</span>
                  <div className="panel-actions">
                    <button 
                      className="panel-action" 
                      title="Commit" 
                      onClick={commitChanges}
                      disabled={gitStatus.staged.length === 0 || !commitMessage.trim()}
                    >
                      📤
                    </button>
                    <button className="panel-action" title="Sync" onClick={syncChanges}>🔄</button>
                    <button className="panel-action" title="Branch" onClick={createBranch}>🌿</button>
                  </div>
                </div>
                <div className="git-container">
                  {/* Commit Message */}
                  <div className="commit-section">
                    <input
                      type="text"
                      className="commit-input"
                      placeholder="Message"
                      value={commitMessage}
                      onChange={(e) => setCommitMessage(e.target.value)}
                      onKeyDown={(e) => {
                        if (e.key === 'Enter' && !e.shiftKey) {
                          commitChanges();
                        }
                      }}
                    />
                    <button 
                      className="commit-btn"
                      onClick={commitChanges}
                      disabled={gitStatus.staged.length === 0 || !commitMessage.trim()}
                    >
                      {isCommitting ? '⏳' : '✓'}
                    </button>
                  </div>

                  {/* Branch Info */}
                  <div className="branch-info">
                    <span className="branch-label">Branch:</span>
                    <span className="branch-name">{currentBranch}</span>
                  </div>

                  {/* Changes */}
                  {gitStatus.modified.length > 0 && (
                    <div className="git-section">
                      <div className="git-section-title">Changes</div>
                      {gitStatus.modified.map((file, index) => (
                        <div key={index} className="git-item modified">
                          <span className="git-icon">📝</span>
                          <span className="git-file">{file}</span>
                          <button 
                            className="git-stage-btn"
                            onClick={() => stageFile(file)}
                            title="Stage file"
                          >
                            +
                          </button>
                        </div>
                      ))}
                    </div>
                  )}
                  
                  {/* Staged Changes */}
                  {gitStatus.staged.length > 0 && (
                    <div className="git-section">
                      <div className="git-section-title">Staged Changes</div>
                      {gitStatus.staged.map((file, index) => (
                        <div key={index} className="git-item staged">
                          <span className="git-icon">✅</span>
                          <span className="git-file">{file}</span>
                          <button 
                            className="git-unstage-btn"
                            onClick={() => unstageFile(file)}
                            title="Unstage file"
                          >
                            -
                          </button>
                        </div>
                      ))}
                    </div>
                  )}
                  
                  {/* Untracked Files */}
                  {gitStatus.untracked.length > 0 && (
                    <div className="git-section">
                      <div className="git-section-title">Untracked Files</div>
                      {gitStatus.untracked.map((file, index) => (
                        <div key={index} className="git-item untracked">
                          <span className="git-icon">➕</span>
                          <span className="git-file">{file}</span>
                          <button 
                            className="git-stage-btn"
                            onClick={() => stageFile(file)}
                            title="Stage file"
                          >
                            +
                          </button>
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              </div>
            )}

            {/* Debug Panel */}
            {activePanel === 'debug' && (
              <div className="panel-placeholder">
                <div className="panel-header">
                  <span className="panel-title">RUN AND DEBUG</span>
                  <div className="panel-actions">
                    <button 
                      className="panel-action" 
                      title={isDebugging ? "Stop Debugging" : "Run and Debug"} 
                      onClick={isDebugging ? stopDebugging : startDebugging}
                    >
                      {isDebugging ? '⏹️' : '▶️'}
                    </button>
                    <button className="panel-action" title="Add Configuration" onClick={addDebugConfig}>⚙️</button>
                  </div>
                </div>
                <div className="debug-container">
                  {/* Launch Configurations */}
                  <div className="debug-section">
                    <div className="debug-section-title">Launch Configurations</div>
                    <select 
                      className="debug-config-select"
                      value={debugConfig}
                      onChange={(e) => setDebugConfig(e.target.value)}
                      disabled={isDebugging}
                    >
                      <option value="chrome">🌐 Launch Chrome</option>
                      <option value="node">🚀 Launch Node.js</option>
                      <option value="firefox">🦊 Launch Firefox</option>
                    </select>
                  </div>

                  {/* Debug Variables */}
                  <div className="debug-section">
                    <div className="debug-section-title">
                      Variables {isDebugging && `(${debugVariables.length})`}
                    </div>
                    <div className="debug-variables">
                      {isDebugging ? (
                        debugVariables.map((variable, index) => (
                          <div key={index} className="debug-variable">
                            <span className="var-name">{variable.name}:</span>
                            <span className={`var-value var-${variable.type}`}>
                              {typeof variable.value === 'boolean' 
                                ? (variable.value ? 'true' : 'false')
                                : String(variable.value)
                              }
                            </span>
                          </div>
                        ))
                      ) : (
                        <div className="no-debug-message">
                          No debugging session active
                        </div>
                      )}
                    </div>
                  </div>

                  {/* Debug Actions */}
                  {isDebugging && (
                    <div className="debug-section">
                      <div className="debug-section-title">Debug Actions</div>
                      <div className="debug-actions">
                        <button className="debug-action-btn" onClick={() => alert('Stepping over...')}>
                          ⏭️ Step Over
                        </button>
                        <button className="debug-action-btn" onClick={() => alert('Stepping into...')}>
                          ⤵️ Step Into
                        </button>
                        <button className="debug-action-btn" onClick={() => alert('Continuing...')}>
                          ⏩️ Continue
                        </button>
                        <button className="debug-action-btn stop" onClick={stopDebugging}>
                          ⏹️ Stop
                        </button>
                      </div>
                    </div>
                  )}
                </div>
              </div>
            )}
          </>
        )}
      </div>

      {/* Context Menu */}
      {contextMenu && (
        <div 
          className="context-menu"
          style={{ left: contextMenu.x, top: contextMenu.y }}
          onClick={(e) => e.stopPropagation()}
        >
          <button className="context-menu-item" onClick={() => startEditingFile(contextMenu.node)}>
            ✏️ Rename
          </button>
          <button className="context-menu-item delete" onClick={() => deleteFile(contextMenu.node)}>
            🗑️ Delete
          </button>
        </div>
      )}
    </div>
  );
};
