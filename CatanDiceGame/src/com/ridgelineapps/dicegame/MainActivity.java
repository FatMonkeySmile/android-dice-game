package com.ridgelineapps.dicegame;

import com.ridgelineapps.dicegame.R;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
   
   Game game;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
         //TODO: confirm restart
         game.reset();
         break;
      }
      return true;
   }
}