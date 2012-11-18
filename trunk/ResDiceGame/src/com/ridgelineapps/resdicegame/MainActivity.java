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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
   
   Game game;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);        
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        game = new Game();
        GameView gameView = new GameView(this, game);
        game.setGameView(gameView);
        setContentView(gameView);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case R.id.quit:
         finish();
         break;
      case R.id.restart:
          new AlertDialog.Builder(game.gameView.activity).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getResources().getString(R.string.restart_title))
          .setMessage(getResources().getString(R.string.restart_text)).setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                  game.reset();
              }

          }).setNegativeButton(getResources().getString(R.string.no), null).show();
         break;
      case R.id.about:
         startActivity(new Intent(MainActivity.this, About.class));
         break;
      case R.id.rules:
         startActivity(new Intent(MainActivity.this, Rules.class));
         break;
      case R.id.high_scores:
          startActivity(new Intent(MainActivity.this, HighScores.class));
          break;
      }
      return true;
   }
}