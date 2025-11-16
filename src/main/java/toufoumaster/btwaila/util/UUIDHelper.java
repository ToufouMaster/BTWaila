package toufoumaster.btwaila.util;

import com.b100.json.JsonParser;
import com.b100.json.element.JsonObject;
import com.b100.utils.StringUtils;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// thanks melon!
public class UUIDHelper {
    private static final String MOJANG_API_URL = "https://sessionserver.mojang.com/session/minecraft/profile/";
    private static final JsonParser JSON_PARSER = new JsonParser();
    private static final Map<@NotNull UUID, @NotNull String> UIID_TO_NAME_MAP = new HashMap<>();
    private static final UUID EMPTY_UUID = new UUID(0, 0);
    private static final String KEY_UNKNOWN_USER = "btwaila.tooltip.username.unknown";
    private static final String KEY_NONE_USER = "btwaila.tooltip.username.none";

    private UUIDHelper() {}

    /**
     * Returns the username of the player with the given UUID,
     * the translation of 'btwaila.tooltip.username.unknown' if it could not be fetched,
     * or the translation of "btwaila.tooltip.username.none" if UUID was 0 or null.
     * @param uuid a player's UUID or null
     * @param world the world or null
     * @return the username or an appropriate translation of "None" or "Unknown" (see the description for specifics)
     */
    public static @NotNull String getNameI18nFromUUID(@Nullable UUID uuid, @Nullable World world) {
        String username = getNameFromUUID(uuid, world);
        I18n i18n = I18n.getInstance();

        if (username == null ) {
            return i18n.translateKey(KEY_NONE_USER);
        }else if (username.isEmpty()) {
            return i18n.translateKey(KEY_UNKNOWN_USER);
        }

        return username;
    }

    /**
     * Returns the username of the player with the given UUID,
     * empty string if UUID could not be fetched,
     * or null if UUID was 0 or null.
     * <br><br>
     * Note:
     * Non-existent players (such as those on offline servers with usernames that have
     * no actual account tied to them) will have a UUID of 0, and because on regular servers UUID 0
     * is effectively invalid, it will be treated as a 'no owner' case.
     * @param uuid a player's UUID or null
     * @param world the world or null
     * @return the username, an empty string, or null (see the description for specifics)
     */
    public static @Nullable String getNameFromUUID(@Nullable UUID uuid, @Nullable World world){
        // Null UUID means there's no owner
        if (uuid == null || EMPTY_UUID.equals(uuid)) return null;

        // 1. Try fetching from map first, this will either return
        //    the username or an empty string if a fetch failed
        //    (or null if it hasn't been mapped)
        String name = UIID_TO_NAME_MAP.get(uuid);
        if (name != null) return name;

        // 2. Try looking for "online" players with the given UUID (if the world isn't null)
        Player player;
        if (world != null && (player = world.getPlayerEntityByUUID(uuid)) != null) {
            name = player.username;
            UIID_TO_NAME_MAP.put(uuid, name);
            return name;
        }

        // 3. Try getting username from Mojang API
        // If this also fails the given UUID will map to an empty string
        name = getUsernameFromAPI(uuid);
        UIID_TO_NAME_MAP.put(uuid, name);

        return name;
    }

    public static void addMapping(@NotNull UUID uuid, @NotNull String username) {
        UIID_TO_NAME_MAP.put(uuid, username);
    }

    private static @NotNull String getUsernameFromAPI(@NotNull UUID uuid) {
        try {
            String content = StringUtils.getWebsiteContentAsString(MOJANG_API_URL + uuid);
            JsonObject contentParsed = JSON_PARSER.parse(content);
            String username = contentParsed.getString("name");
            // Not sure if this can be null, but we'll check it just in case
            return username == null ? "" : username;
        }catch (Exception ignored) {
            return "";
        }
    }

}
