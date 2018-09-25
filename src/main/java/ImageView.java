import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ImageView extends JPanel implements Observer {
    private final int index;
    private static GridLayout listLayout;
    private static GridLayout gridLayout;
    // 0 for grid and 1 for list view
    private boolean isList;
    private BufferedImage image;
    private JPanel rightPanel;
    private JLabel dateLabel;
    private JPanel starPanel;
    private JButton oneStar;
    private JButton twoStar;
    private JButton threeStar;
    private JButton fourStar;
    private JButton fiveStar;
    private JButton clearStars;
    private JFrame imageFrame;
    private JLabel imageLabel;

    public int getIndex() {
        return index;
    }

    public static void initLayouts(){
        listLayout = new GridLayout(1,2);
        gridLayout = new GridLayout(2,1);
    }


    public boolean isList() {
        return isList;
    }

    public void refreshView(boolean list) {
        isList = list;
        if(list == true){
            this.setMaximumSize(new Dimension(625,300));
            this.setLayout(listLayout);
        } else {
            this.setMaximumSize(new Dimension(300,375));
            this.setLayout(gridLayout);
        }
    }

    public void refreshStars() {
        int num = ImageCollectionModel.getInstance().getListModel().get(index).getNumOfStars();

        ImageIcon emptyStar = new ImageIcon("resources/star.png");
        ImageIcon fullStar = new ImageIcon("resources/starfull.png");
        if(num == 0){
            oneStar.setIcon(emptyStar);
            twoStar.setIcon(emptyStar);
            threeStar.setIcon(emptyStar);
            fourStar.setIcon(emptyStar);
            fiveStar.setIcon(emptyStar);
        }else if(num == 1){
            oneStar.setIcon(fullStar);
            twoStar.setIcon(emptyStar);
            threeStar.setIcon(emptyStar);
            fourStar.setIcon(emptyStar);
            fiveStar.setIcon(emptyStar);
        } else if(num == 2){
            oneStar.setIcon(fullStar);
            twoStar.setIcon(fullStar);
            threeStar.setIcon(emptyStar);
            fourStar.setIcon(emptyStar);
            fiveStar.setIcon(emptyStar);
        } else if(num == 3){
            oneStar.setIcon(fullStar);
            twoStar.setIcon(fullStar);
            threeStar.setIcon(fullStar);
            fourStar.setIcon(emptyStar);
            fiveStar.setIcon(emptyStar);
        } else if(num == 4){
            oneStar.setIcon(fullStar);
            twoStar.setIcon(fullStar);
            threeStar.setIcon(fullStar);
            fourStar.setIcon(fullStar);
            fiveStar.setIcon(emptyStar);
        } else if(num == 5){
            oneStar.setIcon(fullStar);
            twoStar.setIcon(fullStar);
            threeStar.setIcon(fullStar);
            fourStar.setIcon(fullStar);
            fiveStar.setIcon(fullStar);
        } else {
            System.out.println("ERROR: check logic in refreshstars");
        }

        Model.getInstance().notifyObservers();
    }


    public ImageView(int i, boolean isList){
        index = i;
        initLayouts();
        refreshView(isList);


        // add image to the panel
        ImageModel imgModel = ImageCollectionModel.getInstance().getListModel().get(i);
        try {
            image = ImageIO.read(new File(imgModel.getPath().toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel picLabel = new JLabel("", SwingConstants.CENTER);
        picLabel.setMaximumSize(new Dimension(300,300));
        picLabel.setSize(new Dimension(300,300));
        Image dimg = image.getScaledInstance(picLabel.getWidth(), picLabel.getHeight(),
                Image.SCALE_SMOOTH);
        add(picLabel);
        ImageIcon imageIcon = new ImageIcon(dimg);
        picLabel.setIcon(imageIcon);
        picLabel.setVisible(true);
        picLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                imageFrame.setVisible(true);
            }
        });

        // right labels and such
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.white);
        rightPanel.setMaximumSize(new Dimension(125,75));
        add(rightPanel);

        // add name of pic to rightpanel
        String name = imgModel.getPath().toFile().getName();
        JLabel imgName = new JLabel(name, SwingConstants.CENTER);
        imgName.setBackground(Color.white);
        imgName.setMaximumSize(new Dimension(125,25));
        imgName.setAlignmentX(Component.CENTER_ALIGNMENT);
        imgName.setAlignmentY(Component.CENTER_ALIGNMENT);
        rightPanel.add(imgName);


        imageFrame = new JFrame(name);
        imageFrame.setMinimumSize(new Dimension(400, 300));
        imageFrame.setMaximumSize(new Dimension(800, 600));
        imageFrame.setSize(400, 300);
        imageLabel = new JLabel(new ImageIcon(image));
        imageFrame.add(imageLabel);

        // add date of pic to rightpanel
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String dateCreated = df.format(imgModel.getDate().toMillis());
        dateLabel = new JLabel(dateCreated, SwingConstants.CENTER);
        dateLabel.setBackground(Color.white);
        dateLabel.setMaximumSize(new Dimension(125,25));
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dateLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        rightPanel.add(dateLabel);

        // init and configure the clearing button
        clearStars = new JButton("Clear Rating");
        clearStars.setMaximumSize(new Dimension(125,25));
        clearStars.setAlignmentX(Component.CENTER_ALIGNMENT);
        clearStars.setAlignmentY(Component.CENTER_ALIGNMENT);
        clearStars.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageCollectionModel.getInstance().getListModel().get(index).setNumOfStars(0);
                ImageCollectionView.getInstance().refreshFilter();
                refreshStars();
            }
        });
        rightPanel.add(clearStars);

        this.setBackground(Color.white);
//        System.out.println(ImageCollectionModel.getInstance().getListModel().get(i).getPath().toString());

        // add stars to rightpanel


        oneStar = new JButton();
        twoStar = new JButton();
        threeStar = new JButton();
        fourStar = new JButton();
        fiveStar = new JButton();

        starPanel  = new JPanel();
        starPanel.setPreferredSize(new Dimension(130,25));
        starPanel.setBackground(Color.white);
        starPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        starPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        rightPanel.add(starPanel);

        starPanel.add(oneStar);
        starPanel.add(twoStar);
        starPanel.add(threeStar);
        starPanel.add(fourStar);
        starPanel.add(fiveStar);

        ImageIcon star = new ImageIcon("resources/star.png");
        oneStar.setIcon(star);
        oneStar.setPreferredSize(new Dimension(25,25));
        oneStar.setBackground(Color.white);
        twoStar.setIcon(star);
        twoStar.setPreferredSize(new Dimension(25,25));
        twoStar.setBackground(Color.white);
        threeStar.setIcon(star);
        threeStar.setPreferredSize(new Dimension(25,25));
        threeStar.setBackground(Color.white);
        fourStar.setIcon(star);
        fourStar.setPreferredSize(new Dimension(25,25));
        fourStar.setBackground(Color.white);
        fiveStar.setIcon(star);
        fiveStar.setPreferredSize(new Dimension(25,25));
        fiveStar.setBackground(Color.white);
        oneStar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageCollectionModel.getInstance().getListModel().get(index).setNumOfStars(1);
                refreshStars();
            }
        });
        twoStar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageCollectionModel.getInstance().getListModel().get(index).setNumOfStars(2);
                refreshStars();
            }
        });
        threeStar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageCollectionModel.getInstance().getListModel().get(index).setNumOfStars(3);
                refreshStars();
            }
        });
        fourStar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageCollectionModel.getInstance().getListModel().get(index).setNumOfStars(4);
                refreshStars();
            }
        });
        fiveStar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageCollectionModel.getInstance().getListModel().get(index).setNumOfStars(5);
                refreshStars();
            }
        });

    }


    public void update(Object observable) {
//        System.out.println("repainting imageview");
        repaint();
        validate();
    }
}
