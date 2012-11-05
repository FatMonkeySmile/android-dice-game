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

public class PointLite {
   public final int x;
   public final int y; 

   // create and initialize a point with given (x, y)
   public PointLite(int x, int y) {
      this.x = x;
      this.y = y;
   }
 
   // return Euclidean distance between this point and that point
   public double distanceTo(PointLite that) {
      if (that == null) return Double.POSITIVE_INFINITY;
      double dx = this.x - that.x;
      double dy = this.y - that.y;
      return Math.hypot(dx, dy);
   }

   // is a->b->c a counter-clockwise turn?
   // +1 if counter-clockwise, -1 if clockwise, 0 if collinear
   public static int ccw(PointLite a, PointLite b, PointLite c) {
       // return a.x*b.y - a.y*b.x + a.y*c.x - a.x*c.y + b.x*c.y - b.y*c.x;
       double area2 = (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y);
       if      (area2 < 0) return -1;
       else if (area2 > 0) return +1;
       else                return  0;
   }

   // is a-b-c collinear?
   public static boolean collinear(PointLite a, PointLite b, PointLite c) {
       return ccw(a, b, c) == 0;
   }

   // is c between a and b?
   // Reference: O' Rourke p. 32
   public static boolean between(PointLite a, PointLite b, PointLite c) {
       if (ccw(a, b, c) != 0) return false;
       if (a.x == b.x && a.y == b.y) {
           return a.x == c.x && a.y == c.y;
       }
       else if (a.x != b.x) {
           // ab not vertical
           return (a.x <= c.x && c.x <= b.x) || (a.x >= c.x && c.x >= b.x);
       }
       else {
           // ab not horizontal
           return (a.y <= c.y && c.y <= b.y) || (a.y >= c.y && c.y >= b.y);
       }
   }
}
