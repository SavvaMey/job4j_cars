package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String path;

    public static Photo of(String path) {
        Photo photo = new Photo();
        photo.path = path;
        return photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Photo photo = (Photo) o;
        return id == photo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}