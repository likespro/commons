<p align="center">
 <img width="100px" src="https://github.com/likespro.png" align="center" alt="MasterID Server" />
 <h2 align="center">likespro - commons</h2>
 <p align="center">A Kotlin library with some common utilities.</p>
</p>
<p align="center">
    <a href="https://github.com/likespro/commons">
      <img alt="LICENSE" src="https://img.shields.io/badge/licence-MIT-green" />
    </a>
    <a href="https://github.com/likespro/commons">
      <img alt="LICENSE" src="https://img.shields.io/badge/licence-Apache 2.0-blue" />
    </a>
    <a href="https://github.com/likespro/commons/graphs/contributors">
      <img alt="GitHub Contributors" src="https://img.shields.io/github/contributors/likespro/commons" />
    </a>
    <a href="https://github.com/likespro/commons/issues">
      <img alt="Issues" src="https://img.shields.io/github/issues/likespro/commons?color=0088ff" />
    </a>
    <a href="https://github.com/likespro/commons/pulls">
      <img alt="GitHub pull requests" src="https://img.shields.io/github/issues-pr/likespro/commons?color=0088ff" />
    </a>
  </p>
<p align="center">
    <a href="https://github.com/likespro/commons/actions/workflows/main-branch.yml">
      <img alt="Build Passing" src="https://github.com/likespro/commons/workflows/Main Branch Workflow/badge.svg" />
    </a>
    <a href="https://codecov.io/gh/likespro/commons"> 
        <img src="https://codecov.io/gh/likespro/commons/graph/badge.svg?token=9H24353DTH"/> 
    </a>
    <a href="https://github.com/likespro/commons">
      <img alt="Git Size" src="https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/likespro/commons/badges/git-size.md" />
    </a>
    <a href="https://github.com/likespro/commons">
      <img alt="Git File Count" src="https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/likespro/commons/badges/git-file-count.md" />
    </a>
    <a href="https://github.com/likespro/commons">
      <img alt="Git Lines Of Code" src="https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/likespro/commons/badges/git-lines-of-code.md" />
    </a>
  </p>

## License
License for `core-mit` module is **MIT**. The license for other modules is **Apache 2.0**.

## Overview
This Kotlin library contains common utilities used by likespro. Explore documentation on https://likespro.github.io/commons

## Quickstart
With Gradle, in `dependencies {}` block
```kotlin
implementation("io.github.likespro:commons-core:3.0.0") // Core features of the library - EncodableResult, .toHex, etc.
implementation("io.github.likespro:commons-network:3.0.0") // Network features - HTTP Utils, DNS Utils, etc.
implementation("io.github.likespro:commons-reflection:3.0.0") // Java/Kotlin Reflection features - Object Encoding, .boxed(), getType(), etc.
implementation("io.github.likespro:commons-security:3.0.0") // Security features - Hash Utils, Encrpyting, etc.
```

## ⚠️ Note
Please note that all package names in library are starting with `eth.likespro.commons`, **NOT** `io.github.likespro` 