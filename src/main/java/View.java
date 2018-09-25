
import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class View extends JFrame implements Observer {

    private Model model;

    /**
     * Create a new View.
     */
    public View(Model model) {
        // Set up the window.
        this.setTitle("Fotag!");
        this.setMinimumSize(new Dimension(600, 300));
        this.setMaximumSize(new Dimension(1280, 720));
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    FileOutputStream fileOut =
                            new FileOutputStream("something");
                    FileOutputStream fileRatings =
                            new FileOutputStream("sRatings");
                    ArrayList<String> arrPath = new ArrayList<>();
                    for(int i = 0; i < ImageCollectionModel.getInstance().getListModel().size(); ++i){
                        arrPath.add(ImageCollectionModel.getInstance().getListModel().get(i).getPath().toString());
                    }
                    ArrayList<Integer> arrInt = new ArrayList<>();
                    for(int i = 0; i < ImageCollectionModel.getInstance().getListModel().size(); ++i){
                        arrInt.add(ImageCollectionModel.getInstance().getListModel().get(i).getNumOfStars());
                    }
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    ObjectOutputStream outRatings = new ObjectOutputStream(fileRatings);
                    out.writeObject(arrPath);
                    outRatings.writeObject(arrInt);
                    out.close();
                    outRatings.close();
                    fileOut.close();
                    fileRatings.close();
                } catch (IOException i) {
                    i.printStackTrace();
                }
                System.exit(0);
            }
        });
        this.model = model;
        this.addComponentListener(new GridResizeListener());
        model.addObserver(this);

        // init the two singletons
        ImageCollectionModel.getInstance();
        ImageCollectionView.getInstance();
        model.addObserver(ImageCollectionView.getInstance());

        // initiate root panel and attach it to this frame
        JPanel rootPanel = new JPanel(new BorderLayout());
        Container content = this.getContentPane();
        content.add(rootPanel);

        // initialize the scrollpane
        JScrollPane scrollPane = new JScrollPane(ImageCollectionView.getInstance());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(50, 30, 300, 50);
        rootPanel.add(scrollPane, BorderLayout.CENTER);

        // initialize the toolbar
        Toolbar tb = new Toolbar(this);
        rootPanel.add(tb, BorderLayout.PAGE_START);
        model.addObserver(tb);

        setVisible(true);

        try{
            FileInputStream fin = new FileInputStream("something");
//            System.out.println("save file path" + jChooser.getSelectedFile().getPath());
            ObjectInputStream ois = new ObjectInputStream(fin);
            ArrayList<String> theArr = (ArrayList<String>) ois.readObject();
            for(String p: theArr){
                try{
                    ImageCollectionModel.getInstance().addNewImage(p);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ois.close();
            fin.close();

            Model.getInstance().notifyObservers();

            fin = new FileInputStream("sRatings");
            ois = new ObjectInputStream(fin);
            ArrayList<Integer> ratingsArr = (ArrayList<Integer>) ois.readObject();
            int i = 0;
            for(Integer p: ratingsArr){
                try{
                    ImageCollectionModel.getInstance().getListModel().get(i).setNumOfStars(p);
                    ImageCollectionView.getInstance().getAloImageView().get(i).refreshStars();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ++i;
            }
            ois.close();
            fin.close();


        } catch(Exception e){
//            e.printStackTrace();
            System.out.println("failed to load");
        }
    }

    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        repaint();
//        System.out.println("view is updating");
    }
}
