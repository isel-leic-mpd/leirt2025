package pt.isel.mpd.reflection2.expressions.parser;

/**
 * Created by jmartins on 14/3/2021
 */
public class Lex {
    private String line;
    private int pos, startPos;
    private char curr;
    
    private class KeyWord {
        String name;
        TokType type;
    }
    
    public enum TokType {
        OP_ADD, OP_MINUS, OP_MUL, OP_DIV, OPEN_BRACKET, CLOSE_BRACKET,
        WORD, NUMBER, KEY_SET, NONE, INVALID, END, RANGE_SEP
    }
    
    public  class Token {
        public final TokType type;
        private  String word;
        private double number;
        private int col;
        
        public Token(double value) {
            this.number = value ;
            this.type = TokType.NUMBER;
            this.col = startPos;
        }
        
        public Token(String name) {
            this.word = name ;
            this.type = TokType.WORD;
            this.col = startPos;
        }
        
        public Token(TokType type) {
            this.type = type;
            this.col = startPos;
        }
        
        public void show() {
            if (type == TokType.NUMBER) System.out.format("Number(%d): %f\n", col, number );
            else if(type == TokType.WORD) System.out.format("Word(%d): %s\n", col, word );
            else System.out.format("%s(%d)\n", type, col);
        }
        
        public boolean isNumber() {
            return  type == TokType.NUMBER;
        }
        
        public boolean isWord() {
            return  type == TokType.WORD;
        }
        
        public String getWord() { return word; }
        
        public double getNumber() { return number; }
        
        public TokType getType() { return type; }
        
    }
    
    public Lex() {
        pos=-1;
    }
    
    public void start(String line) {
        this.line = line.toUpperCase();
        pos=0;
        nextChar();
    }
    
    private void nextChar() {
        if (pos < line.length())
            curr = line.charAt(pos++);
        else
            curr = '\0';
    }
    
    private void ignoreSpaces() {
        while(Character.isSpaceChar(curr))
            nextChar();
    }
    
    private void buildDouble(StringBuffer sb) {
        do {
            sb.append(curr);
            nextChar();
        }
        while(Character.isDigit(curr) || curr == '.');
    }
    
    
    private double getNumber() {
        StringBuffer sb = new StringBuffer();
        buildDouble(sb);
        return Double.parseDouble(sb.toString());
    }
    
    private String getWord() {
        StringBuffer sb = new StringBuffer();
        do {
            sb.append(curr);
            nextChar();
        } while (Character.isLetterOrDigit(curr));
        return sb.toString();
    }
    
    private TokType getKeyWord(String word) {
        if (word.equals("set")) return TokType.KEY_SET;
        return TokType.NONE;
    }
    
    public Token next() {
        Token tok = null;
        ignoreSpaces();
        startPos = pos-1;
        
        if (Character.isDigit(curr)) {
            tok = new Token(getNumber());
        }
        else if(Character.isLetter(curr)) {
            String w = getWord();
            TokType t;
            if ((t = getKeyWord(w)) == TokType.NONE)
                tok = new Token(w);
            else
                tok = new Token(t);
        }
        else {
            switch(curr) {
                case '+':
                    tok = new Token(TokType.OP_ADD);
                    break;
                case '-':
                    tok = new Token(TokType.OP_MINUS);
                    break;
                case '*':
                    tok = new Token(TokType.OP_MUL);
                    break;
                case '/':
                    tok = new Token(TokType.OP_DIV);
                    break;
                case '(':
                    tok = new Token(TokType.OPEN_BRACKET);
                    break;
                case ')':
                    tok = new Token(TokType.CLOSE_BRACKET);
                    break;
                case ':':
                    tok = new Token(TokType.RANGE_SEP);
                    break;
                case '\0':
                    return new Token(TokType.END);
            }
            nextChar();
        }
        if (tok == null) tok = new Token(TokType.INVALID);
        return tok;
    }
}