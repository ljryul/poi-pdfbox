package main;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by bumskim on 2017. 6. 7..
 */
public class MakePDF {

    static public void main(String[] args) {
        try {
            doCreate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static private void doCreate() throws IOException {

        // if you want load the file..
//        File file = new File("path of the document")
//        PDDocument document = PDDocument.load(file);


        // create empty document
        PDDocument document = new PDDocument();

        // create empty page
        PDPage page = new PDPage();

        // add page into document
        document.addPage(page);

        // if you want remove the page..
//        int noOfPages= document.getNumberOfPages();
//        document.removePage(noOfPages);


        doWritePage(document, page);

        // save the pdf
        try {
            document.save("src/pdf/test.pdf");
        } catch (IOException e) {
            System.out.println("save failed");
        }

        // close the document
        try {
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static private void doConfigFile(PDDocument document) {
//        Creating the PDDocumentInformation object
        PDDocumentInformation pdd = document.getDocumentInformation();

//        Setting the author of the document
        pdd.setAuthor("Tutorialspoint");

//        Setting the title of the document
        pdd.setTitle("Sample document");

//        Setting the creator of the document
        pdd.setCreator("PDF Examples");

//        Setting the subject of the document
        pdd.setSubject("Example document");

//        Setting the created date of the document
        Calendar date = new GregorianCalendar();
        date.set(2015, 11, 5);
        pdd.setCreationDate(date);

//        Setting the modified date of the document
        date.set(2016, 6, 5);
        pdd.setModificationDate(date);

//        Setting keywords for the document
        pdd.setKeywords("sample, first example, my pdf");
    }

    static private void doWritePage(PDDocument document, PDPage page) throws IOException {

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        //Begin the Content stream
        contentStream.beginText();

        //Setting the font to the Content stream
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

        //Setting the position for the line
        contentStream.newLineAtOffset(25, 500);

        String text = "This is the sample document and we are adding content to it.";

        //Adding text in the form of string
        contentStream.showText(text);

        //Ending the content stream
        contentStream.endText();

        System.out.println("Content added");

        //Closing the content stream
        contentStream.close();
    }
}
