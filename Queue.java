
/**
 * Write a description of class Queue here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Queue<Type> extends LinkedList<Type>
{
    public Queue()
    {
        super();
    }
    
    public void enqueue(Type data)
    {
        super.InsertAfter(data);
    }
    
    public Type dequeue()
    {
        super.First();
        Type data = super.GetValue();
        super.Remove();
        return data;
    }
}
