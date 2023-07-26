package pdfextraction;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.FileReader;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        // stores presentation objects
        ArrayList<Slide> presentation;
        String projectRoot = System.getProperty("user.dir");

        // where sample pdf is located
        String pdfLocation = projectRoot + "/pdfextraction/content/sample_reports/Lizard_Research.pdf";

        // Where results of pdf extraction will be output
        String outputFolder = projectRoot + "/pdfextraction/content/output";

        // images and json are stored here
        File folder = new File(outputFolder);
        folder.mkdirs();
        outputFolder = folder.getAbsolutePath();

        // create a TextExtraction Object
        TextExtraction pdfExtractor = new TextExtraction(outputFolder);
        PDDocument document = PDDocument.load(new File(pdfLocation));

        // extract Images & Text from the Document
        pdfExtractor.runExtraction(document, projectRoot);

        // Read the JSON file
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(new FileReader(projectRoot + "/parsedPDF.json")).getAsJsonObject();

        // Extract the content from the "text" field
        String extractedText = jsonObject.get("text").getAsString();

        // parse text and break up into paragraphs and store in a json
        presentation = TextSegmenter.divide(extractedText);

        // summarize the text
        TextSummarizer.summarize(presentation);

        // generate powerpoint
        BPowerPointGenerator.create(projectRoot, presentation);

        document.close();

    }
}
