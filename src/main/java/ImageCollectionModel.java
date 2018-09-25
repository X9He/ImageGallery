import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;

public class ImageCollectionModel {
    private static ArrayList<ImageModel> listModel;
    private static ImageCollectionModel imageCollectionModelInstance = null;

    public ArrayList<ImageModel> getListModel() {
        return listModel;
    }

    private ImageCollectionModel()
    {
        listModel = new ArrayList<>();
    }

    public void addNewImage(String s){
        Path p = Paths.get(s);
        try{
            BasicFileAttributes attr = Files.readAttributes(p,
                    BasicFileAttributes.class);
            FileTime d = attr.creationTime();
            listModel.add(new ImageModel(d, p));
            ImageCollectionView.getInstance().addImageView();
        } catch (IOException exp){
            exp.printStackTrace();
//            System.out.println("add new image has had an exception");
        }
        Model.getInstance().notifyObservers();
    }

    public static ImageCollectionModel getInstance()
    {
        if (imageCollectionModelInstance == null){
            imageCollectionModelInstance = new ImageCollectionModel();
        }
        return imageCollectionModelInstance;
    }
}
