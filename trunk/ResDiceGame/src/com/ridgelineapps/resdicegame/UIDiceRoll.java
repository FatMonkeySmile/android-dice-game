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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.view.MotionEvent;

public class UIDiceRoll extends UIEntity {
   int x;
   int y;
   int width;
   int height;
   Paint paint;
   Paint touchPaint;
   Paint textPaint;
   boolean down = false;
   
   public UIDiceRoll(Game game, int x, int y, int width, int height) {
      super(game, Type.roll, 0, x, y, x + width, y + height);
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      
      paint = new Paint();
//      paint.setDither(true);
//      paint.setFilterBitmap(true);
//      paint.setAntiAlias(true);
      paint.setStyle(Style.FILL);
      paint.setARGB(255, 50, 80, 120);         
      
      touchPaint = new Paint();
//      touchPaint.setDither(true);
//      touchPaint.setFilterBitmap(true);
//      touchPaint.setAntiAlias(true);
      touchPaint.setStyle(Style.FILL);
      touchPaint.setARGB(255, 120, 150, 190);
      
      textPaint = new Paint();
      textPaint.setFakeBoldText(true);
      textPaint.setAntiAlias(true);
      textPaint.setARGB(255, 200, 230, 230);
      textPaint.setTextSize(25);
      
      path = new Path();
      path.moveTo(x, y);
      path.lineTo(x + width, y);
      path.lineTo(x + width, y + height);
      path.lineTo(x, y + height);
      path.close();      
   }
   
   @Override
   public void touch(int action) {
       if(action == MotionEvent.ACTION_DOWN) {
           down = true;
           game.gameView.postInvalidate();
       }
       else if(down && action == MotionEvent.ACTION_UP) {
           down = false;
           if (game.isGameDone()) {
               game.gameView.postInvalidate();
               new AlertDialog.Builder(game.gameView.activity).setIcon(android.R.drawable.ic_dialog_alert).setTitle(game.getString(R.string.play_again))
                   .setMessage(game.getString(R.string.restart_text)).setPositiveButton(game.getString(R.string.yes), new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           game.roll();
                       }

                   }).setNegativeButton(game.getString(R.string.no), null).show();
           }
           else {
               game.roll();
           }
       }
       else if(action == MotionEvent.ACTION_CANCEL) {
           down = false;
           game.gameView.postInvalidate();
       }
   }
   
   @Override
   public void draw(Canvas canvas) {
      String text;
      if(game.canRoll()) {
         text = "";
        if (game.rolls == 0) {
            text = game.getString(R.string.first_roll);
        }
        if (game.rolls == 1) {
            text = game.getString(R.string.second_roll);
        }
        if (game.rolls == 2) {
            text = game.getString(R.string.third_roll);
        }
      }
      else if(game.isGameDone()) {
          text = game.getString(R.string.play_again);
      }
      else {
         text = game.getString(R.string.done);
      }
      int textWidth = (int) textPaint.measureText(text);
//      if(!game.isGameDone()) {
      if(down) {
          canvas.drawPath(path, touchPaint);
      }
      else {
          canvas.drawPath(path, paint);          
      }
          canvas.drawText(text, x + width / 2 - textWidth / 2, y + height - 15, textPaint);
//      }
//      else {
//          int offset = 80;
//          canvas.drawRect(x, y - offset, x + width, y + height - offset, paint);
//          canvas.drawText(text, x + width / 2 - textWidth / 2, y + height - 15 - offset, textPaint);
//      }
      super.draw(canvas);      
   }
}
