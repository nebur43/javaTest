
package es.ruben.holamundo.rest.dossier.response;

import java.util.List;

public class Findings {

    private Boolean authenticated;
    private Integer probability;
    private List<Verification> verifications = null;

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Integer getProbability() {
        return probability;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    public List<Verification> getVerifications() {
        return verifications;
    }

    public void setVerifications(List<Verification> verifications) {
        this.verifications = verifications;
    }

}
