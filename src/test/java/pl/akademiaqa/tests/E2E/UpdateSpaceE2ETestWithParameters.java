package pl.akademiaqa.tests.E2E;

import io.restassured.path.json.JsonPath;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.akademiaqa.requests.space.CreateSpaceRequest;
import pl.akademiaqa.requests.space.DeleteSpaceRequest;
import pl.akademiaqa.requests.space.UpdateSpaceRequest;

import java.util.stream.Stream;

public class UpdateSpaceE2ETestWithParameters {

  private static final Logger LOGGER = LoggerFactory.getLogger(UpdateSpaceE2ETestWithParameters.class);

  private String spaceId;
  private String newName;

    @ParameterizedTest(name = "Create space with space name:{0}")
    @DisplayName("Crate space with valid space name")
    @MethodSource("createSpaceData")
    void createSpaceTest(String spaceName, String updatedName) {
       spaceId= createSpaceStep(spaceName);
        LOGGER.info("Space id: {}", spaceId);
       newName= updateSpaceStep(updatedName);
        LOGGER.info("Space new name: {}", newName);
        deleteSpaceStep();


    }

    private String createSpaceStep(String spaceName) {
        JSONObject space = new JSONObject();
        space.put("name", spaceName);

        final var response = CreateSpaceRequest.createSpace(space);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo(spaceName);

        return jsonData.getString("id");
    }

    private String updateSpaceStep(String updatedName) {
        JSONObject updatedSpace = new JSONObject();
        updatedSpace.put("name",updatedName);

        final var response = UpdateSpaceRequest.updateSpace(updatedSpace, spaceId);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo(updatedName);
        return  jsonData.getString("name");
    }
    void deleteSpaceStep(){
        final var deleteResponse = DeleteSpaceRequest.deleteSpace(spaceId);
        Assertions.assertThat(deleteResponse.statusCode()).isEqualTo(200);
    }

    private static Stream<Arguments> createSpaceData() {
        return Stream.of(
                Arguments.of("TEST SPACE"),
                Arguments.of("123"),
                Arguments.of("*")
        );
    }


    private static Stream<Arguments> updatedSpaceData() {
        return Stream.of(
                Arguments.of("UPDATED TEST SPACE"),
                Arguments.of("UPDATED 123"),
                Arguments.of("UPDATED *")
        );
    }
}

