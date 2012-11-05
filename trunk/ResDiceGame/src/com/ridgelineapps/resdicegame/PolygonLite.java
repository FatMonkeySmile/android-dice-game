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
/*
 * Originally from http://algs4.cs.princeton.edu/91primitives/ 
 * 
 * Copyright (C) 2007, Robert Sedgewick and Kevin Wayne.
 */
package com.ridgelineapps.resdicegame;

import android.graphics.Rect;


public class PolygonLite {
   private int N;        // number of points in the polygon
   private PointLite[] a;    // the points, setting points[0] = points[N]
  
   /////////////////////////////////////*
   // Added by Resource Dice Game
   Rect rect;
   /////////////////////////////////////*
   
   public PolygonLite() {
       N = 0;
       a = new PointLite[16];
   }


   // double size of array
   private void resize() {
      PointLite[] temp = new PointLite[2*N+1];
       for (int i = 0; i <= N; i++) temp[i] = a[i];
       a = temp;
   }

   // return size
   public int size() { return N; }

   // add point p to end of polygon
   public void add(PointLite p) {
       if (N >= a.length - 1) resize();   // resize array if needed
       a[N++] = p;                        // add point
       a[N] = a[0];                       // close polygon
   }

   // return signed area of polygon
   public double area() {
       double sum = 0.0;
       for (int i = 0; i < N; i++) {
           sum = sum + (a[i].x * a[i+1].y) - (a[i].y * a[i+1].x);
       }
       return 0.5 * sum;
   }

   // does this Polygon contain the point p?
   // if p is on boundary then 0 or 1 is returned, and p is in exactly one point of every partition of plane
   // Reference: http://exaflop.org/docs/cgafaq/cga2.html
   public boolean contains(PointLite p) {
      if(!quickContains(p)) {
         return false;
      }
      
       int crossings = 0;
       for (int i = 0; i < N; i++) {
           int j = i + 1;
           boolean cond1 = (a[i].y <= p.y) && (p.y < a[j].y);
           boolean cond2 = (a[j].y <= p.y) && (p.y < a[i].y);
           if (cond1 || cond2) {
               // need to cast to double
               if (p.x < (a[j].x - a[i].x) * (p.y - a[i].y) / (a[j].y - a[i].y) + a[i].x)
                   crossings++;
           }
       }
       if (crossings % 2 == 1) return true;
       else                    return false; 
   }

   // does this Polygon contain the point p?
   // Reference: http://softsurfer.com/Archive/algorithm_0103/algorithm_0103.htm
   public boolean contains2(PointLite p) {
      if(!quickContains(p)) {
         return false;
      }
      
       int winding = 0;
       for (int i = 0; i < N; i++) {
           int ccw = PointLite.ccw(a[i], a[i+1], p);
           if (a[i+1].y >  p.y && p.y >= a[i].y)  // upward crossing
               if (ccw == +1) winding++;
           if (a[i+1].y <= p.y && p.y <  a[i].y)  // downward crossing
               if (ccw == -1) winding--;
       }
       return winding != 0;
   }   
   
   /////////////////////////////////////*
   // Added by Resource Dice Game
   public boolean quickContains(PointLite p) {
      if(a == null || a.length == 0) {
         return false;
      }
      
      if(rect == null) {
          int x1 = a[0].x;
          int y1 = a[0].y;
          int x2 = a[0].x;
          int y2 = a[0].y;
          
          for(int i=1;i < N; i++) {
              if(a[i].x < x1) {
                  x1 = a[i].x;
              }
              if(a[i].x > x2) {
                  x2 = a[i].x;
              }
              if(a[i].y < y1) {
                  y1 = a[i].y;
              }
              if(a[i].y > y2) {
                  y2 = a[i].y;
              }
          }
          
          rect = new Rect(x1, y1, x2, y2);
      }
      
      if(!rect.contains((int)p.x, (int)p.y)) {
          return false;
      }
      
      return true;
   }
   /////////////////////////////////////*
}
