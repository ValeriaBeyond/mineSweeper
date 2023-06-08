/*
* 初始化地雷
* */
public class BottomRay {
    int []rays = new int[GameUtil.RAY_MAX*2];
    // 存放坐标 : 相邻两个数存放的是地雷的横纵坐标，所以数组大小为地雷个数的两倍

    int x,y;
    // 地雷坐标

    boolean isPlace = true;
    // 是否可以放置 。T表示可以放置。F表示不可放置

    void newRay() {
        // 生成地雷坐标
        for(int i=0;i<GameUtil.RAY_MAX*2; i=i+2)
        {
            x=(int)(Math.random()*GameUtil.MAP_W+1); // 1-12
            y=(int)(Math.random()*GameUtil.MAP_H+1); // 1-12

            for(int j=0;j<i;j = j+2){      // 从0开始，如果本次随机生成的x,y坐标，已经在存放地雷坐标的数组中了，说明地雷位置重复
                if(x == rays[j] && y == rays[j+1]){
                    i=i-2;      // rays[]数组当前位置回退2个。进入下一轮循环，生成新的x,y坐标
                    isPlace = false;
                    break;
                }
            }

            if(isPlace){
                rays[i] = x;
                rays[i+1] = y;
            }
            // 释放状态
            isPlace = true;
        }

        // 将坐标放置到底层元素二维数组中
        for(int i = 0;i<GameUtil.RAY_MAX*2;i=i+2) {
            GameUtil.DATA_BOTTOM[rays[i]][rays[i+1]] = -1; //
        }
    }
}