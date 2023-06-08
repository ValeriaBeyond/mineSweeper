import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Color.*;

public class GameWin extends JFrame {

    // 设置窗口大小
    int width = 2*GameUtil.OFFSET+GameUtil.MAP_W*GameUtil.SQUARE_LENGTH;
    int height = 4*GameUtil.OFFSET+GameUtil.MAP_H*GameUtil.SQUARE_LENGTH;

    // 单独创建一个画布。先将底层和顶层元素绘制上去
    Image offScreenImage = null;

    MapBottom mapBottom = new MapBottom();
    MapTop mapTop = new MapTop();
    GameSelect gameSelect = new GameSelect();

    // 开始参数:false未开始 true开始
    boolean begin = false;

    void launch(){

        // 开始记录时间
        GameUtil.START_TIME = System.currentTimeMillis();

        // 设置窗口
        this.setVisible(true);

        // 根据游戏状态，判断是否需要重置窗口大小
        if(GameUtil.state == 3){
            this.setSize(500,500);
        }
        else{
            this.setSize(width, height);    // 设置窗口大小
        }

        this.setLocationRelativeTo(null);        // 设置窗口位置：居中显示
        this.setTitle("扫雷游戏");                 // 设置窗口标题
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);   // 设置关闭窗口的方法

        // 鼠标事件
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                switch(GameUtil.state){
                    case 0:
                        if(e.getButton() == 1){  // 鼠标左键
                            GameUtil.MOUSE_X = e.getX();
                            GameUtil.MOUSE_Y = e.getY();
                            GameUtil.LEFT = true;
                        }
                        if(e.getButton() == 3){  // 鼠标右键
                            GameUtil.MOUSE_X = e.getX();
                            GameUtil.MOUSE_Y = e.getY();
                            GameUtil.RIGHT = true;
                        }
                    case 1:
                    case 2:
                        if(e.getButton() == 1){
                            if(e.getX()>GameUtil.OFFSET+GameUtil.SQUARE_LENGTH*(GameUtil.MAP_W/2)
                                    && e.getX()<GameUtil.OFFSET+GameUtil.SQUARE_LENGTH*(GameUtil.MAP_W/2)+GameUtil.SQUARE_LENGTH
                                    && e.getY()>GameUtil.OFFSET
                                    && e.getY()<GameUtil.OFFSET+GameUtil.SQUARE_LENGTH)
                            {
                                mapBottom.reGame();
                                mapTop.reGame();
                                GameUtil.FLAG_NUM = 0;
                                GameUtil.START_TIME = System.currentTimeMillis();
                                GameUtil.state = 0;
                            }
                        }
                        if(e.getButton() == 2){
                            GameUtil.state = 3;
                            begin = true;
                        }
                        break;
                    case 3:
                        if(e.getButton() == 1){  // 鼠标左键
                            GameUtil.MOUSE_X = e.getX();
                            GameUtil.MOUSE_Y = e.getY();
                            begin = gameSelect.hard();
                        }
                        break;

                    default:
                }
            }
        });

        while(true){
            repaint();  //一直调用绘制方法
            begin();
            try {
                Thread.sleep(40); /* 添加一个延时，降低刷新速度 */
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void begin(){
        if(begin){
            begin =false;
            gameSelect.hard(GameUtil.level);
            dispose();
            GameWin gameWin = new GameWin();
            GameUtil.START_TIME = System.currentTimeMillis();
            GameUtil.FLAG_NUM = 0;
            mapBottom.reGame();
            mapTop.reGame();
            gameWin.launch();
        }
    }

    @Override
    public void paint(Graphics g)
    {
        if(GameUtil.state == 3){
            g.setColor(white);
            g.fillRect(0,0,500,500);
            gameSelect.paintSelf(g);
        }
        else{
            // 画布设置宽高
            offScreenImage = this.createImage(width, height);
            // 设置画布的画笔
            Graphics gImage = offScreenImage.getGraphics();
            // 设置画布背景颜色
            gImage.setColor(ORANGE);
            gImage.fillRect(0,0, width, height);
            // 调用新画布上的画笔将底层和顶层元素都绘制出来
            mapBottom.paintSelf(gImage);
            mapTop.paintSelf(gImage);
            // 在主窗口中调用画笔的绘制功能，将新绘制好的画布绘制到主窗口中
            g.drawImage(offScreenImage, 0, 0, null);
        }
    }

    public static void main(String[] args) {
        GameWin gameWin = new GameWin();
        gameWin.launch();
    }
}
