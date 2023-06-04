
package es.ruben.holamundo.rest.dossier.response;

import java.util.List;

public class MitekResponseDossier {

    private DossierMetadata dossierMetadata;
    private List<Evidence> evidence = null;
    private Findings findings;
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

    public Findings getFindings() {
        return findings;
    }

    public void setFindings(Findings findings) {
        this.findings = findings;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

}
