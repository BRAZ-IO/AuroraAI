export { Editor } from './Editor';

export interface EditorProps {
  content?: string;
  language?: string;
  theme?: 'dark' | 'light';
  onContentChange?: (content: string) => void;
  onSave?: () => void;
  readOnly?: boolean;
  fontSize?: number;
  wordWrap?: boolean;
}
