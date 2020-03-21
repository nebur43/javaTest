
package es.ruben.holamundo.rest.dossier.request;

import java.util.List;

public class Configuration {

    private Verifications verifications;
    private List<String> responseImages = null;

    public Verifications getVerifications() {
        return verifications;
    }

    public void setVerifications(Verifications verifications) {
        this.verifications = verifications;
    }

    public List<String> getResponseImages() {
        return responseImages;
    }

    public void setResponseImages(List<String> responseImages) {
        this.responseImages = responseImages;
    }

}
