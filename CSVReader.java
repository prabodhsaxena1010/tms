package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.util.TimeSeriesBean;

public class CSVReader {
	static List<String[]> listOfRows=new ArrayList<>();;
    static long rowCount=0;
    //static int valPer=60;
    static String delimittedBy=",";
    static String NEW_LINE_SEPARATOR = "\n";
    //static String valCSVPath = "D:/Per/Validation.csv";
    //static String allCSVPath = "D:/Per/AllRow.csv";
    //static String testingCSVPath = "D:/Per/Testing.csv";
    static int index_X=0;
    static int index_Y=2;
    
    public List<String[]>readCSV(String csvFile) {

        //String csvFile = "D:/R/bcccarpark.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        
        try {
        	br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
            	rowCount ++;
                // use comma as separator
            	String[] card = line.split(cvsSplitBy);
            	listOfRows.add(card);
                //System.out.println("TDate = " + card[0] + ", Amount=" + card[2] );

            }
            //createPercentageCSV(index_X,index_Y,rowCount,valCSVPath,allCSVPath,testingCSVPath,valPer);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return listOfRows;
    }
    
    //public void createPercentageCSV(int x,int y,long totalRow, String validationCSVPath, String allRowCSVPath,String testCSVPath, int testignPer){
    public void createPercentageCSV(TimeSeriesBean tsBean){    
    	long testingRows;
    	int testingFlag=0;
    	
    	//boolean alreadyExists = new File(validationCSVPath).exists();
    	System.out.println("INFO: totalRowCount: "+tsBean.getAllRowCount());
    	testingRows= (int) ((tsBean.getAllRowCount()*tsBean.getValidationPer())/100);
    	System.out.println("INFO: testingRows: "+testingRows);
    	
    	FileWriter validationFileWriter = null;
    	FileWriter allRowsFileWriter = null;
    	FileWriter testingFileWriter = null;
		try {
			validationFileWriter = new FileWriter(tsBean.getValCSVPath());
			allRowsFileWriter = new FileWriter(tsBean.getAllCSVPath());
			testingFileWriter = new FileWriter(tsBean.getTestingCSVPath());
		
    	for(String[] temp: tsBean.getRowList()){
    		
    		if(testingFlag<testingRows){
    						
    			validationFileWriter.append(String.valueOf(temp[tsBean.getIndexX()]));
    			validationFileWriter.append(delimittedBy);
    			validationFileWriter.append(String.valueOf(temp[tsBean.getIndexY()]));
    			validationFileWriter.append(delimittedBy);
    			validationFileWriter.append(NEW_LINE_SEPARATOR);
    			testingFlag++;
					} else{
						testingFileWriter.append(String.valueOf(temp[tsBean.getIndexX()]));
						testingFileWriter.append(delimittedBy);
						testingFileWriter.append(String.valueOf(temp[tsBean.getIndexY()]));
						testingFileWriter.append(delimittedBy);
						testingFileWriter.append(NEW_LINE_SEPARATOR);
					}
			allRowsFileWriter.append(String.valueOf(temp[tsBean.getIndexX()]));
			allRowsFileWriter.append(delimittedBy);
			allRowsFileWriter.append(String.valueOf(temp[tsBean.getIndexY()]));
			allRowsFileWriter.append(delimittedBy);
			allRowsFileWriter.append(NEW_LINE_SEPARATOR);
    	}
			}catch (IOException e) {
		// TODO Auto-generated catch block
				e.printStackTrace();
				}finally {
						try {
							validationFileWriter.flush();
							validationFileWriter.close();
							allRowsFileWriter.flush();
							allRowsFileWriter.close();
							testingFileWriter.flush();
							testingFileWriter.close();
						} catch (IOException e) {
							System.out.println("ERROR: Error while flushing/closing fileWriter !!!");
							e.printStackTrace();
							}
		
					}
    }
}

