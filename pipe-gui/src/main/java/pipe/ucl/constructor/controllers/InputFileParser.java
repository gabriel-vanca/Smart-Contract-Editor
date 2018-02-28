package pipe.ucl.constructor.controllers;

import pipe.ucl.constructor.models.InputLine;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputFileParser {

    private final String inputFileLocation;
    private Path fileName;
    private Path filePath;
    private ArrayList<InputLine> ParsedReadDataLinesList = new ArrayList<>();

    public InputFileParser(String inputFileLocation) {
        this.inputFileLocation = inputFileLocation;
        try {
            filePath = Paths.get(ClassLoader.getSystemResource(inputFileLocation).toURI());
            fileName = filePath.getFileName();
        } catch (URISyntaxException e) {
            System.out.println("ERROR while reading input file: " + e);
        }
    }

    public ArrayList<InputLine> getParsedReadDataLinesList() {
        return this.ParsedReadDataLinesList;
    }

    public void ParseInputFile() {
        List<String> readDataLinesList = ReadFile();
        for (String readDataLine : readDataLinesList) {
            if(readDataLine == "")
                continue;
            InputLine parsedReadDataLine = LineParser.ParseLine(readDataLine);
            if (parsedReadDataLine == null)
                continue;

            ParsedReadDataLinesList.add(parsedReadDataLine);
        }
    }

    public void EmptyParsedReadDataLinesList() {
        ParsedReadDataLinesList.clear();
    }

    private List<String> ReadFile() {
        

        List<String> readDataLinesList = new ArrayList<>();

        try {

            Stream<String> stream;
            stream = Files.lines(filePath);

            readDataLinesList = stream
                    .collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println("ERROR while reading input file: " + e);
        } catch (Exception e) {
            System.out.println("ERROR while reading input file: " + e);
        }

        readDataLinesList.forEach(System.out::println);

        return readDataLinesList;
    }


    public String getFileName() {
        return fileName.toString();
    }
    
    public String getFileNameWithoutExtension() {
        return getFileName().replaceFirst("[.][^.]+$", "");
    }
}
