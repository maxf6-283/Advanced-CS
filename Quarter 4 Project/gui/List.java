package gui;

import javax.swing.JList;

import utils.ArrayList;
import utils.CustomListModel;
import java.util.Collection;
import javax.swing.ListCellRenderer;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Graphics;
import java.awt.Dimension;
import java.util.function.Function;

public class List<E> extends JList<String> {
    private CustomListModel<E, String> listModel;
    private Function<E, String> toS;

    public List(Collection<E> c, Function<E, String> toStr) {
        toS = toStr;
        listModel = new CustomListModel<>(c, toStr);
        setModel(listModel);
        setOpaque(false);
        setFont(Button.font.deriveFont(24f));
        setCellRenderer(new Renderer<>());
    }

    public List(Collection<E> c) {
        this(c, e -> e.toString());
    }

    public void update() {
        listModel.update();
    }

    public void setList(ArrayList<E> players) {
        listModel = new CustomListModel<>(players, toS);
        setModel(listModel);
    }

    private static class Renderer<T> extends JLabel implements ListCellRenderer<T> {
        private boolean selected = false;

        public Renderer() {
            setOpaque(false);
            setHorizontalAlignment(SwingConstants.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends T> list, T value, int index, boolean isSelected,
                boolean cellHasFocus) {
            setFont(list.getFont());
            setText(value.toString());
            selected = isSelected;
            return this;
        }

        @Override
        public void paintComponent(Graphics g) {
            if (selected) {
                g.setColor(Color.WHITE);
                setForeground(Color.BLACK);
            } else {
                g.setColor(new Color(0f, 0f, 0f, 0.5f));
                setForeground(Color.WHITE);
            }
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(super.getPreferredSize().width, super.getPreferredSize().height + 20);
        }
    }

    public E getSelected() {
        return listModel.getBackingElementAt(getSelectedIndex());
    }
}
