/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cside.ui;

/**
 *
 * @author Ali
 */
public class IOContainer {
    String commaInputTransfer = "";
    boolean newInput = false;
    String compilerOutputTransfer = "";
    boolean newOutput = false;
    
    public String getCommaInput() {
        newInput = false;
        return commaInputTransfer;
    }
    
    public void newCommaInput(String input) {
        commaInputTransfer = input;
        newInput = true;
    }
    
    public String getCompilerOutput() {
        newOutput = false;
        return compilerOutputTransfer;
    }
    
    public void newCompilerOutput(String input) {
        commaInputTransfer = input;
        newOutput = true;
    }
}
