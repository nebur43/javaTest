
package es.ruben.holamundo.rest.dossier.response;

import java.util.HashMap;
import java.util.Map;

public class Verification {

    private Integer verificationType;
    private String name;
    private String judgement;
    private Integer probability;
    private Map<String, Object> notifications = new HashMap<String, Object>();
    private String version;
    private String documentId;

    public Integer getVerificationType() {
        return verificationType;
    }

    public void setVerificationType(Integer verificationType) {
        this.verificationType = verificationType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJudgement() {
        return judgement;
    }

    public void setJudgement(String judgement) {
        this.judgement = judgement;
    }

    public Integer getProbability() {
        return probability;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    public Map<String, Object> getNotifications() {
    	return this.notifications;
    }

    public void setNotifications(String name, Object value) {
    	this.notifications.put(name, value);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

}
