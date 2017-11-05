package ca.ualberta.cs.opgoaltracker.models;

import java.util.ArrayList;

import ca.ualberta.cs.opgoaltracker.exception.MismatchedHabitTypeException;

/**
 * Created by donglin3 on 10/22/17.
 */

public class HabitEventList {
    private ArrayList<HabitEvent> aHabitEventList;
    private String habitType;

    public HabitEventList(String habitType){
        this.habitType = habitType;
    }

    public ArrayList<HabitEvent> getaHabitEventList() {
        return aHabitEventList;
    }

    public HabitEvent getaHabitEvent(int index){
        return this.aHabitEventList.get(index);
    }

    public void addHabitEvent(HabitEvent habitEvent) throws MismatchedHabitTypeException {
        if (!habitEvent.getHabitType().equals(this.habitType)){
            throw new MismatchedHabitTypeException();
        }
        this.aHabitEventList.add(habitEvent);
    }

    public void deleteHabitEvent(int index) throws IndexOutOfBoundsException{
        if (index<this.aHabitEventList.size()) {
            throw new IndexOutOfBoundsException();
        }
        this.aHabitEventList.remove(index);
    }

    public ArrayList<HabitEvent> search(String keyword) {
        ArrayList<HabitEvent> habitEventWithKeywordList = new ArrayList<>();
        for (HabitEvent habitEvent : this.aHabitEventList) {
            if (habitEvent.getComment().contains(keyword)) {
                habitEventWithKeywordList.add(habitEvent);
            }
        }
        return habitEventWithKeywordList;
    }
}
