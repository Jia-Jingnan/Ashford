English | [简体中文](./README.md)

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
  <a><img src="https://img.shields.io/badge/license-MIT-blue.svg"></a>
</p>

<div align="center">
  <sub>The little framework that built with ❤︎
</div>

#### 🚩Table of Contents

- [Ecosystem](#Ecosystem)
- [Useful Resources](#Useful-Resources)
- [QuickStart](#QuickStart)
  - [Install](#Install)
  - [Usage](#Usage)
- [Contribution](#Contribution)
- [Maintainers](#Maintainers)
- [Thanks](#Thanks)
- [License](#license)

#### Ecosystem

[→ 生态学习路线图](https://github.com/Tencent/omi/tree/master/assets/rm.md)

| **Project**                                                                                                                               | **Description**                |
| ----------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------ |
| [ZARA-docs](https://github.com/Tencent/omi/blob/master/docs/main-concepts.cn.md)                                                           | 官方文档                       |
| [ZARA![](https://raw.githubusercontent.com/dntzhang/cax/master/asset/hot.png) ](https://github.com/Tencent/omi/tree/master/packages/omip) | 直接使用 ZARA 开发小程序！！！ |

#### Why Zara

- 小巧的尺寸
- 支持 `TypeScript`

#### Useful-Resources

- [你必须收藏 ES6 Spread Operator 技巧](https://github.com/Tencent/omi/blob/master/tutorial/spread-operator.cn.md)
- [使用 requestIdleCallback](https://div.io/topic/1370)

#### QuickStart

#### Install

```bash
$ npm i zara-cli -g               # install cli
$ omi init my-app     # init project, you can also exec 'omi init' in an empty folder
$ cd my-app           # please ignore this command if you executed 'omi init' in an empty folder
$ npm start                      # develop
$ npm run build                  # release
```

Directory description:

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

#### 📘Usage

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

#### ☕Maintainers

<table>
    <tbody>
        <tr>
            <td>
                <a target="_blank" href="https://github.com/Jia-Jingnan/Chadstone"><img width="40px" src="https://avatars.githubusercontent.com/Jia-Jingnan?s=460&v=4"></a>
            </td>
        </tr>
    </tbody>
</table>

#### 🎨Open Source

[![forthebadge](https://forthebadge.com/images/badges/open-source.svg)](https://opensource.org/licenses/MIT)
