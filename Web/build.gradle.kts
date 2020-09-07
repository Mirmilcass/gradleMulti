plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlin.plugin.jpa")
    kotlin("kapt")
    war

    id("org.asciidoctor.jvm.convert")
}

val snippetsDir by extra { file("build/generated-snippets") }

tasks {
    test {
        outputs.dir(snippetsDir)
        useJUnitPlatform()
    }

    asciidoctor {
        // 참조 https://asciidoctor.org/docs/asciidoctor-gradle-plugin/
        asciidoctorj.attribute("snippets", snippetsDir)
        dependsOn(test)
        baseDirFollowsSourceFile()
        setOutputDir("src/main/resources/static/docs")
    }

    // asciidoctorTask 에서 setOutputDir 로 파일 생성 위치를 지정하여 불필요해짐
    /*asciidoctor.get().doLast {
        copy {
            from("${asciidoctor.get().outputDir}")
            into("src/main/resources/static/docs")
        }
    }*/

    build {
        dependsOn(asciidoctor)
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")

    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:1.3.2")

    //test
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(module = "junit")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.springframework.restdocs", "spring-restdocs-mockmvc")

    // queryDsl
    api("com.querydsl:querydsl-jpa:4.2.2")
    kapt("com.querydsl:querydsl-apt:4.2.2:jpa")
    kapt("org.hibernate.javax.persistence", "hibernate-jpa-2.1-api", "1.0.2.Final")
}