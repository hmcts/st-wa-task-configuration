package uk.gov.hmcts.reform.wataskconfigurationtemplate.utils;

import java.util.HashMap;
import java.util.Map;

public class CaseDataBuilder {

    HashMap<String,Object> caseData;

    private CaseDataBuilder(HashMap<String,Object> caseData) {
        this.caseData = caseData;
    }

    public static CaseDataBuilder defaultCase() {
        HashMap<String,Object> caseData = new HashMap<>();
        caseData.put("caseNamePublic", "Joe Blogs");
        caseData.put("isScottishCase", "No");
        caseData.put("regionalProcessingCenter", Map.of(
            "name", "BRADFORD",
            "epimsId", "123456"
        ));
        caseData.put("caseManagementCategory", Map.of(
            "value", Map.of("label", "Personal Independence Payment")
        ));
        return new CaseDataBuilder(caseData);
    }

    public CaseDataBuilder isScottishCase(String value) {
        caseData.put("isScottishCase", value);
        return this;
    }

    public CaseDataBuilder withNextHearing(String hearingId, String hearingDate) {
        caseData.put("nextHearingId", hearingId);
        caseData.put("nextHearingDate", hearingDate);
        return this;
    }

    public Map<String,Object> build() {
        return caseData;
    }
}
