
/**
 * Write a description of class Postfix here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;

public class Postfix
{
    // method to convert an infix equation to postfix
    // private due to the main method being within the same class
    // takes in an infix formula from the user
    private static void convertInToPost(String form)
    {
        // create a list of operators that will be found in formulas
        ArrayList<String> operList = new ArrayList<String>(5);
        operList.add("+");
        operList.add("-");
        operList.add("*");
        operList.add("/");
        operList.add("^");

        // gets rid of any spaces that may have been typed into the infix formula
        String formReplace = form.replaceAll(" ", "");

        // splits each term up into a string array to be put into a queue made of the infix formula
        String[] terms = formReplace.split("");

        Queue<String> infix = new Queue<String>();
        for(String s: terms)
        {
            infix.enqueue(s);
        }

        Queue<String> postfix = new Queue<String>();
        Stack<String> operators = new Stack<String>();

        while(!infix.IsEmpty())
        {
            // keep track of each element being dequeued from the infix queue
            String infixDe = infix.dequeue();

            // if the element dequequed is an operator
            if(operList.contains(infixDe))
            {
                // if that element is a + or a - (b/c they are the same precedence)
                if(infixDe.equals("+") || infixDe.equals("-"))
                {
                    if(operators.peek() != null)
                    {
                        while(operators.peek() != null)
                        {
                            // if the operator at the peek of the stack is of the same or higher precedence then pop until it's not
                            if(operators.peek().equals("+") || operators.peek().equals("-") || operators.peek().equals("*") || operators.peek().equals("/") || operators.peek().equals("^"))
                            {
                                postfix.enqueue(operators.pop());
                            }
                            else
                            {
                                break;
                            }
                        }

                        operators.push(infixDe);
                    }
                    else
                    {
                        operators.push(infixDe);
                    }
                }

                // if that element is a * or a / (b/c they are the same precedence)
                else if(infixDe.equals("*") || infixDe.equals("/"))
                {
                    if(operators.peek() != null)
                    {
                        while(operators.peek() != null)
                        {
                            // if the operator at the peek of the stack is of the same or higher precedence then pop until it's not
                            if(operators.peek().equals("*") || operators.peek().equals("/") || operators.peek().equals("^"))
                            {
                                postfix.enqueue(operators.pop());
                            }
                            else
                            {
                                break;
                            }
                        }

                        operators.push(infixDe);
                    }
                    else
                    {
                        operators.push(infixDe);
                    }
                }

                // if that element is a ^ (highest operator precedence)
                else if(infixDe.equals("^"))
                {
                    operators.push(infixDe);
                }
            }

            // if the element is a parenthesis of either kind
            else if(infixDe.equals("(") || infixDe.equals(")"))
            {
                if(infixDe.equals("("))
                {
                    operators.push(infixDe);
                }
                else if(infixDe.equals(")"))
                {
                    // pop all operators on the stack until the first occurence of an opening parenthesis
                    while(!operators.peek().equals("("))
                    {
                        postfix.enqueue(operators.pop());
                    }

                    // pop the opening parenthesis of the stack
                    operators.pop();
                }
            }

            // if the element dequeued is a variable
            else
            {
                postfix.enqueue(infixDe);
            }
        }

        // empty the operators stack if it isn't empty yet
        while(!operators.IsEmpty())
        {
            postfix.enqueue(operators.pop());
        }

        // print the postfix conversion
        System.out.println("Postfix Version: " + postfix);

        // call the method to evaluate the postfix formula 
        evaluatePost(postfix, operList);
    }

    private static void evaluatePost(Queue<String> post, ArrayList<String> operList)
    {
        // create a queue that will have the same postfix formula but the variables will be replaced with numbers
        Queue<String> newPost = new Queue<String>();
        
        // while the original postfix queue isn't empty
        while(!post.IsEmpty())
        {
            // keep track of each element being dequeued from the postfix queue
            String postDe = post.dequeue();
            
            // enqueue the element is an operator
            if(operList.contains(postDe))
            {
                newPost.enqueue(postDe);
            }
            
            // if the element isn't an operator replace the variables with numbers
            else
            {
                Scanner input = new Scanner(System.in);
                System.out.println("What does " + postDe + " equal?");
                String num = input.nextLine();
                
                // if the input isn't a number, the user must retry until it is
                while(!num.matches("[0-9]+"))
                {
                    System.out.println("Your input must be a number. Please try again: ");
                    num = input.nextLine();
                }

                newPost.enqueue(num);
            }
        }
        
        // create a stack for the numbers to be evaluated
        Stack<Integer> intPost = new Stack<Integer>();

        while(!newPost.IsEmpty())
        {
            // keep track of each element being dequeued from the newPost queue
            String newPostDe = newPost.dequeue();
            
            // if the element isn't an operator, parse it into an integer and push the element onto the stack
            if(!operList.contains(newPostDe))
            {
                int strToInt = Integer.parseInt(newPostDe);
                intPost.push(strToInt);
            }
            
            // if the element is an operator, evaluate the first two elements in the stack
            else
            {
                int num2 = intPost.pop();
                int num1 = intPost.pop();

                if(newPostDe.equals("+"))
                {
                    intPost.push(num1 + num2);
                }
                else if(newPostDe.equals("-"))
                {
                    intPost.push(num1 - num2);
                }
                else if(newPostDe.equals("*"))
                {
                    intPost.push(num1 * num2);
                }
                else if(newPostDe.equals("/"))
                {
                    intPost.push(num1 / num2);
                }
                else if(newPostDe.equals("^"))
                {
                    intPost.push((int)Math.pow(num1, num2));
                }
            }
        }
        
        // pop the only value in the stack (the result)
        int result = intPost.pop();

        // print the result to user
        System.out.println("Result: " + result);
    }

    public static void main(String args[])
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a variable and operator only formula in infix form: ");
        String form = input.nextLine();
        
        // if the formula has any numbers or anything other than letters and operators in it, make the user retry until it doesn't
        while(!form.matches("[a-zA-Z+[-]/*^[(][)]]+"))
        {
            System.out.println("The formula must be varaibles and operators only. Please try again: ");
            form = input.nextLine();
        }
        
        // call for the formula to be converted
        convertInToPost(form);
    }
}
