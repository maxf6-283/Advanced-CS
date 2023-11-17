import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.Scanner;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Screen extends JFrame implements ActionListener {
    private MyHashTable<Country, MyImage> countryTable;
    private JList<String> countryList;
    private JScrollPane scrollPane;
    private CustomListModel<Entry<Country, DLList<MyImage>>, String> countryListModel;
    private JButton addImage;
    private JButton deleteImage;
    private AddImagePrompt addImagePrompt;
    private ImageViewer imageViewer;

    public Screen() {
        setLayout(null);
        setSize(1200, 800);

        countryTable = new MyHashTable<>();
        scanCountries();
        countryTable.put(new Country("ca", ""), new MyImage(
                "https://travel2next.com/wp-content/uploads/canadian-landmarks-1024x684.jpg",
                "Niagra Falls"));
        countryTable.put(new Country("ca", ""), new MyImage(
                "https://travel2next.com/wp-content/uploads/canadian-landmarks-bay-of-fundy-1024x777.jpg",
                "Hopwell Rocks"));
        countryTable.put(new Country("ca", ""), new MyImage(
                "https://travel2next.com/wp-content/uploads/canadian-landmarks-cn-tower-1024x767.jpg",
                "CN Tower"));

        countryTable.put(new Country("us", ""), new MyImage(
                "https://travel2next.com/wp-content/uploads/usa-natural-landmarks-1024x683.jpg",
                "Sierra Nevada Mountains"));
        countryTable.put(new Country("us", ""), new MyImage(
                "https://travel2next.com/wp-content/uploads/usa-landmarks-devils-tower-wyoming-1024x683.jpg",
                "Devil's Tower"));
        countryTable.put(new Country("us", ""), new MyImage(
                "https://travel2next.com/wp-content/uploads/famous-landmarks-in-USA-1024x680.jpg",
                "The Statue of Liberty"));

        countryTable.put(new Country("fr", ""), new MyImage(
                "https://travel2next.com/wp-content/uploads/religious-france-landmark-notre-dame.jpg",
                "Notre Dame de Paris"));
        countryTable.put(new Country("fr", ""), new MyImage(
                "https://travel2next.com/wp-content/uploads/landmark-in-paris-sacre-coeur.jpg",
                "Basilica de Sacré-Coeur"));
        countryTable.put(new Country("fr", ""), new MyImage(
                "https://travel2next.com/wp-content/uploads/major-landmarks-in-france-pont-neuf.jpg",
                "Pont Neuf"));

        countryListModel = new CustomListModel<>(countryTable.entrySet(), this::getEntryString);
        countryListModel.filters.add(e -> !e.getValue().isEmpty());
        countryListModel.update();
        countryList = new JList<>(countryListModel);
        scrollPane = new JScrollPane(countryList);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(50, 50, 300, 600);
        add(scrollPane);

        addImage = new JButton("Add image");
        addImage.setBounds(400, 50, 200, 50);
        add(addImage);
        addImage.addActionListener(this);

        deleteImage = new JButton("Delete image");
        deleteImage.setBounds(400, 150, 200, 50);
        add(deleteImage);
        deleteImage.addActionListener(this);
        deleteImage.setEnabled(false);

        addImagePrompt = new AddImagePrompt();

        imageViewer = new ImageViewer(650, 50, 400, 500);
        add(imageViewer);
        countryList.addListSelectionListener(imageViewer);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private String getEntryString(Entry<Country, DLList<MyImage>> entry) {
        if (entry == null) {
            return "";
        }
        return entry.getKey() + " - " + entry.getValue().size();
    }

    private void scanCountries() {
        try {
            Scanner countryScanner = new Scanner(new File("countries.txt"));
            while (countryScanner.hasNext()) {
                String[] scans = countryScanner.nextLine().split(",");
                countryTable.put(new Country(scans[0], scans[1]));
            }
            countryScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addImage) {
            addImagePrompt.setVisible(true);
        } else if (e.getSource() == deleteImage) {
            DLList<MyImage> images = countryListModel.getBackingElementAt(countryList.getSelectedIndex()).getValue();
            images.remove(imageViewer.index);
            if(imageViewer.index == images.size()) {
                imageViewer.index--;
            }
            if(images.isEmpty()) {
                countryList.setSelectedValue(null, false);
            }
            imageViewer.repaint();
            countryListModel.update();
        } else if (e.getSource() == addImagePrompt.addImageConfirm) {
            addImagePrompt.setVisible(false);
            countryTable.get(new Country(addImagePrompt.abbreviation.getText().toLowerCase(), ""))
                    .add(new MyImage(addImagePrompt.imageURL.getText(), addImagePrompt.landmarkName.getText()));
            countryListModel.update();
            imageViewer.repaint();
        }
    }

    private class AddImagePrompt extends JFrame implements DocumentListener {
        private PlaceholderTextField abbreviation;
        private PlaceholderTextField landmarkName;
        private PlaceholderTextField imageURL;
        private JButton addImageConfirm;

        public AddImagePrompt() {
            setLayout(null);
            abbreviation = new PlaceholderTextField("Country Abbreviation");
            abbreviation.setBounds(50, 50, 200, 50);
            add(abbreviation);
            abbreviation.getDocument().addDocumentListener(this);

            landmarkName = new PlaceholderTextField("Landmark Name");
            landmarkName.setBounds(50, 150, 200, 50);
            add(landmarkName);
            landmarkName.getDocument().addDocumentListener(this);

            imageURL = new PlaceholderTextField("Image URL");
            imageURL.setBounds(50, 250, 200, 50);
            add(imageURL);
            imageURL.getDocument().addDocumentListener(this);

            addImageConfirm = new JButton("Add Image");
            addImageConfirm.setBounds(50, 350, 200, 50);
            add(addImageConfirm);
            addImageConfirm.addActionListener(Screen.this);
            addImageConfirm.setEnabled(false);

            setSize(300, 500);
            setResizable(false);
            setAlwaysOnTop(true);
            setDefaultCloseOperation(HIDE_ON_CLOSE);
            setVisible(false);
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            checkValid();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            checkValid();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            checkValid();
        }

        private void checkValid() {
            addImageConfirm
                    .setEnabled((countryTable.keySet().contains(new Country(abbreviation.getText().toLowerCase(), "")))
                            && isValidURL(imageURL.getText()) && landmarkName.getText().length() > 0);
        }

        private boolean isValidURL(String url) {
            try {
                new URI(url).toURL();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    private class ImageViewer extends JPanel implements ActionListener, ListSelectionListener {
        private JButton leftImage;
        private JButton rightImage;
        private JLabel imageNumber;

        private int index = 0;

        public ImageViewer(int x, int y, int w, int h) {
            setLayout(null);
            setBounds(x, y, w, h);

            leftImage = new JButton("<<");
            leftImage.setBounds(0, getHeight() - 50, 50, 50);
            add(leftImage);
            leftImage.addActionListener(this);
            leftImage.setEnabled(false);

            rightImage = new JButton(">>");
            rightImage.setBounds(getWidth() - 50, getHeight() - 50, 50, 50);
            add(rightImage);
            rightImage.addActionListener(this);
            rightImage.setEnabled(false);

            imageNumber = new JLabel("");
            imageNumber.setBounds(75, getHeight() - 50, getWidth() - 150, 50);
            add(imageNumber);
        }

        private void setImageNumberText(String text) {
            imageNumber.setText(
                    "<html><div style='text-align:center' width=" + imageNumber.getWidth() + ">" + text
                            + "</div></html>");
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (countryList.getSelectedValue() != null) {
                MyImage image = countryListModel.getBackingElementAt(countryList.getSelectedIndex()).getValue()
                        .get(index);
                g.drawImage(image.image(), 0, 0, getWidth(), getHeight() - 50, null);
                int size = countryListModel.getBackingElementAt(countryList.getSelectedIndex()).getValue().size();
                setImageNumberText(image.name() + " - " + (index + 1) + "/" + size);
                leftImage.setEnabled(index > 0);
                rightImage.setEnabled(size > index + 1);
            } else {
                setImageNumberText("No Selected Country");
                leftImage.setEnabled(false);
                rightImage.setEnabled(false);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == leftImage) {
                index--;
            } else if (e.getSource() == rightImage) {
                index++;
            }
            repaint();
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            index = 0;
            deleteImage.setEnabled(countryList.getSelectedValue() != null);
            repaint();
        }
    }
}
