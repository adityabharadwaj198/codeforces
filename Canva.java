import java.util.Stack;

import javax.management.RuntimeErrorException;

public class Canva {

    // string comes from user - math calculation 
    // three operators - 
//     Input:     string        “5 * 4 - 3 - 5 ^ 3”   (infix) 
    // 54*3-53^

// 
//  •  • Output:  number    -108
    // exponentiation > multiply > add  / subtract 
    // positive numbers, decimals, no negatives, no grouping no parenthesis 
    // don't worry about floating point precisions 
    // method takes string in // double out. 
    // Wide space padding -> handle it correctly 
    // 

    // .replace(" ", ""); 
    // 5*4-3-5^3
    // 5*4^3 => 5 * (64) = 
    // 5*4^3*2 =>  20^3*2 => 1600*2 
    // 5.1*400, i = 4*10 + charAt(5) 
    // 5*4-3-5^3 -> [1, 3, 5, 7]
    // -5*4 - 4 - 5
    // [5], lastOperator = "*", i = 2, [20], i = 3, lastOperator = "-", i=4, [20, -3], 
    Integer calculator (String input) {
        Integer answer = 0;
        Stack<Integer> mystack = new Stack<>();
        if (Character.isDigit(input.charAt(0))) {
            throw new RuntimeErrorException(null);
        }
            //  5*4^3*2 -> 5*64*2
            // 1, 3, 5 O(logn)
        String processedInput = ""; // process the input. 
        // '&'
        // 
        // O(number of operators in their order of precendence * O(n*N))
        for (int i=0; i<input.length(); i++) { // o(n) -> o(n*n)
            if (input.charAt(i) == '&') {
                int left = i; 
                int right = i;
                int leftNumber = createNumber(input, true, left);// o(n) // String input, boolean processLeftwards, left; 
                int rightNumber = createNumber(input, false, right); 
                int exponential = (int)Math.pow(leftNumber, rightNumber);
                processedInput += Integer.toString(exponential); 
            } else {
                processedInput += input.charAt(i);
            }
        }


        Character lastOperator = '+';
        Integer number = 0; 
        for (int i=0; i<input.length(); i++) { // O(n)
            if (input.charAt(0) == ' ') {
                continue;
            }
            if (!Character.isDigit(i)) {
                switch(input.charAt(i)) {
                    case '+':
                        mystack.push(number);
                    case '-':
                        mystack.push(-1*number);
                    case '*':
                        mystack.push(mystack.pop() * number);
                    // case '^':
                    //     mystack.push((int)Math.pow(mystack.pop(), number));
                    lastOperator = input.charAt(i);
                    number = 0; 
                    continue; 
                }
            } else {
                number = number * 10 + (input.charAt(i) - '0');
            }
        }

        while (!mystack.isEmpty()) { // O(n)
            answer += mystack.pop();
        }

        return answer;
    }
    
    public static void main (String[] args) {
        
    }
}
