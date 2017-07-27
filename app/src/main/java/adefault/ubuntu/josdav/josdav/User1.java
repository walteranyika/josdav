package adefault.ubuntu.josdav.josdav;


import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User1 {


    public String fullname;
    public  String phone1;
    public  String phone2;
    public  String home;


    public User1() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User1( String fullname, String phone1, String phone2, String home) {


        this.fullname = fullname;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.home=home;
    }


    public String getFullname() {
        return fullname;
    }

    public String getPhone1() {
        return phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public String getHome() {
        return home;
    }

}

