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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.DmnDecisionTable.WA_TASK_CONFIGURATION_ST_CIC_CRIMINALJURIESCOMPENSATION;

class CamundaTaskWaConfigurationTest extends DmnDecisionTableBaseUnitTest {


    public static final String MINOR_PRIORITY = "minorPriority";
    public static final String MAJOR_PRIORITY = "majorPriority";
    public static final String DUE_DATE_INTERVAL_DAYS = "dueDateIntervalDays";


    static Stream<Arguments> scenarioProvider() {
        return Stream.of(

            Arguments.of(
                "processCaseWithdrawalDirections",
                CaseDataBuilder.defaultCase()
                    .isUrgent()
                    .build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "2000", true)
                    .expectedValue("workType", "routine_work", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "10", true)
                    .expectedValue("description", "[Orders: Send order](/cases/case-details"
                        + "/${[CASE_REFERENCE]}/trigger/caseworker-send-order/"
                        + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType",true)
                    .build()
            ),
            Arguments.of(
                "processRule27Decision",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "routine_work", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue("description",
                                   "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/caseworker-send-order/"
                                       + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType",
                                    true)
                    .build()
            ),
            Arguments.of(
                "processListingDirections",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "routine_work", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue("description",
                                    "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                        + "/caseworker-send-order/"
                                        + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType",
                                    true)
                    .build()
            ),
            Arguments.of(
                "processDirectionsReListedCase",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "routine_work", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue("description",
                                   "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/caseworker-send-order/"
                                       + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType",
                                   true)
                    .build()
            ),
            Arguments.of(
                "processDirectionsReListedCaseWithin5Days",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "priority", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue("description",
                                   "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/caseworker-send-order/"
                                       + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType",
                                   true)
                    .build()
            ),
            Arguments.of(
                "processSetAsideDirections",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue("description",
                                   "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/caseworker-send-order/"
                                       + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType",
                                   true)
                    .build()
            ),
            Arguments.of(
                "processCorrections",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "3", true)
                    .expectedValue("description",
                                   "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/caseworker-send-order/"
                                       + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType",
                                   true)
                    .build()
            ),
            Arguments.of(
                "processDirectionsReturned",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "10", true)
                    .expectedValue("description",
                                   "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/caseworker-send-order/"
                                       + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType",
                                   true)
                    .build()
            ),
            Arguments.of(
                "processPostponementDirections",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue("description",
                                   "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/caseworker-send-order/"
                                       + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType",
                                   true)
                    .build()
            ),
            Arguments.of(
                "processTimeExtensionDirectionsReturned",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue("description",
                                   "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/caseworker-send-order/"
                                       + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType",
                                   true)
                    .build()
            ),
            Arguments.of(
                "processReinstatementDecisionNotice",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue("description",
                                   "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/caseworker-send-order/"
                                       + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType",
                                   true)
                    .build()
            ),
            Arguments.of(
                "processOtherDirectionsReturned",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "10", true)
                    .expectedValue("description",
                                   "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/caseworker-send-order/"
                                       + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType",
                                   true)
                    .build()
            ),
            Arguments.of(
                "processWrittenReasons",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "3", true)
                    .expectedValue("description",
                                   "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/caseworker-send-order/"
                                       + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType",
                                   true)
                    .build()
            ),
            Arguments.of(
                "processStrikeOutDirectionsReturned",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "routine_work", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "10", true)
                    .expectedValue("description",
                                   "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/caseworker-send-order/"
                                       + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType",
                                   true)
                    .build()
            ),
            Arguments.of(
                "processStayDirections",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "routine_work", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "10", true)
                    .expectedValue("description",
                                   "[Orders: Send order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/caseworker-send-order/"
                                       + "caseworker-send-ordercaseworkerSendOrderSelectOrderIssuingType",
                                   true)
                    .build()
            ),
            Arguments.of(
                "issueDecisionNotice",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "hearing_work", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue("description",
                                   "[Decision: Issue a decision](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/caseworker-issue-decision/"
                                       + "caseworker-issue-decisionSelectIssueNoticeOption<br/>"
                                       + "[Decision: Issue final decision](/cases/case-details/${[CASE_REFERENCE]}"
                                       + "/trigger/caseworker-issue-final-decision/"
                                       + "caseworker-issue-final-decisionselectIssueNoticeOption",
                                   true)
                    .build()
            ),
            Arguments.of(
                "completeHearingOutcome",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "hearing_work", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue("description",
                                   "[Hearings: Create listing](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/create-hearing-summary/create-hearing-summarycreateHearingSummary",
                                   true)
                    .build()
            ),
            Arguments.of(
                "referCase",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "routine_work", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .expectedValue("description",
                                   "[Refer case to judge](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/refer-to-judge/refer-to-judgereferToJudgeReason<br/>"
                                       +"[Refer case to legal officer](/cases/case-details/${[CASE_REFERENCE]}"
                                       + "/trigger/refer-to-legal-officer/refer-to-legal-officerreferToLegalOfficer",
                                   true)
                    .build()
            ),
            Arguments.of(
                "vetNewCaseDocuments",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "applications", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue("description",
                                   "[Edit case details](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/edit-case/edit-casecaseCategorisationDetails<br/>"
                                       +"[Case: Build case](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/caseworker-case-built/caseworker-case-builtcaseBuilt",
                                   true)
                    .build()
            ),
            Arguments.of(
                "reviewNewCaseAndProvideDirectionsLO",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "LEGAL_OPERATIONS", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue("description",
                                   "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/create-draft-order/create-draft-ordercreateDraftOrder",
                                   true)
                    .build()
            ),
            Arguments.of(
                "reviewTimeExtensionRequestLO",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "LEGAL_OPERATIONS", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue("description",
                                   "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/create-draft-order/create-draft-ordercreateDraftOrder",
                                   true)
                    .build()
            ),
            Arguments.of(
                "reviewStrikeOutRequestLO",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "LEGAL_OPERATIONS", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue("description",
                                   "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/create-draft-order/create-draft-ordercreateDraftOrder",
                                   true)
                    .build()
            ),
            Arguments.of(
                "reviewStayRequestLO",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "LEGAL_OPERATIONS", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue("description",
                                   "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/create-draft-order/create-draft-ordercreateDraftOrder",
                                   true)
                    .build()
            ),
            Arguments.of(
                "reviewListingDirectionsLO",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "LEGAL_OPERATIONS", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue("description",
                                   "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/create-draft-order/create-draft-ordercreateDraftOrder",
                                   true)
                    .build()
            ),
            Arguments.of(
                "reviewWithdrawalRequestLO",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "LEGAL_OPERATIONS", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue("description",
                                   "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/create-draft-order/create-draft-ordercreateDraftOrder",
                                   true)
                    .build()
            ),
            Arguments.of(
                "reviewRule27RequestLO",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "LEGAL_OPERATIONS", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue("description",
                                   "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/create-draft-order/create-draft-ordercreateDraftOrder",
                                   true)
                    .build()
            ),
            Arguments.of(
                "reviewListCaseLO",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "LEGAL_OPERATIONS", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue("description",
                                   "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/create-draft-order/create-draft-ordercreateDraftOrder",
                                   true)
                    .build()
            ),
            Arguments.of(
                "reviewOtherRequestLO",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "LEGAL_OPERATIONS", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue("description",
                                   "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/create-draft-order/create-draft-ordercreateDraftOrder",
                                   true)
                    .build()
            ),
            Arguments.of(
                "reviewListCaseWithin5DaysLO",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "LEGAL_OPERATIONS", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue("description",
                                   "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/create-draft-order/create-draft-ordercreateDraftOrder",
                                   true)
                    .build()
            ),
            Arguments.of(
                "reviewPostponementRequestLO",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "LEGAL_OPERATIONS", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue("description",
                                   "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/create-draft-order/create-draft-ordercreateDraftOrder",
                                   true)
                    .build()
            ),
            Arguments.of(
                "reviewReinstatementRequestLO",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "LEGAL_OPERATIONS", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue("description",
                                   "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/create-draft-order/create-draft-ordercreateDraftOrder",
                                   true)
                    .build()
            ),
            Arguments.of(
                "reviewListCaseWithin5DaysJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "JUDICIAL", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue("description",
                                   "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/create-draft-order/create-draft-ordercreateDraftOrder",
                                   true)
                    .build()
            ),
            Arguments.of(
                "reviewPostponementRequestJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "JUDICIAL", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue("description",
                                   "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/create-draft-order/create-draft-ordercreateDraftOrder",
                                   true)
                    .build()
            ),
            Arguments.of(
                "reviewCorrectionsRequest",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "JUDICIAL", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .expectedValue("description",
                                   "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/create-draft-order/create-draft-ordercreateDraftOrder",
                                   true)
                    .build()
            ),
            Arguments.of(
                "reviewWrittenReasonsRequest",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "JUDICIAL", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "28", true)
                    .expectedValue("description",
                                   "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/create-draft-order/create-draft-ordercreateDraftOrder",
                                   true)
                    .build()
            ),
            Arguments.of(
                "reviewReinstatementRequestJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "JUDICIAL", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .expectedValue("description",
                                   "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/create-draft-order/create-draft-ordercreateDraftOrder",
                                   true)
                    .build()
            ),
            Arguments.of(
                "reviewSetAsideRequest",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "JUDICIAL", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .expectedValue("description",
                        "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                            + "/create-draft-order/create-draft-ordercreateDraftOrder",
                        true)
                    .build()
            ),
            Arguments.of(
                "reviewStayRequestJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "JUDICIAL", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .expectedValue("description",
                                   "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/create-draft-order/create-draft-ordercreateDraftOrder",
                                   true)
                    .build()
            ),
            Arguments.of(
                "reviewNewCaseAndProvideDirectionsJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue("workType", "decision_making_work", true)
                    .expectedValue("roleCategory", "JUDICIAL", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .expectedValue("description",
                                   "[Orders: Create draft](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                                       + "/create-draft-order/create-draft-ordercreateDraftOrder",
                                   true)
                    .build()
            )
        );
    }
    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_CONFIGURATION_ST_CIC_CRIMINALJURIESCOMPENSATION;
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

        assertThat(logic.getRules().size(), is(22));

    }
    private void resultsMatch(List<Map<String, Object>> results, List<Map<String, Object>> expectation) {
        assertThat(results.size(), is(expectation.size()));
        for (int index = 0; index < expectation.size(); index++) {
            if ("dueDateOrigin".equals(expectation.get(index).get("name"))) {
                assertEquals(
                    results.get(index).get("canReconfigure"),
                    expectation.get(index).get("canReconfigure")
                );
                assertTrue(validNow(
                    LocalDateTime.parse(results.get(index).get("value").toString()),
                    LocalDateTime.parse(expectation.get(index).get("value").toString())
                ));
            } else {
                assertThat(results.get(index), is(expectation.get(index)));
            }
        }
    }
    private boolean validNow(LocalDateTime actual, LocalDateTime expected) {
        LocalDateTime now = LocalDateTime.now();
        return actual != null
            && (expected.isEqual(actual) || expected.isBefore(actual))
            && (now.isEqual(actual) || now.isAfter(actual));
    }
}

