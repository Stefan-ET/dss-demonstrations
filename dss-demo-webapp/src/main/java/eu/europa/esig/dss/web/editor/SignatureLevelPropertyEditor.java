package eu.europa.esig.dss.web.editor;

import eu.europa.esig.dss.enumerations.SignatureLevel;
import eu.europa.esig.dss.utils.Utils;

public class SignatureLevelPropertyEditor extends EnumPropertyEditor {

    public SignatureLevelPropertyEditor() {
        super(SignatureLevel.class);
    }

    @Override
    public String getAsText() {
        return getValue() == null ? Utils.EMPTY_STRING : getValue().toString();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            setValue(SignatureLevel.valueByName(text));
        } catch (Exception e) {
            setValue(null);
        }
    }

}
