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

package io.github.theluca98.creativeheads;

import com.google.gson.Gson;
import io.github.theluca98.creativeheads.command.BrowseCommand;
import io.github.theluca98.creativeheads.command.PlayerHeadCommand;
import io.github.theluca98.creativeheads.command.SearchCommand;
import io.github.theluca98.creativeheads.core.command.MainCommandDispatcher;
import io.github.theluca98.creativeheads.core.gui.InventoryActionsListener;
import io.github.theluca98.creativeheads.repository.LocalRepository;
import io.github.theluca98.creativeheads.util.MainThreadExecutor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.stream.Stream;

@Getter
public final class CreativeHeads extends JavaPlugin {

    public static final Gson GSON = new Gson();

    private final Executor executor = new MainThreadExecutor(this);
    private final LocalRepository repository = new LocalRepository(this);
    private final MainCommandDispatcher commandDispatcher = new MainCommandDispatcher(this);

    @Override
    @SneakyThrows
    public void onEnable() {
        checkServerBrand("spigot", "paper");

        Files.createDirectories(getDataFolder().toPath());
        repository.updateDatabaseAsync().get();

        getCommand("creativeheads").setExecutor(commandDispatcher);
        getCommand("creativeheads").setTabCompleter(commandDispatcher);

        commandDispatcher.register(new SearchCommand(this));
        commandDispatcher.register(new BrowseCommand(this));
        commandDispatcher.register(new PlayerHeadCommand(this));

        registerListener(new InventoryActionsListener(this));
    }

    public Path getFilePath(String fileName) {
        return getDataFolder().toPath().resolve(fileName);
    }

    public boolean giveItem(Player player, ItemStack item) {
        return player.getInventory().addItem(item).isEmpty();
    }

    public void logException(Throwable e) {
        getLogger().log(Level.SEVERE, e.getMessage(), e);
    }

    private void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    private void checkServerBrand(String... supportedBrands) {
        var serverBrand = Bukkit.getServer().getName();
        if (Stream.of(supportedBrands).noneMatch(serverBrand::equalsIgnoreCase)) {
            getLogger().log(Level.WARNING, "This server distribution ({0}) is not officially supported by CreativeHeads", serverBrand);
        }
    }

}
