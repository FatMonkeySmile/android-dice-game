package com.ridgelineapps.dicegame;

public class Playsheet {
   int knights;
   boolean[] resourcesAvail; // 1 based
   boolean[] roads;
   boolean[] villages;
   boolean[] cities;
   int turnsNothingBuilt = 0;
   
   public Playsheet() {
      reset();
   }
   
   public boolean canBuildVillage(int i) {
      if(i < 0 || i > 5) {
         return false;
      }
      
      if(villages[i]) {
         return false;
      }
      
      if(i > 0 && !villages[i-1]) {
         return false;
      }
      
      if(i == 0)
         return roads[0];
      
      if(i == 1)
         return roads[3];
      
      if(i == 2)
         return roads[6];

      if(i == 3)
         return roads[8];
      
      if(i == 4)
         return roads[10];
      
      if(i == 5)
         return roads[12];
      
      return false;
   }
   
   public void buildVillage(int i) {
      if(canBuildVillage(i)) {
         villages[i] = true;
      }      
   }
   
   public boolean canBuildCity(int i) {
      if(i < 0 || i > 3) {
         return false;
      }
      
      if(cities[i]) {
         return false;
      }
      
      if(i > 0 && !cities[i-1]) {
          return false;
       }      

      if(i == 0)
         return roads[2];
      if(i == 1)
         return roads[5];
      if(i == 2)
         return roads[14];
      if(i == 3)
         return roads[16];
      
      return false;
   }
   
   public void buildCity(int i) {
      if(canBuildCity(i)) {
         cities[i] = true;
      }      
   }
   
   public boolean canBuildRoad(int i) {
      if(i < 0 || i > 16) {
         return false;
      }
      
      if(roads[i]) {
         return false;
      }

      // Should never hit this, but just in case...
      if(i == 0)
         return true;
      
      if(i == 1)
         return roads[0];
      
      if(i == 2 || i == 3)
         return roads[1];
      
      if(i == 4)
         return roads[3];
      
      if(i == 5 || i == 6)
         return roads[4];
      
      if(i == 7)
         return roads[6];
      
      if(i == 8)
         return roads[7];
      
      if(i == 9 || i == 13)
         return roads[8];
      
      if(i == 10)
         return roads[9];
      
      if(i == 11)
         return roads[10];
         
      if(i == 12)
         return roads[11];
      
      if(i == 14)
         return roads[13];
      
      if(i == 15)
         return roads[14];
      
      if(i == 16)
         return roads[15];
      
      return false;
   }
   
   public void buildRoad(int i) {
      if(canBuildRoad(i)) {
         roads[i] = true;
      }
   }
   
   public boolean canBuildKnight(int i) {
      if(i < 1 || i > 6) {
         return false;
      }
      
      if(knights == i - 1) {
         return true;
      }
      
      return false;
   }
   
   public void buildKnight(int i) {
      if(canBuildKnight(i)) {
         knights = i;
      }
   }
   
   public boolean canUseKnightResource(int i) {
      if(i < 1 || i > 6) {
         return false;
      }
      
      if(knights >= i && resourcesAvail[i]) {
         return true;
      }
      
      return false;
   }
   
   public boolean isKnightResourceUsed(int i) {
       if(i < 1 || i > 6) {
          return false;
       }
       
       if(knights >= i && !resourcesAvail[i]) {
          return true;
       }
       
       return false;
    }
   
   public Dice.Value useKnightResource(int i) {
      if(canUseKnightResource(i)) {
         resourcesAvail[i] = false;
         return getKnightResource(i);
      }
      
      return Dice.Value.None;
   }
   
   public Dice.Value getKnightResource(int i) {
       if(canUseKnightResource(i)) {
          switch(i) {
             case 1:
                return Dice.Value.Ore;
             case 2:
                return Dice.Value.Grain;
             case 3:
                return Dice.Value.Wool;
             case 4:
                return Dice.Value.Lumber;
             case 5:
                return Dice.Value.Brick;
             case 6:
                return Dice.Value.Any;
          }
       }
       
       return Dice.Value.None;
    }
   
   public void reset() {
      knights = 0;
      resourcesAvail = new boolean[7];
      for(int i=1; i <= 6; i++) {
         resourcesAvail[i] = true;
      }
      roads = new boolean[17];
      roads[0] = true;
      villages = new boolean[6];
      cities = new boolean[4];
      turnsNothingBuilt = 0;
   }
   
   public int getScore() {
      int score = 0;
      for(int i=1; i < roads.length; i++) {
         if(roads[i]) {
            score++;
         }
      }

      if(knights >= 1)
         score += 1;
      if(knights >= 2)
         score += 2;
      if(knights >= 3)
         score += 3;
      if(knights >= 4)
         score += 4;
      if(knights >= 5)
         score += 5;
      if(knights == 6)
         score += 6;
      
      if(villages[0])
         score += 3;
      if(villages[1])
         score += 4;
      if(villages[2])
         score += 5;
      if(villages[3])
         score += 7;
      if(villages[4])
         score += 9;
      if(villages[5])
         score += 11;
      
      if(cities[0])
         score += 7;
      if(cities[1])
         score += 12;
      if(cities[2])
         score += 20;
      if(cities[3])
         score += 30;
      
      score -= turnsNothingBuilt * 2;
      
      return score;
   }
}
