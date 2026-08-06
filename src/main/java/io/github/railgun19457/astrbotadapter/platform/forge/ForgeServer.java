package io.github.railgun19457.astrbotadapter.platform.forge;

import io.github.railgun19457.astrbotadapter.platform.common.CommonServer;
import net.minecraft.server.MinecraftServer;

/**
 * Forge 平台服务器信息实现
 */
public class ForgeServer implements CommonServer {

    private final MinecraftServer server;

    public ForgeServer(MinecraftServer server) {
        this.server = server;
    }

    @Override
    public String getName() {
        return "Forge";
    }

    @Override
    public String getMotd() {
        return server.getMotd();
    }

    @Override
    public String getVersion() {
        return server.getServerVersion();
    }

    @Override
    public int getMaxPlayers() {
        return server.getMaxPlayers();
    }

    @Override
    public int getOnlinePlayerCount() {
        return server.getPlayerCount();
    }

    @Override
    public long getUptime() {
        // 由 PlatformAdapter 层基于启动时间计算
        return 0;
    }

    @Override
    public int getPort() {
        return server.getPort();
    }

    @Override
    public String getIp() {
        return server.getLocalIp();
    }
}
