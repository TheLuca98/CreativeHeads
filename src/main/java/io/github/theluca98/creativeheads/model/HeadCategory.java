/*
 * CreativeHeads
 * Copyright (C) 2022 Luca <https://github.com/TheLuca98>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.theluca98.creativeheads.model;

import io.github.theluca98.creativeheads.core.gui.InventoryGUI;
import io.github.theluca98.creativeheads.util.ItemBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum HeadCategory {

    ALPHABET("alphabet", "Alphabet", "51d383401f77beffcb998c2cf79b7afee23f18c41d8a56affed79bb56e2267a3", InventoryGUI.slot(2, 2)),
    ANIMALS("animals", "Animals", "5d6c6eda942f7f5f71c3161c7306f4aed307d82895f9d2b07ab4525718edc5", InventoryGUI.slot(2, 3)),
    BLOCKS("blocks", "Blocks", "2cdc0feb7001e2c10fd5066e501b87e3d64793092b85a50c856d962f8be92c78", InventoryGUI.slot(2, 4)),
    DECORATION("decoration", "Decoration", "d5c6dc2bbf51c36cfc7714585a6a5683ef2b14d47d8ff714654a893f5da622", InventoryGUI.slot(2, 5)),
    FOOD_DRINKS("food-drinks", "Food and drinks", "5fb48e2b969c4c1b86c5f82a2e23799f4a6f31ce009a5f92b39f5b25057b2dd0", InventoryGUI.slot(2, 6)),
    HUMANS("humans", "Humans", "bb8eea7a7d31ea09245d7bd543a932e82b58ef5958dda152fdb3531238437091", InventoryGUI.slot(3, 2)),
    HUMANOID("humanoid", "Humanoid", "89091d79ea0f59ef7ef94d7bba6e5f17f2f7d4572c44f90f76c4819a714", InventoryGUI.slot(3, 3)),
    MISCELLANEOUS("miscellaneous", "Miscellaneous", "4586536df9306cc1ac6f498944b45f8a0be204f3786d9dab7211d5adccbe692", InventoryGUI.slot(3, 4)),
    MONSTERS("monsters", "Monsters", "96c0b36d53fff69a49c7d6f3932f2b0fe948e032226d5e8045ec58408a36e951", InventoryGUI.slot(3, 5)),
    PLANTS("plants", "Plants", "1169b5368c8fb5980f67db596463ee4d7b3828545ab7158de2ac68e47d72d6e", InventoryGUI.slot(3, 6)),
    UNKNOWN(null, "Uncategorized", "89a995928090d842d4afdb2296ffe24f2e944272205ceba848ee4046e01f3168", -1);

    private static final Map<String, HeadCategory> MAP = Stream.of(values())
        .collect(Collectors.toMap(HeadCategory::getId, Function.identity()));

    private final String id;
    private final String displayName;
    private final String textureHash;
    private final int menuSlot;

    public static HeadCategory fromId(String id) {
        return MAP.getOrDefault(id.toLowerCase(), UNKNOWN);
    }

    public String getTextureUrl() {
        return String.format(CustomHead.TEXTURE_URL_FORMAT, textureHash);
    }

    public ItemStack toMenuIcon() {
        return ItemBuilder.of(Material.PLAYER_HEAD)
            .withCustomSkullTexture(getTextureUrl())
            .withName(ChatColor.LIGHT_PURPLE + getDisplayName())
            .withLore(
                "\n",
                ChatColor.AQUA + "\u203A Click to browse"
            )
            .withHiddenAttributes()
            .build();
    }

}
