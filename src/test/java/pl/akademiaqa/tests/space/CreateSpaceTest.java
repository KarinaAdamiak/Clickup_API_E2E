package pl.akademiaqa.tests.space;


import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import pl.akademiaqa.requests.space.CreateSpaceRequest;
import pl.akademiaqa.requests.space.DeleteSpaceRequest;


class CreateSpaceTest {
//{{ClickUpURL}}/team/9015168523/space
private static final String SPACE_NAME="MY SPACE IN JAVA REST ASSURED";

    @Test
    void createSpaceTest() {
        JSONObject space = new JSONObject();
        space.put("name", SPACE_NAME);

        final var response = CreateSpaceRequest.createSpace(space);

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.jsonPath().getString("name")).isEqualTo(SPACE_NAME);

        final var spaceId = response.jsonPath().getString("id");
        final var deleteResponse = DeleteSpaceRequest.deleteSpace(spaceId);
        Assertions.assertThat(deleteResponse.statusCode()).isEqualTo(200);
    }
}
