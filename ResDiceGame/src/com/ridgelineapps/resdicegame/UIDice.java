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

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

public class UIDice extends UIEntity {
   public static final int size = 65;
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
   Paint holdPaint;
   Paint holdBorderPaint;
   Paint backPaint;
   
   public UIDice(Game game, int index, int x, int y) {
      super(game, Type.dice, index, x, y, x + size, y + size);
      dice = game.dice[index];
      this.x = x;
      this.y = y;
      
      paint = new Paint();
      paint.setDither(true);
      paint.setFilterBitmap(true);
      paint.setAntiAlias(true);
      
      holdPaint = new Paint();
      holdPaint.setARGB(140, 80, 65, 0);
      holdPaint.setStyle(Style.FILL);
      
      holdBorderPaint = new Paint();
      holdBorderPaint.setARGB(255, 135, 125, 55);
      holdBorderPaint.setAntiAlias(true);
      holdBorderPaint.setStyle(Style.FILL_AND_STROKE);
      holdBorderPaint.setStrokeWidth(4);
      
      backPaint = new Paint();
      backPaint.setARGB(255, 225, 215, 140);
      backPaint.setAntiAlias(true);
      backPaint.setStyle(Style.FILL);
      
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
      if(dice.isUsable() && dice.getValue() != Dice.Value.None) {
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
            if(dice.isHeld() || !game.canRoll()) {
                RectF rect = new RectF(x - 1, y - 1, x + size + 1, y + size + 1);
                canvas.drawRoundRect(rect, 7, 7, holdBorderPaint);
            }
            RectF rect = new RectF(x + 3, y + 3, x + size - 3, y + size - 3);
            canvas.drawRoundRect(rect, 7, 7, backPaint);
            Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            Rect dest = new Rect(x, y, x + size, y + size);
            canvas.drawBitmap(bitmap, src, dest, paint);
         }
      }
      super.draw(canvas);
   }
}
