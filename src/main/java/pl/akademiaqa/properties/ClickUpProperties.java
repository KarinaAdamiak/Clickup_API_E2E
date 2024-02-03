package pl.akademiaqa.properties;

import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;

public class ClickUpProperties {

    private static final String TOKEN = "clickup.token";
    private static final String TEAM_ID = "clickup.team.id";

    public static String getToken(){
        return getProperty(TOKEN);
    }
    public static String getTeamId(){
        return getProperty(TEAM_ID);
    }


    private static String getProperty(String key){
        return ResourceBundle.getBundle("clickup").getString(key);

    }
}
