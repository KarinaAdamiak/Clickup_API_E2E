package pl.akademiaqa.requests.task;

import io.restassured.response.Response;
import org.json.JSONObject;
import pl.akademiaqa.dto.request.CreateTaskRequestDto;
import pl.akademiaqa.dto.request.UpdateTaskRequestDto;
import pl.akademiaqa.dto.response.CreateTaskResponseDto;
import pl.akademiaqa.requests.BaseRequest;
import pl.akademiaqa.url.ClickUpUrl;

import static io.restassured.RestAssured.given;

public class UpdateTaskRequest {
    public static Response updateTask(JSONObject updateTask, String taskId) {
        return  given()
                .spec(BaseRequest.requestSpecWithLogs())
                .body(updateTask.toString())
                .when()
                .put(ClickUpUrl.getTaskUrl(taskId))
                .then()
                .statusCode(200)
                .log().ifError()
                .extract()
                .response();

    }
}