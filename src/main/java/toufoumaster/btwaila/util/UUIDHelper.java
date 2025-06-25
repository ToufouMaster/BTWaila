package toufoumaster.btwaila.util;

import com.b100.json.JsonParser;
import com.b100.json.element.JsonObject;
import com.b100.utils.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// thanks melon!
public class UUIDHelper {

    private static final String url = "https://sessionserver.mojang.com/session/minecraft/profile/";
    private static final JsonParser jsonParser = new JsonParser();
    private static final Map<UUID, String> UUIDtoNameMap = new HashMap<>();

    public static @Nullable String getNameFromUUID(UUID uuid){
        if (uuid == null) return null;

        if(UUIDtoNameMap.containsKey(uuid)){
            return UUIDtoNameMap.get(uuid);
        }

        String string;
        try{
            string = StringUtils.getWebsiteContentAsString(url + uuid);
        }catch (Exception e) {
            System.err.println("Can't connect to Mojang API.");
            e.printStackTrace();
            UUIDtoNameMap.put(uuid, null);
            return null;
        }
        if(string.isEmpty()) {
            System.err.println("UUID [" + uuid + "] doesn't exist!");
            UUIDtoNameMap.put(uuid, null);
            return null;
        }
        String username;
        try {
            JsonObject contentParsed = jsonParser.parse(string);
            username = contentParsed.getString("name");
        }catch (Exception e) {
            e.printStackTrace();
            UUIDtoNameMap.put(uuid, null);
            return null;
        }

        UUIDtoNameMap.put(uuid, username);
        return username;
    }

}
