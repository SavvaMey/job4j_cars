package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "mark_id")
    private Mark mark;
    private String model;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "body_id")
    private Body body;
    private double price;

        public static Car of(Mark mark, String model, Body body, double price) {
            Car car = new Car();
            car.mark = mark;
            car.model = model;
            car.body = body;
            car.price = price;
        return car;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public String getMark() {
//        return mark;
//    }
//
//    public void setMark(String mark) {
//        this.mark = mark;
//    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

//    public String getBody() {
//        return body;
//    }
//
//    public void setBody(String body) {
//        this.body = body;
//    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return id == car.id && Double.compare(car.price, price) == 0
                && Objects.equals(mark, car.mark) && Objects.equals(model, car.model)
                && Objects.equals(body, car.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mark, model, body, price);
    }

    @Override
    public String toString() {
        return "Car{"
                + "id=" + id
                + ", mark=" + mark
                + ", model='" + model + '\''
                + ", body=" + body
                + ", price=" + price
                + '}';
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        Car car = (Car) o;
//        return id == car.id && Double.compare(car.price, price) == 0 && Objects.equals(mark, car.mark)
//                && Objects.equals(model, car.model) && Objects.equals(body, car.body);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, mark, model, body, price);
//    }
//
//    @Override
//    public String toString() {
//        return "model.Car{"
//                + "id=" + id
//                + ", mark='" + mark + '\''
//                + ", model='" + model + '\''
//                + ", body='" + body + '\''
//                + ", price=" + price
//                + '}';
//    }
}
