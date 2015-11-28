package model;

import javafx.scene.image.Image;

import java.io.Serializable;

/**
 * Created by ribra on 11/8/2015.
 */
public class Person implements Serializable {
    private String creationDate;
    private String name;
    private String birthDate;
    private String city;
    private String phoneNumber;
    private String email;
    private String university;
    private String place;
    private String reason;
    private Image image;
    private boolean attended;

    public Person() {}

    public Person(String creationDate, String name, String birthDate, String city, String phoneNumber,
                  String email, String university, String place, String reason) {
        this.creationDate = creationDate;
        this.name = name;
        this.birthDate = birthDate;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.university = university;
        this.place = place;
        this.reason = reason;
    }

    public Person(String creationDate, String name, String birthDate, String city, String phoneNumber,
                  String email, String university, String place, String reason, Image image) {
        this.creationDate = creationDate;
        this.name = name;
        this.birthDate = birthDate;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.university = university;
        this.place = place;
        this.reason = reason;
        this.image = image;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    public String [] getAllValues() {
        int size = 9;
        String [] strings = new String[]{creationDate, name, birthDate, city, phoneNumber, email, university, place, reason};
        return strings;
    }

    @Override
    public String toString() {
        return "Person{" +
                "creationDate='" + creationDate + '\'' +
                ", name='" + name + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", city='" + city + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", university='" + university + '\'' +
                ", place='" + place + '\'' +
                ", reason='" + reason + '\'' +
                ", image=" + image +
                ", attended=" + attended +
                '}';
    }
}
