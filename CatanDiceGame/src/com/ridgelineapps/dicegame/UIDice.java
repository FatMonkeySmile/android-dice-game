package com.ridgelineapps.dicegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

public class UIDice extends UIEntity {
   public static final int size = 40;
   Dice dice;
   int x;
   int y;
   static Bitmap wool;
   static Bitmap grain;
   static Bitmap brick;
   static Bitmap ore;
   static Bitmap lumber;
   static Bitmap gold;
   Paint paint;
   
   public UIDice(Game game, int index, int x, int y) {
      super(game, Type.dice, index, x, y, x + size, y + size);
      dice = game.dice[index];
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
   
   public static void loadBitmaps(Resources resources) {
      wool = BitmapFactory.decodeResource(resources, R.drawable.wool);
      grain= BitmapFactory.decodeResource(resources, R.drawable.grain);
      brick = BitmapFactory.decodeResource(resources, R.drawable.brick);
      ore = BitmapFactory.decodeResource(resources, R.drawable.ore);
      lumber = BitmapFactory.decodeResource(resources, R.drawable.lumber);
      gold = BitmapFactory.decodeResource(resources, R.drawable.gold);
   }
   
   @Override
   public void draw(Canvas canvas) {
      if(dice.getValue() != Dice.Value.None) {
         Bitmap bitmap = null;
         switch(dice.getValue()) {
            case Wool:
               bitmap = wool;
               break;
            case Grain:
               bitmap = grain;
               break;
            case Brick:
               bitmap = brick;
               break;
            case Ore:
               bitmap = ore;
               break;
            case Lumber:
               bitmap = lumber;
               break;
            case Gold:
               bitmap = gold;
               break;
         }
         if(bitmap != null) {
            Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            Rect dest = new Rect(x, y, x + size, y + size);
            canvas.drawBitmap(bitmap, src, dest, paint);
         }
      }
      super.draw(canvas);
   }
}
