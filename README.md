[English](./README.EN.md) | 简体中文

<h1 align="center" style="border-bottom: none">Ashford</h1>

<h5 align="center">Api自动化测试框架</h5>
<p align="center">基于 HttpClient | TestNG 并使用Maven构建</p>

<p align="center">
  <a href="https://github.com/Jia-Jingnan/Ashford"><img src="https://img.shields.io/badge/ashford-1.1.0-brightgreen.svg?style=flat-square"></a>
  <a><img src="https://img.shields.io/badge/env-Maven|TestNG|HttpClient-lightgrey.svg"></a>
  <a><img src="https://img.shields.io/badge/language-Java-red.svg"></a>
  <a><img src="https://img.shields.io/badge/ide-IntelliJ IDEA-yellow.svg"></a>
  <a><img src="https://img.shields.io/badge/vcs-Git-orange.svg"></a>
  <a><img src="https://img.shields.io/badge/ci|cd-Jenkins-yellowgreen.svg"></a>
  <a><img src="https://visitor-badge.glitch.me/badge?page_id=Jia-Jingnan.Ashford"></a>
</p>
<div align="center">
  <sub>The little framework that could built with ❤︎
</div>



#### 🚩目录

- [生态](#生态)
- [必须收藏的资源](#必须收藏的资源)
- [快速入门](#快速入门)
  - [安装](#安装)
  - [用法](#用法)
- [贡献者们](#贡献者们)
- [维护者](#维护者)
- [感谢](#感谢)
- [License](#license)

#### 生态

[→ 生态学习路线图](https://github.com/Tencent/omi/tree/master/assets/rm.md)

| **项目**                                                                                                                                  | **描述**                       |
| ----------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------ |
| [ZARA-docs](https://github.com/Tencent/omi/blob/master/docs/main-concepts.cn.md)                                                           | 官方文档                       |
| [ZARA![](https://raw.githubusercontent.com/dntzhang/cax/master/asset/hot.png) ](https://github.com/Tencent/omi/tree/master/packages/omip) | 直接使用 ZARA 开发小程序！！！ |

#### 特性

- 小巧的尺寸
- 支持 `TypeScript`

#### 👀 示例

```java
public class SampleCase extends BaseCase(){
    
}
```



### ⚡️快速入门

#### 📀 安装

```bash
$ npm i zara-cli -g               # install cli
$ omi init my-app     # init project, you can also exec 'omi init' in an empty folder
$ cd my-app           # please ignore this command if you executed 'omi init' in an empty folder
$ npm start                      # develop
$ npm run build                  # release
```

目录说明:

```
├─ config
├─ public
├─ scripts
├─ src
│  ├─ assets
│  ├─ elements    //存放所有 custom elements
│  ├─ store       //存放所有页面的 store
│  ├─ admin.js    //入口文件，会 build 成  admin.html
│  └─ index.js    //入口文件，会 build 成  index.html
```

#### 📘用法

```vue
<template>
  <Slider v-model="value" range />
</template>
<script>
export default {
  data() {
    return {
      value: [20, 50]
    };
  }
};
</script>
```

#### ☕维护者

<table>
    <tbody>
        <tr>
            <td>
                <a target="_blank" href="https://github.com/Jia-Jingnan/Chadstone"><img width="40px" src="https://avatars.githubusercontent.com/Jia-Jingnan?s=460&v=4"></a>
            </td>
        </tr>
    </tbody>
</table>
#### 🎨开源

[![forthebadge](https://forthebadge.com/images/badges/open-source.svg)](https://forthebadge.com)

