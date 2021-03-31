package model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ad")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id")
    private Photo photo;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    private boolean sold;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public static Ad of(String description, Car car, Photo photo, User user, boolean sold) {
        Ad ad = new Ad();
        ad.description = description;
        ad.car = car;
        ad.photo = photo;
        ad.user = user;
        ad.sold = sold;
        ad.created = new Date(System.currentTimeMillis());
        return ad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ad ad = (Ad) o;
        return id == ad.id && sold == ad.sold
                && Objects.equals(description, ad.description) && Objects.equals(car, ad.car)
                && Objects.equals(photo, ad.photo) && Objects.equals(user, ad.user)
                && Objects.equals(created, ad.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, car, photo, user, sold, created);
    }

    @Override
    public String toString() {
        return "model.Ad{"
                + "id=" + id
                + ", description='" + description + '\''
                + ", car=" + car
                + ", photo=" + photo
                + ", user=" + user
                + ", sold=" + sold
                + ", created=" + created
                + '}';
    }
}
