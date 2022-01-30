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
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

@RequiredArgsConstructor
public class InventoryActionsListener implements Listener {

    private final CreativeHeads plugin;

    @EventHandler
    private void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof InventoryGUI) {
            e.setResult(Event.Result.DENY);
        }
        if (e.getClickedInventory() != null && e.getClickedInventory().getHolder() instanceof InventoryGUI) {
            var gui = (InventoryGUI) e.getClickedInventory().getHolder();
            var player = (Player) e.getWhoClicked();
            gui.executeAction(player, e.getSlot(), e.getClick());
        }
    }

    @EventHandler
    private void onInventoryInteract(InventoryInteractEvent e) {
        if (e.getInventory().getHolder() instanceof InventoryGUI) {
            e.setResult(Event.Result.DENY);
        }
    }

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof InventoryGUI) {
            updateInventory((Player) e.getPlayer());
        }
    }

    private void updateInventory(Player player) {
        plugin.getExecutor().execute(player::updateInventory);
    }

}

