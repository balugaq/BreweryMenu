# AGENTS.md — BreweryMenu 操作契约

> 本文件是 AI 编程助手与本仓库开发者的操作约定。改动均须遵守；与代码冲突时以代码为准，并更新本文。

## TL;DR

1. 本仓库是 **Spigot + Slimefun4 附属插件**（Java 21 / Gradle Kotlin DSL），所有改动必须通过 `./gradlew clean build` 验收后才能提交，禁止提交未编译验证的代码。
2. **禁止改动已发布物品的 Slimefun ID**（如 `BREWERY`、`SKOTSKA_WHISKY`），改 ID 即毁玩家存档；**禁止** `git push -f`。
3. 直接在 `master` 分支开发，提交信息用 Conventional Commits（`feat:`/`fix:`/`docs:`/`chore:`）；提交与推送由开发者执行。

---

## 1. 项目身份与边界

| 维度 | 事实 |
| --- | --- |
| 定位 | Brewery（酿酒）插件的 Slimefun 指南菜单附属：展示酒饮配方，不实现酿造逻辑 |
| 入口类 | `me.figgnus.slimefunaddon.BreweryMenu`（`extends JavaPlugin implements SlimefunAddon`） |
| 版本目标 | Java 21（`options.release = 21`）、spigot-api 1.17.1、Slimefun4 2025.1、`api-version: 1.14` |
| 硬依赖 | `plugin.yml` 的 `depend: [Slimefun, ExoticGarden]`；配方里酵母/玉米用 ExoticGarden 的材质哈希 |
| 仓库 | `balugaq/BreweryMenu`，默认分支 `master` |
| AI 职责 | 编写/修改代码、Bug 修复、构建/CI 维护、文档维护；不负责发布版本与推送 |

## 2. 代码地图与技术栈

- **单主类结构**：全部逻辑在 `BreweryMenu.onEnable()` 中——读 `config.yml`（仅 `options.auto-update`，当前无实际逻辑）→ 创建 `brewery_category` ItemGroup（药水图标「酿酒」）→ 定义 23 个 `SlimefunItemStack`（1 个 `BREWERY` 炼药锅多方块展示项 + 22 种酒饮）→ 逐个 `register(this)`。
- **配方展示**：酒饮用自定义 `RecipeType MENU_BOTTLE`（以分类图标为配方类型图标）；配方数组由 `createRecipeItem`（原料×数量）、`createBarrel`（桶型+陈酿年数）、`createClock`（烹饪时间）、`createBrewingStand`（蒸馏次数）、`createMultiblockRecipe`（多方块结构说明）拼装。
- **药水颜色**：`configureMeta()` 设置 `PotionMeta` 颜色并隐藏药水效果。
- **构建**：Gradle Kotlin DSL，无 shadow（全部依赖 `compileOnly`）；依赖集中在 `gradle/libs.versions.toml`；`plugin.yml` 的 `version: ${version}` 由 `processResources` 展开，**勿改回 Maven 占位符**。
- **CI**：`build.yml`（JDK 21，push/PR 触发）、`release.yml`（`gradle.properties` 的 `version=` 变化时自动打 tag 发布）、`test-compatibility.yml`（矩阵 1.20.1,26.2；先下载 Slimefun4 + ExoticGarden 再启动 Paper）。

## 3. 决策树

- **新增一种酒饮**：在 `onEnable()` 中新增 `SlimefunItemStack`（**ID 全大写唯一，发布后不可改**）→ `configureMeta` 设颜色 → 拼 `ItemStack[]` 配方 → `new SlimefunItem(...)` → `register(this)`。三条链路缺一不可。
- **新增原料**：优先 `createRecipeItem(名称, 数量, Material)`；需 ExoticGarden 材质时用其材质哈希（参照酵母/玉米的 `createRecipeItem(id, 名称, texture, 数量)` 重载）。
- **构建失败**：先读报错定位（依赖解析失败 / 编译错误），修复后重跑；禁止 `--offline` 硬跳过。
- **改版本号**：只改 `gradle.properties` 的 `version=`，`release.yml` 会自动据此打 tag；不要手动打 tag。

## 4. 红线

1. **禁止修改已发布物品的 Slimefun ID**——玩家指南历史/存档引用会失效。
2. **禁止 `git push -f`、rebase 改写已推送历史。**
3. **禁止未通过 `./gradlew clean build` 就提交。**
4. **禁止把 `plugin.yml` 的 `version: ${version}` 改回 `${project.version}`**（Maven 已移除，展开机制不同）。
5. **禁止改动 `gradlew` 的执行位**：git mode 必须保持 `100755`；三个 workflow 首次调用 `./gradlew` 前的 `chmod +x gradlew` 不可删除。

## 5. 质量规范

- 改动保持可编译完整状态，不留死代码；未使用 import 及时清理。
- 本项目暂无测试；纯逻辑工具方法可引入 JUnit 5（build.yml 已有 `./gradlew test` 步骤），Bukkit/Slimefun 相关逻辑靠兼容性 CI 与游戏内验证。
- 无 LICENSE 文件，对外文案注明版权归属原上游 Figgnus/BreweryMenu。

## 6. 常用命令

| 场景 | 命令 |
| --- | --- |
| 标准验收构建 | `./gradlew clean build` |
| 快速编译检查 | `./gradlew compileJava` |
| 产物位置 | `build/libs/BreweryMenu-<version>.jar` |

## 7. 文档更新契约

1. AI 发现本文与代码不一致时必须主动指出，并说明以哪边为准。
2. 产生新的可复用约定时先提议、经开发者确认后写入本文。
3. 修改本文提交信息用 `docs(agents): ...`。
