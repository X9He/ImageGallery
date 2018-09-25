
import java.util.*;

public class Model {
    /** The observers that are watching this model for changes. */
    private List<Observer> observers;
    private static Model mInstance;


    public static Model getInstance(){
        if (mInstance == null){
            mInstance = new Model();
        }
        return mInstance;
    }
    /**
     * Create a new model.
     */
    private Model() {
        this.observers = new ArrayList();
    }

    /**
     * Add an observer to be notified when this model changes.
     */
    public void addObserver(Observer observer) {
        mInstance.observers.add(observer);
    }

    /**
     * Remove an observer from this model.
     */
    public void removeObserver(Observer observer) {
        mInstance.observers.remove(observer);
    }

    /**
     * Notify all observers that the model has changed.
     */
    public void notifyObservers() {
        for (Observer observer: mInstance.observers) {
            observer.update(mInstance);
        }
    }
}
