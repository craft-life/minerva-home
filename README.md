<!DOCTYPE html>
<html>
<body>
    <a href="https://craftlife.com.br">
        <img align="right" src="https://i.imgur.com/ABJeWPY.png" height="220" width="220">
    </a>
    <h1>Minerva-Home</h1>
    <p><b>a module of minerva plugin based on the eureka framework</b></p>
    <hr>
    <h2>Usage</h2>
</body>
</html>




First, make sure the repository is in your  `pom.xml`:

```xml
<repository>
 <id>craftlife</id>
 <url>https://artifactory.craftlife.com.br/artifactory/libs-release-local</url>
</repository>
```

Then, make sure the dependency is in your `pom.xml`:

```xml
<dependency>
    <groupId>br.com.craftlife</groupId>
    <artifactId>minerva-home-api</artifactId>
    <version>3.1.0</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>br.com.craftlife</groupId>
    <artifactId>minerva-home-impl</artifactId>
    <version>3.1.0</version>
    <scope>provided</scope>
</dependency>
```
