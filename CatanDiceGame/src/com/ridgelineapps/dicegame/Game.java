package com.ridgelineapps.dicegame;

import java.util.ArrayList;

//TODO: 
//  unhold, un-use knight resources
//  new package name (resdicegame?)
//  copyrights/gpl
//  Green check overlay for usable knight resources
//  Add quick rect check for isWithin()
//  Size dice bitmaps
//  All colors better
//  Icon

public class Game {
   Playsheet playsheet;
   Dice[] dice;
   int rolls;
   ArrayList<Dice.Value> knightResources;
   boolean builtResourceThisTurn;
   int turnsTaken;
   
   public static Dice.Value[] RESOURCES_ROAD = new Dice.Value[]{ Dice.Value.Brick, Dice.Value.Lumber };
   public static Dice.Value[] RESOURCES_VILLAGE = new Dice.Value[]{ Dice.Value.Lumber, Dice.Value.Brick, Dice.Value.Wool, Dice.Value.Grain };
   public static Dice.Value[] RESOURCES_KNIGHT = new Dice.Value[]{ Dice.Value.Wool, Dice.Value.Grain, Dice.Value.Ore };
   public static Dice.Value[] RESOURCES_CITY = new Dice.Value[]{ Dice.Value.Grain, Dice.Value.Grain, Dice.Value.Ore, Dice.Value.Ore, Dice.Value.Ore };
   
   GameView gameView;
   
   public Game() {
      playsheet = new Playsheet();
      knightResources = new ArrayList<Dice.Value>();
      dice = new Dice[6];
      for(int i=0; i < dice.length; i++) {
         dice[i] = new Dice();
      }
      
      turnsTaken = 0;
      newTurn(true);
   }
   
   public void setGameView(GameView gameView) {
      this.gameView = gameView;
   }
   
   public void reset() {
      turnsTaken = 0;
      playsheet.reset();
      newTurn(true);
      if(gameView != null)
         gameView.postInvalidate();      
   }
   
   public void newTurn(boolean firstTurn) {
       if(gameOver()) {
           return;
       }
       
      rolls = 0;
      
      //Return unused knightResources
      for(int i=0; i < knightResources.size(); i++) {
          int index = 0;
          switch(knightResources.get(i)) {
              case Ore:
                  index = 1;
                  break;
              case Grain:
                  index = 2;
                  break;
              case Wool:
                  index = 3;
                  break;
              case Lumber:
                  index = 4;
                  break;
              case Brick:
                  index = 5;
                  break;
              case Any:
                  index = 6;
                  break;
           }
          
          if(index > 0) {
              playsheet.resourcesAvail[index] = true;
          }
      }
      
      knightResources.clear();
      for(int i=0; i < dice.length; i++) {
         dice[i].reset();
      }
      if(!firstTurn && !builtResourceThisTurn) {
         playsheet.turnsNothingBuilt++;
      }
      builtResourceThisTurn = false;
      
      if(!firstTurn) {
         turnsTaken++;
      }
      if(gameView != null)
         gameView.postInvalidate();      
   }
   
   public boolean canUseKnightResource(int i) {
      return playsheet.canUseKnightResource(i);
   }
   
   public void consumeKnightResource(int i) {
      Dice.Value val = playsheet.useKnightResource(i);
      if(val != Dice.Value.None) {
         knightResources.add(val);
      }
      if(gameView != null)
          gameView.postInvalidate();      
   }
   
   public void holdDice(int i) {
      if(rolls == 0 || i < 0 || i >= dice.length) {
         return;
      }
      
      if(dice[i].held && dice[i].rollHeld == rolls) {
          dice[i].held = false;
      }
      else if(!dice[i].held){
          dice[i].hold(rolls);
      }
      if(gameView != null)
          gameView.postInvalidate();      
   }
   
   public void roll() {
      if(!canRoll()) {
         newTurn(false);
         return;
      }
      
      for(Dice die : dice) {
         if(!die.isHeld()) {
            die.roll();
         }
      }
      
      rolls++;
      if(gameView != null)
         gameView.postInvalidate();      
   }
   
   public boolean canRoll() {
      return (rolls < 3 && !builtResourceThisTurn && !gameOver());
   }
   
   public void buildVillage(int i) {
      if(!canBuildVillage(i)) {
         return;
      }
      
      useResources(RESOURCES_VILLAGE);
      playsheet.buildVillage(i);
      builtResourceThisTurn = true;
      if(gameView != null)
         gameView.postInvalidate();            
   }
   
   public boolean canBuildVillage(int i) {
      if(!canBuildVillage()) {
         return false;
      }
      
      return playsheet.canBuildVillage(i);
   }
   
   public boolean canBuildVillage() {
      if(rolls == 0) {
         return false;
      }
      
      return canBuild(RESOURCES_VILLAGE);
   }
   
   public void buildKnight(int i) {
      if(!canBuildKnight(i)) {
         return;
      }
      
      useResources(RESOURCES_KNIGHT);
      playsheet.buildKnight(i);
      builtResourceThisTurn = true;
      if(gameView != null)
         gameView.postInvalidate();            
   }
   
   public boolean canBuildKnight(int i) {
      if(!canBuildKnight()) {
         return false;
      }
      
      return playsheet.canBuildKnight(i);
   }
   
   public boolean canBuildKnight() {
      if(rolls == 0) {
         return false;
      }
      
      return canBuild(RESOURCES_KNIGHT);
   }
   
   public void buildRoad(int i) {
      if(!canBuildRoad(i)) {
         return;
      }
      
      useResources(RESOURCES_ROAD);
      playsheet.buildRoad(i);
      builtResourceThisTurn = true;
      if(gameView != null)
         gameView.postInvalidate();            
   }
   
   public boolean canBuildRoad(int i) {
      if(!canBuildRoad()) {
         return false;
      }
      
      return playsheet.canBuildRoad(i);
   }   
   
   public boolean canBuildRoad() {
      if(rolls == 0) {
         return false;
      }

      return canBuild(RESOURCES_ROAD);
   }
   
   public void buildCity(int i) {
      if(!canBuildCity(i)) {
         return;
      }
      
      useResources(RESOURCES_CITY);
      playsheet.buildCity(i);
      builtResourceThisTurn = true;
      if(gameView != null)
         gameView.postInvalidate();            
   }
   
   public boolean canBuildCity(int i) {
      if(!canBuildCity()) {
         return false;
      }
      
      return playsheet.canBuildCity(i);
   }   
   
   public boolean canBuildCity() {
      if(rolls == 0) {
         return false;
      }

      return canBuild(RESOURCES_CITY);
   }
   
   public void useResources(Dice.Value[] resources) {
      boolean[] diceUsed = new boolean[dice.length];
      boolean[] knightResourcesUsed;
      int gold = 0;
      for(Dice die : dice) {
         if(die.isUsable() && die.getValue().equals(Dice.Value.Gold)) {
            gold++;
         }
      }         
      for(Dice.Value val : resources) {
         knightResourcesUsed = new boolean[knightResources.size()];
         boolean found = false;
         for(int i=0; i < dice.length; i++) {
            if(!dice[i].isUsable() || diceUsed[i]) {
               continue;
            }
            if(dice[i].value.equals(val)) {
               diceUsed[i] = true;
               found = true;
               break;
            }
         }
         if(!found) {
            for(int i=0; i < knightResources.size(); i++) {
               if(knightResourcesUsed[i]) {
                  continue;
               }
               if(knightResources.get(i).equals(val)) {
                  knightResourcesUsed[i] = true;
                  found = true;
                  break;
               }
            }            
         }
         if(!found) {
             for(int i=0; i < knightResources.size(); i++) {
                if(knightResourcesUsed[i]) {
                   continue;
                }
                if(knightResources.get(i).equals(Dice.Value.Any)) {
                   knightResourcesUsed[i] = true;
                   found = true;
                   break;
                }
             }            
          }
         
         if(found) {
            for(int i=0; i < dice.length; i++) {
               if(diceUsed[i]) {
                  dice[i].use();
               }
            }
            for(int i=knightResourcesUsed.length - 1; i >= 0; i--) {
               if(knightResourcesUsed[i]) {
                  knightResources.remove(i);
               }
            }
         }
         else if(gold >= 2) {
            int goldNeeded = 2;
            for(int i=0; i < dice.length; i++) {
               if(dice[i].isUsable() && dice[i].value.equals(Dice.Value.Gold)) {
                  dice[i].use();
                  if(--goldNeeded == 0) {
                     break;
                  }
               }
            }
         }
      }
   }

   public boolean canBuild(Dice.Value[] resourcesRequired) {
      boolean[] diceUsed = new boolean[dice.length];
      boolean[] knightResourcesUsed = new boolean[knightResources.size()];
      int gold = 0;
      for(Dice die : dice) {
         if(die.isUsable() && die.getValue().equals(Dice.Value.Gold)) {
            gold++;
         }
      }         
      for(Dice.Value val : resourcesRequired) {
         boolean found = false;
         for(int i=0; i < dice.length; i++) {
            if(!dice[i].isUsable() || diceUsed[i]) {
               continue;
            }
            if(dice[i].value.equals(val)) {
               diceUsed[i] = true;
               found = true;
               break;
            }
         }
         if(!found) {
            for(int i=0; i < knightResources.size(); i++) {
               if(knightResourcesUsed[i]) {
                  continue;
               }
               if(knightResources.get(i).equals(val)) {
                  knightResourcesUsed[i] = true;
                  found = true;
                  break;
               }
            }            
         }
         if(!found) {
             for(int i=0; i < knightResources.size(); i++) {
                if(knightResourcesUsed[i]) {
                   continue;
                }
                if(knightResources.get(i).equals(Dice.Value.Any)) {
                   knightResourcesUsed[i] = true;
                   found = true;
                   break;
                }
             }            
          }
         if(!found) {
            if(gold >= 2) {
               gold -= 2;
               found = true;
               break;
            }
         }
         
         if(!found) {
            return false;
         }
      }
      return true;
   }
   
   public boolean gameOver() {
       return (turnsTaken == 15);
   }
}
