package ca.ualberta.cs.opgoaltracker.models;

/**
 * Created by song on 2017/10/23.
 */

public class Admin extends User{

    public Admin(String id) {
        super(id);
    }

    private int picSize = 65536;
    private int titleLength = 20;
    private int reasonLength = 30;
    private int commentLength = 20;

    public int getPicSize(){return this.picSize;}

    public int getTitleLength(){return this.titleLength;}

    public int getReasonLength(){return this.reasonLength;}

    public int getCommentLength(){return this.commentLength;}

    public void setPicSize(int picSize){this.picSize = picSize;}

    public void setTitleLength(int titleLength){this.titleLength = titleLength;}

    public void setReasonLength(int reasonLength){this.reasonLength = reasonLength;}

    public void setCommentLength(int commentLength){this.commentLength = commentLength;}

}
