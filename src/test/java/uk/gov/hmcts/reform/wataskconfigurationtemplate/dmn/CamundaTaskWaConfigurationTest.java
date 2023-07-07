package uk.gov.hmcts.reform.wataskconfigurationtemplate.dmn;

import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.StringUtils;
import uk.gov.hmcts.reform.wataskconfigurationtemplate.DmnDecisionTableBaseUnitTest;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyMap;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.hmcts.reform.wataskconfigurationtemplate.DmnDecisionTable.WA_TASK_CONFIGURATION_ST_CIC_CRIMINALJURIESCOMPENSATION;

@Slf4j
class CamundaTaskWaConfigurationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_CONFIGURATION_ST_CIC_CRIMINALJURIESCOMPENSATION;
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(87));
    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "refusalOfHumanRights, Human rights",
        "refusalOfEu, EEA",
        "deprivation, DoC",
        "protection, Protection",
        "revocationOfProtection, Revocation",
        "NULL_VALUE, ''",
        "'', ''"
    }, nullValues = "NULL_VALUE")
    void when_caseData_then_return_expected_appealType(String appealType, String expectedAppealType) {
        VariableMap inputVariables = new VariableMapImpl();
        Map<String, Object> caseData = new HashMap<>(); // allow null values
        caseData.put("appealType", appealType);
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of("dueDateTime", "2023-01-01T14:00:00.000"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "appealType",
            "value", expectedAppealType,
            "canReconfigure", true
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "caseManagementCategory",
            "value", expectedAppealType,
            "canReconfigure", true
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "priorityDate",
            "value", "2023-01-01T14:00:00.000",
            "canReconfigure", true
        )));
    }

    @Test
    void when_caseData_then_return_expected_case_management_category() {
        String refusalOfEuLabel = "Refusal of application under the EEA regulations";
        String revocationLabel = "Revocation of a protection status";
        List<Map<String, Object>> caseManagementCategories = List.of(
            Map.of(
                "value",
                Map.of("code", "refusalOfHumanRights", "label", "Refusal of a human rights claim"),
                "list_items",
                List.of(Map.of("code", "refusalOfHumanRights", "label", "Refusal of a human rights claim"))
            ),
            Map.of(
                "value", Map.of("code", "refusalOfEu", "label", "Refusal of application under the EEA regulations"),
                "list_items", List.of(Map.of("code", "refusalOfEu", "label", refusalOfEuLabel))
            ),
            Map.of(
                "value", Map.of("code", "deprivation", "label", "Deprivation of citizenship"),
                "list_items", List.of(Map.of("code", "deprivation", "label", "Deprivation of citizenship"))
            ),
            Map.of(
                "value", Map.of("code", "protection", "label", "Refusal of protection claim"),
                "list_items", List.of(Map.of("code", "protection", "label", "Refusal of protection claim"))
            ),
            Map.of(
                "value", Map.of("code", "revocationOfProtection", "label", "Revocation of a protection status"),
                "list_items", List.of(Map.of("code", "revocationOfProtection", "label", revocationLabel))
            )
        );

        List<String> expectedCaseManagementCategories = List.of(
            "Human rights",
            "EEA",
            "DoC",
            "Protection",
            "Revocation"
        );

        for (int i = 0; i < caseManagementCategories.size(); i++) {
            Map<String, Object> caseManagementCategory = caseManagementCategories.get(i);
            Map<String, Object> caseData = new HashMap<>(); // allow null values
            caseData.put("caseManagementCategory", caseManagementCategory);
            VariableMap inputVariables = new VariableMapImpl();
            inputVariables.putValue("caseData", caseData);

            DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

            assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                "name", "caseManagementCategory",
                "value", expectedCaseManagementCategories.get(i),
                "canReconfigure", true
            )));
        }
    }

    @ParameterizedTest
    @MethodSource("nameAndValueScenarioProvider")
    void when_caseData_then_return_expected_name_and_value_rows(Scenario scenario) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", scenario.caseData);
        inputVariables.putValue("taskAttributes", scenario.taskAttributes);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        assertThat(dmnDecisionTableResult.getResultList(), is(getExpectedValues(scenario)));
    }

    private static Stream<Scenario> nameAndValueScenarioProvider() {
        String expectedDueDate = ZonedDateTime.now().plusDays(2)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Scenario givenCaseDataIsMissedThenDefaultToTaylorHouseScenario = Scenario.builder()
            .scenarioName("test1")
            .caseData(emptyMap())
            .expectedCaseNameValue(null)
            .expectedAppealTypeValue("")
            .expectedRegionValue("1")
            .expectedLocationValue("765324")
            .expectedLocationNameValue("Taylor House")
            .expectedCaseManagementCategoryValue("")
            .expectedDescription("")
            .expectedAdditionalPropertiesKey1(null)
            .expectedAdditionalPropertiesKey2(null)
            .expectedAdditionalPropertiesKey3(null)
            .expectedAdditionalPropertiesKey4(null)
            .expectedPriorityDate("")
            .expectedMinorPriority("500")
            .expectedMajorPriority("5000")
            .expectedNextHearingId("")
            .expectedNextHearingDate("")
            .expectedDueDate(null)
            .expectedDueDateTime(null)
            .build();
        String refusalOfEuLabel = "Refusal of a human rights claim";
        String nextHearingDate = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ"));
        Scenario givenCaseDataIsPresentThenReturnNameAndValueScenario = Scenario.builder()
            .scenarioName("test2")
            .caseData(Map.of(
                "appealType", "refusalOfHumanRights",
                "appellantGivenNames", "some appellant given names",
                "appellantFamilyName", "some appellant family name",
                "caseManagementLocation", Map.of(
                    "region", "some other region",
                    "baseLocation", "some other location"
                ),
                "staffLocation", "some other location name",
                "caseManagementCategory", Map.of(
                    "value", Map.of("code", "refusalOfHumanRights", "label", "Refusal of a human rights claim"),
                    "list_items", List.of(Map.of("code", "refusalOfHumanRights", "label", refusalOfEuLabel))
                ),
                "nextHearingId", "next Hearing Id",
                "nextHearingDate", nextHearingDate
            ))
            .taskAttributes(Map.of("taskType", "processApplication"))
            .expectedCaseNameValue("some appellant given names some appellant family name")
            .expectedAppealTypeValue("Human rights")
            .expectedRegionValue("some other region")
            .expectedLocationValue("some other location")
            .expectedLocationNameValue("some other location name")
            .expectedCaseManagementCategoryValue("Human rights")
            .expectedWorkType("hearing_work")
            .expectedRoleCategory("LEGAL_OPERATIONS")
            .expectedDescription("[Decide an application]"
                                     + "(/case/WA/WaCaseType/${[CASE_REFERENCE]}/trigger/decideAnApplication)")
            .expectedAdditionalPropertiesKey1("value1")
            .expectedAdditionalPropertiesKey2("value2")
            .expectedAdditionalPropertiesKey3("value3")
            .expectedAdditionalPropertiesKey4("value4")
            .expectedPriorityDate(nextHearingDate)
            .expectedMinorPriority("500")
            .expectedMajorPriority("5000")
            .expectedNextHearingId("next Hearing Id")
            .expectedNextHearingDate(nextHearingDate)
            .build();

        Scenario givenDueDateAndTimeScenario = Scenario.builder()
            .scenarioName("test4")
            .caseData(emptyMap())
            .taskAttributes(Map.of("taskType", "followUpOverdue"))
            .expectedCaseNameValue(null)
            .expectedAppealTypeValue("")
            .expectedRegionValue("1")
            .expectedLocationValue("765324")
            .expectedLocationNameValue("Taylor House")
            .expectedCaseManagementCategoryValue("")
            .expectedDescription("")
            .expectedPriorityDate("")
            .expectedMinorPriority("500")
            .expectedMajorPriority("5000")
            .expectedNextHearingId("")
            .expectedNextHearingDate("")
            .expectedAdditionalPropertiesKey1("value1")
            .expectedAdditionalPropertiesKey2("value2")
            .expectedAdditionalPropertiesKey3("value3")
            .expectedAdditionalPropertiesKey4("value4")
            .expectedDueDate(expectedDueDate + "T00:00")
            .expectedDueDateTime("16:00")
            .build();

        Scenario givenTaskAttributesForAdditionalPropertiesThenReturnNameAndValueScenario = Scenario.builder()
            .scenarioName("test3")
            .caseData(Map.of(
                "appealType", "refusalOfHumanRights",
                "appellantGivenNames", "some appellant given names",
                "appellantFamilyName", "some appellant family name",
                "caseManagementLocation", Map.of(
                    "region", "some other region",
                    "baseLocation", "some other location"
                ),
                "staffLocation", "some other location name",
                "caseManagementCategory", Map.of(
                    "value", Map.of("code", "refusalOfHumanRights", "label", "Refusal of a human rights claim"),
                    "list_items", List.of(Map.of("code", "refusalOfHumanRights", "label", refusalOfEuLabel))
                ),
                "nextHearingId", "next Hearing Id",
                "nextHearingDate", nextHearingDate
            ))
            .taskAttributes(Map.of("taskType", "processApplication",
                                   "key1", "someValue1",
                                   "key2", "someValue2",
                                   "key3", "someValue3",
                                   "key4", "someValue4"
            ))
            .expectedCaseNameValue("some appellant given names some appellant family name")
            .expectedAppealTypeValue("Human rights")
            .expectedRegionValue("some other region")
            .expectedLocationValue("some other location")
            .expectedLocationNameValue("some other location name")
            .expectedCaseManagementCategoryValue("Human rights")
            .expectedWorkType("hearing_work")
            .expectedRoleCategory("LEGAL_OPERATIONS")
            .expectedDescription("[Decide an application]"
                                     + "(/case/WA/WaCaseType/${[CASE_REFERENCE]}/trigger/decideAnApplication)")
            .expectedAdditionalPropertiesKey1("someValue1")
            .expectedAdditionalPropertiesKey2("someValue2")
            .expectedAdditionalPropertiesKey3("someValue3")
            .expectedAdditionalPropertiesKey4("someValue4")
            .expectedPriorityDate(nextHearingDate)
            .expectedMinorPriority("500")
            .expectedMajorPriority("5000")
            .expectedNextHearingId("next Hearing Id")
            .expectedNextHearingDate(nextHearingDate)
            .expectedDueDate(null)
            .expectedDueDateTime(null)
            .build();

        Scenario givenDueDateOriginScenario = Scenario.builder()
            .scenarioName("calculateDueDate")
            .caseData(Map.of(
            ))
            .taskAttributes(Map.of("taskType", "calculateDueDate"))
            .expectedCaseNameValue(null)
            .expectedAppealTypeValue("")
            .expectedRegionValue("1")
            .expectedLocationValue("765324")
            .expectedLocationNameValue("Taylor House")
            .expectedCaseManagementCategoryValue("")
            .expectedDescription("")
            .expectedPriorityDate("")
            .expectedMinorPriority("500")
            .expectedMajorPriority("5000")
            .expectedNextHearingId("")
            .expectedNextHearingDate("")
            .expectedDueDateTime("20:00")
            .expectedDueDateOrigin("2022-10-13T18:00")
            .expectedDueDateIntervalDays("8")
            .expectedDueDateNonWorkingCalendar("https://www.gov.uk/bank-holidays/england-and-wales.json")
            .expectedDueDateNonWorkingDaysOfWeek("SATURDAY,SUNDAY")
            .expectedDueDateSkipNonWorkingDays("true")
            .expectedDueDateMustBeWorkingDay("Next")
            .build();


        return Stream.of(
            givenCaseDataIsMissedThenDefaultToTaylorHouseScenario,
            givenCaseDataIsPresentThenReturnNameAndValueScenario,
            givenTaskAttributesForAdditionalPropertiesThenReturnNameAndValueScenario,
            givenDueDateAndTimeScenario,
            givenDueDateOriginScenario
        );
    }

    @Test
    void when_casaDate_hearing_date_then_return_expected_priority_date() {
        Map<String, Object> caseData = new HashMap<>(); // allow null values
        caseData.put("appealType", "refusalOfHumanRights");
        caseData.put("nextHearingDate", "2023-01-01");
        caseData.put("urgent", "Yes");
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of("dueDateTime", "2023-01-01T14:00:00.000"));


        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "priorityDate",
            "value", "2023-01-01",
            "canReconfigure", true
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "minorPriority",
            "value", "500",
            "canReconfigure", true
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "majorPriority",
            "value", "1000",
            "canReconfigure", true
        )));
    }

    @Test
    void when_no_casaDate_hearingDate_then_return_expected_priority_Date() {
        VariableMap inputVariables = new VariableMapImpl();
        Map<String, Object> caseData = new HashMap<>(); // allow null values
        caseData.put("appealType", "refusalOfHumanRights");
        caseData.put("urgent", "No");
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of("dueDateTime", "2023-01-01T14:00:00.000"));


        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "priorityDate",
            "value", "2023-01-01T14:00:00.000",
            "canReconfigure", true
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "minorPriority",
            "value", "500",
            "canReconfigure", true
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "majorPriority",
            "value", "5000",
            "canReconfigure", true
        )));
    }

    @Test
    void when_no_casaDate_urgent_then_return_expected_major_priority() {
        VariableMap inputVariables = new VariableMapImpl();
        Map<String, Object> caseData = new HashMap<>(); // allow null values
        caseData.put("appealType", "refusalOfHumanRights");
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of("dueDateTime", "2023-01-01T14:00:00.000"));


        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "priorityDate",
            "value", "2023-01-01T14:00:00.000",
            "canReconfigure", true
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "minorPriority",
            "value", "500",
            "canReconfigure", true
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "majorPriority",
            "value", "5000",
            "canReconfigure", true
        )));
    }

    @ParameterizedTest
    @CsvSource({
        "reviewSpecificAccessRequestJudiciary", "reviewSpecificAccessRequestLegalOps",
        "reviewSpecificAccessRequestAdmin"
    })
    void when_given_task_type_then_return_review_specific_access_requests(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("taskAttributes", Map.of("taskId", "1234",
                                                         "taskType", taskType
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "workType",
            "value", "access_requests",
            "canReconfigure", true
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "processApplication", "reviewSpecificAccessRequestLegalOps"
    })
    void when_given_task_type_then_return_Legal_Operations(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue(
            "taskAttributes",
            Map.of("taskId", "1234",
                   "taskType", taskType
            )
        );

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "LEGAL_OPERATIONS",
            "canReconfigure", true
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "reviewSpecificAccessRequestJudiciary", "reviewSpecificAccessRequestLegalOps",
        "reviewSpecificAccessRequestAdmin"
    })
    void should_return_request_value_when_role_assignment_id_exists_in_task_attributes(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();

        String roleAssignmentId = UUID.randomUUID().toString();
        inputVariables.putValue("taskAttributes", Map.of(
                                    "taskId", "1234",
                                    "taskType", taskType,
                                    "additionalProperties", Map.of("roleAssignmentId", roleAssignmentId)
                                )
        );

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> dmnResults = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("additionalProperties_roleAssignmentId"))
            .collect(Collectors.toList());

        assertThat(dmnResults.size(), is(1));

        assertTrue(dmnResults.contains(Map.of(
            "name", "additionalProperties_roleAssignmentId",
            "value", roleAssignmentId,
            "canReconfigure", true
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    @ParameterizedTest
    @CsvSource({
        "reviewSpecificAccessRequestJudiciary", "reviewSpecificAccessRequestLegalOps",
        "reviewSpecificAccessRequestAdmin"
    })
    void should_return_default_value_when_additional_properties_is_empty(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("taskAttributes", Map.of(
                                    "taskId", "1234",
                                    "taskType", taskType,
                                    "additionalProperties", Map.of()
                                )
        );

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> dmnResults = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("additionalProperties_roleAssignmentId"))
            .collect(Collectors.toList());

        assertThat(dmnResults.size(), is(1));

        assertTrue(dmnResults.contains(Map.of(
            "name", "additionalProperties_roleAssignmentId",
            "value", "roleAssignmentId",
            "canReconfigure", true
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }


    @ParameterizedTest
    @CsvSource({
        "reviewSpecificAccessRequestJudiciary", "reviewSpecificAccessRequestLegalOps",
        "reviewSpecificAccessRequestAdmin"
    })
    void should_return_dmn_value_when_role_assignment_id_is_not_exist_in_task_attributes(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("taskAttributes", Map.of("taskId", "1234",
                                                         "taskType", taskType
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> dmnResults = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("additionalProperties_roleAssignmentId"))
            .collect(Collectors.toList());

        assertThat(dmnResults.size(), is(1));

        assertTrue(dmnResults.contains(Map.of(
            "name", "additionalProperties_roleAssignmentId",
            "value", "roleAssignmentId",
            "canReconfigure", true
        )));

        assertDescriptionField(taskType, dmnDecisionTableResult);
    }

    private void assertDescriptionField(String taskType, DmnDecisionTableResult dmnDecisionTableResult) {
        if ("reviewSpecificAccessRequestLegalOps".equals(taskType)) {
            List<Map<String, Object>> descriptionResultList = dmnDecisionTableResult.getResultList().stream()
                .filter((r) -> r.containsValue("description"))
                .toList();
            assertThat(descriptionResultList.size(), is(1));
            assertTrue(descriptionResultList.contains(Map.of(
                "name", "description",
                "value", "1234",
                "canReconfigure", true
            )));
        }
    }

    @Test
    void when_given_task_type_then_return_admin_role_category() {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("taskAttributes", Map.of("taskType", "reviewSpecificAccessRequestAdmin"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "ADMIN",
            "canReconfigure", true
        )));
    }

    @Test
    void when_given_task_type_then_return_judicial_role_category() {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("taskAttributes", Map.of("taskType", "reviewSpecificAccessRequestJudiciary"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "JUDICIAL",
            "canReconfigure", true
        )));
    }

    @Test
    void when_taskId_is_review_appeal_skeleton_argument_then_return_correct_values() {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("taskAttributes", Map.of("taskType", "reviewAppealSkeletonArgument"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "workType",
            "value", "hearing_work",
            "canReconfigure", true
        )));

        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());

        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "CTSC",
            "canReconfigure", true
        )));

    }

    @Test
    void when_taskId_is_inspect_appeal_then_return_correct_values() {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("taskAttributes", Map.of("taskType", "inspectAppeal"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "workType",
            "value", "decision_making_work",
            "canReconfigure", true
        )));

        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());

        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "LEGAL_OPERATIONS",
            "canReconfigure", true
        )));

    }

    @Test
    void when_taskId_is_first_task_then_return_correct_values() {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("taskAttributes", Map.of("taskType", "firstTask"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "workType",
            "value", "hearing_work",
            "canReconfigure", true
        )));

    }

    @Test
    void when_taskId_is_second_task_then_return_correct_values() {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("taskAttributes", Map.of("taskType", "secondTask"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "ADMIN",
            "canReconfigure", true
        )));

    }

    @Test
    void when_taskId_is_decide_on_time_extension_then_return_correct_values() {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("taskAttributes", Map.of("taskType", "decideOnTimeExtension"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "workType",
            "value", "decision_making_work",
            "canReconfigure", true
        )));

        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());

        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "LEGAL_OPERATIONS",
            "canReconfigure", true
        )));

    }

    @Test
    void when_taskId_is_functional_test_task2_extension_then_return_correct_values() {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("taskAttributes", Map.of("taskType", "functionalTestTask2"));
        inputVariables.putValue("caseData", Map.of("nextHearingDate", "2022-12-12T16:00"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(getMatchingOutput(dmnDecisionTableResult, "calculatedDates").contains(Map.of(
            "name", "calculatedDates",
            "value", "nextHearingDate,hearingPreDate,dueDate,priorityDate"
        )));

        assertTrue(getMatchingOutput(dmnDecisionTableResult, "hearingPreDateOriginRef").contains(Map.of(
            "name", "hearingPreDateOriginRef",
            "value", "nextHearingDate"
        )));
        assertTrue(getMatchingOutput(dmnDecisionTableResult, "hearingPreDateIntervalDays").contains(Map.of(
            "name", "hearingPreDateIntervalDays",
            "value", "-5"
        )));
        assertTrue(getMatchingOutput(dmnDecisionTableResult, "hearingPreDateSkipNonWorkingDays").contains(Map.of(
            "name", "hearingPreDateSkipNonWorkingDays",
            "value", "false"
        )));
        assertTrue(getMatchingOutput(dmnDecisionTableResult, "dueDateOrigin").contains(Map.of(
            "name", "dueDateOrigin",
            "value", "2022-12-23T18:00"
        )));
        assertTrue(getMatchingOutput(dmnDecisionTableResult, "dueDateIntervalDays").contains(Map.of(
            "name", "dueDateIntervalDays",
            "value", "5"
        )));
        assertTrue(getMatchingOutput(dmnDecisionTableResult, "dueDateNonWorkingDaysOfWeek").contains(Map.of(
            "name", "dueDateNonWorkingDaysOfWeek",
            "value", "SATURDAY,SUNDAY"
        )));
        assertTrue(getMatchingOutput(dmnDecisionTableResult, "dueDateMustBeWorkingDay").contains(Map.of(
            "name", "dueDateMustBeWorkingDay",
            "value", "Next"
        )));
        assertTrue(getMatchingOutput(dmnDecisionTableResult, "priorityDateOriginEarliest").contains(Map.of(
            "name", "priorityDateOriginEarliest",
            "value", "hearingPreDate,dueDate"
        )));
        assertTrue(getMatchingOutput(dmnDecisionTableResult, "nextHearingDateOrigin").contains(Map.of(
            "name", "nextHearingDateOrigin",
            "value", "2022-12-12T16:00"
        )));

    }

    @Test
    void when_taskId_is_endToEndTask_extension_then_return_correct_values() {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("taskAttributes", Map.of("taskType", "endToEndTask"));
        inputVariables.putValue("caseData", Map.of("nextHearingDate", "2022-12-12T16:00"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(getMatchingOutput(dmnDecisionTableResult, "calculatedDates").contains(Map.of(
            "name", "calculatedDates",
            "value", "nextHearingDate,nextHearingDateLine,dueDate,priorityDate"
        )));

        assertTrue(getMatchingOutput(dmnDecisionTableResult, "nextHearingDateLineOriginRef").contains(Map.of(
            "name", "nextHearingDateLineOriginRef",
            "value", "nextHearingDate"
        )));
        assertTrue(getMatchingOutput(dmnDecisionTableResult, "nextHearingDateLineIntervalDays").contains(Map.of(
            "name", "nextHearingDateLineIntervalDays",
            "value", "-5"
        )));
        assertTrue(getMatchingOutput(dmnDecisionTableResult, "nextHearingDateLineNonWorkingDaysOfWeek").contains(Map.of(
            "name", "nextHearingDateLineNonWorkingDaysOfWeek",
            "value", "SATURDAY,SUNDAY"
        )));
        assertTrue(getMatchingOutput(dmnDecisionTableResult, "nextHearingDateLineSkipNonWorkingDays").contains(Map.of(
            "name", "nextHearingDateLineSkipNonWorkingDays",
            "value", "true"
        )));
        assertTrue(getMatchingOutput(dmnDecisionTableResult, "nextHearingDateLineNextWorkingDay").contains(Map.of(
            "name", "nextHearingDateLineNextWorkingDay",
            "value", "Previous"
        )));
        assertTrue(getMatchingOutput(dmnDecisionTableResult, "dueDateOrigin").contains(Map.of(
            "name", "dueDateOrigin",
            "value", "2022-12-01T18:00"
        )));
        assertTrue(getMatchingOutput(dmnDecisionTableResult, "dueDateIntervalDays").contains(Map.of(
            "name", "dueDateIntervalDays",
            "value", "5"
        )));
        assertTrue(getMatchingOutput(dmnDecisionTableResult, "dueDateNonWorkingDaysOfWeek").contains(Map.of(
            "name", "dueDateNonWorkingDaysOfWeek",
            "value", "SATURDAY,SUNDAY"
        )));
        assertTrue(getMatchingOutput(dmnDecisionTableResult, "dueDateMustBeWorkingDay").contains(Map.of(
            "name", "dueDateMustBeWorkingDay",
            "value", "Next"
        )));
        assertTrue(getMatchingOutput(dmnDecisionTableResult, "priorityDateOriginEarliest").contains(Map.of(
            "name", "priorityDateOriginEarliest",
            "value", "nextHearingDateLine,dueDate"
        )));

    }

    @Test
    void when_taskId_is_functionalTestTask1_then_return_correct_values() {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("taskAttributes", Map.of("taskType", "functionalTestTask1"));
        inputVariables.putValue("caseData", Map.of("nextHearingDate", "2022-12-12T16:00"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(getMatchingOutput(dmnDecisionTableResult, "dueDateOrigin").contains(Map.of(
            "name", "dueDateOrigin",
            "value", "2022-12-23T18:00"
        )));
        assertTrue(getMatchingOutput(dmnDecisionTableResult, "dueDateIntervalDays").contains(Map.of(
            "name", "dueDateIntervalDays",
            "value", "14"
        )));
        assertTrue(getMatchingOutput(dmnDecisionTableResult, "dueDateNonWorkingDaysOfWeek").contains(Map.of(
            "name", "dueDateNonWorkingDaysOfWeek",
            "value", "SATURDAY,SUNDAY"
        )));
        assertTrue(getMatchingOutput(dmnDecisionTableResult, "dueDateMustBeWorkingDay").contains(Map.of(
            "name", "dueDateMustBeWorkingDay",
            "value", "Next"
        )));
        assertTrue(getMatchingOutput(dmnDecisionTableResult, "dueDateSkipNonWorkingDays").contains(Map.of(
            "name", "dueDateSkipNonWorkingDays",
            "value", "true"
        )));
        assertTrue(getMatchingOutput(dmnDecisionTableResult, "priorityDateOriginEarliest").contains(Map.of(
            "name", "priorityDateOriginEarliest",
            "value", "nextHearingDate,dueDate",
            "canReconfigure", true
        )));

    }

    private List<Map<String, Object>> getMatchingOutput(DmnDecisionTableResult dmnDecisionTableResult, String key) {
        List<Map<String, Object>> output = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue(key)).toList();
        log.info("output value: {}", output);
        return output;
    }

    @Value
    @Builder
    private static class Scenario {

        String scenarioName;
        Map<String, Object> caseData;
        Map<String, Object> taskAttributes;
        String expectedCaseNameValue;
        String expectedAppealTypeValue;
        String expectedRegionValue;
        String expectedLocationValue;
        String expectedLocationNameValue;
        String expectedCaseManagementCategoryValue;
        String expectedWorkType;
        String expectedRoleCategory;
        String expectedDescription;
        String expectedAdditionalPropertiesKey1;
        String expectedAdditionalPropertiesKey2;
        String expectedAdditionalPropertiesKey3;
        String expectedAdditionalPropertiesKey4;
        String expectedPriorityDate;
        String expectedMinorPriority;
        String expectedMajorPriority;
        String expectedNextHearingId;
        String expectedNextHearingDate;
        String expectedDueDate;
        String expectedDueDateTime;
        String expectedDueDateOrigin;
        String expectedDueDateIntervalDays;
        String expectedDueDateNonWorkingCalendar;
        String expectedDueDateNonWorkingDaysOfWeek;
        String expectedDueDateSkipNonWorkingDays;
        String expectedDueDateMustBeWorkingDay;
    }

    private List<Map<String, Object>> getExpectedValues(Scenario scenario) {
        List<Map<String, Object>> rules = new ArrayList<>();

        getExpectedValue(rules, "caseName", scenario.getExpectedCaseNameValue());
        getExpectedValue(rules, "appealType", scenario.getExpectedAppealTypeValue());
        getExpectedValue(rules, "region", scenario.getExpectedRegionValue());
        getExpectedValue(rules, "location", scenario.getExpectedLocationValue());
        getExpectedValue(rules, "locationName", scenario.getExpectedLocationNameValue());
        getExpectedValue(rules, "caseManagementCategory", scenario.getExpectedCaseManagementCategoryValue());

        if (!Objects.isNull(scenario.getTaskAttributes())
            && StringUtils.isNotBlank(scenario.taskAttributes.get("taskType").toString())) {
            Optional.ofNullable(scenario.getExpectedWorkType())
                .ifPresent(key -> getExpectedValue(rules, "workType", key));

            Optional.ofNullable(scenario.getExpectedRoleCategory())
                .ifPresent(key -> getExpectedValue(rules, "roleCategory", key));
        }
        getExpectedValue(rules, "description", scenario.getExpectedDescription());

        Optional.ofNullable(scenario.getExpectedAdditionalPropertiesKey1())
            .ifPresent(key -> getExpectedValue(rules, "additionalProperties_key1", key));
        Optional.ofNullable(scenario.getExpectedAdditionalPropertiesKey2())
            .ifPresent(key -> getExpectedValue(rules, "additionalProperties_key2", key));
        Optional.ofNullable(scenario.getExpectedAdditionalPropertiesKey3())
            .ifPresent(key -> getExpectedValue(rules, "additionalProperties_key3", key));
        Optional.ofNullable(scenario.getExpectedAdditionalPropertiesKey4())
            .ifPresent(key -> getExpectedValue(rules, "additionalProperties_key4", key));

        getExpectedValue(rules, "priorityDate", scenario.getExpectedPriorityDate());
        getExpectedValue(rules, "minorPriority", scenario.getExpectedMinorPriority());
        getExpectedValue(rules, "majorPriority", scenario.getExpectedMajorPriority());

        getExpectedValue(rules, "nextHearingId", scenario.getExpectedNextHearingId());
        getExpectedValue(rules, "nextHearingDate", scenario.getExpectedNextHearingDate());

        Optional.ofNullable(scenario.getExpectedDueDate())
            .ifPresent(key -> getExpectedValue(rules, "dueDate", key));

        Optional.ofNullable(scenario.getExpectedDueDateTime())
            .ifPresent(key -> getExpectedValue(rules, "dueDateTime", key));

        Optional.ofNullable(scenario.getExpectedDueDateOrigin())
            .ifPresent(key -> getExpectedValue(rules, "dueDateOrigin", key));
        Optional.ofNullable(scenario.getExpectedDueDateIntervalDays())
            .ifPresent(key -> getExpectedValue(rules, "dueDateIntervalDays", key));
        Optional.ofNullable(scenario.getExpectedDueDateNonWorkingCalendar())
            .ifPresent(key -> getExpectedValue(rules, "dueDateNonWorkingCalendar", key));
        Optional.ofNullable(scenario.getExpectedDueDateNonWorkingDaysOfWeek())
            .ifPresent(key -> getExpectedValue(rules, "dueDateNonWorkingDaysOfWeek", key));
        Optional.ofNullable(scenario.getExpectedDueDateSkipNonWorkingDays())
            .ifPresent(key -> getExpectedValue(rules, "dueDateSkipNonWorkingDays", key));
        Optional.ofNullable(scenario.getExpectedDueDateMustBeWorkingDay())
            .ifPresent(key -> getExpectedValue(rules, "dueDateMustBeWorkingDay", key));


        return rules;
    }

    private void getExpectedValue(List<Map<String, Object>> rules, String name, Object value) {
        Map<String, Object> rule = new HashMap<>();
        rule.put("name", name);
        rule.put("value", value);
        rule.put("canReconfigure", true);
        rules.add(rule);
    }

}

