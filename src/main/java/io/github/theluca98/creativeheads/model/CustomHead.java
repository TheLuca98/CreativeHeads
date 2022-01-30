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

import io.github.theluca98.creativeheads.util.ItemBuilder;
import io.github.theluca98.creativeheads.util.ListHandler;
import lombok.Value;
import org.apache.commons.csv.CSVRecord;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Value
public class CustomHead {

    public static final String TEXTURE_URL_FORMAT = "https://textures.minecraft.net/texture/%s";

    public static final ListHandler<CustomHead> HANDLER = ListHandler.of(rs -> new CustomHead(
        rs.getString("name"),
        rs.getString("category"),
        rs.getString("hash"),
        rs.getString("tags")
    ));

    String name;
    String category;
    String hash;
    String tags;

    public static CustomHead fromCSV(CSVRecord record) {
        return new CustomHead(
            record.get("name"),
            record.get("category"),
            record.get("hash"),
            record.get("tags")
        );
    }

    public String getTextureUrl() {
        return String.format(TEXTURE_URL_FORMAT, hash);
    }

    public String[] toRowArray() {
        return new String[]{name, category, hash, tags};
    }

    public ItemStack toItem() {
        return ItemBuilder.of(Material.PLAYER_HEAD)
            .withCustomSkullTexture(getTextureUrl())
            .withName(ChatColor.YELLOW + name)
            .build();
    }

    public ItemStack toMenuIcon() {
        return ItemBuilder.of(Material.PLAYER_HEAD)
            .withCustomSkullTexture(getTextureUrl())
            .withName(ChatColor.YELLOW + name)
            .withLore(
                ChatColor.DARK_GRAY + HeadCategory.fromId(category).getDisplayName(),
                "\n",
                ChatColor.AQUA + "\u203A Click to get",
                ChatColor.DARK_AQUA + "\u203A Right-click to get a stack"
            )
            .build();
    }

}
