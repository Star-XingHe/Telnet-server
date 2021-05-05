package com.company;

import java.util.Arrays;

public class MyStack<T>{
    private Object[]stack;
    private int top;
    MyStack()
    {
        stack =new Object[10];
        top=0;
    }
    public boolean isEmpty()
    {
        return top==0;
    }
    public T top(){
        T t=null;
        if(top>0)
        {
            t = (T)stack[top-1];
        }
        return t;
    }
    public void push(T t)
    {
        expandCapacity(top+1);
        stack[top]=t;
        top++;
    }
    public T pop()
    {
        T t = top();
        if(top>0)
        {
            stack[top-1]=null;
            top--;
        }
        return t;
    }
    public void expandCapacity(int size)
    {
        int len=stack.length;
        if(size>len)
        {
            size = size*3/2+1;//每次扩大50%
            stack = Arrays.copyOf(stack,size);
        }
    }

}
