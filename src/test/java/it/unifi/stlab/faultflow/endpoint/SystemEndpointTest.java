package it.unifi.stlab.faultflow.endpoint;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.get;
import static org.junit.Assert.assertEquals;

public class SystemEndpointTest {
    private static JsonObject pollutionMonitorSys_NoSuggestionFailure;
    private static JsonObject pollutionMonitorSys_WrongSuggFail_IdealDesignConcretised;
    private static JsonObject pollutionMonitorSys_WrongSuggFail_PreliminaryDesignConcretised;
    private static JsonObject pollutionMonitorSys_WrongSuggFail_TargetDesignConcretised;
    private static JsonObject simpleSystem;
    private static JsonObject simpleSystem02;
    private static JsonObject steamBoilerSystem;
    private final String SYSTEM_SERVICE_BASE_URL = "http://localhost:8080/faultflow/rest";

    @BeforeClass
    public static void setup() {
        try {
            pollutionMonitorSys_NoSuggestionFailure = parseJSONObject("pollutionmonitor/PollutionMonitorSys_NoSuggestionFailure.JSON");
            pollutionMonitorSys_WrongSuggFail_IdealDesignConcretised = parseJSONObject("pollutionmonitor/PollutionMonitorSys_WrongSuggFail_IdealDesignConcretised.JSON");
            pollutionMonitorSys_WrongSuggFail_PreliminaryDesignConcretised = parseJSONObject("pollutionmonitor/PollutionMonitorSys_WrongSuggFail_PreliminaryDesignConcretised.JSON");
            pollutionMonitorSys_WrongSuggFail_TargetDesignConcretised = parseJSONObject("pollutionmonitor/PollutionMonitorSys_WrongSuggFail_TargetDesignConcretised.JSON");
            simpleSystem = parseJSONObject("simplesystem/SimpleSys.JSON");
            simpleSystem02 = parseJSONObject("simplesystem/SimpleSys02.JSON");
            steamBoilerSystem = parseJSONObject("SteamBoilerSys.JSON");

        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static JsonObject parseJSONObject(String filepath) throws FileNotFoundException {
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(new InputStreamReader(new FileInputStream("./systemJSON/" + filepath), StandardCharsets.UTF_8)).getAsJsonObject();

    }

    @Test
    public void testFaultFlow() {
        Response response = get(SYSTEM_SERVICE_BASE_URL + "/system");
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testSystemService_PollutionMonitorSys_NoSuggestionFailure() {
        Response response = RestAssured.given()
                .baseUri(SYSTEM_SERVICE_BASE_URL)
                .contentType(ContentType.JSON)
                .body(pollutionMonitorSys_NoSuggestionFailure)
                .when()
                .post("/system");
        assertEquals(200, response.getStatusCode());

    }

    @Test
    public void testSystemService_PollutionMonitorSys_WrongSuggFail_IdealDesignConcretised() {
        Response response = RestAssured.given()
                .baseUri(SYSTEM_SERVICE_BASE_URL)
                .contentType(ContentType.JSON)
                .body(pollutionMonitorSys_WrongSuggFail_IdealDesignConcretised)
                .when()
                .post("/system");
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testSystemService_PollutionMonitorSys_WrongSuggFail_PreliminaryDesignConcretised() {
        Response response = RestAssured.given()
                .baseUri(SYSTEM_SERVICE_BASE_URL)
                .contentType(ContentType.JSON)
                .body(pollutionMonitorSys_WrongSuggFail_PreliminaryDesignConcretised)
                .when()
                .post("/system");
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testSystemService_PollutionMonitorSys_WrongSuggFail_TargetDesignConcretised() {
        Response response = RestAssured.given()
                .baseUri(SYSTEM_SERVICE_BASE_URL)
                .contentType(ContentType.JSON)
                .body(pollutionMonitorSys_WrongSuggFail_TargetDesignConcretised)
                .when()
                .post("/system");
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testSystemService_SimpleSystem() {
        Response response = RestAssured.given()
                .baseUri(SYSTEM_SERVICE_BASE_URL)
                .contentType(ContentType.JSON)
                .body(simpleSystem)
                .when()
                .post("/system");
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testSystemService_SimpleSystem02() {
        Response response = RestAssured.given()
                .baseUri(SYSTEM_SERVICE_BASE_URL)
                .contentType(ContentType.JSON)
                .body(simpleSystem02)
                .when()
                .post("/system");
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testSystemService_SteamBoilerSys() {
        Response response = RestAssured.given()
                .baseUri(SYSTEM_SERVICE_BASE_URL)
                .contentType(ContentType.JSON)
                .body(steamBoilerSystem)
                .when()
                .post("/system");
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testSystemService_EmptyBody() {
        Response response = RestAssured.given()
                .baseUri(SYSTEM_SERVICE_BASE_URL)
                .when()
                .post("/system");
        assertEquals(415, response.getStatusCode());
    }
}
