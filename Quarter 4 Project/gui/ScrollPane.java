package gui;

import javax.swing.JScrollPane;
import javax.swing.JComponent;
import javax.swing.ScrollPaneConstants;

public class ScrollPane extends JScrollPane {
    public ScrollPane(JComponent j) {
        super(j);
        setOpaque(false);
        getViewport().setOpaque(false);
        setBorder(null);
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        getHorizontalScrollBar().setOpaque(false);
        getVerticalScrollBar().setOpaque(false);
    }
}
