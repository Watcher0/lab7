package common.data;

import java.io.Serializable;
import java.time.LocalDate;

public class Person implements Comparable<Person>, Serializable {
    private String name;
    private LocalDate birthday;
    private long height;
    private String passportID;
    private Location location;

    public Person(String name, LocalDate birthday, long height, String passportID, Location location){
        this.name = name;
        this.birthday = birthday;
        this.height = height;
        this.passportID = passportID;
        this.location = location;
    }

    @Override
    public String toString() {
        return "name = " + name + "\n"
                + "birthday = " + birthday + "\n"
                + "height = " + height + "\n"
                + "passportID = " + passportID + "\n"
                + "location = " + location + "\n";
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public long getHeight() {
        return height;
    }

    public String getPassportID() {
        return passportID;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public int compareTo(Person o) {
        LocalDate notNullBirthday = birthday;
        if (birthday == null){
            notNullBirthday = LocalDate.now();
        }
        LocalDate notNullBirthdayO = o.birthday;
        if (o.birthday == null){
             notNullBirthdayO = LocalDate.now();
        }
        if (notNullBirthday.getDayOfMonth() - height + passportID.length() > notNullBirthdayO.getDayOfMonth() - o.height + o.passportID.length()){
            return 1;
        }
        else if (notNullBirthday.getDayOfMonth() - height + passportID.length() < notNullBirthdayO.getDayOfMonth() - o.height + o.passportID.length()){
            return -1;
        }
        else return 0;

    }
}
