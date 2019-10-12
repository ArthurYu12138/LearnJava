import java.util.*;

public class Main{
    public static void main(String[] args){
        String InFix = "a+b*c+(d*e+f)*g";
        String kkk = InFixToReversePolish(InFix);
        System.out.println(kkk);
        System.out.println(ReversePolishToInFix(kkk));
    }
    static String InFixToReversePolish(String InFix){
        Deque<String> stk1 = new LinkedList<>();
        String result = "";
        for(int i = 0; i < InFix.length(); i++){
            String tmp = String.valueOf(InFix.charAt(i));
            //System.out.println(result);
            //System.out.println(stk1);
            if(!isOperator(tmp)){
                result = result + tmp;
            }
            else{
                if(tmp.equals("(")){
                    stk1.push(tmp);
                }
                else if(/*stk1.peek() != null && */tmp.equals(")")){
                    //System.out.println(stk1);
                    //System.out.println(result);
                    while(/*stk1.peek() != null && */stk1.peek().equals("(") == false){
                        result = result + stk1.pop();
                    }
                    //System.out.println(stk1);
                    if(/*stk1.peek() != null && */stk1.peek().equals("(")){
                        stk1.pop();
                    }
                }
                else{
                    if(stk1.peek() != null && stk1.peek().equals("(")){
                        stk1.push(tmp);
                    }
                    else{
                        while(stk1.peek() != null && LowerOrEqual(tmp, stk1.peek())){
                            result = result + stk1.pop();
                        }
                        stk1.push(tmp);
                    }

                }

            }
        }
        while(stk1.peek() != null){
            result = result + stk1.pop();
        }
        return result;
    }
    static boolean LowerOrEqual(String oa, String ob){
        List<String> high = List.of("*", "/");
        List<String> low = List.of("+", "-");
        if((high.contains(oa) & low.contains(ob)) || ob.equals("(")){
            return false;
        }
        else{
            return true;
        }
    }
    static boolean isOperator(String str){
        List<String> op = List.of("*", "/", "+", "-", "(", ")");
        if(op.contains(str)){
            return true;
        }
        else{
            return false;
        }
    }
    static String ReversePolishToInFix(String RP){
        Deque<String> stk2 = new LinkedList<>();
        String result = "";
        for(int i = 0; i < RP.length(); i++){
            String tmp = String.valueOf(RP.charAt(i));
            if(!isOperator(tmp)){
                stk2.push(tmp);
            }
            else{
                String oprand2 = stk2.pop();
                String oprand1 = stk2.pop();
                String newtop = "(" + oprand1 + tmp + oprand2 + ")";
                stk2.push(newtop);
            }
        }
        result = stk2.pop();
        return result;
    }
}




