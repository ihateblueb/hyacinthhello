plugins {
	kotlin("jvm")
}

group = "site.remlit.hyacinthhello"
version = "2.5.0"

repositories {
	mavenCentral()
}

dependencies {
	testImplementation(kotlin("test"))
}

kotlin {
	jvmToolchain(21)
}

tasks.test {
	useJUnitPlatform()
}