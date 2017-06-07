package main;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
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


        String[][] str = {{"a","b", "1"},
                {"c","d", "2"},
                {"e","f", "3"},
                {"g","h", "4"},
                {"i","j", "5"}} ;

        PDPageContentStream content = new PDPageContentStream(document, page);
        drawTable(page, content, 700, 100, str);
//        doWritePage(document, page);

        // must close stream before save file
        content.close();

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
        contentStream.newLineAtOffset(610, 700);

        String text = "This is the sample document and we are adding content to it.";

        //Adding text in the form of string
        contentStream.showText(text);

        //Ending the content stream
        contentStream.endText();

        System.out.println("Content added");

        //Closing the content stream
        contentStream.close();
    }

    /**
     * @param page
     * @param contentStream
     * @param y the y-coordinate of the first row
     * @param margin the padding on left and right of table
     * @param content a 2d array containing the table data
     * @throws IOException
     */
    public static void drawTable(PDPage page, PDPageContentStream contentStream,
                                 float y, float margin,
                                 String[][] content) throws IOException {
        final int rows = content.length;
        final int cols = content[0].length;
        final float rowHeight = 20f;
        final float tableWidth = page.getMediaBox().getWidth()-(2*margin);
        final float tableHeight = rowHeight * rows;
        final float colWidth = tableWidth/(float)cols;
        final float cellMargin=5f;

        //draw the rows
        float nexty = y ;
        for (int i = 0; i <= rows; i++) {
            contentStream.drawLine(margin,nexty,margin+tableWidth,nexty);
            nexty-= rowHeight;
        }

        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols; i++) {
            contentStream.drawLine(nextx,y,nextx,y-tableHeight);
            nextx += colWidth;
        }

        //now add the text
        contentStream.setFont(PDType1Font.HELVETICA_BOLD,12);

        float textx = margin+cellMargin;
        float texty = y-15;
        for(int i = 0; i < content.length; i++){
            for(int j = 0 ; j < content[i].length; j++){
                String text = content[i][j];
                contentStream.beginText();
                contentStream.moveTextPositionByAmount(textx,texty);
                contentStream.drawString(text);
                contentStream.endText();
                textx += colWidth;
            }
            texty-=rowHeight;
            textx = margin+cellMargin;
        }
    }
}
