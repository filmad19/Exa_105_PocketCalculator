package com.example.exa_105_pocketcalculator;

import android.widget.Toast;

public class Stack {
    private double[] stack;
    //top of Stack
    private int tos = 0;

    public Stack(int size){
        stack = new double[size];
    }

    public boolean isEmpty(){
        return tos == 0;
    }

    public boolean isFull(){
        return tos == stack.length;
    }

    public void push(double value){
        if(isFull()){
            throw new RuntimeException("Stack is full");
        }
        stack[tos++] = value;
    }

    public double pop(){
        if(isEmpty()){
            throw new RuntimeException("Stack is empty");
        }
        return stack[--tos];

    }

    public void outputStack(){
        for(int i = 0;i < tos;i++){
            System.out.println(stack[i] + "; ");
        }
    }

    public int getTos(){
        return tos;
    }

    public void resetTos(){
        tos = 0;
    }
}

