# AstrBot Adapter (Forge) 群服互通适配器

[![Version](https://img.shields.io/badge/version-v1.0.0-blue.svg)](https://github.com/OMSociety/AstrBotAdapter_Forge)
[![AstrBot](https://img.shields.io/badge/AstrBot-%E2%89%A5v4-green.svg)](https://github.com/AstrBotDevs/AstrBot)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.20.1-orange.svg)](https://www.minecraft.net/)
[![License](https://img.shields.io/badge/license-MIT-orange.svg)](LICENSE)

一个用于连接 Minecraft Forge 服务器和 AstrBot 的 Mod，支持消息互通、服务器状态监测、远程指令执行与游戏内 AI 聊天。

> 大部分代码来源于 [AstrBotAdapter](https://github.com/Railgun19457/AstrBotAdapter)（原作者 [railgun19457](https://github.com/Railgun19457)）
>
> 本仓库为其 **Forge 1.20.1 服务端移植版**：移除 Bukkit / Folia / Velocity 平台与代理模式，仅保留独立模式（WS/REST）。

[📖 功能概览](#-功能概览) • [🚀 快速开始](#-快速开始) • [⚙️ 配置项说明](#-配置项说明) • [🧩 架构](#-架构) • [🎮 游戏内指令](#-游戏内指令) • [📝 更新日志](#-更新日志) • [🤝 贡献与反馈](#-贡献与反馈) • [📜 许可证](#-许可证)

---

## 📖 功能概览

### 消息互通
服务器聊天消息实时转发至 AstrBot，AstrBot 也可向服务器发送消息，支持发送者信息展示：
- 💬 游戏内 → AstrBot：玩家聊天自动转发（支持前缀过滤与自定义显示格式）
- 📨 AstrBot → 游戏内：外部消息推送至服务器，显示平台来源与发送者

### 服务器状态监控
实时监测并上报服务器运行状态：
- 👥 **玩家信息** — 在线列表与数量变化
- 📊 **TPS** — 服务器运行流畅度
- 🧠 **内存** — JVM 内存使用情况

### 远程指令执行
通过 REST API 远程执行服务器指令，支持黑白名单过滤：
- 🛡️ **过滤模式** — `NONE` / `BLACKLIST` / `WHITELIST`
- 🔀 **通配符匹配** — 指令列表支持 `*` 通配符

### 玩家事件通知
- 🟢 玩家加入服务器时通知
- 🔴 玩家离开服务器时通知

### 游戏内 AI 聊天
在游戏内直接与 AstrBot 的 AI 对话：
- 👥 **群聊 AI** — 前缀触发（默认 `@`）
- 💬 **私聊 AI** — 前缀触发（默认 `#`），可自定义回显格式
- ⏳ **思考中提示** — 可开关，AI 回复期间显示「思考中...」

---

## 🚀 快速开始

### 兼容性
| 平台 | 版本 | 加载方式 | Java |
|------|------|---------|------|
| **Forge** | 1.20.1 (Forge 47.x) | 放入服务端 `mods/` 目录 | 17+ |

> 仅服务端安装，客户端无需安装（`displayTest = IGNORE_ALL_VERSION`）

### 第一步：下载 mod
从 [GitHub Releases](https://github.com/OMSociety/AstrBotAdapter_Forge/releases/latest) 下载最新版 `astrbotadapter-1.0.0-all.jar`。

> 想从源码自行构建？需要 JDK 17+（ForgeGradle 1.20.1 要求），执行 `./gradlew build`，产物位于 `build/libs/`。

### 第二步：安装
1. 将 jar 放入服务端 `mods/` 目录
2. 启动服务器，首次启动自动生成配置 `config/astrbotadapter/config.yml`

### 第三步：连接 AstrBot
1. 获取认证 token：游戏内执行 `/astrbot token show`，或查看配置文件中的 `auth.token`
2. 在 AstrBot 安装孪生插件 [Minecraft 适配器](https://github.com/Railgun19457/astrbot_plugin_minecraft_adapter)，用于对接本 mod
3. 在插件中添加服务器，配置地址、端口（默认 `8765`）和认证 token

---

## ⚙️ 配置项说明

配置文件（首次启动自动生成）：`config/astrbotadapter/config.yml`

### 基础设置
| 配置项 | 默认值 | 类型 | 说明 |
|--------|--------|------|------|
| `general.language` | `zh_CN` | string | 插件语言，支持 `zh_CN` / `en_US` |
| `general.debug` | `false` | boolean | 调试模式，开启后输出详细日志 |

### 认证设置
| 配置项 | 默认值 | 类型 | 说明 |
|--------|--------|------|------|
| `auth.token` | `""` | string | 认证 Token，留空启动时自动生成 32 位随机 Token |

### 网络服务设置
| 配置项 | 默认值 | 类型 | 说明 |
|--------|--------|------|------|
| `server.host` | `0.0.0.0` | string | WS/REST 监听地址 |
| `server.port` | `8765` | int | WS/REST 监听端口 |
| `server.websocket.enabled` | `true` | boolean | 是否启用 WebSocket 服务 |
| `server.websocket.heartbeatInterval` | `30` | int(秒) | 心跳间隔 |
| `server.websocket.heartbeatTimeout` | `90` | int(秒) | 心跳超时阈值 |
| `server.restapi.enabled` | `true` | boolean | 是否启用 REST API |
| `server.restapi.rateLimit` | `100` | int(次/分钟) | REST 频率限制，`0` 为不限流 |

### 消息转发设置
| 配置项 | 默认值 | 类型 | 说明 |
|--------|--------|------|------|
| `messageForward.enabled` | `true` | boolean | 是否启用聊天消息转发 |
| `messageForward.prefix` | `*` | string | 转发触发前缀（留空表示转发所有消息） |
| `messageForward.stripPrefix` | `true` | boolean | 转发时是否移除前缀 |
| `messageForward.incomingFormat` | `§7[§b{platform}§7] §f{username}§7: §f{content}` | string | 外来消息显示格式，支持 `{platform}` `{username}` `{content}` |

### AI 聊天设置
| 配置项 | 默认值 | 类型 | 说明 |
|--------|--------|------|------|
| `aiChat.group.enabled` | `true` | boolean | 是否启用群聊 AI |
| `aiChat.group.prefix` | `@` | string | 群聊 AI 触发前缀 |
| `aiChat.private.enabled` | `true` | boolean | 是否启用私聊 AI |
| `aiChat.private.prefix` | `#` | string | 私聊 AI 触发前缀 |
| `aiChat.private.echoFormat` | `<{player}> {message}` | string | 私聊回显格式，支持 `{player}` `{message}` |
| `aiChat.responseFormat` | `§7[§dAI§7] §f{content}` | string | AI 回复格式，支持 `{content}` |
| `aiChat.thinkingMessage` | `§7[§dAI§7] §e思考中...` | string | AI 思考中提示文案 |
| `aiChat.showThinking` | `true` | boolean | 是否显示思考中提示 |
| `aiChat.timeout` | `60` | int(秒) | AI 请求超时时间 |

### 玩家通知设置
| 配置项 | 默认值 | 类型 | 说明 |
|--------|--------|------|------|
| `playerNotification.join.enabled` | `true` | boolean | 是否通知玩家加入 |
| `playerNotification.quit.enabled` | `true` | boolean | 是否通知玩家离开 |

### 指令执行设置
| 配置项 | 默认值 | 类型 | 说明 |
|--------|--------|------|------|
| `commandExecution.enabled` | `true` | boolean | 是否允许远程执行指令 |
| `commandExecution.filterType` | `BLACKLIST` | enum | `NONE` / `BLACKLIST` / `WHITELIST` |
| `commandExecution.commandList` | 见默认配置 | list[string] | 指令过滤列表，支持 `*` 通配符 |

### 日志查询设置
| 配置项 | 默认值 | 类型 | 说明 |
|--------|--------|------|------|
| `logQuery.enabled` | `true` | boolean | 是否启用日志查询 |
| `logQuery.maxLines` | `1000` | int | 最大返回行数 |
| `logQuery.logFile` | `""` | string | 日志路径（相对服务器根目录），留空默认 `logs/latest.log` |

### 兼容保留设置
| 配置项 | 默认值 | 类型 | 说明 |
|--------|--------|------|------|
| `updateCheck.enabled` | `true` | boolean | 兼容保留项，Forge 版本不生效 |
| `updateCheck.notifyOps` | `true` | boolean | 兼容保留项，Forge 版本不生效 |
| `proxyMode.enabled` | `false` | boolean | 兼容保留项，Forge 版本不支持代理模式，请保持 `false` |
| `proxyMode.secret` | `""` | string | 兼容保留项，Forge 版本不使用 |

> `updateCheck` 与 `proxyMode` 为上游兼容保留配置，Forge 版本不生效。

---

## 🧩 架构

### 通信层（Netty）
基于 Netty 的 WS + REST 双通道服务，与平台完全解耦：
- **WebSocket** — 与 AstrBot 插件长连接，Token 鉴权 + 心跳保活
- **REST API** — 状态查询 / 远程指令 / 日志查询
- 运行时缺省依赖（netty-codec-http、snakeyaml）通过 jarJar 打包进 mod

### 平台抽象层（PlatformAdapter）
统一抽象接口隔离平台差异，Forge 实现类完成实际对接：
- 玩家操作 / 聊天消息 / 指令执行 / 服务器信息上报
- 仅需替换实现类即可迁移至其他平台

### 通信协议
与 [astrbot_plugin_minecraft_adapter](https://github.com/Railgun19457/astrbot_plugin_minecraft_adapter) 对接（`PROTOCOL_VERSION = 2`），消息格式详见 [doc/protocol.md](doc/protocol.md)。

---

## 🎮 游戏内指令

| 指令 | 说明 |
|------|------|
| `/astrbot help` | 显示帮助信息 |
| `/astrbot reload` | 重载配置文件 |
| `/astrbot status` | 显示 ws/restapi 运行状态 |
| `/astrbot token [show/regen]` | 显示/重新生成认证 token |
| `/astrbot connections` | 显示当前活跃的 ws 连接 |

> 权限：敏感子命令（`reload` / `token` / `connections`）需要 **OP 等级 2**（Forge 无 Bukkit 权限系统，按 OP 等级判定）

---

## 📝 开发规划

> 短期内不会有进一步开发，欢迎提ISSUE和PR
适配更多版本

---

## 🤝 贡献与反馈

如遇问题请在 [GitHub Issues](https://github.com/OMSociety/MineAstrbotForge/issues) 提交，欢迎 Pull Request！

---

## 📜 许可证

本项目采用 **MIT License** 开源协议（上游 [AstrBotAdapter](https://github.com/Railgun19457/AstrBotAdapter) 同样为 MIT）。

---

## 👤 作者

**railgun19457** — AstrBotAdapter 原作者 [@Railgun19457](https://github.com/Railgun19457)  
**OMSociety** — Forge 移植版维护 [@OMSociety](https://github.com/OMSociety)
