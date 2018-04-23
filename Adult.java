/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author khanhnguyen
 */
public class Adult extends Person{
    private Child[] children = new Child[3];
    private int childrenNumber= 0;
    private Adult spouse;
    private Boolean isMarried = false;

    private Adult[] colleagues = new Adult[10];
    private int colleagueNumber = 0;

    public Adult(String name, int age){
        super(name,age);
    }

    public void addChildren(Child child){
        children[childrenNumber] = child;
        childrenNumber++;
    }

    public Person[] getChildrenList(){
        return children;
    }

    public Boolean isMarried(){
        return isMarried;
    }

    public void setSpouse(Adult spouse){
        this.spouse = spouse;
        isMarried = true;
    }

    public Adult getSpouse(){
        return spouse;
    }

    public int getChildrenNumber(){
        return childrenNumber;
    }

    public void decreaseChildrenNumber(){
        childrenNumber--;
    }

    public void addColleague(Adult colleague){
        colleagues[colleagueNumber] = colleague;
        colleagueNumber++;
    }

    public int getColleagueNumber(){
        return colleagueNumber;
    }

    public Adult[] getColleagues(){
        return colleagues;
    }

    public void decreaseColleagueNumber(){
        colleagueNumber--;
    }
}
