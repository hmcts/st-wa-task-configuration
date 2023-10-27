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
        caseData.put("caseNameHmctsInternal", "Joe Blogs");
        caseData.put("isUrgent", "No");
        return new CaseDataBuilder(caseData);
    }

    public Map<String,Object> build() {
        return caseData;
    }

    public CaseDataBuilder isUrgent() {
        caseData.put("isUrgent", "Yes");
        return this;
    }
}
