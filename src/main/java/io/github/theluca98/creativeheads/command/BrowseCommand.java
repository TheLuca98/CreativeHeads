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

package io.github.theluca98.creativeheads.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.github.theluca98.creativeheads.CreativeHeads;
import io.github.theluca98.creativeheads.core.command.Subcommand;
import io.github.theluca98.creativeheads.gui.CategoryListView;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BrowseCommand extends Subcommand {

    private final CreativeHeads plugin;

    public BrowseCommand(CreativeHeads plugin) {
        this.plugin = plugin;
    }

    @Override
    public LiteralArgumentBuilder<CommandSender> node() {
        return literal("browse")
            .executes(this);
    }

    @Override
    public void onCommand(Player sender, CommandContext<CommandSender> ctx) {
        new CategoryListView(plugin).open(sender);
    }

}
