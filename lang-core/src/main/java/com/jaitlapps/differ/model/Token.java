package com.jaitlapps.differ.model;

public class Token {

    private String sourceString;
    private String capitalizedString;

    private TokenType tokenType;
    private KeywordType keywordType;
    private ExpressionType expressionType;

    private int value;
    private double valueReal;

    private Token prevToken;

    private int startPosition;
    private int endPosition;

    public String getSourceString() {
        return sourceString;
    }

    public void setSourceString(String sourceString) {
        this.sourceString = sourceString;
    }

    public String getCapitalizedString() {
        return capitalizedString;
    }

    public void setCapitalizedString(String capitalizedString) {
        this.capitalizedString = capitalizedString;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public KeywordType getKeywordType() {
        return keywordType;
    }

    public void setKeywordType(KeywordType keywordType) {
        this.keywordType = keywordType;
    }

    public ExpressionType getExpressionType() {
        return expressionType;
    }

    public void setExpressionType(ExpressionType expressionType) {
        this.expressionType = expressionType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getValueReal() {
        return valueReal;
    }

    public void setValueReal(double valueReal) {
        this.valueReal = valueReal;
    }

    public Token getPrevToken() {
        return prevToken;
    }

    public void setPrevToken(Token prevToken) {
        this.prevToken = prevToken;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(int endPosition) {
        this.endPosition = endPosition;
    }
}
