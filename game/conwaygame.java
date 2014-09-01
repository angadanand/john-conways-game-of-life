/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

import static java.lang.Math.random;

/**
 *
 * @author Angad Alienware
 */
public class conwaygame 
{
    boolean[][] grid;
    boolean[][] nextgrid;
    int rows;
    int cols;
    
    public conwaygame(int rows, int cols, boolean grid[][], boolean nextgird[][])
    {
        this.rows = rows;
        this.cols = cols;
        this.grid = grid;
        this.nextgrid = nextgird;
    }
    
    public void populategrid()
    {
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                double seed =random(); // can be anything. related to time or clocks etc.
                
                if(seed > 0.49)
                    grid[i][j] = true;
            }
        }
        copyarray(nextgrid, grid);
    }
    
    //  true true
    //  true true
    public void testgrid1()
    {
        grid[11][11]=true;
        grid[11][12]=true;
        grid[12][11]=true;
        grid[12][12]=true;
        nextgrid[11][11]=true;
        nextgrid[11][12]=true;
        nextgrid[12][11]=true;
        nextgrid[12][12]=true;
    }
    
    
    //      true
    //      true
    //      true
    public void testgrid2()
    {
        
        grid[10][11] = true;
        grid[11][11] = true;
        grid[12][11] = true;
        
        nextgrid[10][11] = true;
        nextgrid[11][11] = true;
        nextgrid[12][11] = true;
               
    }
    
    
    /*
         T T T   
        T     T
               T 
    */
    public void testgrid3()
    {
        grid[10][10] = true;
        grid[9][11] = true;
        grid[9][12] = true;
        grid[9][13] = true;
        grid[10][14] = true;
        grid[11][15] = true;
        
        nextgrid[10][10] = true;
        nextgrid[9][11] = true;
        nextgrid[9][12] = true;
        nextgrid[9][13] = true;
        nextgrid[10][14] = true;
        nextgrid[11][15] = true;
    }
    
    public void testgrid4(int rows, int cols)
    {
        int filledrow = rows/2;
        for(int i = 0; i < cols; i++)
        {
            grid[filledrow][i] = true;
            nextgrid[filledrow][i] = true;
        }
    }
    
    private int getneighbours(int x, int y)
    {
        int neighbourcount = 0;
        
        //Diagonal corners with 3 surrounding them.
        if(x == 0 && y == 0)
        {
            if(grid[x][y+1] == true)
                neighbourcount++;
            if(grid[x+1][y] == true)
                neighbourcount++;
            if(grid[x+1][y+1] == true)
                neighbourcount++;
        }
        
        else if(x == rows-1 && y == cols-1)
        {
            if(grid[x][y-1] == true)
                neighbourcount++;
            if(grid[x-1][y] == true)
                neighbourcount++;
            if(grid[x-1][y-1] == true)
                neighbourcount++;
        }
        //--------------------------------------------//
        
        // first row //
        else if(x == 0 && y < cols-1)
        {
            if(grid[x+1][y] == true)
                neighbourcount++;
            
            if(y+1 < cols)
            {
                if(grid[x][y+1] == true)
                    neighbourcount++;
                if(grid[x+1][y+1] == true)
                neighbourcount++;
            }
            
            if(grid[x][y-1] == true)
                neighbourcount++;
            if(grid[x+1][y-1] == true)
                neighbourcount++;
        }
        
        // last row //
        else if(x == rows-1 && y < cols-1)
        {
            
            if(grid[x-1][y] == true)
                neighbourcount++;
            if(grid[x-1][y+1] == true)
                neighbourcount++;
            if(grid[x][y+1] == true)
                neighbourcount++;
            
            if(y!=0)
            {
                if(grid[x-1][y-1] == true)
                    neighbourcount++;
                if(grid[x][y-1] == true)
                    neighbourcount++;
            }
        }
        
        // first element of every row //
        else if(x < rows-1 && y == 0)
        {
            if(grid[x-1][y] == true)
                neighbourcount++;
            if(grid[x-1][y+1] == true)
                neighbourcount++;
            if(grid[x][y+1] == true)
                neighbourcount++;
            if(x+1 < rows)
            {
                if(grid[x+1][y+1] == true)
                    neighbourcount++;
                if(grid[x+1][y] == true)
                    neighbourcount++;
            }
        }
        
        // last element of every row //
        else if(x < rows-1 && y == cols-1)
        {
            if(grid[x][y-1] == true)
                neighbourcount++;
            if(grid[x+1][y-1] == true)
                neighbourcount++;
            if(grid[x+1][y] == true)
                neighbourcount++;
            if(x!=0)
            {
                if(grid[x-1][y-1] == true)
                    neighbourcount++;
                if(grid[x-1][y] == true)
                    neighbourcount++;
            }
        }
        
        //General Case.
        else
        {
            if(grid[x][y+1] == true)
                neighbourcount++;
            if(grid[x][y-1] == true)
                neighbourcount++;
            
            if(grid[x+1][y-1] == true)
                neighbourcount++;
            if(grid[x+1][y] == true)
                neighbourcount++;
            if(grid[x+1][y+1] == true)
                neighbourcount++;
           
            if(grid[x-1][y-1] == true)
                neighbourcount++;
            if(grid[x-1][y] == true)
                neighbourcount++;
            if(grid[x-1][y+1] == true)
                neighbourcount++;
        }
        
        return neighbourcount;
    }
    
    private void copyarray(boolean[][] a, boolean[][] b) //copy B onto A
    {
        for(int i = 0; i < rows; i++)
        {
            System.arraycopy(b[i], 0, a[i], 0, cols);
        }
    }
    
    public void analyzeandupdate()
    {
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                int nc = getneighbours(i, j);
                if(grid[i][j])
                {
                    if(nc < 2)
                    {
                        nextgrid[i][j]=false;
                    }
                    else if(nc >= 4)
                    {
                        nextgrid[i][j]=false;
                    }
                    else
                    {
                        nextgrid[i][j] = true;
                    }
                }
                else
                {
                    if(nc == 3)
                    {
                        nextgrid[i][j] = true;
                    }
                }
              
            }
        }
        copyarray(grid, nextgrid);
    }
}
