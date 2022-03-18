# ImageLoaderDemo

practice for TDD and CI/CD

### Part 1: TDD



### Part 2: Test  Coverage Report

Use jacoco to generate test coverage report.

Note:

1.Jacoco  coverage results are different to Android Studio coverage  

​	solution: config jacoco like this:

```
jacoco {
    toolVersion = "0.8.7"
    tasks.withType(Test) {
        jacoco.includeNoLocationClasses = true
        jacoco.excludes = ['jdk.internal.*']
    }
}
```

2.quality gate:

https://docs.gradle.org/7.2/dsl/org.gradle.testing.jacoco.tasks.JacocoCoverageVerification.html

Use JacocoCoverageVerification and violationRules



```
task jacocoTestCoverageVerification(type: JacocoCoverageVerification) {
    group = "Reporting"
    dependsOn = ['jacocoTestReport']
    enabled = true

    sourceDirectories.from = files([mainSrc, debugSrc])
    classDirectories.from = files([javaDebugTree, kotlinDebugTree])

    executionData.from = fileTree(dir: "$buildDir", includes: [
            'jacoco/testDebugUnitTest.exec',
            'outputs/code_coverage/debugAndroidTest/connected/*coverage.ec'
    ])

    violationRules {
        failOnViolation = true
        rule {
            limit {
                minimum = 0.7
                counter = 'LINE'
            }
        }
    }
}

// to run coverage verification during the build (and fail when appropriate)
check.dependsOn jacocoTestCoverageVerification
```

### Part 3: Jenkins CI/CD

1.manage jenkins -> config global setting and path :

​	ANDROID_HOME:  /Users/xxxx/Library/Android/sdk

​	JAVA_HOME: 	/Applications/Android Studio.app/Contents/jre/Contents/Home

2.add new job->  config pipeline 

```groovy
pipeline {
    agent any

    stages {
        stage('check out'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/master']],
                          userRemoteConfigs: [[url: 'https://github.com/xiongwengong/ImageLoaderDemo.git']]])
            }
        }

        stage('Build') {
            steps {
                sh "/Users/xxxx/Documents/jenkins/dev/gradle/gradle-7.2/bin/gradle clean assembleDebug  -PnotCheck=true "
            }
        }

        stage('Unit Test') {
            steps {
                echo 'execut UT'
                sh "/Users/xxxxx/Documents/jenkins/dev/gradle/gradle-7.2/bin/gradle jacocoTestCoverageVerification  -PnotCheck=true "
                jacoco buildOverBuild: true, classPattern: 'ImageLoader/build/intermediates/javac/debug,ImageLoader/build/tmp/kotlin-classes/debug', deltaLineCoverage: '20', minimumLineCoverage: '50', sourceInclusionPattern: '', sourcePattern: 'ImageLoader/src/main/java,ImageLoader/src/debug/java'
            }
        }
        
        stage('publish'){
            steps {
                sh "/Users/xxxx/Documents/jenkins/dev/gradle/gradle-7.2/bin/gradle publishToMavenLocal  -PnotCheck=true "
            }
        }
    }
}
```



