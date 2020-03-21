
package es.ruben.holamundo.rest.dossier.response;

import java.util.List;

// no hay definido ningun verifications en la response de la documentaci�n, y parece que siempre va vac�o. 
// si no hay problemas usamos siempre el mismo que ne la request
import es.ruben.holamundo.rest.dossier.request.Verifications;


public class Configuration {

	private Verifications verifications;
    private List<Object> responseImages = null;


    public Verifications getVerifications() {
        return verifications;
    }

    public void setVerifications(Verifications verifications) {
        this.verifications = verifications;
    }
    
    public List<Object> getResponseImages() {
        return responseImages;
    }

    public void setResponseImages(List<Object> responseImages) {
        this.responseImages = responseImages;
    }

}
