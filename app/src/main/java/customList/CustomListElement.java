package customList;

/**
 * Created by Jose Pablo on 8/21/2017.
 * Simple class used to store two string values in order to handle weather locations.
 * -A String for the city's name.
 * -A String for the location's ID given by the OpenWeatherApp API documentation
 */

public class CustomListElement {
    private String stName;
    private String stId;
    public CustomListElement(){
        stName = "";
        stId = "0";
    }
    public CustomListElement(String name, String id){
        this.stName = name;
        this.stId = id;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    @Override
    public String toString(){
        return this.stName;
    }
}
