package com.ridgelineapps.dicegame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;

import com.ridgelineapps.dicegame.java.Polygon;

public class UIEntity {
   enum Type {
      dice,
      road,
      village,
      city,
      knight,
      resource,
      roll,
//      turnOver,
   }
   
   int x1;
   int y1;
   int x2;
   int y2;
   
   Game game;
   
   Polygon poly;
   Path path;
   
   Type type;
   int index;
   
   public UIEntity(Game game, Type type, int index, int x1, int y1, int x2, int y2) {
      this.type = type;
      this.game = game;
      this.index = index;
      this.x1 = Math.min(x1, x2);
      this.y1 = Math.min(y1, y2);
      this.x2 = Math.max(x1, x2);
      this.y2 = Math.max(y1, y2);
   }
   
   public UIEntity(Game game, Type type, int index, Polygon poly, Path path) {
      this.type = type;
      this.game = game;
      this.index = index;
      this.poly = poly;
      this.path = path;
   }
   
   public boolean isWithin(int x, int y) {
      if(poly == null) {
         if(x >= x1 && x <= x2 && y >= y1 && y <= y2) {
            return true;
         }
         return false;
      }
      else {
         return poly.contains(x, y);
      }
   }
   
   public void move() {
      
   }
   
   public Path getViewPath() {
      return path;
   }
   
   public void up() {
      switch (type) {
         case dice:
            game.holdDice(index);
            break;
         case roll:
            game.roll();
            break;
//         case turnOver:
//            game.newTurn(false);
//            break;
         case road:
            game.buildRoad(index);
            break;
         case village:
            game.buildVillage(index);
            break;
         case city:
            game.buildCity(index);
            break;
         case knight:
            game.buildKnight(index);
            break;
         case resource:
//            game.consumeKnightResource(index);
            break;
      }
   }
   
   public void draw(Canvas canvas) {
      Paint p = new Paint();
      p.setStyle(Style.FILL);
      boolean draw = true;
      switch (type) {
//      case dice:
//         p.setARGB(255, 255, 0, 0);
//         break;
//      case roll:
//         p.setARGB(255, 0, 255, 0);
//         break;
//      case turnOver:
//         p.setARGB(255, 0, 0, 255);
//         break;
      case road:
          if(game.canBuildRoad(index)) {
              highlight(p);
          }
          else if(game.playsheet.roads[index]) {
              darken(p);
          }
          else {
              draw = false;
          }
         break;
      case village:
          if(game.canBuildVillage(index)) {
              highlight(p);
          }
          else if(game.playsheet.villages[index]) {
              darken(p);
          }
          else {
              draw = false;
          }
         break;
      case city:
          if(game.canBuildCity(index)) {
              highlight(p);
          }
          else if(game.playsheet.cities[index]) {
              darken(p);
          }
          else {
              draw = false;
          }
         break;
      case knight:
          if(game.canBuildKnight(index)) {
              highlight(p);
          }
          else if(game.playsheet.knights >= index) {
              darken(p);
          }
          else {
              draw = false;
          }
         break;
      case resource:
          if(game.playsheet.canUseKnightResource(index)) {
              draw = false;
//              p.setARGB(128, 255, 255, 255);
          }
          else if(game.playsheet.isKnightResourceUsed(index)) {
              p.setARGB(128, 220, 20, 20);
          }
          else {
              draw = false;
          }
         break;
     default:
         draw = false;
         break;
      }
      if(draw) {
          canvas.drawPath(path, p);
      }
   }
   
   public void highlight(Paint p) {
       p.setARGB(100, 35, 180, 15);
//       p.setARGB(128, 20, 200, 75);
   }
   
   public void darken(Paint p) {
       p.setARGB(128, 100, 100, 0);
   }
}
