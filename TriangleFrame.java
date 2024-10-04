import java.awt.*;

public class TriangleFrame extends Frame {
    public TriangleFrame() {
        super("TriangleFrame");
        setSize(550, 550);
        add(new TriangleDrawing(), null);
        setResizable(false);
        setVisible(true);
    }

}