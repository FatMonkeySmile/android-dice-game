package com.ridgelineapps.dicegame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class UIDiceRoll extends UIEntity {
   public static final int size = 50;
   int x;
   int y;
   Paint paint;
   
   public UIDiceRoll(Game game, int x, int y) {
      super(game, Type.roll, 0, x, y, x + size, y + size);
      this.x = x;
      this.y = y;
      
      paint = new Paint();
      paint.setDither(true);
      paint.setFilterBitmap(true);
      paint.setAntiAlias(true);
      
      path = new Path();
      path.moveTo(x, y);
      path.lineTo(x + size, y);
      path.lineTo(x + size, y + size);
      path.lineTo(x, y + size);
      path.close();      
   }
   
   @Override
   public void draw(Canvas canvas) {
      if(game.canRoll()) {
         paint.setARGB(255, 128, 10, 10);
      }
      else {
         paint.setARGB(255, 10, 10, 128);         
      }
      canvas.drawRect(x, y, x + size, y + size, paint);
      super.draw(canvas);      
   }
}
