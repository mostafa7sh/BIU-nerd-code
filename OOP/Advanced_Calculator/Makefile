SOURCES := $(wildcard *.java)

MAIN_CLASS := Main

all: compile

compile:
	javac $(SOURCES)

run: compile
	java $(MAIN_CLASS)

clean:
	rm -f *.class
