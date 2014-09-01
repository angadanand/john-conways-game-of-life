/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Angad Alienware
 */
public class grid 
{
    int width, height, rows, cols;
    boolean[][] conwaygrid;
    boolean[][] nextconwaygrid;

    public grid(int width, int height)
    {
        this.width = width;
        this.height = height;
        rows = height/5;
        cols = width/5;
        conwaygrid = new boolean[rows][cols];
        nextconwaygrid = new boolean[rows][cols];
    }
    
    private void makeDisplay()
    {
         try
        {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
        }
        catch(LWJGLException l)
        {
            l.printStackTrace();
        }
    }
    
     private void initGL()
     {
         glMatrixMode(GL_PROJECTION);
         glLoadIdentity();
         glOrtho(0, width, 0, height, 1, -1);
         glMatrixMode(GL_MODELVIEW);
     }
     
     void makeRect(int x, int y, boolean alive)
     {
         glPushMatrix();
         {
            glTranslated(x, y, 0);
            if(alive)    
                glColor3d(0, 0.60f, 0);
            else
                glColor3f(128,0,0);
            glBegin(GL_QUADS);
            {
               glVertex2d(0, 0);
               glVertex2d(4, 0);
               glVertex2d(4, 4);
               glVertex2d(0, 4);
            }
            glEnd();
         }
         glPopMatrix();
     }
     
     private void fillWithRect()
     {
         //int height = Display.getHeight() - 10;
         //int width = Display.getWidth() - 10;
         int y = 2;
         for(int row = 0; row < rows; row++)
         {
             int x = 2;
             for(int col = 0; col < cols; col++)
             {
                 makeRect(x, y, conwaygrid[row][col]);
                 x += 5; 
             }
             y += 5;
         }
     }
     
     private void gameLoop()
     {
         Display.setTitle("Press Enter to Begin | Red = Dead | Green = Alive");
         conwaygame game = new conwaygame(rows, cols, conwaygrid, nextconwaygrid);
         game.testgrid4(rows, cols);
         boolean pressed = false;
         int gen=0;
         fillWithRect();
         
         while(!Display.isCloseRequested())
         {
             while(true)
            {
                if(!Keyboard.isKeyDown(Keyboard.KEY_RETURN) && !pressed)
                {
                    Display.update();
                    Display.sync(60);
                }
                else
                {
                    pressed = true;
                    break;
                }
            }
             ++gen;
             Display.setTitle("Generation : "+gen+" | Red = Dead | Green = Alive");
             fillWithRect();
             game.analyzeandupdate();
             Display.sync(60);
             Display.update();
         }
         Display.destroy();
     }
     
     public void begin()
     {
         makeDisplay();
         initGL();
         gameLoop();
     }
}
