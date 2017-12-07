package shafin.shareride;

/**
 * Created by shafins on 7/12/17.
 */

public class ComplainInformation {
    String name;
    String id;
    String complain;

    public ComplainInformation(String name, String id, String complain) {
        this.name = name;
        this.id = id;
        this.complain = complain;
    }



    public ComplainInformation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComplain() {
        return complain;
    }

    public void setComplain(String complain) {
        this.complain = complain;
    }


}
