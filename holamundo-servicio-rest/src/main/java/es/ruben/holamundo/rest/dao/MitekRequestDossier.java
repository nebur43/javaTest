package es.ruben.holamundo.rest.dao;

import java.util.List;

public class MitekRequestDossier {

	private MitekDossierMetadata dossierMetadata;
	
	private List<MitekEvidence> evidence;
	
	private MitekConfiguration configuration;

	public MitekDossierMetadata getDossierMetadata() {
		return dossierMetadata;
	}

	public void setDossierMetadata(MitekDossierMetadata dossierMetadata) {
		this.dossierMetadata = dossierMetadata;
	}

	// MitekBiometric ???
	public List<MitekEvidence> getEvidence() {
		return evidence;
	}

	public void setEvidence(List<MitekEvidence> evidence) {
		this.evidence = evidence;
	}

	public MitekConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(MitekConfiguration configuration) {
		this.configuration = configuration;
	}
	
	
	
}
