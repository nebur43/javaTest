
package es.ruben.holamundo.rest.dossier.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Evidence {

    private String type;
    private List<Image> images = null;
    private String biometricType;
    private String data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getBiometricType() {
        return biometricType;
    }

    public void setBiometricType(String biometricType) {
        this.biometricType = biometricType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
