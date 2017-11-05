package ca.ualberta.cs.opgoaltracker.models;

import java.util.ArrayList;

import ca.ualberta.cs.opgoaltracker.exception.DuplicatedHabitException;

/**
 * Created by song on 2017/10/23.
 */

public class HabitList{
    private ArrayList<Habit> aHabitList;

    public ArrayList<Habit> getHabitList() {
        return aHabitList;
    }

    public Habit getHabit(int index){
        return this.aHabitList.get(index);
    }

    public void addHabit(Habit habit) throws DuplicatedHabitException {
        //not fully implemented, should just check habitType
        if (aHabitList.contains(habit)){
            throw new DuplicatedHabitException();
        }
        this.aHabitList.add(habit);
    }

    public void deleteHabit(int index) throws IndexOutOfBoundsException{
        if (index<this.aHabitList.size()) {
            throw new IndexOutOfBoundsException();
        }
        this.aHabitList.remove(index);
    }

    public void todayHabit(){
        //todo
    }

    public void habitStatusIndicator(){
        //todo
    }

}
