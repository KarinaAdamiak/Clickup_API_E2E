package pl.akademiaqa.tests.E2E;

import io.restassured.path.json.JsonPath;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.akademiaqa.dto.request.CreateTaskRequestDto;
import pl.akademiaqa.requests.list.CreateListRequest;
import pl.akademiaqa.requests.space.CreateSpaceRequest;
import pl.akademiaqa.requests.space.DeleteSpaceRequest;
import pl.akademiaqa.requests.task.CreateTaskRequest;
import pl.akademiaqa.requests.task.UpdateTaskRequest;

class UpdateTaskE2ETest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateTaskE2ETest.class);
    private static String spaceName = "SPACE E2E";
    private static String listName = "ZADANIA";
    private static String taskName = "Przetestować clickup";

    private String spaceId;
    private String listId;
    private String taskId;

    @Test
    void updateTaskE2ETest() {
        spaceId = createSpaceStep();
        LOGGER.info("Space created with id: {}", spaceId);

        listId = createListStep();
        LOGGER.info("List created with id: {}", listId);

        taskId = createTaskStep();
        LOGGER.info("Task created with id: {}", taskId);

        updateTaskStep();
        closeTaskStep();
        deleteSpace();
    }


    private String createSpaceStep() {
        JSONObject json = new JSONObject();
        json.put("name", spaceName);

        final var response = CreateSpaceRequest.createSpace(json);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo(spaceName);
        return jsonData.getString("id");

    }

    private String createListStep() {
        JSONObject json = new JSONObject();
        json.put("name", listName);

        final var response = CreateListRequest.createList(json, spaceId);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo(listName);

        return jsonData.getString("id");
    }

    private String createTaskStep() {
//        JSONObject task = new JSONObject();
//        task.put("name",taskName);
//        task.put("description","Ciekawe jak to działa");
//        task.put("status", "to do");
//        task.put("priority",JSONObject.NULL);
//        task.put("assignees",JSONObject.NULL);

        CreateTaskRequestDto taskDto = new CreateTaskRequestDto();
        taskDto.setName(taskName);
        taskDto.setDescription("Ciekawe jak to działa");
        taskDto.setStatus("to do");
        taskDto.setPriority(null);
        taskDto.setPriority(null);


        final var response = CreateTaskRequest.createTask(taskDto, listId);

        LOGGER.info("CREATE TASK RESPONSE: {}" + response);
        Assertions.assertThat(response.getName()).isEqualTo(taskName);
        Assertions.assertThat(response.getDescription()).isEqualTo("Ciekawe jak to działa");


//DO SERIALIZACJI
//      Assertions.assertThat(response.statusCode()).isEqualTo(200);
//
//      JsonPath jsonData = response.jsonPath();
//      Assertions.assertThat(jsonData.getString("name")).isEqualTo(taskName);
//      Assertions.assertThat(jsonData.getString("description")).isEqualTo("Ciekawe jak to działa");
//      return jsonData.getString("id");
        return response.getId();
    }

    private void updateTaskStep() {
        JSONObject updateTask = new JSONObject();
        updateTask.put("name", "Zmieniam nazwę zadania");
        updateTask.put("description", "Zmieniam opis zadania");

        final var response = UpdateTaskRequest.updateTask(updateTask, taskId);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo("Zmieniam nazwę zadania");
        Assertions.assertThat(jsonData.getString("description")).isEqualTo("Zmieniam opis zadania");

    }

    private void closeTaskStep() {
        JSONObject closeTask = new JSONObject();
        closeTask.put("status", "complete");
        final var response = UpdateTaskRequest.updateTask(closeTask, taskId);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("status.status")).isEqualTo("complete");
//status.status=> ponieważ mamy obiekt z małym obiektem w środku:)
    }

    private void deleteSpace() {
        final var response = DeleteSpaceRequest.deleteSpace(spaceId);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }
}
