/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author khanhnguyen
 */
public class Child extends Person{
    private Adult[] parents = new Adult[2];
    private int parentNumber = 0;


    public Child(String name, int age){
        super(name,age);
    }

    public void addParent(Adult parent){
        parents[parentNumber] = parent;
        parentNumber++;
        parent.addChildren(this);
    }

    public Adult[] getParentList(){
        return parents;
    }

    public int getParentNumber(){
        return parentNumber;
    }

}
