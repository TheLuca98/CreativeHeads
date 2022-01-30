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

package io.github.theluca98.creativeheads.core.gui;

import io.github.theluca98.creativeheads.CreativeHeads;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkElementIndex;

public class InventoryGUI implements InventoryHolder {

    public static final int ROWS = 6;
    public static final int COLUMNS = 9;

    @Getter
    private final CreativeHeads plugin;
    private final Inventory inventory;
    private final Map<Integer, ClickFunction> actions;

    public InventoryGUI(CreativeHeads plugin, String title) {
        this.plugin = plugin;
        this.inventory = Bukkit.createInventory(this, ROWS * COLUMNS, title);
        this.actions = new HashMap<>();
    }

    /**
     * Calculates slot ID based on row and column number (zero-indexed).
     */
    public static int slot(int row, int column) {
        checkElementIndex(row, ROWS, "Invalid row range");
        checkElementIndex(column, COLUMNS, "Invalid column range");
        return (COLUMNS * row) + column;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void addItem(ItemStack item, ClickFunction action) {
        addItem(inventory.firstEmpty(), item, action);
    }

    public void addItem(int slot, ItemStack item, ClickFunction action) {
        checkElementIndex(slot, inventory.getSize(), "Invalid slot");
        inventory.setItem(slot, item);
        actions.put(slot, action);
    }

    public void removeItem(int slot) {
        checkElementIndex(slot, inventory.getSize());
        inventory.clear(slot);
    }

    public void reset() {
        inventory.clear();
        actions.clear();
    }

    public void executeAction(Player player, int slot, ClickType type) {
        if (actions.containsKey(slot)) {
            plugin.getExecutor().execute(() -> {
                if (actions.get(slot).execute(player, type)) {
                    close(player);
                }
            });
        }
    }

    public void open(Player player) {
        player.openInventory(getInventory());
    }

    public void close(Player player) {
        player.closeInventory();
    }

}
