package common.data;

import java.io.Serializable;

public enum MpaaRating implements Serializable {
    G,
    PG,
    PG_13,
    R,
    NC_17;

    /**
     * Generates a beautiful list of enum string values.
     * @return String with all enum values splitted by comma.
     */
    public static String nameList(){
        String nameList = "";
        for (MpaaRating mpaaRating : values()){
            nameList += mpaaRating.name() + ", ";
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}
