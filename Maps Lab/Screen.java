import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Screen extends JFrame implements ListSelectionListener, ActionListener {

    private JList<String> listOfStates;
    private CustomListModel<Entry<State, StateInfo>, String> stateMapModel;
    private MyHashMap<State, StateInfo> stateMap;
    private ImageShower thingy;
    private int selectedIndex = -1;
    private JButton addImageURL;
    private JTextField url;
    private JButton addState;
    private JButton removeState;
    private AddStateMenu addStateMenu = new AddStateMenu();

    public Screen() {
        super("States");
        setLayout(null);

        stateMap = new MyHashMap<>();
        stateMap.put(new State("California", "CA"), new StateInfo("Sacramento", 38_940_231, 163_696));
        stateMap.get(new State("California", "CA")).addUrl(
                "https://www.thoughtco.com/thmb/tfjtL8rhGxHCuCkwNFRbUOczKio=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/GettyImages-869041446-5c8b237746e0fb000146acda.jpg");
        stateMap.get(new State("California", "CA"))
                .addUrl("https://www.state.gov/wp-content/uploads/2022/01/shutterstock_1029712684-scaled.jpg");

        stateMap.put(new State("Nevada", "NV"), new StateInfo("Carson City", 3_104_614, 110_577));
        stateMap.get(new State("Nevada", "NV")).addUrl(
                "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/15/33/fb/01/nevada.jpg?w=700&h=500&s=1");
        stateMap.get(new State("Nevada", "NV")).addUrl(
                "https://www.tripsavvy.com/thmb/LlPLKNY5ALPvTKWcteLqgUf8J_M=/4320x2868/filters:no_upscale():max_bytes(150000):strip_icc()/sand-harbor-state-park--lake-tahoe--nevada-453331363-1840f205e0584fb9867559c2f2179e12.jpg");

        stateMap.put(new State("Oregon", "OR"), new StateInfo("Salem", 4_246_155, 98_381));
        stateMap.get(new State("Oregon", "OR")).addUrl(
                "https://afar.brightspotcdn.com/dims4/default/97d0b99/2147483647/strip/true/crop/1500x796+0+93/resize/1440x764!/quality/90/?url=https%3A%2F%2Fafar-media-production-web.s3.us-west-2.amazonaws.com%2Fbrightspot%2Fba%2Fa2%2F521406ca517b726472dd1527a438%2Foriginal-shutterstock-1286239129.jpg");
        stateMap.get(new State("Oregon", "OR"))
                .addUrl("https://oregonwild.org/sites/default/files/MtJeffersonJeremyCram.jpg");

        stateMapModel = new CustomListModel<>(stateMap.entrySet(), this::getString);

        listOfStates = new JList<>(stateMapModel);
        listOfStates.setFixedCellHeight(100);
        listOfStates.setBounds(50, 50, 400, 600);
        add(listOfStates);
        listOfStates.addListSelectionListener(this);

        thingy = new ImageShower();
        thingy.setBounds(500, 50, 250, 600);
        add(thingy);

        addImageURL = new JButton("Add new image URL");
        addImageURL.setBounds(800, 50, 300, 50);
        add(addImageURL);
        addImageURL.addActionListener(this);
        addImageURL.setEnabled(false);

        url = new JTextField();
        url.setBounds(800, 150, 300, 50);
        add(url);

        removeState = new JButton("Remove selected state");
        removeState.setBounds(800, 250, 300, 50);
        add(removeState);
        removeState.addActionListener(this);
        removeState.setEnabled(false);

        addState = new JButton("Add/replace state");
        addState.setBounds(800, 350, 300, 50);
        add(addState);
        addState.addActionListener(this);

        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private String getString(Entry<State, StateInfo> entry) {
        if (selectedIndex < 0) {
            if (entry == null) {
                return "null";
            }
            return entry.getKey().toString().replaceAll("\n", "<br>");
        }
        if (entry == stateMapModel.getBackingElementAt(selectedIndex)) {
            String toRet = "<html>" + (entry.getKey() + "<br>" + entry.getValue()).replaceAll("\n", "<br>");
            return toRet + "</html>";
        }
        return entry.getKey().toString();

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        selectedIndex = listOfStates.getSelectedIndex();
        if (selectedIndex >= 0) {
            thingy.images = stateMapModel.getBackingElementAt(selectedIndex).getValue().URLList();
            removeState.setEnabled(true);
            addImageURL.setEnabled(true);
        }
        thingy.repaint();
    }

    private class ImageShower extends JPanel {
        public List<String> images;

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (images == null) {
                return;
            }
            int y = 0;
            for (String url : images) {
                g.drawImage(Images.getImage(url), 0, y, getWidth(), getHeight() / images.size(), null);
                y += getHeight() / images.size();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addImageURL) {
            stateMapModel.getBackingElementAt(selectedIndex).getValue().URLList().add(url.getText());
            thingy.repaint();
        } else if (e.getSource() == removeState) {
            stateMap.remove(stateMapModel.getBackingElementAt(selectedIndex).getKey());
            selectedIndex = -1;
            listOfStates.setSelectedValue(null, false);
            removeState.setEnabled(false);
            addImageURL.setEnabled(false);
            stateMapModel.update();
            thingy.images = null;
            thingy.repaint();
        } else if (e.getSource() == addState) {
            addStateMenu.ask();
        } else if (e.getSource() == addStateMenu.add) {
            addStateMenu.addState();
        }
    }

    private class AddStateMenu extends JFrame {
        private JButton add;
        private JTextField name;
        private JLabel nameLabel;
        private JTextField abbr;
        private JLabel abbrLabel;
        private JTextField capital;
        private JLabel capitalLabel;
        private JTextField population;
        private JLabel populationLabel;
        private JTextField size;
        private JLabel sizeLabel;
        private JComponent[] components;

        public AddStateMenu() {
            super("Add/replace state");

            setLayout(null);

            nameLabel = new JLabel("Name:");
            name = new JTextField();
            abbrLabel = new JLabel("Abbreviation:");
            abbr = new JTextField();
            capitalLabel = new JLabel("Capital:");
            capital = new JTextField();
            populationLabel = new JLabel("Population:");
            population = new JTextField();
            sizeLabel = new JLabel("Size:");
            size = new JTextField();
            add = new JButton("Add/replace");

            components = new JComponent[] { nameLabel, name, abbrLabel, abbr, capitalLabel, capital, populationLabel,
                    population, sizeLabel, size, add };

            int y = 50;
            for (JComponent c : components) {
                c.setBounds(50, y, 200, 50);
                add(c);
                y += 50;
            }

            add.addActionListener(Screen.this);

            setSize(300, 50 * components.length + 100);
            setAlwaysOnTop(true);
        }

        public void addState() {
            stateMap.put(new State(name.getText(), abbr.getText()), new StateInfo(capital.getText(),
                    Integer.parseInt(population.getText()), Integer.parseInt(size.getText())));
            stateMapModel.update();
            setVisible(false);
        }

        public void ask() {
            for (JComponent c : components) {
                if (c instanceof JTextField t) {
                    t.setText("");
                }
            }
            setVisible(true);
        }

    }
}
