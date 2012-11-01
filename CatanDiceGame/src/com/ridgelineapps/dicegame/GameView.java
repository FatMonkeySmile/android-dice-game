package com.ridgelineapps.dicegame;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.ridgelineapps.dicegame.R;
import com.ridgelineapps.dicegame.java.Polygon;
import com.ridgelineapps.dicegame.mappings.Cities;
import com.ridgelineapps.dicegame.mappings.Knights;
import com.ridgelineapps.dicegame.mappings.Resources;
import com.ridgelineapps.dicegame.mappings.Roads;
import com.ridgelineapps.dicegame.mappings.Villages;

public class GameView extends View {
   int width = 480;
   int height = 800;
   
   Game game;
   ArrayList<UIEntity> entities = new ArrayList<UIEntity>();
   
   Bitmap playSheetImage;
   Paint imagePaint;
   
   MainActivity activity;
   
   public GameView(Context context, Game game) {
      super(context);
      activity = (MainActivity) context;
      this.game = game;
      imagePaint = new Paint();
      imagePaint.setDither(true);
      imagePaint.setFilterBitmap(true);
      imagePaint.setAntiAlias(true);
      playSheetImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.sheet);
      
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
         UIEntity entity = new UIEntity(game, UIEntity.Type.resource, i, poly, path);
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
         UIEntity entity = new UIEntity(game, UIEntity.Type.knight, i, poly, path);
         entities.add(entity);
      }
   }

   @Override
   protected void onDraw(Canvas canvas) {
      float scale = (float) playSheetImage.getScaledWidth(canvas) / 480;
      Rect src = new Rect(0, 0, (int) (480 * scale), (int) (800 * scale));
      Rect dest = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
      
      canvas.drawBitmap(playSheetImage, src, dest, imagePaint);
      
      for(UIEntity e : entities) {
         e.draw(canvas);
      }
   }
   
   @Override
   public boolean onTouchEvent(MotionEvent event) {

      if((event.getAction() & MotionEvent.ACTION_UP) != 0) {
         for(UIEntity e : entities) {
            if(e.isWithin(event.getX(), event.getY())) {
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
