package ca.ualberta.cs.opgoaltracker;

import java.util.ArrayList;

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

    public void addHabitEvent(HabitEvent habitEvent){
        if (habitEvent.getHabitType().equals(this.habitType)){
            this.aHabitEventList.add(habitEvent);
        }
    }

    public void deleteHabitEvent(int index){
        if (index<this.aHabitEventList.size()) {
            this.aHabitEventList.remove(index);
        }
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
