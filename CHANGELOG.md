# 更新日志

## v1.0.0-forge
- 移植至 Forge 1.20.1 服务端
- 移除 Bukkit / Folia / Velocity 平台与代理模式
- 使用 Brigadier 重写 `/astrbot` 命令
- 构建系统由 Maven 迁移至 Gradle（ForgeGradle）
- 配置文件位于 `config/astrbotadapter/config.yml`

## 上游原版（AstrBotAdapter）历史版本

### v2.0.5
- 重构后首个正式版
- 支持前后端插件协作
- 对应 AstrBot 插件版本 2.0.1+

### v2.0.1-beta - v2.0.4-beta
- 具体更新内容见 commit 记录

### v2.0.0-beta
- 重构架构，支持多平台（Bukkit/Folia/Velocity）
- 新增 Folia 区域化多线程支持
- 新增 Velocity 代理服务器支持
- 统一抽象层设计，更好的可扩展性
- 改进的国际化支持

### v1.0.3
- 支持 AI 聊天
- 添加配置自动校验

### v1.0.2
- 修复重复转发的 bug
- 精简代码，提高性能
- 修改游戏内提示为中文
- 为 status 命令添加详细连接信息

### v1.0.1
- 修复 bug
- 修改配置文件

### v1.0.0
- 实现基本功能
