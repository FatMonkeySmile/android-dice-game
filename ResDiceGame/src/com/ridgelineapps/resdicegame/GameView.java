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

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import com.ridgelineapps.dicegame.mappings.Cities;
import com.ridgelineapps.dicegame.mappings.Knights;
import com.ridgelineapps.dicegame.mappings.Resources;
import com.ridgelineapps.dicegame.mappings.Roads;
import com.ridgelineapps.dicegame.mappings.Scores;
import com.ridgelineapps.dicegame.mappings.Villages;

public class GameView extends View {
   Matrix matrix;
   
   Rect playsheetSrcRect;
   Rect playsheetDestRect;
   
   int width = 480;
   int height = 800;
   
   float scale = 0.0f;
   int xOffset = 0;
   int yOffset = 0;

   int touchOffset = 20;
   
   Point scoreLoc = new Point(444, 184);
   
   Game game;
   ArrayList<UIEntity> entities = new ArrayList<UIEntity>();
   
   Bitmap playSheetImage;
   Paint imagePaint;
   Paint borderPaint;
   Paint scorePaint;
   Paint backPaint;
   UIDiceRoll uiDiceRoll;
   
   MainActivity activity;
   
   int diceLocX = 15;
   int diceLocY = 650;
   int diceBuffer = 13;
   int rollOffsetX = 65;
   int rollOffsetY = 18;
   
   boolean scaleInit = false;
   
   public GameView(Context context, Game game) {
      super(context);
      activity = (MainActivity) context;
      this.game = game;
      imagePaint = new Paint();
      imagePaint.setDither(true);
      imagePaint.setFilterBitmap(true);
      imagePaint.setAntiAlias(true);
      playSheetImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.orig_sheet);
      
      playsheetSrcRect = new Rect(0, 0, playSheetImage.getWidth(), playSheetImage.getHeight());
      playsheetDestRect = new Rect(0, 0, 480, 639);

      scorePaint = new Paint();
      scorePaint.setFakeBoldText(true);
      scorePaint.setAntiAlias(true);
      scorePaint.setARGB(255, 0, 10, 10);
      scorePaint.setTextSize(23);
      
      backPaint = new Paint();
      backPaint.setStyle(Style.FILL);
      backPaint.setARGB(255, 25, 25, 25);
      
      borderPaint = new Paint();
      borderPaint.setStyle(Style.STROKE);
      borderPaint.setStrokeWidth(3);
      borderPaint.setARGB(255, 200, 200, 100);
      
      UIDice.loadBitmaps(context.getResources());
      int diceX = diceLocX;
      int diceY = diceLocY;
      for(int i=0; i < game.dice.length; i++) {
         UIDice dice = new UIDice(game, i, diceX, diceY);
         entities.add(dice);
         diceX += UIDice.size + diceBuffer;
      }
      
      diceY += UIDice.size + rollOffsetY;
      uiDiceRoll = new UIDiceRoll(game, rollOffsetX, diceY, width - rollOffsetX * 2, height - rollOffsetY - diceY);
      entities.add(uiDiceRoll);
      
      PolygonLite poly;
      Path path;

      for(int i=0; i < Roads.touch.length; i++) {
         poly = new PolygonLite();
         path = new Path();
         for(int j = 0; j < Roads.touch[i].length; j++) {
            int x = Roads.touch[i][j][0];
            int y = Roads.touch[i][j][1];
            poly.add(new PointLite(x, y));
         }
         for(int j = 0; j < Roads.view[i].length; j++) {
            int x = Roads.view[i][j][0];
            int y = Roads.view[i][j][1];
            if(j == 0) {
               path.moveTo(x, y);
            }
            else {
               path.lineTo(x, y);
            }
         }
         path.close();
         UIEntity entity = new UIEntity(game, UIEntity.Type.road, i, poly, path);
         entities.add(entity);
      }
      
      for(int i=0; i < Villages.touch.length; i++) {
         poly = new PolygonLite();
         path = new Path();
         for(int j = 0; j < Villages.touch[i].length; j++) {
            int x = Villages.touch[i][j][0];
            int y = Villages.touch[i][j][1];
            poly.add(new PointLite(x, y));
         }
         for(int j = 0; j < Villages.view[i].length; j++) {
            int x = Villages.view[i][j][0];
            int y = Villages.view[i][j][1];
            if(j == 0) {
               path.moveTo(x, y);
            }
            else {
               path.lineTo(x, y);
            }
         }
         path.close();
         UIEntity entity = new UIEntity(game, UIEntity.Type.village, i, poly, path);
         entities.add(entity);
      }
      
      for(int i=0; i < Cities.touch.length; i++) {
         poly = new PolygonLite();
         path = new Path();
         for(int j = 0; j < Cities.touch[i].length; j++) {
            int x = Cities.touch[i][j][0];
            int y = Cities.touch[i][j][1];
            poly.add(new PointLite(x, y));
         }
         for(int j = 0; j < Cities.view[i].length; j++) {
            int x = Cities.view[i][j][0];
            int y = Cities.view[i][j][1];
            if(j == 0) {
               path.moveTo(x, y);
            }
            else {
               path.lineTo(x, y);
            }
         }
         path.close();
         UIEntity entity = new UIEntity(game, UIEntity.Type.city, i, poly, path);
         entities.add(entity);
      }
      
      for(int i=0; i < Resources.touch.length; i++) {
         poly = new PolygonLite();
         path = new Path();
         for(int j = 0; j < Resources.touch[i].length; j++) {
            int x = Resources.touch[i][j][0];
            int y = Resources.touch[i][j][1];
            poly.add(new PointLite(x, y));
         }
         for(int j = 0; j < Resources.view[i].length; j++) {
            int x = Resources.view[i][j][0];
            int y = Resources.view[i][j][1];
            if(j == 0) {
               path.moveTo(x, y);
            }
            else {
               path.lineTo(x, y);
            }
         }
         path.close();
         UIEntity entity = new UIEntity(game, UIEntity.Type.resource, i + 1, poly, path);
         entities.add(entity);
      }

      for(int i=0; i < Knights.touch.length; i++) {
         poly = new PolygonLite();
         path = new Path();
         for(int j = 0; j < Knights.touch[i].length; j++) {
            int x = Knights.touch[i][j][0];
            int y = Knights.touch[i][j][1];
            poly.add(new PointLite(x, y));
         }
         for(int j = 0; j < Knights.view[i].length; j++) {
            int x = Knights.view[i][j][0];
            int y = Knights.view[i][j][1];
            if(j == 0) {
               path.moveTo(x, y);
            }
            else {
               path.lineTo(x, y);
            }
         }
         path.close();
         UIEntity entity = new UIEntity(game, UIEntity.Type.knight, i + 1, poly, path);
         entities.add(entity);
      }
   }

   @Override
   protected void onDraw(Canvas canvas) {
      if(!scaleInit) {
         scaleInit = true;
         float xScale = (float) canvas.getWidth() / width;
         float yScale = (float) canvas.getHeight() / height;
         
         double screenInches = 0;
         float density = 0;
         try
         {
            Display display = activity.getWindowManager().getDefaultDisplay();
            DisplayMetrics dm = new DisplayMetrics();
            display.getMetrics(dm);
            density = dm.density;
            screenInches = Math.sqrt(Math.pow(display.getWidth() / dm.xdpi, 2) + Math.pow(display.getHeight() / dm.ydpi, 2));
         }
         catch(Throwable t) {
            t.printStackTrace();
         }
         
         if(xScale > 1 && yScale > 1 && screenInches >= 6) {
            if(density > 1) {
               scale = density;
               if(yScale < scale) {
                  scale = yScale;
               }
               if(xScale < scale) {
                  scale = xScale;
               }
            }
            else {
               scale = 1;               
            }
            yOffset = (int) (canvas.getHeight() - height * scale) / 2;
            xOffset = (int) (canvas.getWidth() - width * scale) / 2;             
         }
         else if(xScale == yScale) {
            scale = xScale;
         }
         else if(xScale < yScale) {
            scale = xScale;
            yOffset = (int) (canvas.getHeight() - height * scale) / 2;
         }
         else {
            scale = yScale;
            xOffset = (int) (canvas.getWidth() - width * scale) / 2;
         }
         
         matrix = new Matrix();
         matrix.setScale(scale, scale, 0, 0);
         matrix.postTranslate(xOffset, yOffset);
      }
      
      int restoreCount = canvas.save();
      canvas.concat(matrix);

      canvas.drawRect(0, 0, width, height, backPaint); 
       
//      float scale = (float) playSheetImage.getScaledWidth(canvas) / 480;
//      Rect src = new Rect(0, 0, playSheetImage.getWidth(), playSheetImage.getHeight()); //(int) (width * scale), (int) (height * scale));
//      Rect dest = new Rect(0, 0, (int) (src.width()* scale), (int) (src.height() * scale)); //canvas.getWidth(), canvas.getHeight());
//      canvas.drawBitmap(playSheetImage, src, dest, imagePaint);
      
      canvas.drawBitmap(playSheetImage, playsheetSrcRect, playsheetDestRect, imagePaint);

//      canvas.drawBitmap(playSheetImage, 0, 0, imagePaint);
      
      for(UIEntity e : entities) {
         e.draw(canvas);
      }

      for(int i=1; i <= game.turnsTaken; i++) {
         int x = Scores.view[i][0];
         int y = Scores.view[i][1];
      
         int turnScoreInt = game.playsheet.getTurnScore(i);
         String turnScore;
         if(turnScoreInt > 0) {
            turnScore = "" + turnScoreInt;
         }
         else {
            turnScore = "X";
         }
         //TODO: Cache xOffset?
         int xOffset = (int) (scorePaint.measureText(turnScore) / 2);
         canvas.drawText(turnScore, x - xOffset, y, scorePaint);
      }
      
      if(!game.isGameDone() && game.turnsTaken < 15) {
         int thisTurnScore = game.playsheet.getScore() - game.playsheet.turnScore[game.turnsTaken];
         if(thisTurnScore > 0) {
            int x = Scores.view[game.turnsTaken + 1][0];
            int y = Scores.view[game.turnsTaken + 1][1];
            String turnScore = "" + thisTurnScore;
            int xOffset = (int) (scorePaint.measureText(turnScore) / 2);
            canvas.drawText(turnScore, x - xOffset, y, scorePaint);
         }
      }
      
      String score = "" + game.playsheet.getScore();
      int xOffset = (int) (scorePaint.measureText(score) / 2);
      canvas.drawText(score, scoreLoc.x - xOffset, scoreLoc.y, scorePaint);
      canvas.drawRect(0, 0, width - 1, height - 1, borderPaint);
      
//      if(!game.isGameDone()) {
//          String turn = "Turn:  " + (game.turnsTaken + 1);
//          xOffset = (int) (scorePaint.measureText(turn) / 2);
//          canvas.drawText(turn, 334, 38, scorePaint);
//      }
      
      canvas.restoreToCount(restoreCount);
   }
   
   @Override
   public boolean onTouchEvent(MotionEvent event) {

       if(uiDiceRoll.down && event.getAction() == MotionEvent.ACTION_MOVE) {
           //TODO: Fix this check to be quicker... 
           if(!uiDiceRoll.isWithin((int) (event.getX() / scale), (int) (event.getY() / scale))) {
               if(!uiDiceRoll.isWithin((int) ((event.getX() - xOffset) / scale), (int) ((event.getY() - yOffset - touchOffset) / scale))) {
                   uiDiceRoll.down = false;
                   postInvalidate();
               }
           }
       }
       
       if(event.getAction() != MotionEvent.ACTION_MOVE) {
          boolean found = false;
           for(UIEntity e : entities) {
              if(e.isWithin((int) ((event.getX() - xOffset) / scale), (int) ((event.getY() - yOffset) / scale))) {
                 e.touch(event.getAction());
                 found = true;
                 break;
              }
           }

           if(!found) {
              for(UIEntity e : entities) {
                 if(e.isWithin((int) ((event.getX() - xOffset) / scale), (int) ((event.getY() - yOffset - touchOffset) / scale))) {
                    e.touch(event.getAction());
                    break;
                 }
              }
           }
        }
      
      return true;
   }
//   @Override
//   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//      setMeasuredDimension(width, height);
//   }   
}
