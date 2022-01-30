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

package io.github.theluca98.creativeheads.util;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.ProfileLookupCallback;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

import java.net.Proxy;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

/**
 * Currently unused.
 */
public class MojangClient {

    private final GameProfileRepository repository;

    private MojangClient(GameProfileRepository repository) {
        this.repository = repository;
    }

    public static MojangClient fromNewRepository() {
        return new MojangClient(new YggdrasilAuthenticationService(Proxy.NO_PROXY).createProfileRepository());
    }

    public static MojangClient fromExistingRepository(GameProfileRepository repository) {
        return new MojangClient(repository);
    }

    public CompletableFuture<GameProfile> fetchProfile(String name) {
        var future = new CompletableFuture<GameProfile>();
        ForkJoinPool.commonPool().execute(() -> fetchProfileInternal(future, name));
        return future;
    }

    private void fetchProfileInternal(CompletableFuture<GameProfile> future, String name) {
        repository.findProfilesByNames(new String[]{name}, Agent.MINECRAFT, new ProfileLookupCallback() {
            @Override
            public void onProfileLookupSucceeded(GameProfile profile) {
                future.complete(profile);
            }

            @Override
            public void onProfileLookupFailed(GameProfile profile, Exception exception) {
                future.completeExceptionally(exception);
            }
        });
    }

}
