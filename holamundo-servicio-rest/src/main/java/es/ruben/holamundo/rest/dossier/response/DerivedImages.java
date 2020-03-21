
package es.ruben.holamundo.rest.dossier.response;

public class DerivedImages {

    private CroppedPortrait croppedPortrait;
    private CroppedSignature croppedSignature;
    private CroppedDocument croppedDocument;

    public CroppedPortrait getCroppedPortrait() {
        return croppedPortrait;
    }

    public void setCroppedPortrait(CroppedPortrait croppedPortrait) {
        this.croppedPortrait = croppedPortrait;
    }

    public CroppedSignature getCroppedSignature() {
        return croppedSignature;
    }

    public void setCroppedSignature(CroppedSignature croppedSignature) {
        this.croppedSignature = croppedSignature;
    }

    public CroppedDocument getCroppedDocument() {
        return croppedDocument;
    }

    public void setCroppedDocument(CroppedDocument croppedDocument) {
        this.croppedDocument = croppedDocument;
    }

}
