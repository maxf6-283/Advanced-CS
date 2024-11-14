import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen extends JFrame implements ActionListener, ListSelectionListener, DocumentListener {

    private MyArrayList<Song> songs;
    private CustomListModel<Song, Song> songListModel;

    private JList<Song> songList;
    private JTextField songNameField;
    private JLabel songNameLabel;
    private JTextField songArtistField;
    private JLabel songArtistLabel;
    private JTextField songAlbumField;
    private JLabel songAlbumLabel;
    private JButton addSong;
    private JButton removeSong;
    private JButton sortByName;
    private JButton sortByArtist;
    private JButton sortByAlbum;
    private JButton randomize;

    public Screen() {
        super("MyArrayList Lab");

        setLayout(null);

        setSize(725, 700);
        setResizable(false);

        songs = new MyArrayList<>();
        songs.add(new Song("Dummy!", "Toby \"Radiation\" Fox", "Undertale OST"));
        songs.add(new Song("Asgore", "Toby \"Radiation\" Fox", "Undertale OST"));
        songs.add(new Song("Attack of the Killer Queen", "Toby \"Radiation\" Fox", "DELTARUNE OST"));
        songs.add(new Song("[[BIG SHOT!!!]]", "Toby \"Radiation\" Fox", "DELTARUNE OST"));
        songs.add(new Song("A Cyber's World", "Toby \"Radiation\" Fox", "DELTARUNE OST"));
        songs.add(new Song("Checker Dance", "Toby \"Radiation\" Fox", "DELTARUNE OST"));

        songListModel = new CustomListModel<>(songs);
        songList = new JList<>(songListModel);
        songNameField = new JTextField();
        songNameLabel = new JLabel("Song name");
        songArtistField = new JTextField();
        songArtistLabel = new JLabel("Song artist");
        songAlbumField = new JTextField();
        songAlbumLabel = new JLabel("Song album");
        addSong = new JButton("Add song");
        addSong.setForeground(Color.GRAY);
        removeSong = new JButton("Remove song");
        removeSong.setForeground(Color.GRAY);
        sortByName = new JButton("Sort by name");
        sortByArtist = new JButton("Sort by artist");
        sortByAlbum = new JButton("Sort by album");
        randomize = new JButton("Shuffle");

        JComponent[] components = { songList, songNameLabel, songNameField, songArtistLabel, songArtistField,
                songAlbumLabel, songAlbumField, addSong, removeSong, sortByName, sortByArtist, sortByAlbum, randomize };

        arrangePieces(components);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void arrangePieces(JComponent[] components) {
        int xPos = 50;
        int yPos = 50;
        int compWidth = 400;
        for (JComponent component : components) {
            int compHeight = 50;
            if (component instanceof JList jList) {
                compHeight = 300;
                jList.addListSelectionListener(this);
            } else if (component instanceof JLabel) {
                compHeight = 25;
            } else if (component instanceof JButton jButton) {
                compHeight = 50;
                jButton.addActionListener(this);
            } else if (component instanceof JTextField jTextField) {
                compHeight = 50;
                jTextField.getDocument().addDocumentListener(this);
            }

            if (yPos + compHeight + 25 > getHeight()) {
                yPos = 50;
                xPos += compWidth + 25;
                compWidth = 200;
            }

            component.setBounds(xPos, yPos, compWidth, compHeight);
            add(component);
            yPos += compHeight + 10;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (songList.getSelectedValue() != null) {
            removeSong.setForeground(Color.BLACK);
            if (songList.getSelectedIndices().length > 1) {
                removeSong.setText("Remove songs");
            } else {
                removeSong.setText("Remove song");
            }
        } else {
            removeSong.setForeground(Color.GRAY);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addSong) {
            if (addSong.getForeground() == Color.BLACK) {
                Song newSong = new Song(songNameField.getText(), songArtistField.getText(), songAlbumField.getText());
                if (songList.getSelectedValue() == null) {
                    songs.add(newSong);
                } else {
                    songs.add(songList.getSelectedIndex(), newSong);
                    songList.setSelectedValue(null, false);
                }
                songListModel.update();
            } else {
                addSong.setSelected(false);
            }
        } else if (e.getSource() == removeSong) {
            if (removeSong.getForeground() == Color.BLACK) {
                for (int index : songList.getSelectedIndices()) {
                    songs.remove(index);
                }
                removeSong.setForeground(Color.GRAY);
                removeSong.setText("Remove song");
                songList.setSelectedValue(null, false);
                songListModel.update();
            } else {
                removeSong.setSelected(false);
            }
        } else if (e.getSource() == sortByName) {
            songListModel.sorter = (e1, e2) -> e1.getName().compareTo(e2.getName());
            songListModel.update();
            songListModel.sorter = (e1, e2) -> 0;
        } else if (e.getSource() == sortByArtist) {
            songListModel.sorter = (e1, e2) -> e1.getArtist().compareTo(e2.getArtist());
            songListModel.update();
            songListModel.sorter = (e1, e2) -> 0;
        } else if (e.getSource() == sortByAlbum) {
            songListModel.sorter = (e1, e2) -> e1.getAlbum().compareTo(e2.getAlbum());
            songListModel.update();
            songListModel.sorter = (e1, e2) -> 0;
        } else if (e.getSource() == randomize) {
            songListModel.sorter = (e1, e2) -> (int) (Math.random() * 2) * 3 - 2;
            songListModel.update();
            songListModel.sorter = (e1, e2) -> 0;
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (canAddSong()) {
            addSong.setForeground(Color.BLACK);
        } else {
            addSong.setForeground(Color.GRAY);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (canAddSong()) {
            addSong.setForeground(Color.BLACK);
        } else {
            addSong.setForeground(Color.GRAY);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (canAddSong()) {
            addSong.setForeground(Color.BLACK);
        } else {
            addSong.setForeground(Color.GRAY);
        }
    }

    private boolean canAddSong() {
        return songNameField.getText().matches("[^ ].{1,29}") && songArtistField.getText().matches("[^ ].{1,29}")
                && songAlbumField.getText().matches("[^ ].{1,29}");
    }
}
