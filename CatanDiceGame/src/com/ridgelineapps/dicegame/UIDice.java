package com.ridgelineapps.dicegame;

import com.ridgelineapps.dicegame.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class UIDice extends UIEntity {
   public static final int size = 80;
   public static final int halfSize = size / 2;
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
   
   public UIDice(Game game, Type type, int index, int x, int y) {
      super(game, Type.dice, index, x - halfSize, y - halfSize, x + halfSize, y + halfSize);
      dice = game.dice[index];
      this.x = x - halfSize;
      this.y = y - halfSize;
      
      paint = new Paint();
      paint.setDither(true);
      paint.setFilterBitmap(true);
      paint.setAntiAlias(true);
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
            canvas.drawBitmap(bitmap, x, y, paint);
         }
      }
   }
}
