#!/bin/bash

echo "Creating POMs for all pattern modules..."

# GoF Behavioral Patterns
cat > ObserverPattern/pom.xml << 'EOF'
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.balazsholczer</groupId>
        <artifactId>ultimate-design-patterns</artifactId>
        <version>1.0.0</version>
    </parent>
    <artifactId>observer-pattern</artifactId>
    <packaging>jar</packaging>
    <name>Observer Pattern</name>
    <description>GoF Behavioral - Observer Pattern</description>
    <properties>
        <exec.mainClass>com.balazsholczer.observer.App</exec.mainClass>
    </properties>
</project>
EOF

cat > CommandPattern/pom.xml << 'EOF'
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.balazsholczer</groupId>
        <artifactId>ultimate-design-patterns</artifactId>
        <version>1.0.0</version>
    </parent>
    <artifactId>command-pattern</artifactId>
    <packaging>jar</packaging>
    <name>Command Pattern</name>
    <description>GoF Behavioral - Command Pattern</description>
    <properties>
        <exec.mainClass>com.balazsholczer.command.App</exec.mainClass>
    </properties>
</project>
EOF

cat > SingletonPattern/pom.xml << 'EOF'
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.balazsholczer</groupId>
        <artifactId>ultimate-design-patterns</artifactId>
        <version>1.0.0</version>
    </parent>
    <artifactId>singleton-pattern</artifactId>
    <packaging>jar</packaging>
    <name>Singleton Pattern</name>
    <description>GoF Creational - Singleton Pattern</description>
    <properties>
        <exec.mainClass>com.balazsholczer.singleton.App</exec.mainClass>
    </properties>
</project>
EOF

echo "POMs created for key patterns. Run mvn clean compile to test."