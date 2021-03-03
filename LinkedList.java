/* ***************************************************
 * <your name>
 * <the date>
 *
 * CopyOfLinkedList Class - handles any form of data
 *************************************************** */

public class LinkedList<Type>
{
    // We don't actually have to set a max size with linked LinkedLists
    // But it is a good idea.
    // Just picture an infinite loop adding to the LinkedList! :O
    public static final int MAX_SIZE = 50;

    private Node<Type> head;
    private Node<Type> tail;
    private Node<Type> curr;
    private int num_items;

    // constructor
    // remember that an empty LinkedList has a "size" of -1 and its "position" is at -1
    public LinkedList()
    {
        this.head = this.tail = this.curr = null;
        this.num_items = 0;
    }

    // copy constructor
    // clones the LinkedList l and sets the last element as the current
    public LinkedList(LinkedList<Type> l)
    {
        Node<Type> n = new Node();
        n.setLink(l.head.getLink());

        this.head = this.tail = this.curr = null;
        this.num_items = 0;
        
        while (n.getLink() != null)
        {
            this.InsertAfter(n.getLink().getData());
            n.setLink(n.getLink().getLink());
        }
    }

    // navigates to the beginning of the LinkedList
    public void First()
    {
        this.curr.setLink(this.head.getLink());
    }

    // navigates to the end of the LinkedList
    // the end of the LinkedList is at the last valid item in the LinkedList
    public void Last()
    {
        this.curr.setLink(this.tail.getLink());
    }

    // navigates to the specified element (0-index)
    // this should not be possible for an empty LinkedList
    // this should not be possible for invalid positions
    public void SetPos(int pos)
    {
        if(IsEmpty() == false && pos < GetSize() && pos >= 0)
        {
            Node<Type> temp = new Node();
            temp.setLink(this.head.getLink());
                
            for(int i = 0; i != pos; i++)
            {
                temp.setLink(temp.getLink().getLink());
            }
                
            this.curr.setLink(temp.getLink());
        }
    }

    // navigates to the previous element
    // this should not be possible for an empty LinkedList
    // there should be no wrap-around
    public void Prev()
    {
        if(IsEmpty() == false)
        {
            if(this.GetSize() > 1)
            {
                SetPos(GetPos() - 1);
            }
        }
    }

    // navigates to the next element
    // this should not be possible for an empty LinkedList
    // there should be no wrap-around
    public void Next()
    {
        if(IsEmpty() == false)
        {
            if(this.GetSize() > 1)
            {
               SetPos(GetPos() + 1);
            }
        }
    }

    // returns the location of the current element (or -1)
    // might need to use .getLink()
    public int GetPos()
    {
        if(IsEmpty() == false)
        {
            int pos = 0;
            Node<Type> temp = new Node();
            temp.setLink(this.head.getLink());
            
            for(int i = 0; temp.getLink() != this.curr.getLink(); i++)
            {
                if(temp.getLink().getLink() == null)
                {
                    break;
                }
                temp.setLink(temp.getLink().getLink());
                pos++;
            }
            
            return pos;
        }
        else
        {
            return -1;
        }
    }

    // returns the value of the current element (or null)
    public Type GetValue()
    {
        if(IsEmpty() == true)
        {
            return null;
        }
        else
        {
            return this.curr.getLink().getData();
        }
    }

    // returns the size of the LinkedList
    // size does not imply capacity
    public int GetSize()
    {
        return num_items;
    }

    // inserts an item before the current element
    // the new element becomes the current
    // this should not be possible for a full LinkedList
    public void InsertBefore(Type data)
    {
        if(IsFull() == false)
        {
            Node<Type> newNode = new Node();
            newNode.setData(data);
            
            if(IsEmpty() == true)
            {
                this.head = new Node();
                this.curr = new Node();
                this.tail = new Node();
                
                newNode.setLink(null);
                
                this.head.setLink(newNode);
                this.curr.setLink(newNode);
                this.tail.setLink(newNode);
                
                this.num_items += 1;
            }
            else if(this.curr.getLink() == this.head.getLink())
            {
                newNode.setLink(this.curr.getLink());
                    
                this.head.setLink(newNode);
                this.curr.setLink(newNode);
                    
                this.num_items += 1;
            }
            else
            {
                Prev();
                InsertAfter(data);
            }
        }
     }
    
    // inserts an item after the current element
    // the new element becomes the current
    // this should not be possible for a full LinkedList
    public void InsertAfter(Type data)
    {
        if(IsFull() == false)
        {
            Node<Type> newNode = new Node();
            newNode.setData(data);

            if(IsEmpty() == true)
            {
                this.head = new Node();
                this.curr = new Node();
                this.tail = new Node();
                
                newNode.setLink(null);
                
                this.head.setLink(newNode);
                this.curr.setLink(newNode);
                this.tail.setLink(newNode);
            }
            else if(this.curr.getLink() == this.tail.getLink())
            {
                this.curr.getLink().setLink(newNode);
                this.tail.setLink(newNode);
                this.curr.setLink(newNode);
            }
            else
            {
                newNode.setLink(this.curr.getLink().getLink());
                this.curr.getLink().setLink(newNode);
                this.curr.setLink(newNode);
            }
            
            this.num_items += 1;
        }
    }
    

    // removes the current element 
    // this should not be possible for an empty LinkedList
    public void Remove()
    {
        if(IsEmpty() == false)
        {
            if(this.curr.getLink() == this.tail.getLink())
            {
                Prev();
                this.curr.getLink().setLink(null);
                this.tail.setLink(this.curr.getLink());
            }
            else if(this.curr.getLink() == this.head.getLink())
            {
                Next();
                this.head.getLink().setLink(null);
                this.head.setLink(this.curr.getLink());
            }
            else
            {
               Node<Type> pacman = new Node();
               pacman.setLink(this.curr.getLink());
               Prev();
               this.curr.getLink().setLink(pacman.getLink().getLink());
               Next();
            }
            
            this.num_items -= 1;
        }
    }

    // replaces the value of the current element with the specified value
    // this should not be possible for an empty LinkedList
    // may actually just be changing the data within the current node and not replacing the whole node
    public void Replace(Type data)
    {
        if(IsEmpty() == false)
        {
            curr.getLink().setData(data);
        }
    }

    // returns if the LinkedList is empty
    public boolean IsEmpty()
    {
        if(this.num_items == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // returns if the LinkedList is full
    public boolean IsFull()
    {
        if(GetSize() == MAX_SIZE)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // returns if two LinkedLists are equal (by value)
    public boolean Equals(LinkedList<Type> l)
    {
        if(this.GetSize() != l.GetSize())
        {
            return false;
        }
        
        Node<Type> temp1 = new Node();
        Node<Type> temp2 = new Node();
        
        temp1.setLink(this.head.getLink());
        temp2.setLink(l.head.getLink());
        
        for(int i = 0; i < this.GetSize(); i++)
        {
            if(temp1.getLink().getData() != temp2.getLink().getData())
            {
                return false;
            }
            temp1.setLink(temp1.getLink().getLink());
            temp2.setLink(temp2.getLink().getLink());
        }
        
        return true;
    }

    // returns the concatenation of two LinkedLists
    // l should not be modified
    // l should be concatenated to the end of *this
    // the returned LinkedList should not exceed MAX_SIZE elements
    // the last element of the new LinkedList is the current
    public LinkedList<Type> Add(LinkedList<Type> l)
    {
        if(this.toString() == "NULL")
        {
            LinkedList<Type> newLinkedList = new LinkedList(l);
            return newLinkedList;
        }
        else if(l.toString() == "NULL")
        {
            LinkedList<Type> newLinkedList = new LinkedList(this);
            return newLinkedList;
        }
        else
        {
            LinkedList<Type> newLinkedList = new LinkedList(this);
            
            Node<Type> temp1 = new Node();
            Node<Type> temp2 = new Node();
            
            temp1.setLink(this.tail.getLink());
            temp2.setLink(l.head.getLink());
            
            for(int i = 0; i < GetSize(); i++)
            {
                newLinkedList.InsertAfter(temp2.getLink().getData());
                temp2.setLink(temp2.getLink().getLink());
            }
            
            return newLinkedList;
        }
    }

    // returns a string representation of the entire LinkedList (e.g., 1 2 3 4 5)
    // the string "NULL" should be returned for an empty LinkedList
    public String toString()
    {
        if(IsEmpty() == true)
        {
            return "NULL";
        }
        
        Node<Type> temp = new Node();
        temp.setLink(this.head.getLink());
        
        String LinkedList = "";
        
        for(int i = 0; i < GetSize(); i++)
        {
                LinkedList += temp.getLink().getData() + " ";
                temp.setLink(temp.getLink().getLink());
        }
        
        return LinkedList;
    }
}