import java.awt.*;

/*
* 底层地图
* 绘制游戏相关的组件
* */
public class MapBottom {
    // 地雷类
    BottomRay bottomRay = new BottomRay();
    // 数字类
    BottomNum bottomNum = new BottomNum();
    {
        bottomRay.newRay();
        bottomNum.newNum();
    }
    // 游戏重置
    void reGame(){
        for(int i=1;i<=GameUtil.MAP_W;i++){
            for(int j=1;j<=GameUtil.MAP_H;j++){
                // 将底层元素全部重置为0
                GameUtil.DATA_BOTTOM[i][j] = 0;
            }
        }
        bottomRay.newRay();
        bottomNum.newNum();
    }

    void paintSelf(Graphics g) {
        g.setColor(Color.red);

        // 画竖线
        for (int i = 0; i <= GameUtil.MAP_W; i++) {
            g.drawLine(GameUtil.OFFSET + i * GameUtil.SQUARE_LENGTH,
                    3 * GameUtil.OFFSET,
                    GameUtil.OFFSET + i * GameUtil.SQUARE_LENGTH,
                    3 * GameUtil.OFFSET + GameUtil.MAP_H * GameUtil.SQUARE_LENGTH);
        }

        // 画横线
        for (int i = 0; i <=GameUtil.MAP_H; i++){
            g.drawLine(GameUtil.OFFSET,
                    3*GameUtil.OFFSET+i*GameUtil.SQUARE_LENGTH,
                    GameUtil.OFFSET+GameUtil.MAP_W*GameUtil.SQUARE_LENGTH,
                    3*GameUtil.OFFSET+i*GameUtil.SQUARE_LENGTH);
        }


        for (int i = 1; i <= GameUtil.MAP_W ; i++) {
            for (int j = 1; j <=GameUtil.MAP_H ; j++) {
                // 画雷
                if(GameUtil.DATA_BOTTOM[i][j] == -1) {
                    g.drawImage(GameUtil.lei,
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            null);
                }

                // 绘制数字
                if(GameUtil.DATA_BOTTOM[i][j] >=0) {
                    g.drawImage(GameUtil.images[GameUtil.DATA_BOTTOM[i][j]],
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 15,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 5,
                            null);
                }
            }
        }

        // 绘制剩余雷数的数字
        GameUtil.drawWord(g,""+(GameUtil.RAY_MAX-GameUtil.FLAG_NUM),
                GameUtil.OFFSET,2*GameUtil.OFFSET,30,Color.red);
        GameUtil.drawWord(g,""+(GameUtil.END_TIME-GameUtil.START_TIME)/1000,  //换算成秒
                GameUtil.OFFSET+GameUtil.SQUARE_LENGTH*(GameUtil.MAP_W-1),
                2*GameUtil.OFFSET,30,Color.red);

        switch (GameUtil.state){
            case 0:
                GameUtil.END_TIME = System.currentTimeMillis();
                g.drawImage(GameUtil.face,
                        GameUtil.OFFSET+GameUtil.SQUARE_LENGTH*(GameUtil.MAP_W/2),
                        GameUtil.OFFSET,
                        null);
                break;
            case 1:
                g.drawImage(GameUtil.win,
                        GameUtil.OFFSET+GameUtil.SQUARE_LENGTH*(GameUtil.MAP_W/2),
                        GameUtil.OFFSET,
                        null);
                break;
            case 2:
                g.drawImage(GameUtil.over,
                        GameUtil.OFFSET+GameUtil.SQUARE_LENGTH*(GameUtil.MAP_W/2),
                        GameUtil.OFFSET,
                        null);
                break;
            default:
        }
    }
}
