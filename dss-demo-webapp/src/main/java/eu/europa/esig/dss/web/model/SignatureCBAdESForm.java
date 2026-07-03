package eu.europa.esig.dss.web.model;

import eu.europa.esig.dss.enumerations.COSEStructureType;
import eu.europa.esig.dss.enumerations.SigDMechanism;
import eu.europa.esig.dss.enumerations.SignatureForm;
import eu.europa.esig.dss.enumerations.SignaturePackaging;
import eu.europa.esig.dss.web.WebAppUtils;
import eu.europa.esig.dss.web.validation.AssertMultipartFile;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class SignatureCBAdESForm extends AbstractSignatureForm {

    @AssertMultipartFile
    private List<MultipartFile> documentsToSign;

    @NotNull(message = "{error.signature.cose.structure.type.mandatory}")
    private COSEStructureType coseStructureType;

    private boolean tagged;

    @NotNull(message = "{error.signature.packaging.mandatory}")
    private SignaturePackaging signaturePackaging;

    private SigDMechanism sigDMechanism;

    @AssertMultipartFile
    private MultipartFile externallySuppliedData;

    public SignatureCBAdESForm() {
        setSignatureForm(SignatureForm.CBAdES);
    }

    public List<MultipartFile> getDocumentsToSign() {
        return documentsToSign;
    }

    public void setDocumentsToSign(List<MultipartFile> documentsToSign) {
        this.documentsToSign = documentsToSign;
    }

    public @NotNull(message = "{error.signature.cose.structure.type.mandatory}") COSEStructureType getCoseStructureType() {
        return coseStructureType;
    }

    public void setCoseStructureType(@NotNull(message = "{error.signature.cose.structure.type.mandatory}") COSEStructureType coseStructureType) {
        this.coseStructureType = coseStructureType;
    }

    public boolean isTagged() {
        return tagged;
    }

    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    public @NotNull(message = "{error.signature.packaging.mandatory}") SignaturePackaging getSignaturePackaging() {
        return signaturePackaging;
    }

    public void setSignaturePackaging(@NotNull(message = "{error.signature.packaging.mandatory}") SignaturePackaging signaturePackaging) {
        this.signaturePackaging = signaturePackaging;
    }

    public SigDMechanism getSigDMechanism() {
        return sigDMechanism;
    }

    public void setSigDMechanism(SigDMechanism sigDMechanism) {
        this.sigDMechanism = sigDMechanism;
    }

    public MultipartFile getExternallySuppliedData() {
        return externallySuppliedData;
    }

    public void setExternallySuppliedData(MultipartFile externallySuppliedData) {
        this.externallySuppliedData = externallySuppliedData;
    }

    @AssertTrue(message = "{error.to.sign.files.mandatory}")
    public boolean isDocumentsToSign() {
        return WebAppUtils.isCollectionNotEmpty(documentsToSign);
    }

    @AssertTrue(message = "{error.to.sign.one.file.enveloping}")
    public boolean isDocumentsToSignConfigurationValid() {
        return !SignaturePackaging.ENVELOPING.equals(signaturePackaging) || (documentsToSign != null && documentsToSign.size() == 1);
    }

    @AssertTrue(message = "{error.jades.sigDMechanism.mandatory}")
    public boolean isSigDMechanismValid() {
        return !SignaturePackaging.DETACHED.equals(signaturePackaging) || sigDMechanism != null;
    }

}