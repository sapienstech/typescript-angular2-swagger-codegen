# typescript-angular2-swagger-codegen

This project was generated and then modified by using:
https://github.com/swagger-api/swagger-codegen#making-your-own-codegen-modules

See https://github.com/swagger-api/swagger-codegen for usage information on swagger-codegen and swagger .

To use this module, simply include the following in your Maven build:
```
<pluginRepositories>
    <pluginRepository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </pluginRepository>
</pluginRepositories>
...

<build>
    ...
    
    <plugins>
      
      ...
        <plugin>
            <groupId>io.swagger</groupId>
            <artifactId>swagger-codegen-maven-plugin</artifactId>
            <version>2.1.5</version>
            <dependencies>
                <dependency>
                    <groupId>com.github.sapienstech</groupId>
                    <artifactId>typescript-angular2-swagger-codegen</artifactId>
                    <version>-SNAPSHOT</version>
                </dependency>
            </dependencies>
            <executions>
                <execution>
                    <goals>
                        <goal>generate</goal>
                    </goals>
                    <configuration>
                        
                        <!-- This could be http://localhost:9080/swagger.yaml or a local file, depending on your setup -->
                        <inputSpec>${codegen.source.url}</inputSpec> 
                        
                        <language>typescript-angular2</language>
                        
                        <!-- This would be the directory to generate the files onto -->
                        <output>${codegen.target.dir}</output>
                    </configuration>
                </execution>
            </executions>
        </plugin>
        
      ...
      </plugins>
</build>
```
