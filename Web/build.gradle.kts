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
        dependsOn(test)
        sources {
            include("**/index.adoc")
        }
        baseDirFollowsSourceFile()
    }

    asciidoctor.get().doLast {
        copy {
            from("${asciidoctor.get().outputDir}")
            into("src/main/resources/static/docs")
        }
    }

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