import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.Icon;

/**
 * 
 * @author Changtai Xiong
 * @version 0.1
 * 
 * 类名: CloseTabIcon
 * 
 * 继承：Icon类
 * 
 * 作用: 用来作为Tab控件的关闭按钮
 *
 * 成员：
 *       x_pos, y_pos  :int  ==> 横坐标与纵坐标
 *       width, height :int  ==> 长宽
 *
 */

class CloseTabIcon implements Icon {

 private int x_pos;
 private int y_pos;
 private int width;
 private int height;

 public CloseTabIcon() {
  width = 16;
  height = 16;
 }

 public void paintIcon(Component c, Graphics g, int x, int y) {
  this.x_pos = x;
  this.y_pos = y;
  Color col = g.getColor();
  g.setColor(Color.RED.darker());
  int y_p = y + 2;
  //以下代码用于画一个×
  g.drawLine(x + 1, y_p, x + 12, y_p);
  g.drawLine(x + 1, y_p + 13, x + 12, y_p + 13);
  g.drawLine(x, y_p + 1, x, y_p + 12);
  g.drawLine(x + 13, y_p + 1, x + 13, y_p + 12);
  g.drawLine(x + 3, y_p + 3, x + 10, y_p + 10);
  g.drawLine(x + 3, y_p + 4, x + 9, y_p + 10);
  g.drawLine(x + 4, y_p + 3, x + 10, y_p + 9);
  g.drawLine(x + 10, y_p + 3, x + 3, y_p + 10);
  g.drawLine(x + 10, y_p + 4, x + 4, y_p + 10);
  g.drawLine(x + 9, y_p + 3, x + 3, y_p + 9);
  g.setColor(col);
 }

 public int getIconWidth() {
  return width;
 }

 public int getIconHeight() {
  return height;
 }

 public Rectangle getBounds() {
  return new Rectangle(x_pos, y_pos, width, height);
 }
}