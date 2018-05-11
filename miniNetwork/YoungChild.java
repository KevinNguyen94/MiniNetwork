package miniNetwork;

public class YoungChild extends Child {
    private Person[] siblings = new Person[3];
    private int siblingNumber=0;

    public YoungChild(String name, int age){
        super(name,age);
    }

    public void addSibling(YoungChild sibling){
        siblings[siblingNumber] = sibling;
        siblingNumber++;
    }

    public int getSiblingNumber(){
        return siblingNumber;
    }

    public void decreaseSiblingNumber(){
        siblingNumber--;
    }

    public Person[] getSiblings(){
        return siblings;
    }
}
