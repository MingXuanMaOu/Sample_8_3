package com.example.sample_8_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Game {

    MySurfaceView gp;
    ArrayList<int[][]> searchProcess = new ArrayList<>();
    Stack<int[][]> stack = new Stack<>();
    int[][] visited = new int[Map.MAP_DATA.length][Map.MAP_DATA[0].length];
    HashMap<String,int[][]> hm=new HashMap<String,int[][]>();//结果路径记录

    int[] source = Map.source;
    int[] target;
    int[][] map = Map.MAP_DATA;
    boolean pathFlag = false;

    public Game(MySurfaceView gp){
        this.gp = gp;
        target = Map.target[0];
    }

    int[][][] sequenceZ=//col,row
            {
                    {//偶数行
                            {-1,-1},
                            {0,-1},
                            {1,0},
                            {-1,0},
                            {-1,1},
                            {0,1},
                    },
                    {//奇数行
                            {0,-1},
                            {1,-1},
                            {-1,0},
                            {1,0},
                            {0,1},
                            {1,1},
                    }
            };

    public void DFS(){
        new Thread(){
            public void run(){
                boolean flag = true;
                int[][] start = {{source[0],source[1]},{source[0],source[1]}};
                stack.push(start);
                int count=0;
                while (flag){
                    int[][] currentEdge = stack.pop();
                    int[] tempTarget = currentEdge[1];

                    if(visited[tempTarget[1]][tempTarget[0]] == 1){
                        continue;
                    }
                    count++;

                    visited[tempTarget[1]][tempTarget[0]] = 1;
                    searchProcess.add(currentEdge);
                    hm.put(tempTarget[0]+":"+tempTarget[1],new int[][]{currentEdge[1],currentEdge[0]});

                    gp.repaint();

                    try{
                        Thread.sleep(10);
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                    if(tempTarget[0] == target[0] && tempTarget[1] == target[1]){
                        break;
                    }

                    int currCol = tempTarget[0];
                    int currRow = tempTarget[1];

                    int[][] sequence = null;
                    if(currRow % 2 == 0){
                        sequence = sequenceZ[0];
                    }else{
                        sequence = sequenceZ[1];
                    }

                    for (int[] rc : sequence) {
                        int i = rc[1];
                        int j = rc[0];
                        if(i == 0 && j == 0){continue;}
                        if(currRow+i>=0&&currRow+i< Map.MAP_DATA.length&&currCol+j>=0&&currCol+j<Map.MAP_DATA[0].length&&map[currRow+i][currCol+j]!=1){
                            int[][] tempEdge={
                                    {tempTarget[0],tempTarget[1]},
                                    {currCol+j,currRow+i}
                            };
                            stack.push(tempEdge);
                        }
                    }
                }
                pathFlag = true;
                System.out.println("使用步数：" + count);
                gp.repaint();
            }
        }.start();
    }
}
