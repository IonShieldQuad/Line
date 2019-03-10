import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LineDisplay extends JPanel {
    
    private boolean antialiasing;
    private Color lineColor = Color.WHITE;
    private boolean altAlpha = false;
    private int scale = 1;
    private ArrayList<Line> lines = new ArrayList<>();
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        lines.forEach(l -> drawLine(l, g));
    }
    
    private void drawLine(Line l, Graphics g) {
        Line line = new Line(getScale() * (int)Math.round(getWidth() * (l.x1 / 100.0) / getScale()), getScale() * (int)Math.round(getHeight() * (1 - (l.y1 / 100.0)) / getScale()), getScale() * (int)Math.round(getWidth() * (l.x2 / 100.0) / getScale()), getScale() * (int)Math.round(getHeight() * (1 - (l.y2 / 100.0)) / getScale()));
        
        int dx = line.x2 - line.x1;
        int dy = line.y2 - line.y1;
        boolean inv = Math.abs(dy) >= Math.abs(dx);
        
        getPoints(dx / scale, dy / scale, isAntialiasing()).forEach(p -> {
            float a = (float)Math.max(Math.min(p.a, 1.0), 0.0);
            //System.out.println("X:" + p.x + " Y:" + p.y + " A:" + a);
            if (!altAlpha) {
                g.setColor(new Color(getLineColor().getRed(), getLineColor().getGreen(), getLineColor().getBlue(), Math.round(a * 255)));
            }
            else {
                g.setColor(new Color((int) Math.floor(getLineColor().getRed() * a), (int) Math.floor(getLineColor().getGreen() * a), (int) Math.floor(getLineColor().getBlue() * a)));
            }
            if (inv) {
                g.fillRect(line.x1 + p.y * (int)Math.signum(dx) * getScale(), line.y1 + p.x * (int)Math.signum(dy) * getScale(), getScale(), getScale());
            }
            else {
                g.fillRect(line.x1 + p.x * (int)Math.signum(dx) * getScale(), line.y1 + p.y * (int)Math.signum(dy) * getScale(), getScale(), getScale());
            }
        });
    }
    
    private java.util.List<Point> getPoints(int dx, int dy, boolean aa) {
        dx = Math.abs(dx);
        dy = Math.abs(dy);
        if (dy > dx) {
            return getPoints(dy, dx, aa);
        }
        java.util.List<Point> list = new ArrayList<>(dx);
        list.add(new Point(0, 0));
        
        int x = 0;
        int y = 0;
        int d = 2*dy - dx;
        
        for (x = 1; x <= dx; x++) {
            if (d >= 0) {
                if (aa) {
                    //System.out.println("X:" + x + " Y:" + y + " D:" + d + " DY/DX:" + (double)dy/dx + " A1:" + (((double)dy/dx) * x - y) + " A2:" + ((y + 1) - ((double)dy/dx) * x));
                    list.add(new Point(x, y + 1, ((double)dy/dx) * x - y));
                    list.add(new Point(x, y, (y + 1) - ((double)dy/dx) * x));
                }
                else {
                    list.add(new Point(x, y + 1));
                }
                y++;
                d += 2*(dy - dx);
            }
            else {
                if (aa) {
                    //System.out.println("X:" + x + " Y:" + y + " D:" + d + " DY/DX:" + (double)dy/dx + " A1:" + (((double)dy/dx) * x - y) + " A2:" + ((y + 1) - ((double)dy/dx) * x));
                    list.add(new Point(x, y + 1, ((double)dy/dx) * x - y));
                    list.add(new Point(x, y, (y + 1) - ((double)dy/dx) * x));
                }
                else {
                    list.add(new Point(x, y));
                }
                d += 2*dy;
            }
            //list.add(new Point(x, y));
        }
        
        return list;
    }
    
    public Color getLineColor() {
        return lineColor;
    }
    
    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }
    
    public boolean isAntialiasing() {
        return antialiasing;
    }
    
    public void setAntialiasing(boolean antialiasing) {
        this.antialiasing = antialiasing;
    }
    
    public boolean isAltAlpha() {
        return altAlpha;
    }
    
    public void setAltAlpha(boolean altAlpha) {
        this.altAlpha = altAlpha;
    }
    
    public void addLine(Line line) {
        lines.add(line);
    }
    
    public void clearLines() {
        lines.clear();
    }
    
    public int getScale() {
        return scale;
    }
    
    public void setScale(int scale) {
        this.scale = scale;
    }
}
