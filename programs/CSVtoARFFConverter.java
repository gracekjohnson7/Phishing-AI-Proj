//PURPOSE: This program converts the test data from CSV (Excel standard) to ARFF (Weka standard)

import java.io.File;
import java.io.IOException;

//from weka.jar
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.ArffSaver;

public class CSVtoARFFConverter {
    public static void main(String[] args) {
        try {
            //loading the csv file
            CSVLoader loader = new CSVLoader();
            loader.setSource(new File("data/TrainingDatabase-Processed.csv")); //csv file, input
            Instances data = loader.getDataSet();
            
            //saving as arff file
            ArffSaver saver = new ArffSaver();
            saver.setInstances(data);
            saver.setFile(new File("data/TrainingDatabase-Processed.arff")); //arff file, output
            saver.writeBatch();
            
            System.out.println("CSV to ARFF conversion completed.");
        } //end try
        
        catch (IOException e) {
            e.printStackTrace();
        } //end catch
        
    } //end main
} //end class


//NOTE: ONCE PROCESSED, OPEN FILE AS TEXT AND CHANGE line 6 FROM @attribute label numeric TO @attribute label {0,1}