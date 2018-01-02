package ke.co.toshngure.gikosh.model;

/**
 * Created by Anthony Ngure on 11/12/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class User {


    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String facebookId;
    private String facebookPictureUrl;
    private String facebookAgeRangeMin;
    private String createdAt;
    private String updatedAt;
    private String token;
    private String name;

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public String getFacebookPictureUrl() {
        return facebookPictureUrl;
    }

    public String getFacebookAgeRangeMin() {
        return facebookAgeRangeMin;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }
}
