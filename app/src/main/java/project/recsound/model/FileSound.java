package project.recsound.model;

/**
 * Created by susy on 2/01/17.
 */

public class FileSound {

    String title;
    String date;

    public FileSound(String title, String date) {
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
