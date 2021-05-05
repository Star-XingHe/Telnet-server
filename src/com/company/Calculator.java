package com.company;

public class Calculator {
    private String s;
    private String ans;

    Calculator(String s)
    {
        s = new String("");
        ans = new String("");
        this.s = "#"+s+"#";
    }
    public String getAns() {
        return ans;
    }

    public String getS() {
        return s;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public void setS(String s) {
        this.s = s;
    }
    public int optrNum(char a)
    {
        int k=7;
        switch (a)
        {
            case '+':k = 0;break;
            case '-':k = 1;break;
            case '*':k = 2;break;
            case '/':k = 3;break;
            case '(':k = 4;break;
            case ')':k = 5;break;
            case '#':k = 6;break;
        }
        return k;
    }
    public char getPriority(char c1, char c2)
    {//规定优先级
	char [][]Priority=new char [][]
        {       // +   -   *    /   (   )   #
                {'>','>','<','<','<','>','>'},// +
                {'>','>','<','<','<','>','>'},// -
                {'>','>','>','>','<','>','>'},// *
                {'>','>','>','>','<','>','>'},// /
                {'<','<','<','<','<','=','0'},// (
                {'>','>','>','>','0','>','>'},// )
                {'<','<','<','<','<','0','='}// #
        };
        int n1, n2;
        n1 = optrNum(c1);//得到符号c1的编号
        n2 = optrNum(c2);
        if (n1 == 7 || n2 == 7)
            return '\0';
        return Priority[n1][n2];
    }
    //简单四则运算
    private static double doubleCal(double a,double b, char operator){
       double res=0.;
        switch(operator){
            case '+':
                res = a+b;
                break;
            case '-':
                res = a-b;
                break;
            case '/':
                res = a/b;
                break;
            case '*':
                res = a*b;
                break;
            default:
                break;
        }
        return res;
    }
    public void calc(){
        MyStack<Double>Opnd = new MyStack<Double>();
        MyStack<Character>Optr = new MyStack<Character>();
        int i=0;
        int len= s.length();
        double d=0;
        while(i<len)
        {
            d = 0.;
            boolean isDig=false;//标记是否是数字
            int f = 0;//记录当前是小数点后几位
            boolean hasDot=false;//标记是否有小数点
            while((s.charAt(i)>='0'&&s.charAt(i)<='9')||s.charAt(i)=='.')
            {
                isDig = true;
                if(s.charAt(i)=='.'){
                    hasDot = true;
                }
                if(!hasDot)
                {
                    d = d*10+s.charAt(i)-'0';
                }
                else
                {
                    f++;
                    Double t =Double.valueOf(s.charAt(i)-'0');//小数部分
                    for(int j=0;j<f;j++)
                    {
                        t = t/10.;
                    }
                    d+=t;
                }
                i++;
            }
            if(isDig)
            {
                Opnd.push(d);
                isDig=false;
            }
            else
            {
                if(Optr.isEmpty())
                {
                    Optr.push(s.charAt(i));
                    i++;
                }
                else
                {
                    char ch = Optr.top();
                    char c = getPriority(ch,s.charAt(i));
                    switch (c){
                        case '<':
                            Optr.push(s.charAt(i));
                            i++;
                            break;
                        case '>':
                            char x = Optr.pop();
                            double b= Opnd.pop();
                            double a=Opnd.pop();
                            double res = doubleCal(a,b,x);
                            Opnd.push(res);
                            break;
                        case '=':
                            Optr.pop();
                            i++;
                            break;
                        default:
                            break;
                    }

                }

            }
        }
        double res = Opnd.pop();
        ans = String.valueOf(res);
        System.out.println(ans);
    }

}
