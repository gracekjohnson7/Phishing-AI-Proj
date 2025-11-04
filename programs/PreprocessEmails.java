//PURPOSE: This program preprocesses the email text to remove unnecessary characters/excessive white space,
//which keeps the focus on the content of the test itself

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

//from opencsv-5.7.1.jar
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

//from commons-lang3-3.17.0.jar
import org.apache.commons.lang3.StringUtils;

public class PreprocessEmails {
    public static void main(String[] args) {
        String inputFilePath = "data/TrainingDatabase.csv"; //file is in format (sender, subject, email_text, label)
        String outputFilePath = "data/TrainingDatabse-Processed.csv"; //output
        
        try (CSVReader reader = new CSVReader(new FileReader(inputFilePath));
             CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath))) {

            String[] nextLine;
            List<String[]> allRows = new ArrayList<>();
            int rowCount = 0;

            //reading through each line in csv file
            while ((nextLine = reader.readNext()) != null) {
                //used for debugging, checking how many rows are being read
                rowCount++;
                
                //ensuring that there are 4 columns
                if (nextLine.length >= 4) {
                    //processing sender, subject, and email text
                    String sender = nextLine[0].toLowerCase(); //converting sender to lowercase
                    String subject = nextLine[1].toLowerCase(); //converting subject to lowercase
                    String emailText = nextLine[2]; //original email text

                    //preprocessing the email text
                    String cleanedText = preprocessText(emailText);
                    
                    //replacing the original sender, subject, and email text with the cleaned versions
                    nextLine[0] = sender;
                    nextLine[1] = subject;
                    nextLine[2] = cleanedText;

                    //adding the processed row to a list
                    allRows.add(nextLine);
                } //end if
            } //end while

            //writing all processed rows to the new CSV
            writer.writeAll(allRows);
            System.out.println("Data has been processed and saved to " + outputFilePath);
            System.out.println("Total rows processed: " + rowCount);

        } //end try
        catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        } //end catch
    } //main

    
    //method for preprocessing email text
    private static String preprocessText(String emailText) {
        //removing excessive spaces and new lines
        emailText = emailText.replaceAll("\\s+", " ").trim(); //replacing whitespace w/ a single space

        //removing filler words
        List<String> fillerWords = Arrays.asList("the", "and", "but", "or", "for", "a", "an", "to", "of", "in", "on");
        for (String word : fillerWords) {
            emailText = emailText.replaceAll("\\b" + word + "\\b", "");
        } //end for

        //removing unwanted symbols (keeping only url symbols)
        emailText = emailText.replaceAll("[^a-zA-Z0-9\\s./?&]", "");  //removing equals sign

        //normalizing text
        emailText = StringUtils.stripAccents(emailText); //removing accents/special characters

        //converting email text to lowercase
        emailText = emailText.toLowerCase();

        return emailText;
    } //end preprocessText
    
} //end class