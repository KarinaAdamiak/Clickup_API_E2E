package pl.akademiaqa.requests.space;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import pl.akademiaqa.properties.ClickUpProperties;
import pl.akademiaqa.requests.BaseRequest;
import pl.akademiaqa.url.ClickUpUrl;

import static io.restassured.RestAssured.given;

public class CreateSpaceRequest {

    public static Response createSpace(JSONObject space) {
        return given()
                .spec(BaseRequest.requestSpecWithLogs())
                .body(space.toString())
                .when()
                .post(ClickUpUrl.getSpacesUrl(ClickUpProperties.getTeamId()))
                .then()
                .log().ifError()
                .extract()
                .response();
    }
}
