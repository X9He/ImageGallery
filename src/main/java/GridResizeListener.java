import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GridResizeListener extends ComponentAdapter {
    public void componentResized(ComponentEvent e) {
        double width = e.getComponent().getWidth();
//        System.out.println("calling gridresize listener, width is " + width);
        ImageCollectionView.getInstance().refreshSize(width);
    }
}
