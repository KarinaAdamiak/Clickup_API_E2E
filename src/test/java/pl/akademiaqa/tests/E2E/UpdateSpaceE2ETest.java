package pl.akademiaqa.tests.E2E;

import io.restassured.path.json.JsonPath;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.akademiaqa.requests.space.CreateSpaceRequest;
import pl.akademiaqa.requests.space.DeleteSpaceRequest;
import pl.akademiaqa.requests.space.UpdateSpaceRequest;

public class UpdateSpaceE2ETest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateSpaceE2ETest.class);
    private static final String SPACE_NAME = "MY SPACE E2E TEST WITH UPDATE";
    private String spaceId;

    @Test
    void createSpaceTest() {
        spaceId = createSpaceStep();
        LOGGER.info("Space id: {}", spaceId);

        updateSpaceStep();

        deleteSpaceStep();


    }

    private String createSpaceStep() {
        JSONObject space = new JSONObject();
        space.put("name", SPACE_NAME);

        final var response = CreateSpaceRequest.createSpace(space);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo(SPACE_NAME);

        return jsonData.getString("id");
    }

    void updateSpaceStep() {
        JSONObject updatedSpace = new JSONObject();
        updatedSpace.put("name", "Updated space name");

        final var response = UpdateSpaceRequest.updateSpace(updatedSpace, spaceId);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo("Updated space name");

    }
    void deleteSpaceStep(){
        final var deleteResponse = DeleteSpaceRequest.deleteSpace(spaceId);
        Assertions.assertThat(deleteResponse.statusCode()).isEqualTo(200);
}}
