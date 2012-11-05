package com.ridgelineapps.resdicegame;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.ridgelineapps.dicegame.java.Polygon;
import com.ridgelineapps.dicegame.mappings.Cities;
import com.ridgelineapps.dicegame.mappings.Knights;
import com.ridgelineapps.dicegame.mappings.Resources;
import com.ridgelineapps.dicegame.mappings.Roads;
import com.ridgelineapps.dicegame.mappings.Villages;

public class GameView extends View {
   int width = 480;
   int height = 800;
   
   Point scoreLoc = new Point(444, 184);
   
   Game game;
   ArrayList<UIEntity> entities = new ArrayList<UIEntity>();
   
   Bitmap playSheetImage;
   Paint imagePaint;
   Paint borderPaint;
   Paint scorePaint;
   Paint backPaint;
   
   MainActivity activity;
   
   int diceLocX = 15;
   int diceLocY = 650;
   int diceBuffer = 13;
   int rollOffsetX = 65;
   int rollOffsetY = 18;
   
   public GameView(Context context, Game game) {
      super(context);
      activity = (MainActivity) context;
      this.game = game;
      imagePaint = new Paint();
      imagePaint.setDither(true);
      imagePaint.setFilterBitmap(true);
      imagePaint.setAntiAlias(true);
      playSheetImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.sheet);

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
      entities.add(new UIDiceRoll(game, rollOffsetX, diceY, width - rollOffsetX * 2, height - rollOffsetY - diceY));
      
      Polygon poly;
      Path path;

      for(int i=0; i < Roads.touch.length; i++) {
         poly = new Polygon();
         path = new Path();
         for(int j = 0; j < Roads.touch[i].length; j++) {
            int x = Roads.touch[i][j][0];
            int y = Roads.touch[i][j][1];
            poly.addPoint(x, y);
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
         poly = new Polygon();
         path = new Path();
         for(int j = 0; j < Villages.touch[i].length; j++) {
            int x = Villages.touch[i][j][0];
            int y = Villages.touch[i][j][1];
            poly.addPoint(x, y);
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
         poly = new Polygon();
         path = new Path();
         for(int j = 0; j < Cities.touch[i].length; j++) {
            int x = Cities.touch[i][j][0];
            int y = Cities.touch[i][j][1];
            poly.addPoint(x, y);
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
         poly = new Polygon();
         path = new Path();
         for(int j = 0; j < Resources.touch[i].length; j++) {
            int x = Resources.touch[i][j][0];
            int y = Resources.touch[i][j][1];
            poly.addPoint(x, y);
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
         poly = new Polygon();
         path = new Path();
         for(int j = 0; j < Knights.touch[i].length; j++) {
            int x = Knights.touch[i][j][0];
            int y = Knights.touch[i][j][1];
            poly.addPoint(x, y);
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
      canvas.drawRect(0, 0, width, height, backPaint); 
       
      float scale = (float) playSheetImage.getScaledWidth(canvas) / 480;
      Rect src = new Rect(0, 0, (int) (width * scale), (int) (height * scale));
      Rect dest = new Rect(0, 0, width, height); //canvas.getWidth(), canvas.getHeight());
      
      canvas.drawBitmap(playSheetImage, src, dest, imagePaint);
      
//      canvas.drawBitmap(playSheetImage, 0, 0, imagePaint);
      
      for(UIEntity e : entities) {
         e.draw(canvas);
      }

      String score = "" + game.playsheet.getScore();
      int xOffset = (int) (scorePaint.measureText(score) / 2);
      canvas.drawText(score, scoreLoc.x - xOffset, scoreLoc.y, scorePaint);
      canvas.drawRect(0, 0, width - 1, height - 1, borderPaint);
      
      if(!game.isGameDone()) {
////         String turn = "" + (game.turnsTaken + 1);
          String turn = "Turn:  " + (game.turnsTaken + 1);
          xOffset = (int) (scorePaint.measureText(turn) / 2);
          canvas.drawText(turn, 334, 38, scorePaint);
      }
   }
   
   @Override
   public boolean onTouchEvent(MotionEvent event) {

      if((event.getAction() & MotionEvent.ACTION_UP) != 0) {
         for(UIEntity e : entities) {
            if(e.isWithin((int) event.getX(), (int) event.getY())) {
               e.up();
            }
         }
      }
      
      return true;
   }
   
   @Override
   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      setMeasuredDimension(width, height);
   }   
}
