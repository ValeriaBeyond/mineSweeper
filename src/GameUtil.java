import java.awt.*;

/*
*  工具类
* 存放静态参数
* 工具方法
*/
public class GameUtil {
    // 地雷个数
    static int RAY_MAX = 100;
    // 格子的宽 : 横着的格子的数量
    static int MAP_W = 36;
    // 格子的高 ： 竖着的格子的数量
    static int MAP_H = 17;
    // 雷区偏移量
    static int OFFSET = 45;
    // 格子边长
    static int SQUARE_LENGTH = 50;
    // 插旗数量
    static int FLAG_NUM =0;

    // 鼠标相关参数
    // 坐标
    static int MOUSE_X;
    static int MOUSE_Y;
    // 状态
    static boolean LEFT = false;
    static boolean RIGHT = false;

    // 游戏状态:0-游戏中，1-成功，2-失败, 3-游戏状态
    static int state = 3;
    // 难度级别
    static int level;

    // 倒计时
    static long START_TIME;
    static long END_TIME;


    // 底层元素 -1代表雷， 0代表空  1-8表示对应数字
    static int[][] DATA_BOTTOM = new int[MAP_W+2][MAP_H+2];

    // 顶层元素 -1代表未覆盖(被翻开)， 0代表被覆盖(未被翻开)  1代表插旗， 2代表插错棋
    static int[][] DATA_TOP = new int[MAP_W+2][MAP_H+2];

    // 载入图片
    static Image lei = Toolkit.getDefaultToolkit().getImage("D:\\ValeriaLi\\java_idea\\" +
            "IdeaProjects\\LIYuXin'sProgram\\java选修课\\FINALHOMEWORK\\images\\lei.png");
    static Image top = Toolkit.getDefaultToolkit().getImage("D:\\ValeriaLi\\java_idea\\" +
            "IdeaProjects\\LIYuXin'sProgram\\java选修课\\FINALHOMEWORK\\images\\top.gif");
    static Image flag = Toolkit.getDefaultToolkit().getImage("D:\\ValeriaLi\\java_idea\\" +
            "IdeaProjects\\LIYuXin'sProgram\\java选修课\\FINALHOMEWORK\\images\\flag.gif");
    static Image noflag = Toolkit.getDefaultToolkit().getImage("D:\\ValeriaLi\\java_idea\\" +
            "IdeaProjects\\LIYuXin'sProgram\\java选修课\\FINALHOMEWORK\\images\\noflag.png");

    static Image face = Toolkit.getDefaultToolkit().getImage("D:\\ValeriaLi\\java_idea\\" +
            "IdeaProjects\\LIYuXin'sProgram\\java选修课\\FINALHOMEWORK\\images\\face.png");
    static Image win = Toolkit.getDefaultToolkit().getImage("D:\\ValeriaLi\\java_idea\\" +
            "IdeaProjects\\LIYuXin'sProgram\\java选修课\\FINALHOMEWORK\\images\\win.png");
    static Image over = Toolkit.getDefaultToolkit().getImage("D:\\ValeriaLi\\java_idea\\" +
            "IdeaProjects\\LIYuXin'sProgram\\java选修课\\FINALHOMEWORK\\images\\over.png");



    // 图片数组存放1~8数字
    static Image[] images = new Image[9];
    static{
        for(int i=1;i<=8;i++){
            images[i] = Toolkit.getDefaultToolkit().getImage("D:\\ValeriaLi\\java_idea\\IdeaProjects" +
                    "\\LIYuXin'sProgram\\java选修课\\FINALHOMEWORK\\images\\num\\"+i+".png");
        }
    }

    static void drawWord(Graphics g, String str, int x, int y, int size,Color color) {
        g.setColor(color);
        g.setFont(new Font("仿宋",Font.BOLD,size));
        g.drawString(str, x,y);
    }
}




























