package com.android.online.voteapp;

/**
 * Created by Kiduyu klaus
 * on 10/11/2020 11:51 2020
 */
public class VotesCast {

    String id,vote,seat;

    public VotesCast() {


    }

    public VotesCast(String id, String vote, String seat) {
        this.id = id;
        this.vote = vote;
        this.seat = seat;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
}
