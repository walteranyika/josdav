package adefault.ubuntu.josdav.josdav;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by android on 6/20/2017.
 */
@IgnoreExtraProperties
public class Users {

    private String email;
    private String phone_num;
    private String name;
    public Users(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }
}