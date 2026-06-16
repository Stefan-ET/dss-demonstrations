package eu.europa.esig.dss.web.ws;

import eu.europa.esig.dss.diagnostic.DiagnosticData;
import eu.europa.esig.dss.diagnostic.EAAWrapper;
import eu.europa.esig.dss.diagnostic.SignatureWrapper;
import eu.europa.esig.dss.enumerations.EAAType;
import eu.europa.esig.dss.model.FileDocument;
import eu.europa.esig.dss.validation.reports.Reports;
import eu.europa.esig.dss.web.config.CXFConfig;
import eu.europa.esig.dss.ws.converter.RemoteDocumentConverter;
import eu.europa.esig.dss.ws.dto.RemoteDocument;
import eu.europa.esig.dss.ws.eaa.validation.dto.EAAToValidateDTO;
import eu.europa.esig.dss.ws.eaa.validation.rest.client.RestEAAValidationService;
import eu.europa.esig.dss.ws.validation.dto.WSReportsDTO;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RestEAAValidationIT extends AbstractRestIT {

    private RestEAAValidationService validationService;

    @BeforeEach
    public void init() {
        JAXRSClientFactoryBean factory = new JAXRSClientFactoryBean();

        factory.setAddress(getBaseCxf() + CXFConfig.REST_EAA_VALIDATION);
        factory.setServiceClass(RestEAAValidationService.class);
        factory.setProviders(Collections.singletonList(jacksonJsonProvider()));

        LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
        factory.getInInterceptors().add(loggingInInterceptor);
        factory.getInFaultInterceptors().add(loggingInInterceptor);

        LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
        factory.getOutInterceptors().add(loggingOutInterceptor);
        factory.getOutFaultInterceptors().add(loggingOutInterceptor);

        validationService = factory.create(RestEAAValidationService.class);
    }

    @Test
    public void test() {
        RemoteDocument eaaPresentation = RemoteDocumentConverter.toRemoteDocument(new FileDocument("src/test/resources/sd-jwt-eaa.json"));
        EAAToValidateDTO dto = new EAAToValidateDTO(eaaPresentation);
        WSReportsDTO result = validationService.validateEAA(dto);

        assertNotNull(result.getDiagnosticData());
        assertNotNull(result.getDetailedReport());
        assertNotNull(result.getSimpleReport());
        assertNotNull(result.getValidationReport());

        Reports reports = new Reports(result.getDiagnosticData(), result.getDetailedReport(), result.getSimpleReport(), result.getValidationReport());

        assertNotNull(reports);
        assertNotNull(reports.getDiagnosticData());
        assertNotNull(reports.getDetailedReport());
        assertNotNull(reports.getSimpleReport());

        DiagnosticData diagnosticData = reports.getDiagnosticData();
        EAAWrapper eaa = diagnosticData.getEAAById(diagnosticData.getFirstEAAId());
        assertNotNull(eaa);
        assertEquals(EAAType.SD_JWT_VC, eaa.getEAAType());

        List<SignatureWrapper> eaaSignatures = eaa.getEAASignatures();
        assertEquals(1, eaaSignatures.size());
        SignatureWrapper signature = eaaSignatures.get(0);
        assertTrue(signature.isBLevelTechnicallyValid());
    }

}
