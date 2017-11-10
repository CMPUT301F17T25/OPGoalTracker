package ca.ualberta.cs.opgoaltracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import ca.ualberta.cs.opgoaltracker.exception.DuplicatedHabitException;

/**
 * Created by song on 2017/10/23.
 */

public class HabitList implements Parcelable {
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


    protected HabitList(Parcel in) {
        if (in.readByte() == 0x01) {
            aHabitList = new ArrayList<Habit>();
            in.readList(aHabitList, Habit.class.getClassLoader());
        } else {
            aHabitList = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (aHabitList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(aHabitList);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HabitList> CREATOR = new Parcelable.Creator<HabitList>() {
        @Override
        public HabitList createFromParcel(Parcel in) {
            return new HabitList(in);
        }

        @Override
        public HabitList[] newArray(int size) {
            return new HabitList[size];
        }
    };
}