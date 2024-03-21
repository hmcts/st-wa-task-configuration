package uk.gov.hmcts.reform.wataskconfigurationtemplate.dmn;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.reform.wataskconfigurationtemplate.DmnDecisionTableBaseUnitTest;
import uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CaseDataBuilder;
import uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.ConfigurationExpectationBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.DmnDecisionTable.WA_TASK_CONFIGURATION_ST_CIC_CRIMINALINJURIESCOMPENSATION;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.APPLICATION_WORK_TYPE;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.ACCESS_WORK_TYPE;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.CASE_NAME;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.CASE_MANAGEMENT_CATEGORY;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.DECISION_WORK_TYPE;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.DEFAULT_MAJOR_PRIORITY;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.DEFAULT_MINOR_PRIORITY;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.DESCRIPTION;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.DUE_DATE_INTERVAL_DAYS;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.DUE_DATE_ORIGIN;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.HEARING_WORK_TYPE;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.LOCATION;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.LOCATION_NAME;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.MAJOR_PRIORITY;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.MINOR_PRIORITY;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.PRIORITY_DATE_ORIGIN_REF;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.PRIORITY_WORK_TYPE;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.REGION;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.ROLE_CATEGORY;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.ROLE_CATEGORY_ADMIN;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.ROLE_CATEGORY_CTSC;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.ROLE_CATEGORY_JUDICIAL;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.ROLE_CATEGORY_LO;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.ROUTINE_WORK_TYPE;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.URGENT_MAJOR_PRIORITY;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.utils.CamundaTaskConstants.WORK_TYPE;

class CamundaTaskWaConfigurationTest extends DmnDecisionTableBaseUnitTest {

    private static final String REQUEST = "classpath:caseData/custom-case-data.json";

    static Stream<Arguments> scenarioProvider() throws IOException {
        return Stream.of(

            Arguments.of(
                "processCaseWithdrawalDirections",
                CaseDataBuilder.defaultCase()
                    .isUrgent()
                    .build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, URGENT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, ROUTINE_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "10", true)
                    .expectedValue(DESCRIPTION, "[Orders: Send order](/cases/case-details"
                        + "/${[CASE_REFERENCE]}/trigger/caseworker-send-order/"
                        + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType)", true)
                    .expectedValue(DUE_DATE_ORIGIN, ZonedDateTime.now(), false)
                    .build()
            ),
            Arguments.of(
                "processRule27Decision",
                CaseDataBuilder.customCase(REQUEST).build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(CASE_NAME, "Rio Read", true)
                    .expectedValue(CASE_MANAGEMENT_CATEGORY, "ST CIC", true)
                    .expectedValue(REGION, "123", true)
                    .expectedValue(LOCATION, "123456", true)
                    .expectedValue(LOCATION_NAME, "GTC", true)
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, ROUTINE_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/caseworker-send-order/"
                            + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "processListingDirections",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, ROUTINE_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/caseworker-send-order/"
                            + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "processDirectionsReListedCase",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, ROUTINE_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/caseworker-send-order/"
                            + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "processDirectionsReListedCaseWithin5Days",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, PRIORITY_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/caseworker-send-order/"
                            + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "processSetAsideDirections",
                CaseDataBuilder.customCase(REQUEST).build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(CASE_NAME, "Rio Read", true)
                    .expectedValue(CASE_MANAGEMENT_CATEGORY, "ST CIC", true)
                    .expectedValue(REGION, "123", true)
                    .expectedValue(LOCATION, "123456", true)
                    .expectedValue(LOCATION_NAME, "GTC", true)
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, ROUTINE_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/caseworker-send-order/"
                            + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "processCorrections",
                CaseDataBuilder.defaultCase()
                    .isUrgent()
                    .build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, URGENT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, HEARING_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "3", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/caseworker-send-order/"
                            + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "processDirectionsReturned",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, ROUTINE_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "10", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/caseworker-send-order/"
                            + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "processPostponementDirections",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, ROUTINE_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/caseworker-send-order/"
                            + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "processTimeExtensionDirectionsReturned",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, ROUTINE_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/caseworker-send-order/"
                            + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "processReinstatementDecisionNotice",
                CaseDataBuilder.customCase(REQUEST).build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(CASE_NAME, "Rio Read", true)
                    .expectedValue(CASE_MANAGEMENT_CATEGORY, "ST CIC", true)
                    .expectedValue(REGION, "123", true)
                    .expectedValue(LOCATION, "123456", true)
                    .expectedValue(LOCATION_NAME, "GTC", true)
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, ROUTINE_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/caseworker-send-order/"
                            + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "processOtherDirectionsReturned",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "10", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/caseworker-send-order/"
                            + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "processWrittenReasons",
                CaseDataBuilder.defaultCase()
                    .isUrgent()
                    .build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, URGENT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, ROUTINE_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "3", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/caseworker-send-order/"
                            + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "processStrikeOutDirectionsReturned",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, ROUTINE_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "10", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/caseworker-send-order/"
                            + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "processStayDirections",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, ROUTINE_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "10", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/caseworker-send-order/"
                            + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "issueDecisionNotice",
                CaseDataBuilder.customCase(REQUEST).build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(CASE_NAME, "Rio Read", true)
                    .expectedValue(CASE_MANAGEMENT_CATEGORY, "ST CIC", true)
                    .expectedValue(REGION, "123", true)
                    .expectedValue(LOCATION, "123456", true)
                    .expectedValue(LOCATION_NAME, "GTC", true)
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, HEARING_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Decision: Issue a decision](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/caseworker-issue-decision/"
                            + "caseworker-issue-decisionSelectIssueNoticeOption<br/>"
                            + "[Decision: Issue final decision](/cases/case-details/${[CASE_REFERENCE]}"
                            + "/trigger/caseworker-issue-final-decision/"
                            + "caseworker-issue-final-decisionselectIssueNoticeOption)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "completeHearingOutcome",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, HEARING_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Hearings: Create listing](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-hearing-summary/create-hearing-summarycreateHearingSummary)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "referCase",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, ROUTINE_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Refer case to judge](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/refer-to-judge/refer-to-judgereferToJudgeReason)<br/>"
                            + "[Refer case to legal officer](/cases/case-details/${[CASE_REFERENCE]}"
                            + "/trigger/refer-to-legal-officer/refer-to-legal-officerreferToLegalOfficer)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "vetNewCaseDocuments",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, APPLICATION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Edit case details](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/edit-case/edit-casecaseCategorisationDetails)<br/>"
                            + "[Case: Build case](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/caseworker-case-built/caseworker-case-builtcaseBuilt)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewNewCaseAndProvideDirectionsLO",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_LO, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewTimeExtensionRequestLO",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_LO, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewStrikeOutRequestLO",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_LO, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewStayRequestLO",
                CaseDataBuilder.defaultCase()
                    .isUrgent()
                    .build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, URGENT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_LO, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewListingDirectionsLO",
                CaseDataBuilder.customCase(REQUEST).build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(CASE_NAME, "Rio Read", true)
                    .expectedValue(CASE_MANAGEMENT_CATEGORY, "ST CIC", true)
                    .expectedValue(REGION, "123", true)
                    .expectedValue(LOCATION, "123456", true)
                    .expectedValue(LOCATION_NAME, "GTC", true)
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_LO, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewWithdrawalRequestLO",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_LO, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewRule27RequestLO",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_LO, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewListCaseLO",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_LO, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewOtherRequestLO",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_LO, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewListCaseWithin5DaysLO",
                CaseDataBuilder.customCase(REQUEST).build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(CASE_NAME, "Rio Read", true)
                    .expectedValue(CASE_MANAGEMENT_CATEGORY, "ST CIC", true)
                    .expectedValue(REGION, "123", true)
                    .expectedValue(LOCATION, "123456", true)
                    .expectedValue(LOCATION_NAME, "GTC", true)
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_LO, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewPostponementRequestLO",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_LO, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewReinstatementRequestLO",
                CaseDataBuilder.defaultCase()
                    .isUrgent()
                    .build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, URGENT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_LO, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewListCaseWithin5DaysJudge",
                CaseDataBuilder.customCase(REQUEST).build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(CASE_NAME, "Rio Read", true)
                    .expectedValue(CASE_MANAGEMENT_CATEGORY, "ST CIC", true)
                    .expectedValue(REGION, "123", true)
                    .expectedValue(LOCATION, "123456", true)
                    .expectedValue(LOCATION_NAME, "GTC", true)
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_JUDICIAL, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewPostponementRequestJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_JUDICIAL, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewCorrectionsRequest",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_JUDICIAL, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewWrittenReasonsRequest",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_JUDICIAL, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "28", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewReinstatementRequestJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_JUDICIAL, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewSetAsideRequest",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_JUDICIAL, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewStayRequestJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_JUDICIAL, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewNewCaseAndProvideDirectionsJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_JUDICIAL, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewOtherRequestJudge",
                CaseDataBuilder.customCase(REQUEST).build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(CASE_NAME, "Rio Read", true)
                    .expectedValue(CASE_MANAGEMENT_CATEGORY, "ST CIC", true)
                    .expectedValue(REGION, "123", true)
                    .expectedValue(LOCATION, "123456", true)
                    .expectedValue(LOCATION_NAME, "GTC", true)
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY,true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE,true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_JUDICIAL,true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2",true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewWithdrawalRequestJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_JUDICIAL, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewRule27RequestJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_JUDICIAL, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewListingDirectionsJudge",
                CaseDataBuilder.customCase(REQUEST).build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(CASE_NAME, "Rio Read", true)
                    .expectedValue(CASE_MANAGEMENT_CATEGORY, "ST CIC", true)
                    .expectedValue(REGION, "123", true)
                    .expectedValue(LOCATION, "123456", true)
                    .expectedValue(LOCATION_NAME, "GTC", true)
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_JUDICIAL, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewListCaseJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_JUDICIAL, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewStrikeOutRequestJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_JUDICIAL, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewTimeExtensionRequestJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, DECISION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_JUDICIAL, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewSpecificAccessRequestJudiciary",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, ACCESS_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_JUDICIAL, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Review Access Request](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/reviewSpecificAccessRequestJudiciary)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewSpecificAccessRequestLegalOps",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, ACCESS_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_LO, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Review Access Request](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/reviewSpecificAccessRequestLegalOps)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewSpecificAccessRequestAdmin",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, ACCESS_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Review Access Request](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/reviewSpecificAccessRequestAdmin)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "reviewSpecificAccessRequestCTSC",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, ACCESS_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_CTSC, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Review Access Request](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/reviewSpecificAccessRequestCTSC)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "followUpNoncomplianceOfDirections",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, ROUTINE_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Application DSS Update (cic)](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/caseworker-update-dss-application)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "registerNewCase",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, APPLICATION_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue(
                        DESCRIPTION,
                        "New Case Received",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "processFurtherEvidence",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, ROUTINE_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "10", true)
                    .expectedValue(
                        DESCRIPTION,
                        "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/caseworker-send-order/"
                            + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType)",
                        true
                    )
                    .build()
            ),
            Arguments.of(
                "stitchCollateHearingBundle",
                CaseDataBuilder.customCase(REQUEST).build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(CASE_NAME, "Rio Read", true)
                    .expectedValue(CASE_MANAGEMENT_CATEGORY, "ST CIC", true)
                    .expectedValue(REGION, "123", true)
                    .expectedValue(LOCATION, "123456", true)
                    .expectedValue(LOCATION_NAME, "GTC", true)
                    .expectedValue(MINOR_PRIORITY, DEFAULT_MINOR_PRIORITY, true)
                    .expectedValue(MAJOR_PRIORITY, DEFAULT_MAJOR_PRIORITY, true)
                    .expectedValue(WORK_TYPE, HEARING_WORK_TYPE, true)
                    .expectedValue(ROLE_CATEGORY, ROLE_CATEGORY_ADMIN, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue(
                        DESCRIPTION,
                        "Prepare Hearing Bundle",
                        true
                    )
                    .build()
            )
        );
    }

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_CONFIGURATION_ST_CIC_CRIMINALINJURIESCOMPENSATION;
    }

    @ParameterizedTest(name = "task type: {0} case data: {1}")
    @MethodSource("scenarioProvider")
    void should_return_correct_configuration_values_for_scenario(
        String taskType, Map<String, Object> caseData,
        List<Map<String, Object>> expectation) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskType", taskType);
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        resultsMatch(dmnDecisionTableResult.getResultList(), expectation);
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();

        assertEquals(40, logic.getRules().size());
    }

    private void resultsMatch(List<Map<String, Object>> results, List<Map<String, Object>> expectation) {
        assertThat(results.size(), is(expectation.size()));

        for (int index = 0; index < expectation.size(); index++) {
            if (DUE_DATE_ORIGIN.equals(expectation.get(index).get("name"))) {
                assertEquals(
                    expectation.get(index).get("canReconfigure"),
                    results.get(index).get("canReconfigure")
                );
                assertTrue(validNow(
                    ZonedDateTime.parse(expectation.get(index).get("value").toString()),
                    ZonedDateTime.parse(results.get(index).get("value").toString())
                ));

            } else if (PRIORITY_DATE_ORIGIN_REF.equals(expectation.get(index).get("name"))) {
                assertEquals(
                    expectation.get(index).get("canReconfigure"),
                    results.get(index).get("canReconfigure")
                );
                assertTrue(LocalDate.parse(expectation.get(index).get("value").toString()).isEqual(
                    LocalDate.parse(results.get(index).get("value").toString())) ||
                               LocalDate.parse(expectation.get(index).get("value").toString()).isAfter(
                    LocalDate.parse(results.get(index).get("value").toString()))
                );

            } else {
                assertThat(results.get(index), is(expectation.get(index)));
            }
        }
    }

    private boolean validNow(ZonedDateTime expected, ZonedDateTime result) {
        ZonedDateTime now = ZonedDateTime.now();
        return result != null
            && (expected.isEqual(result) || expected.isBefore(result))
            && (now.isEqual(result) || now.isAfter(result));
    }
}

