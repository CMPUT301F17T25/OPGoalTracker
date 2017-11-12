/*
 * Copyright 2017 Yongjia Huang, Dichong Song, Mingwei Li, Donglin Han, Long Ma,CMPUT301F17T25 CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */

package ca.ualberta.cs.opgoaltracker.models;

/**
 * This Admin Class is a subclass of User<br>
 *     This Class allows Admin user to set the restrictions to this app
 * @author Dichong Song, Yongjia Huang
 * @version 3.0
 * @see User
 * @since 1.0
 */
public class Admin extends User{

    public Admin(String id) {
        super(id);
    }

    private int picSize = 65536;
    private int titleLength = 20;
    private int reasonLength = 30;
    private int commentLength = 20;

    /**
     * Basic PicSize getter
     * @return int PicSize
     */
    public int getPicSize(){return this.picSize;}

    /**
     * Basic Title Length getter
     * @return int titlelenght
     */
    public int getTitleLength(){return this.titleLength;}

    /**
     * Basic Reason lenght getter
     * @return int reason length
     */
    public int getReasonLength(){return this.reasonLength;}

    /**
     * Basic CommentLenght getter
     * @return int commentLenght
     */
    public int getCommentLength(){return this.commentLength;}

    /**
     * Basic picSize setter
     * @param picSize int
     */
    public void setPicSize(int picSize){this.picSize = picSize;}

    /**
     * Basic titleLenght Setter
     * @param titleLength int
     */
    public void setTitleLength(int titleLength){this.titleLength = titleLength;}

    /**
     * Basic reasonLength setter
     * @param reasonLength int
     */
    public void setReasonLength(int reasonLength){this.reasonLength = reasonLength;}

    /**
     * Basic commentLength setter
     * @param commentLength int 
     */
    public void setCommentLength(int commentLength){this.commentLength = commentLength;}

}
