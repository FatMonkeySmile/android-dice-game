/*
 * Copyright (C) 2012 Resource Dice Game (http://code.google.com/p/android-dice-game)
 * 
 * This program is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *   
 * This source code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.ridgelineapps.resdicegame;

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
   
   public void swap(Dice die) {
      Dice.Value temp = die.getValue();
      die.setValue(value);
      value = temp;      
   }
   
   public boolean isHeld() {
      return held;
   }
   
   public void hold(int roll, boolean b) {
      held = b;
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
