# BreweryMenu

在粘液科技指南新增酿酒分类，收录22种酒饮配方，标注桶型、陈酿年数与蒸馏次数，需前置异域花园。

本插件是 **Brewery（酿酒）插件的可视化菜单附属**：把酒饮配方（原料、桶型、陈酿年数、蒸馏次数、烹饪时间）以 Slimefun 指南条目的形式展示出来，方便玩家在游戏内查阅。真正的酿造过程仍由 Brewery 插件完成，本插件不改变原有酿造机制。

## 前置

| 插件 | 说明 |
| --- | --- |
| [Slimefun4](https://github.com/SlimefunGuguProject/Slimefun4) | 硬依赖（2025.1 及以上），提供指南与物品注册 |
| [ExoticGarden](https://github.com/SlimefunGuguProject/ExoticGarden) | 硬依赖，配方中的酵母、玉米使用其材质 |
| Brewery | 强烈建议安装。没有它本插件只是查表，无法实际酿酒 |

## 安装

1. 服务端安装上述前置插件；
2. 将 `BreweryMenu-<版本>.jar` 放入 `plugins/` 目录；
3. 重启服务器，在 Slimefun 指南中找到「酿酒」分类。

## 内容

- **酿酒（ItemGroup：brewery_category）**：1 个展示条目 + 22 种酒饮，包括各类威士忌、啤酒、葡萄酒、蜂蜜酒、朗姆酒、伏特加、杜松子酒、龙舌兰、苦艾酒，以及咖啡、土豆汤、蛋酒等。
- 每种酒饮的配方条目标注：原料数量、桶类型与陈酿年数、烹饪时间、蒸馏次数（如适用）。
- 未改动 Slimefun 与原版已有的任何机制。

## 配置

`config.yml` 仅含 `options.auto-update`（当前版本未实现自动更新逻辑，保留占位）。

## 构建

```bash
./gradlew clean build
```

产物位于 `build/libs/BreweryMenu-<版本>.jar`。构建要求 JDK 21+，依赖坐标集中在 `gradle/libs.versions.toml`。

## 兼容性

- 编译目标：Java 21；`api-version: 1.14`。
- CI 兼容性测试矩阵：Paper 1.20.1 与 26.2（见 `.github/workflows/test-compatibility.yml`）。

## 反馈

请在 [Issues](https://github.com/balugaq/BreweryMenu/issues) 提交问题。

## 许可

仓库未附带 LICENSE 文件，版权归属原作者（[Figgnus/BreweryMenu](https://github.com/Figgnus/BreweryMenu)）。
