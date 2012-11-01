package com.ridgelineapps.dicegame;

import java.util.ArrayList;

//TODO: 
//  unhold, un-use knight resources
//  new package name (resdicegame?)
//  copyrights/gpl
//  Green check overlay for usable knight resources
//  Add quick rect check for isWithin()
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
   
   public void reset() {
      turnsTaken = 0;
      playsheet.reset();
      newTurn(true);
   }
   
   public void newTurn(boolean firstTurn) {
      rolls = 0;
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
   }
   
   public boolean canUseKnightResource(int i) {
      return playsheet.canUseKnightResource(i);
   }
   
   public void consumeKnightResource(int i) {
      Dice.Value val = playsheet.useKnightResource(i);
      if(val != Dice.Value.None) {
         knightResources.add(val);
      }
   }
   
   public void holdDice(int i) {
      if(rolls == 0 || i < 0 || i >= dice.length) {
         return;
      }
      
      dice[i].hold();
   }
   
   public void roll() {
      if(!canRoll()) {
         return;
      }
      
      for(Dice die : dice) {
         if(!die.isHeld()) {
            die.roll();
         }
      }
      
      rolls++;
   }
   
   public boolean canRoll() {
      return (rolls < 3);
   }
   
   public void buildVillage(int i) {
      if(!canBuildVillage(i)) {
         return;
      }
      
      useResources(RESOURCES_VILLAGE);
      playsheet.buildVillage(i);
      builtResourceThisTurn = true;
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
      boolean[] knightResourcesUsed = new boolean[knightResources.size()];
      int gold = 0;
      for(Dice die : dice) {
         if(die.isUsable() && die.getValue().equals(Dice.Value.Gold)) {
            gold++;
         }
      }         
      for(Dice.Value val : resources) {
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
}
