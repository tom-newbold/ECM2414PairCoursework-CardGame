# Card Game

### ECM2414 Pair Coursework Project

This project is a threaded card game simulation, written in java. Our development is documented within the attached *Report.pdf*.
Source code has been documented with javadoc comments.

## Running the Test Suite

The test class files have been compiled into */Tests* , along with the supporting files for certain tests.
Extract this folder, and then extract the class files from *cards.jar* into a subdirectory within this folder named CardGame.

It should appear as follows:

    cardsTest/
        CardGame/
            class files from *cards.jar*
        lib/
            junit-4.13.2.jar
            hamcrest-1.3.jar
        Tests/
            class files of tests
        pack.txt
        pack_8player.txt
        README.md

Execute `java -cp "lib/junit-4.13.2.jar;lib/hamcrest-1.3.jar;." org.junit.runner.JUnitCore Tests.CardGameTestSuite` in this directory to run the test suite.


## Contributors

**Thomas Newbold** – 71000126; **Steven Jangcan** – 710042102
