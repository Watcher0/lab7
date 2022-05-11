package common.data;

import java.io.Serializable;

public enum MovieGenre implements Serializable {
    ACTION,
    WESTERN,
    COMEDY,
    MUSICAL;

    /**
     * Generates a beautiful list of enum string values.
     * @return String with all enum values splitted by comma.
     */
    public static String nameList(){
        String nameList = "";
        for (MovieGenre movieGenre : values()){
            nameList += movieGenre.name() + ", ";
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}
