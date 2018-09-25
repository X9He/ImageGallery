import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ImageCollectionView extends JPanel implements Observer {
    private ImageModel image;
    private BoxLayout boxLayout;
    private GridLayout gridLayout;
    private ArrayList<ImageView> aloImageView;
    private static ImageCollectionView ICVInstance;
    private int limit;
    private boolean isList;

    public ArrayList<ImageView> getAloImageView() {
        return aloImageView;
    }
    public boolean isList() {
        return isList;
    }

    public void setList(boolean list) {
        isList = list;
    }

    public void changeLayout(boolean list){
        isList = list;
        refreshLayout();
        for(int i = 0; i < aloImageView.size(); ++i){
            aloImageView.get(i).refreshView(list);
        }
        Model.getInstance().notifyObservers();
    }

    public void refreshFilter(int lim){
        limit = lim;
        ICVInstance.removeAll();
        ICVInstance.revalidate();
        ICVInstance.repaint();
        for(int i = 0; i < aloImageView.size() ; ++i){
            if(ImageCollectionModel.getInstance().getListModel().get(aloImageView.get(i).getIndex()).getNumOfStars() >= lim){
                add(aloImageView.get(i));
            }
        }
        Model.getInstance().notifyObservers();
    }

    public void refreshFilter(){
        ICVInstance.removeAll();
        ICVInstance.revalidate();
        ICVInstance.repaint();
        for(int i = 0; i < aloImageView.size() ; ++i){
            if(ImageCollectionModel.getInstance().getListModel().get(aloImageView.get(i).getIndex()).getNumOfStars() >= limit){
                add(aloImageView.get(i));
            }
        }
        Model.getInstance().notifyObservers();
    }

    public void addImageView(){
//        if(isList){
//            listLayout = new GridLayout(aloImageView.size(),1);
//            ICVInstance.setLayout(listLayout);
//        }
//        System.out.println("reached addnewimageview");
        aloImageView.add(new ImageView(ImageCollectionModel.getInstance().getListModel().size() - 1, isList));
//        System.out.println(aloImageView.size() - 1);
        ICVInstance.add(aloImageView.get(aloImageView.size() - 1));
        Model.getInstance().addObserver(aloImageView.get(aloImageView.size() - 1));
        refreshLayout();
    }

    public static ImageCollectionView getInstance(){
        if(ICVInstance == null){
            ICVInstance = new ImageCollectionView();
        }
        return ICVInstance;
    }

    public void refreshSize(double width){
        if(isList != true){
            int newCol = (int)(width/320);
            gridLayout = new GridLayout(0,newCol);
            ICVInstance.setLayout(gridLayout);
//            System.out.println("newCol is: " + newCol);
        }
        Model.getInstance().notifyObservers();
//        refreshLayout();
    }

    public void refreshLayout(){
        if(isList == true){
            boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
            ICVInstance.setLayout(boxLayout);
//            for(int i = 0; i < aloImageView.size(); ++i){
//                ICVInstance.add(aloImageView.get(i));
//            }
        } else {
            if(aloImageView.size() > (gridLayout.getColumns() * gridLayout.getRows())){
                gridLayout = new GridLayout(gridLayout.getRows() + 1, gridLayout.getColumns());
            }
            ICVInstance.setLayout(gridLayout);
        }
        Model.getInstance().notifyObservers();
    }

    private ImageCollectionView(){
        aloImageView = new ArrayList<>();
        isList = true;
        limit = 0;
        boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        gridLayout = new GridLayout(1,2);
        this.setBackground(Color.white);
        this.setLayout(boxLayout);
    }

    public void update(Object observable) {
        revalidate();
        repaint();
    }
}
