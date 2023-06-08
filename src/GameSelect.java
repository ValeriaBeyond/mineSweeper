import java.awt.*;

/*
* 难度选择类
* */
public class GameSelect {

    // 判断鼠标是否点击到矩形区域
    boolean hard(){
        if(GameUtil.MOUSE_X>100&& GameUtil.MOUSE_X<400){
            if(GameUtil.MOUSE_Y>50 && GameUtil.MOUSE_Y<150){
                GameUtil.level = 1;
                GameUtil.state = 0;
                return true;
            }

            if(GameUtil.MOUSE_Y>200 && GameUtil.MOUSE_Y<300){
                GameUtil.level = 2;
                GameUtil.state = 0;
                return true;
            }

            if(GameUtil.MOUSE_Y>350 && GameUtil.MOUSE_Y<450){
                GameUtil.level = 3;
                GameUtil.state = 0;
                return true;
            }
        }
        return false;
    }

    void paintSelf(Graphics g){
        g.setColor(Color.black);
        g.drawRect(100, 50, 300, 100);
        GameUtil.drawWord(g,"简单",220, 100, 30, Color.BLACK);

        g.drawRect(100, 200, 300, 100);
        GameUtil.drawWord(g,"普通",220, 250, 30, Color.BLACK);

        g.drawRect(100, 350, 300, 100);
        GameUtil.drawWord(g,"困难",220, 400, 30, Color.BLACK);
    }

    // 重载hard()函数
    void hard(int level){
        switch(level){
            case 1:
                GameUtil.RAY_MAX = 10;
                GameUtil.MAP_W = 9;
                GameUtil.MAP_H = 9;
                break;

            case 2:
                GameUtil.RAY_MAX = 20;
                GameUtil.MAP_W = 12;
                GameUtil.MAP_H = 12;
                break;
            case 3:
                GameUtil.RAY_MAX = 35;
                GameUtil.MAP_W = 17;
                GameUtil.MAP_H = 16;
                break;
            default:
        }
    }
}
