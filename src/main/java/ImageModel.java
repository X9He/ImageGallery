import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Date;

public class ImageModel {
    private int numOfStars;
    private Path path;
    private FileTime date;


    public int getNumOfStars() {
        return numOfStars;
    }

    public void setNumOfStars(int numOfStars) {
        this.numOfStars = numOfStars;
        Model.getInstance().notifyObservers();
    }


    public FileTime getDate() {
        return date;
    }

    public void setDate(FileTime date) {
        this.date = date;
        Model.getInstance().notifyObservers();
    }

    public Path getPath() {
        return path;
    }

    public ImageModel(FileTime d, Path p){
        numOfStars = 0;
        date = d;
        path = p;
        Model.getInstance().notifyObservers();
    }
}
