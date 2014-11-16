import spock.lang.Specification

import static NumbersProcessor.*

class NumbersProcessorSpecification extends Specification {

    def 'Should print "Can\'t read input" to System.err if input file not found'() {
        setup:
        def errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent))
        File folder = new File("test_folder")
        folder.mkdir()

        File outputFile = new File(folder, 'output.txt')
        outputFile.createNewFile()

        when:
        new NumbersProcessor(folder).run()

        then:
        "${CANT_READ_INPUT}\n" == errContent.toString()

        cleanup:
        System.setErr(null)
        folder.deleteDir()
    }

    def "Should print to System.out if output.txt isn't available"() {
        setup:
        def errContent = new ByteArrayOutputStream();
        def outContent = new ByteArrayOutputStream();

        System.setErr(new PrintStream(errContent))
        System.setOut(new PrintStream(outContent))

        File folder = new File("test_folder")
        folder.mkdir()

        File outputFile = new File(folder, 'output.txt')
        outputFile.createNewFile()
        outputFile.writable = false
        outputFile.readable = false

        File inputFile = new File(folder, "input.txt")
        inputFile.createNewFile()
        inputFile.write('''\
                        0 0
                        0 1
                        1 0
                        1 1
                        '''.stripIndent())

        when:
        new NumbersProcessor(folder).run()

        then:
        "${CANT_WRITE_OUTPUT_TXT}\n" == errContent.toString()

        """\
        ${CANT_CALCULATE}
        ${CANT_CALCULATE}
        ${CANT_CALCULATE}
        2
        """.stripIndent() == outContent.toString()

        cleanup:
        System.setErr(null)
        System.setOut(null)
        folder.deleteDir()
    }

    def "Should differentiate exception types"() {
        setup:
        def errContent = new ByteArrayOutputStream();
        def outContent = new ByteArrayOutputStream();

        System.setErr(new PrintStream(errContent))
        System.setOut(new PrintStream(outContent))

        File folder = new File("test_folder")
        folder.mkdir()

        File outputFile = new File(folder, 'output.txt')
        outputFile.createNewFile()
        outputFile.writable = false
        outputFile.readable = false

        File inputFile = new File(folder, "input.txt")
        inputFile.createNewFile()
        inputFile.write('''\
                        0 0
                        0 1
                        1 0
                        1 1
                        1
                        0.5 0
                        0 .01
                        0.0 1.02
                        '''.stripIndent())

        when:
        new NumbersProcessor(folder).run()

        then:
        """\
        ${CANT_CALCULATE}
        ${CANT_CALCULATE}
        ${CANT_CALCULATE}
        2
        ${NOT_ENOUGH_ARGS}
        ${WRONG_NUMBER_FORMAT}
        ${WRONG_NUMBER_FORMAT}
        ${WRONG_NUMBER_FORMAT}
        """.stripIndent() == outContent.toString()

        cleanup:
        System.setErr(null)
        System.setOut(null)
        folder.deleteDir()
    }

}
