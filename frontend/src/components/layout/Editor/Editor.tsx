import React, { useState, useEffect, useRef } from 'react';
import './Editor.css';

interface EditorProps {
  content?: string;
  language?: string;
  theme?: 'dark' | 'light';
  onContentChange?: (content: string) => void;
  onSave?: () => void;
  readOnly?: boolean;
  fontSize?: number;
  wordWrap?: boolean;
}

export const Editor: React.FC<EditorProps> = ({
  content = '',
  language = 'typescript',
  theme = 'dark',
  onContentChange,
  onSave,
  readOnly = false,
  fontSize = 14,
  wordWrap = true
}) => {
  const [editorContent, setEditorContent] = useState<string>(content);
  const [cursorPosition, setCursorPosition] = useState<{ line: number; column: number }>({ line: 1, column: 1 });
  const [selectedText, setSelectedText] = useState<string>('');
  const textareaRef = useRef<HTMLTextAreaElement>(null);
  const lineNumberRef = useRef<HTMLDivElement>(null);

  // Calculate line numbers
  const getLineNumbers = (text: string): string[] => {
    const lines = text.split('\n');
    return lines.map((_, index) => String(index + 1));
  };

  const handleContentChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const newContent = e.target.value;
    setEditorContent(newContent);
    if (onContentChange) {
      onContentChange(newContent);
    }
    updateCursorPosition();
  };

  const updateCursorPosition = () => {
    if (textareaRef.current) {
      const textarea = textareaRef.current;
      const text = textarea.value.substring(0, textarea.selectionStart);
      const lines = text.split('\n');
      const currentLine = lines.length;
      const currentColumn = lines[lines.length - 1].length + 1;
      setCursorPosition({ line: currentLine, column: currentColumn });
    }
  };

  const handleKeyDown = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
    // Handle common shortcuts
    if (e.ctrlKey || e.metaKey) {
      switch (e.key) {
        case 's':
          e.preventDefault();
          if (onSave) onSave();
          break;
        case 'f':
          e.preventDefault();
          // Trigger search
          break;
        case 'g':
          e.preventDefault();
          // Trigger go to line
          const lineNumber = prompt('Go to line number:');
          if (lineNumber && !isNaN(Number(lineNumber))) {
            goToLine(Number(lineNumber));
          }
          break;
        case 'a':
          e.preventDefault();
          selectAll();
          break;
        case 'z':
          e.preventDefault();
          // Handle undo
          break;
        case 'y':
          e.preventDefault();
          // Handle redo
          break;
      }
    }

    // Handle tab key for indentation
    if (e.key === 'Tab') {
      e.preventDefault();
      insertText('  '); // 2 spaces for indentation
    }

    // Handle enter key for auto-indentation
    if (e.key === 'Enter') {
      e.preventDefault();
      handleEnterKey();
    }

    updateCursorPosition();
  };

  const handleMouseUp = () => {
    updateSelectedText();
    updateCursorPosition();
  };

  const updateSelectedText = () => {
    if (textareaRef.current) {
      const textarea = textareaRef.current;
      const selectedText = textarea.value.substring(textarea.selectionStart, textarea.selectionEnd);
      setSelectedText(selectedText);
    }
  };

  const insertText = (text: string) => {
    if (textareaRef.current) {
      const textarea = textareaRef.current;
      const start = textarea.selectionStart;
      const end = textarea.selectionEnd;
      const newContent = textarea.value.substring(0, start) + text + textarea.value.substring(end);
      setEditorContent(newContent);
      if (onContentChange) {
        onContentChange(newContent);
      }
      
      // Restore cursor position
      setTimeout(() => {
        textarea.selectionStart = textarea.selectionEnd = start + text.length;
        updateCursorPosition();
      }, 0);
    }
  };

  const handleEnterKey = () => {
    if (textareaRef.current) {
      const textarea = textareaRef.current;
      const textBeforeCursor = textarea.value.substring(0, textarea.selectionStart);
      const lines = textBeforeCursor.split('\n');
      const currentLine = lines[lines.length - 1];
      
      // Auto-indentation based on current line
      const indentation = currentLine.match(/^(\s*)/)?.[1] || '';
      const extraIndentation = currentLine.trim().endsWith('{') ? '  ' : '';
      
      insertText('\n' + indentation + extraIndentation);
    }
  };

  const goToLine = (lineNumber: number) => {
    if (textareaRef.current) {
      const textarea = textareaRef.current;
      const lines = textarea.value.split('\n');
      
      if (lineNumber > 0 && lineNumber <= lines.length) {
        let characterCount = 0;
        for (let i = 0; i < lineNumber - 1; i++) {
          characterCount += lines[i].length + 1; // +1 for newline
        }
        
        textarea.focus();
        textarea.setSelectionRange(characterCount, characterCount);
        setCursorPosition({ line: lineNumber, column: 1 });
        
        // Scroll to line
        if (lineNumberRef.current) {
          const lineHeight = fontSize * 1.4; // Approximate line height
          lineNumberRef.current.scrollTop = (lineNumber - 1) * lineHeight;
        }
      }
    }
  };

  const selectAll = () => {
    if (textareaRef.current) {
      const textarea = textareaRef.current;
      textarea.select();
      setSelectedText(textarea.value);
    }
  };

  // Sync scroll between textarea and line numbers
  const handleScroll = () => {
    if (textareaRef.current && lineNumberRef.current) {
      lineNumberRef.current.scrollTop = textareaRef.current.scrollTop;
    }
  };

  useEffect(() => {
    setEditorContent(content);
  }, [content]);

  const lineNumbers = getLineNumbers(editorContent);
  const totalLines = lineNumbers.length;

  return (
    <div className={`editor-container ${theme}`}>
      {/* Line Numbers */}
      <div className="line-numbers" ref={lineNumberRef}>
        {lineNumbers.map((lineNumber, index) => (
          <div 
            key={index} 
            className={`line-number ${cursorPosition.line === index + 1 ? 'active' : ''}`}
          >
            {lineNumber}
          </div>
        ))}
      </div>

      {/* Code Editor */}
      <div className="code-editor">
        <textarea
          ref={textareaRef}
          value={editorContent}
          onChange={handleContentChange}
          onKeyDown={handleKeyDown}
          onMouseUp={handleMouseUp}
          onScroll={handleScroll}
          className={`editor-textarea ${language}`}
          style={{
            fontSize: `${fontSize}px`,
            whiteSpace: wordWrap ? 'pre-wrap' : 'pre'
          }}
          placeholder="Start typing your code here..."
          readOnly={readOnly}
          spellCheck={false}
        />
      </div>

      {/* Status Bar */}
      <div className="editor-status">
        <span className="status-item">
          Ln {cursorPosition.line}, Col {cursorPosition.column}
        </span>
        <span className="status-item">
          {selectedText.length > 0 ? `${selectedText.length} selected` : ''}
        </span>
        <span className="status-item">
          {totalLines} lines
        </span>
        <span className="status-item">
          {language}
        </span>
        <span className="status-item">
          UTF-8
        </span>
      </div>
    </div>
  );
};
