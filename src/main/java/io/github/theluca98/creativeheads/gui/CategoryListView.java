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

package io.github.theluca98.creativeheads.gui;

import io.github.theluca98.creativeheads.CreativeHeads;
import io.github.theluca98.creativeheads.core.gui.InventoryGUI;
import io.github.theluca98.creativeheads.model.HeadCategory;
import io.github.theluca98.creativeheads.util.Pagination;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class CategoryListView extends InventoryGUI {

    public CategoryListView(CreativeHeads plugin) {
        super(plugin, "Categories");
        for (var category : HeadCategory.values()) {
            if (category.getMenuSlot() >= 0) {
                addItem(category.getMenuSlot(), category.toMenuIcon(), (player, type) -> {
                    browseCategory(player, category);
                    return false;
                });
            }
        }
    }

    private void browseCategory(Player player, HeadCategory category) {
        getPlugin().getRepository().findByCategoryAsync(category)
            .thenApply(heads -> new Pagination<>(heads, HeadListView.PAGE_SIZE))
            .thenAcceptAsync(heads -> {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                new HeadListView(getPlugin(), heads).open(player);
            }, getPlugin().getExecutor()).exceptionally(e -> {
                player.sendMessage(ChatColor.RED + "An internal error occurred, please try again later.");
                getPlugin().logException(e);
                return null;
            });
    }

}
