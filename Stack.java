
/**
 * Write a description of class Stack here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Stack<Type> extends LinkedList<Type>
{
    public Stack()
    {
        super();
    }

    public void push(Type data)
    {
        super.InsertBefore(data);
    }

    public Type pop()
    {
        Type data = super.GetValue();
        super.Remove();
        return data;
    }

    public Type peek()
    {
        Type data = super.GetValue();
        return data;
    }
}
