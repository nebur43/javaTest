
package es.ruben.holamundo.rest.dossier.request;

import java.util.List;

public class MitekRequestDossier {

    private DossierMetadata dossierMetadata;
    private List<Evidence> evidence = null;
    private Configuration configuration;

    public DossierMetadata getDossierMetadata() {
        return dossierMetadata;
    }

    public void setDossierMetadata(DossierMetadata dossierMetadata) {
        this.dossierMetadata = dossierMetadata;
    }

    public List<Evidence> getEvidence() {
        return evidence;
    }

    public void setEvidence(List<Evidence> evidence) {
        this.evidence = evidence;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }


}
