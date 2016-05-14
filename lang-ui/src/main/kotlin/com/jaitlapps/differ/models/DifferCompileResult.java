package com.jaitlapps.differ.models;

import com.jaitlapps.differ.interpreter.methods.MethodResult;

public class DifferCompileResult {
    private boolean error = false;
    private String textError;
    private MethodResult result;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getTextError() {
        return textError;
    }

    public void setTextError(String textError) {
        this.textError = textError;
    }

    public MethodResult getResult() {
        return result; //Коши второго порядка,
    }

    public void setResult(MethodResult result) {
        this.result = result;
    }
}
