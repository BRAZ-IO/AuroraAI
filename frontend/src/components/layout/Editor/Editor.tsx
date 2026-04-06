import React, { useState, useRef, useEffect, useCallback } from 'react';
import './Editor.css';

interface EditorProps {
  content: string;
  language: string;
  theme: string;
  onContentChange: (content: string) => void;
  onSave: () => void;
  fontSize: number;
  wordWrap: boolean;
  onSelectionChange?: (selection: string, start: number, end: number) => void;
  onCursorChange?: (position: { line: number; column: number }) => void;
}

export const Editor: React.FC<EditorProps> = ({
  content,
  language,
  theme,
  onContentChange,
  onSave,
  fontSize,
  wordWrap,
  onSelectionChange,
  onCursorChange
}) => {
  const [lines, setLines] = useState<string[]>([]);
  const [cursorPosition, setCursorPosition] = useState({ line: 1, column: 1 });
  const [selectedText, setSelectedText] = useState('');
  const textareaRef = useRef<HTMLTextAreaElement>(null);
  const lineNumberRef = useRef<HTMLDivElement>(null);

  // Parse content into lines
  useEffect(() => {
    const parsedLines = content.split('\n');
    setLines(parsedLines);
  }, [content]);

  // Handle content change
  const handleChange = useCallback((e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const newContent = e.target.value;
    onContentChange(newContent);
    
    // Update cursor position
    const textarea = e.target;
    const cursorPos = textarea.selectionStart;
    const textBeforeCursor = newContent.substring(0, cursorPos);
    const linesBeforeCursor = textBeforeCursor.split('\n');
    const currentLine = linesBeforeCursor.length;
    const currentColumn = linesBeforeCursor[linesBeforeCursor.length - 1].length + 1;
    
    const newPosition = { line: currentLine, column: currentColumn };
    setCursorPosition(newPosition);
    onCursorChange?.(newPosition);
  }, [onContentChange, onCursorChange]);

  // Handle selection change
  const handleSelectionChange = useCallback(() => {
    if (!textareaRef.current) return;
    
    const textarea = textareaRef.current;
    const start = textarea.selectionStart;
    const end = textarea.selectionEnd;
    const selected = content.substring(start, end);
    
    setSelectedText(selected);
    onSelectionChange?.(selected, start, end);
  }, [content, onSelectionChange]);

  // Handle keyboard shortcuts
  const handleKeyDown = useCallback((e: React.KeyboardEvent<HTMLTextAreaElement>) => {
    // Ctrl/Cmd + S: Save
    if ((e.ctrlKey || e.metaKey) && e.key === 's') {
      e.preventDefault();
      onSave();
    }
    
    // Ctrl/Cmd + A: Select All
    if ((e.ctrlKey || e.metaKey) && e.key === 'a') {
      e.preventDefault();
      if (textareaRef.current) {
        textareaRef.current.select();
        setSelectedText(content);
      }
    }
    
    // Tab: Insert spaces
    if (e.key === 'Tab') {
      e.preventDefault();
      const textarea = e.target as HTMLTextAreaElement;
      const start = textarea.selectionStart;
      const end = textarea.selectionEnd;
      const spaces = '  '; // 2 spaces for TypeScript
      const newContent = content.substring(0, start) + spaces + content.substring(end);
      onContentChange(newContent);
      
      // Restore cursor position
      setTimeout(() => {
        textarea.selectionStart = textarea.selectionEnd = start + spaces.length;
      }, 0);
    }
    
    // Handle selection change
    handleSelectionChange();
  }, [onSave, content, onContentChange, handleSelectionChange]);

  // Handle mouse up for selection
  const handleMouseUp = useCallback(() => {
    handleSelectionChange();
  }, [handleSelectionChange]);

  // Sync scroll between textarea and line numbers
  const handleScroll = useCallback(() => {
    if (textareaRef.current && lineNumberRef.current) {
      lineNumberRef.current.scrollTop = textareaRef.current.scrollTop;
    }
  }, []);

  // Get line numbers
  const getLineNumbers = () => {
    return lines.map((_, index) => index + 1).join('\n');
  };

  return (
    <div className="editor-container">
      <div className="editor-main">
        <div className="editor-header">
          <div className="editor-tabs">
            <div className="tab active">
              <span className="tab-icon">📄</span>
              <span className="tab-name">index.ts</span>
              <button className="tab-close">×</button>
            </div>
          </div>
          <div className="editor-actions">
            <button className="action-button" onClick={onSave} title="Save (Ctrl+S)">
              💾 Save
            </button>
            <button className="action-button" title="Find (Ctrl+F)">
              🔍 Find
            </button>
            <button className="action-button" title="Go to Line (Ctrl+G)">
              ➡️ Go to Line
            </button>
          </div>
        </div>
        
        <div className="editor-content">
          <div className="line-numbers" ref={lineNumberRef}>
            <pre>{getLineNumbers()}</pre>
          </div>
          
          <textarea
            ref={textareaRef}
            value={content}
            onChange={handleChange}
            onKeyDown={handleKeyDown}
            onMouseUp={handleMouseUp}
            onScroll={handleScroll}
            onSelect={handleSelectionChange}
            className="editor-textarea"
            style={{
              fontSize: `${fontSize}px`,
              fontFamily: 'Consolas, Monaco, "Courier New", monospace',
              resize: 'none',
              overflow: 'auto',
              whiteSpace: 'pre',
              tabSize: 2
            }}
            placeholder="Start typing your code here..."
            spellCheck={false}
            data-gramm="false"
          />
        </div>
        
        <div className="editor-footer">
          <div className="status-left">
            <span className="cursor-position">
              Line {cursorPosition.line}, Column {cursorPosition.column}
            </span>
            {selectedText && (
              <span className="selection-info">
                • {selectedText.length} characters selected
              </span>
            )}
          </div>
          <div className="status-right">
            <span className="language">{language}</span>
            <span className="encoding">UTF-8</span>
            <span className="eol">LF</span>
          </div>
        </div>
      </div>
    </div>
  );
};
