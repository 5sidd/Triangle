import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TriangleDrawing extends Canvas implements MouseListener {
    private int ax;
    private int ay;
    private int bx;
    private int by;
    private int cx;
    private int cy;
    private int px;
    private int py;
    private int turn;
    private int leftMostX;
    private int rightMostX;
    private int topMostY;
    private int bottomMostY;
    private String result;

    public TriangleDrawing() {
        addMouseListener(this);
        this.ax = -1;
        this.ay = -1;
        this.bx = -1;
        this.by = -1;
        this.cx = -1;
        this.cy = -1;
        this.px = -1;
        this.py = -1;
        this.turn = 0;
        this.leftMostX = -1;
        this.rightMostX = -1;
        this.topMostY = -1;
        this.bottomMostY = -1;
        this.result = null;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.drawQuitBox(g);
        this.drawTriangle(g);
        this.drawPoints(g);
        this.drawResult(g);
    }

    private void drawQuitBox(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(5, 5, 100, 30);
        g.drawString("QUIT", 40, 25);
    }

    private void drawPoints(Graphics g) {
        if (this.turn >= 1) {
            g.setColor(Color.green);
            g.fillOval(this.ax - 4, this.ay - 4, 8, 8);
        }
        if (this.turn >= 2) {
            g.setColor(Color.blue);
            g.fillOval(this.bx - 4, this.by - 4, 8, 8);
        }
        if (this.turn >= 3) {
            g.setColor(Color.orange);
            g.fillOval(this.cx - 4, this.cy - 4, 8, 8);
        }
        if (this.turn >= 4) {
            g.setColor(Color.red);
            g.fillOval(this.px - 4, this.py - 4, 8, 8);
        }
        g.setColor(Color.black);
    }

    private void drawTriangle(Graphics g) {
        if (this.turn >= 3) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(5));
            g2d.setColor(Color.black);
            int[] xCoordinates = {this.ax, this.bx, this.cx};
            int[] yCoordinates = {this.ay, this.by, this.cy};
            g2d.drawPolygon(xCoordinates, yCoordinates, 3);
            g2d.setStroke(new BasicStroke(1));
        }
    }

    private void drawResult(Graphics g) {
        if (this.result != null) {
            g.setColor(Color.black);
            Font font = new Font("Serif", Font.BOLD, 25);
            g.setFont(font);
            g.drawString(this.result, 125, 30);
        }
    }

    private int calculateArea2D(int ax, int ay, int bx, int by, int cx, int cy) {
        return (ax - cx) * (by - cy) - (ay - cy) * (bx - cx);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        int leftX = 5;
        int rightX = 105;
        int topY = 5;
        int bottomY = 35;

        if (mouseX >= leftX && mouseX <= rightX && mouseY >= topY && mouseY <= bottomY) {
            System.exit(0);
        }

        if (this.turn == 0) {
            this.ax = mouseX;
            this.ay = mouseY;
            this.turn = 1;
            this.leftMostX = mouseX;
            this.rightMostX = mouseX;
            this.topMostY = mouseY;
            this.bottomMostY = mouseY;
        }
        else if (this.turn == 1) {
            this.bx = mouseX;
            this.by = mouseY;
            this.turn = 2;
            this.leftMostX = Math.min(this.leftMostX, mouseX);
            this.rightMostX = Math.max(this.rightMostX, mouseX);
            this.topMostY = Math.min(this.topMostY, mouseY);
            this.bottomMostY = Math.max(this.bottomMostY, mouseY);
        }
        else if (this.turn == 2) {
            this.cx = mouseX;
            this.cy = mouseY;
            this.turn = 3;
            this.leftMostX = Math.min(this.leftMostX, mouseX);
            this.rightMostX = Math.max(this.rightMostX, mouseX);
            this.topMostY = Math.min(this.topMostY, mouseY);
            this.bottomMostY = Math.max(this.bottomMostY, mouseY);
        }
        else if (this.turn >= 3) {
            this.px = mouseX;
            this.py = mouseY;
            this.turn += 1;
        }

        System.out.println("A: " + ax + ", " + ay);
        System.out.println("B: " + bx + ", " + by);
        System.out.println("C: " + cx + ", " + cy);
        System.out.println("P: " + px + ", " + py);

        if (this.turn >= 4) {
            int abp = this.calculateArea2D(this.ax, this.ay, this.bx, this.by, this.px, this.py);
            int bcp = this.calculateArea2D(this.bx, this.by, this.cx, this.cy, this.px, this.py);
            int cap = this.calculateArea2D(this.cx, this.cy, this.ax, this.ay, this.px, this.py);
            System.out.println(abp);
            System.out.println(bcp);
            System.out.println(cap);

            boolean onEdge = false;
            if (mouseX >= this.leftMostX - 5 && mouseX <= this.rightMostX + 5 && mouseY >= this.topMostY - 5 && mouseY <= this.bottomMostY + 5) {
                if (Math.abs(abp) < 1000 || Math.abs(bcp) < 1000 || Math.abs(cap) < 1000) {
                    onEdge = true;
                }
            }

            if (onEdge) {
                this.result = "On Edge";
            }
            else {
                if (abp < 0 && bcp < 0 && cap < 0) {
                    this.result = "Inside";
                }
                else {
                    this.result = "Outside";
                }
            }
            //System.out.println("");
        }
        System.out.println("");
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
