package com.github.kalaha.kalaha_game.domain.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class KalahBoard {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Column
   private String pits;

   @Column
   private int playerTurn = 0;

   public KalahBoard() {};
   public KalahBoard(String pits){
    this.pits = pits;
   }
public Integer getId() {
    return id;
}
public void setId(Integer id) {
    this.id = id;
}
public String getPits() {
    return pits;
}
public void setPits(String pits) {
    this.pits = pits;
}
public int getPlayerTurn() {
    return playerTurn;
}
public void setPlayerTurn(int playerTurn) {
    this.playerTurn = playerTurn;
}
@Override
public String toString() {
    return "KalahBoard [id=" + id + ", pits=" + pits + ", playerTurn=" + playerTurn + "]";
}
}
