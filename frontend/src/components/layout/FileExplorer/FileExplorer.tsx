import React, { useState } from 'react';
import './FileExplorer.css';

interface FileNode {
  id: string;
  name: string;
  type: 'file' | 'folder';
  children?: FileNode[];
  language?: string;
}

interface FileExplorerProps {
  isCollapsed?: boolean;
}

export const FileExplorer: React.FC<FileExplorerProps> = ({ 
  isCollapsed = false 
}) => {
  const [expandedFolders, setExpandedFolders] = useState<Set<string>>(new Set(['src']));

  const fileTree: FileNode[] = [
    {
      id: 'src',
      name: 'src',
      type: 'folder',
      children: [
        {
          id: 'components',
          name: 'components',
          type: 'folder',
          children: [
            {
              id: 'App.tsx',
              name: 'App.tsx',
              type: 'file',
              language: 'typescript'
            },
            {
              id: 'index.tsx',
              name: 'index.tsx',
              type: 'file',
              language: 'typescript'
            }
          ]
        },
        {
          id: 'pages',
          name: 'pages',
          type: 'folder',
          children: [
            {
              id: 'Dashboard.tsx',
              name: 'Dashboard.tsx',
              type: 'file',
              language: 'typescript'
            },
            {
              id: 'Settings.tsx',
              name: 'Settings.tsx',
              type: 'file',
              language: 'typescript'
            }
          ]
        },
        {
          id: 'styles.css',
          name: 'styles.css',
          type: 'file',
          language: 'css'
        }
      ]
    },
    {
      id: 'package.json',
      name: 'package.json',
      type: 'file',
      language: 'json'
    },
    {
      id: 'README.md',
      name: 'README.md',
      type: 'file',
      language: 'markdown'
    }
  ];

  const toggleFolder = (folderId: string) => {
    const newExpanded = new Set(expandedFolders);
    if (newExpanded.has(folderId)) {
      newExpanded.delete(folderId);
    } else {
      newExpanded.add(folderId);
    }
    setExpandedFolders(newExpanded);
  };

  const getFileIcon = (fileName: string, type: string) => {
    if (type === 'folder') {
      return '📁';
    }
    
    const ext = fileName.split('.').pop()?.toLowerCase();
    switch (ext) {
      case 'tsx':
      case 'ts':
        return '📘';
      case 'jsx':
      case 'js':
        return '📜';
      case 'css':
        return '🎨';
      case 'json':
        return '📋';
      case 'md':
        return '📝';
      default:
        return '📄';
    }
  };

  const renderFileNode = (node: FileNode, level: number = 0) => {
    const isExpanded = expandedFolders.has(node.id);
    const paddingLeft = level * 16;

    if (node.type === 'folder') {
      return (
        <div key={node.id}>
          <div
            className="file-explorer-item folder"
            style={{ paddingLeft: `${paddingLeft + 8}px` }}
            onClick={() => toggleFolder(node.id)}
          >
            <span className="file-icon">
              {isExpanded ? '📂' : '📁'}
            </span>
            <span className="file-name">{node.name}</span>
          </div>
          {isExpanded && node.children && (
            <div className="file-children">
              {node.children.map(child => renderFileNode(child, level + 1))}
            </div>
          )}
        </div>
      );
    }

    return (
      <div
        key={node.id}
        className="file-explorer-item file"
        style={{ paddingLeft: `${paddingLeft + 8}px` }}
      >
        <span className="file-icon">
          {getFileIcon(node.name, node.type)}
        </span>
        <span className="file-name">{node.name}</span>
      </div>
    );
  };

  if (isCollapsed) {
    return (
      <div className="file-explorer collapsed">
        <div className="explorer-header">
          <span>📁</span>
        </div>
      </div>
    );
  }

  return (
    <div className="file-explorer">
      <div className="explorer-header">
        <span>📁 EXPLORER</span>
      </div>
      <div className="file-tree">
        {fileTree.map(node => renderFileNode(node))}
      </div>
    </div>
  );
};
