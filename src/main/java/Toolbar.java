import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar extends JPanel implements Observer {
    private JButton btActivateGrid;
    private JButton btActivateList;
    private JButton loadNewImage;
    private JButton clearFilter;
    private JLabel programName;
    private JLabel ratingsLabel;
    private JButton oneStar;
    private JButton twoStar;
    private JButton threeStar;
    private JButton fourStar;
    private JButton fiveStar;
    private JFrame parent;
    private int limit;

    public Toolbar(JFrame p){
        parent = p;
        limit = 0;
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setPreferredSize(new Dimension(1280, 25));
        this.setMinimumSize(new Dimension(320,25));

        btActivateGrid = new JButton();
        btActivateList = new JButton();
        loadNewImage = new JButton();
        clearFilter = new JButton("Clear Filter");
        ratingsLabel = new JLabel("Filter by:");
        oneStar = new JButton();
        twoStar = new JButton();
        threeStar = new JButton();
        fourStar = new JButton();
        fiveStar = new JButton();
        programName = new JLabel("FOTAG!   ");


        this.add(btActivateGrid);
        this.add(btActivateList);
        this.add(programName);
        this.add(loadNewImage);
        this.add(clearFilter);
        this.add(ratingsLabel);
        this.add(oneStar);
        this.add(twoStar);
        this.add(threeStar);
        this.add(fourStar);
        this.add(fiveStar);

        this.setBackground(Color.white);

        ImageIcon img = new ImageIcon("resources/square-grid.png");
        btActivateGrid.setIcon(img);
        btActivateGrid.setBackground(Color.white);
        btActivateGrid.setPreferredSize(new Dimension(50,25));
        ImageIcon img2 = new ImageIcon("resources/view-list.png");
        btActivateList.setIcon(img2);
        btActivateList.setPreferredSize(new Dimension(50,25));
        btActivateList.setBackground(Color.white);
        ImageIcon img3 = new ImageIcon("resources/add-new-document.png");
        loadNewImage.setIcon(img3);
        loadNewImage.setPreferredSize(new Dimension(50,25));
        loadNewImage.setBackground(Color.white);
        ImageIcon star = new ImageIcon("resources/star.png");
        oneStar.setIcon(star);
        oneStar.setPreferredSize(new Dimension(50,25));
        oneStar.setBackground(Color.white);
        twoStar.setIcon(star);
        twoStar.setPreferredSize(new Dimension(50,25));
        twoStar.setBackground(Color.white);
        threeStar.setIcon(star);
        threeStar.setPreferredSize(new Dimension(50,25));
        threeStar.setBackground(Color.white);
        fourStar.setIcon(star);
        fourStar.setPreferredSize(new Dimension(50,25));
        fourStar.setBackground(Color.white);
        fiveStar.setIcon(star);
        fiveStar.setPreferredSize(new Dimension(50,25));
        fiveStar.setBackground(Color.white);

        loadNewImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jChooser = new JFileChooser();
                int rVal = jChooser.showOpenDialog(parent);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    try{
                        ImageCollectionModel.getInstance().addNewImage(jChooser.getSelectedFile().getPath());
                        Model.getInstance().notifyObservers();
//                        System.out.println("save file path" + jChooser.getSelectedFile().getPath());
                    } catch (Exception i) {
                        i.printStackTrace();
                    }
                } else if (rVal == JFileChooser.CANCEL_OPTION) {
                }
            }
        });

        btActivateGrid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageCollectionView.getInstance().changeLayout(false);
                ImageCollectionView.getInstance().refreshSize(parent.getWidth());
            }
        });

        btActivateList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageCollectionView.getInstance().changeLayout(true);
            }
        });

        oneStar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tRefreshStars(1);
                ImageCollectionView.getInstance().refreshFilter(1);
            }
        });
        twoStar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tRefreshStars(2);
                ImageCollectionView.getInstance().refreshFilter(2);
            }
        });
        threeStar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tRefreshStars(3);
                ImageCollectionView.getInstance().refreshFilter(3);
            }
        });
        fourStar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tRefreshStars(4);
                ImageCollectionView.getInstance().refreshFilter(4);
            }
        });
        fiveStar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tRefreshStars(5);
                ImageCollectionView.getInstance().refreshFilter(5);
            }
        });
        clearFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tRefreshStars(0);
                ImageCollectionView.getInstance().refreshFilter(0);
            }
        });
    }

    public void tRefreshStars(int lim){
        ImageIcon emptyStar = new ImageIcon("resources/star.png");
        ImageIcon fullStar = new ImageIcon("resources/starfull.png");

        if(lim == 0){
            oneStar.setIcon(emptyStar);
            twoStar.setIcon(emptyStar);
            threeStar.setIcon(emptyStar);
            fourStar.setIcon(emptyStar);
            fiveStar.setIcon(emptyStar);
        }else if(lim == 1){
            oneStar.setIcon(fullStar);
            twoStar.setIcon(emptyStar);
            threeStar.setIcon(emptyStar);
            fourStar.setIcon(emptyStar);
            fiveStar.setIcon(emptyStar);
        } else if(lim == 2){
            oneStar.setIcon(fullStar);
            twoStar.setIcon(fullStar);
            threeStar.setIcon(emptyStar);
            fourStar.setIcon(emptyStar);
            fiveStar.setIcon(emptyStar);
        } else if(lim == 3){
            oneStar.setIcon(fullStar);
            twoStar.setIcon(fullStar);
            threeStar.setIcon(fullStar);
            fourStar.setIcon(emptyStar);
            fiveStar.setIcon(emptyStar);
        } else if(lim == 4){
            oneStar.setIcon(fullStar);
            twoStar.setIcon(fullStar);
            threeStar.setIcon(fullStar);
            fourStar.setIcon(fullStar);
            fiveStar.setIcon(emptyStar);
        } else if(lim == 5){
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

    public void update(Object observable){
        oneStar.repaint();
        twoStar.repaint();
        threeStar.repaint();
        fourStar.repaint();
        fiveStar.repaint();
    }

}
