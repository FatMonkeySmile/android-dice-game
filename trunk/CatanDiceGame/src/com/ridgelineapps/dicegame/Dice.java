package com.ridgelineapps.dicegame;

public class Dice {
   enum Value {
      None,
      Wool,
      Grain,
      Brick,
      Ore,
      Lumber,
      Gold,
      Any
   }
   
   Value value;
   boolean held;
   boolean used;
   int rollHeld;
   
   public Dice() {
      reset();
   }
   
   public void roll() {
      int i = (int) (Math.random() * 6 + 1);
      switch(i) {
         case 1:
            value = Value.Wool;
            break;
         case 2:
            value = Value.Grain;
            break;
         case 3:
            value = Value.Brick;
            break;
         case 4:
            value = Value.Ore;
            break;
         case 5:
            value = Value.Lumber;
            break;
         case 6:
            value = Value.Gold;
            break;
      }
   }
   
   public boolean isHeld() {
      return held;
   }
   
   public void hold(int roll) {
      held = true;
      rollHeld = roll;
   }
   
   public boolean isUsable() {
      return !used;
   }
   
   public void use() {
      used = true;
   }
   
   public Value getValue() {
      return value;
   }
   
   public void setValue(Value value) {
      this.value = value;
   }
   
   public void reset() {
      held = false;
      used = false;
      value = Value.None;
   }
}
