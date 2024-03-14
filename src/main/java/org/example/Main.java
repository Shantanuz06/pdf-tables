package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {

        InputStream in;
        in = this.getClass().getResourceAsStream("textFile.pdf");
        {
            try (PDDocument document = PDDocument.load(in)) {
                SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
                PageIterator pi = new ObjectExtractor(document).extract();
                while (pi.hasNext()) {
                    // iterate over the pages of the document
                    Page page = pi.next();
                    List<Table> table = (List<Table>) sea.extract(page);
                    // iterate over the tables of the page
                    for (Table tables : table) {
                        List<List<RectangularTextContainer>> rows = tables.getRows();
                        // iterate over the rows of the table
                        for (List<RectangularTextContainer> cells : rows) {
                            // print all column-cells of the row plus linefeed
                            for (RectangularTextContainer content : cells) {
                                // Note: Cell.getText() uses \r to concat text chunks
                                String text = content.getText().replace("\r", " ");
                                System.out.print(text + "|");
                            }
                            System.out.println();
                        }
                    }
                }
            }
        }
    }
}