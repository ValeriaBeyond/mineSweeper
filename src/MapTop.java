import java.awt.*;

/*
* 顶层地图类
* 绘制顶层图片
* 判断逻辑
*/
public class MapTop {
    // 鼠标坐标转换为格子位置
    int temp_x;
    int temp_y;

    void reGame(){
        for(int i=1;i<=GameUtil.MAP_W;i++){
            for(int j=1;j<=GameUtil.MAP_H;j++){
                // 将底层元素全部重置为0
                GameUtil.DATA_TOP[i][j] = 0;
            }
        }
    }

    void logic() {
        temp_x = 0;
        temp_y = 0;
        // 鼠标坐标转化为格子所在的坐标
        if (GameUtil.MOUSE_X > GameUtil.OFFSET && GameUtil.MOUSE_Y > 3 * GameUtil.OFFSET) {
            temp_x = (GameUtil.MOUSE_X - GameUtil.OFFSET) / GameUtil.SQUARE_LENGTH + 1;
            temp_y = (GameUtil.MOUSE_Y - 3 * GameUtil.OFFSET) / GameUtil.SQUARE_LENGTH + 1;
        }

        // 判断在雷区才进行操作
        if (temp_x >= 1 && temp_x <= GameUtil.MAP_W && temp_y >= 1 && temp_y <= GameUtil.MAP_H) {
            if (GameUtil.LEFT) {      // 左键
                // 未被翻开则翻开
                if (GameUtil.DATA_TOP[temp_x][temp_y] == 0) {   // 如果
                    GameUtil.DATA_TOP[temp_x][temp_y] = -1;
                }
                spaceOpen(temp_x, temp_y);
                GameUtil.LEFT = false;
            }
            if (GameUtil.RIGHT) {     // 右键
                // 未被翻开，则点击右键则插旗
                if (GameUtil.DATA_TOP[temp_x][temp_y] == 0) {   // 如果
                    GameUtil.DATA_TOP[temp_x][temp_y] = 1;
                    GameUtil.FLAG_NUM++;
                }
                // 已被插旗，点击右键则取消插旗
                else if (GameUtil.DATA_TOP[temp_x][temp_y] == 1) {   // 如果该区域被打开，
                    GameUtil.DATA_TOP[temp_x][temp_y] = 0;
                    GameUtil.FLAG_NUM--;
                }
                // 已被翻开，右键则
                else if (GameUtil.DATA_TOP[temp_x][temp_y] == -1) {
                    numOpen(temp_x, temp_y);
                }
                GameUtil.RIGHT = false;
            }
        }

        boom();
        victory();
    }

    void numOpen(int x, int y) {
        int count = 0;
        // 如果底层是数字
        if (GameUtil.DATA_BOTTOM[x][y] > 0) {
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (GameUtil.DATA_TOP[i][j] == 1) {
                        // 记录右键点击的格子周围3*3区域内的插旗数
                        count++;
                    }
                }
            }

            if (count == GameUtil.DATA_BOTTOM[x][y]) {
                // 如果记录的旗数和底层数字相同，说明插旗数正确。则把周围未插旗空格翻开
                for (int i = x - 1; i <= x + 1; i++) {
                    for (int j = y - 1; j <= y + 1; j++) {
                        if (GameUtil.DATA_TOP[i][j] != 1) {  // 未插旗，则翻开
                            GameUtil.DATA_TOP[i][j] = -1;
                        }
                        // 必须在雷区中才能翻开
                        if (i >= 1 && j >= 1 && i <= GameUtil.MAP_W && j <= GameUtil.MAP_H) {
                            spaceOpen(i, j);
                        }
                    }
                }
            }
        }
    }

    void paintSelf(Graphics g) {
        logic();
        for (int i = 1; i <= GameUtil.MAP_W; i++) {
            for (int j = 1; j <= GameUtil.MAP_H; j++) {
                // 覆盖
                if (GameUtil.DATA_TOP[i][j] == 0) {
                    g.drawImage(GameUtil.top,
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            null);
                }

                // 插旗
                if (GameUtil.DATA_TOP[i][j] == 1) {
                    g.drawImage(GameUtil.flag,
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            null);
                }

                // 插错棋
                if (GameUtil.DATA_TOP[i][j] == 2) {
                    g.drawImage(GameUtil.noflag,
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            null);
                }
            }
        }
    }

    // 打开空格
    void spaceOpen(int x, int y) {
        if (GameUtil.DATA_BOTTOM[x][y] == 0) { // 如果(x,y)是空白格
            for (int i = x - 1; i <= x + 1; i++) {       // 翻开空白格周围3*3的格子
                for (int j = y - 1; j <= y + 1; j++) {
                    if (GameUtil.DATA_TOP[i][j] != -1) {
                    // 未被翻开，则翻开
                        // 如果有插旗，则释放
                        if(GameUtil.DATA_TOP[i][j] == 1){
                            GameUtil.FLAG_NUM--;
                        }
                        GameUtil.DATA_TOP[i][j] = -1;
                        // 周围的空白格必须在雷区中才能进行递归调用
                        if (i >= 1 && j >= 1 && i <= GameUtil.MAP_W && j <= GameUtil.MAP_H) {
                            spaceOpen(i, j);
                        }
                    }
                }
            }
        }
    }

    // 失败判定：true为失败，false为成功
    boolean boom() {
        if(GameUtil.FLAG_NUM == GameUtil.RAY_MAX){
            for (int i = 1; i <= GameUtil.MAP_W; i++) {
                for (int j = 1; j <= GameUtil.MAP_H; j++) {
                    if(GameUtil.DATA_TOP[i][j] == 0){
                        // 没有被翻开，则翻开
                        GameUtil.DATA_TOP[i][j] = -1;
                    }
                }
            }
        }
        for (int i = 1; i <= GameUtil.MAP_W; i++) {
            for (int j = 1; j <= GameUtil.MAP_H; j++) {
                if (GameUtil.DATA_BOTTOM[i][j] == -1 && GameUtil.DATA_TOP[i][j] == -1) {
                    // 如果底层是雷，并且被翻开的话。则失败
                    GameUtil.state = 2;
                    seeBoom();
                    return true;
                }
            }
        }
        return false;
    }
    // 失败后显示所有雷以及插错的棋
    void seeBoom(){
        for (int i = 1; i <= GameUtil.MAP_W; i++) {
            for (int j = 1; j <= GameUtil.MAP_H; j++) {
                // 底层是雷，顶层不是棋
                if(GameUtil.DATA_BOTTOM[i][j]==-1 && GameUtil.DATA_TOP[i][j] != 1){
                    // 翻开雷
                    GameUtil.DATA_TOP[i][j] = -1;
                }
                // 底层不是雷，顶层是棋
                if(GameUtil.DATA_BOTTOM[i][j]!=-1 && GameUtil.DATA_TOP[i][j] == 1){
                    // 显示插错棋
                    GameUtil.DATA_TOP[i][j] = 2;
                }
            }
        }
    }
    // 胜利判断 : true胜利 false失败
    boolean victory(){
        int count = 0;
        // 统计未翻开格子数
        for (int i = 1; i <= GameUtil.MAP_W; i++) {
            for (int j = 1; j <= GameUtil.MAP_H; j++) {
                if(GameUtil.DATA_TOP[i][j] != -1){
                    count++;
                }
            }
        }
        if(count == GameUtil.RAY_MAX){
            GameUtil.state = 1;
            for (int i = 1; i <= GameUtil.MAP_W; i++) {
                for (int j = 1; j <= GameUtil.MAP_H; j++) {
                    if(GameUtil.DATA_TOP[i][j] == 0){
                        // 未翻开则变成棋
                        GameUtil.DATA_TOP[i][j] = 1;
                    }
                }
            }
            return true;
        }
        return false;
    }
}