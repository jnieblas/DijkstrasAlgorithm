import java.io.*;

public class TopoValidator {

    BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));
    private int numOfRouters;
    private int[] numsInLine;
    private int lineCount;
    private int[] costMatrix;
    private int manualEntryCounter = 0;
    private String fileName = "topo.txt";

    public int GetNumOfRouters() throws IOException{
        int n = 0;
        while(n < 2){
            System.out.println("Please enter the number of numOfRouters (must be >= 2): ");
            n = Integer.parseInt(sysIn.readLine());
        }
        //initiate costMatrix;
        return n;
    }

    public void ExtractNumsInLine(String line){
        String[] numberSeparator = line.split("\t");
        if(numberSeparator.length != 2){
            System.out.println("Incorrect number of values at line " + lineCount);
            manualEntryCounter++;
        } else {
            numsInLine = new int[numberSeparator.length];
            for (int i = 0; i < 3; i++) {
                String currentNum = numberSeparator[i];
                numsInLine[i] = Integer.parseInt(currentNum);
            }
        }
    }

    public void ValidateTopoTxt() throws IOException{
        numOfRouters = GetNumOfRouters();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String currentLine;
            while((currentLine = bufferedReader.readLine()) != null){
                if(manualEntryCounter > 0){
                    fileReader.close();
                    break;
                }
                lineCount++;
                ExtractNumsInLine(currentLine);
                ValidateRouterNums();
            }
            if(manualEntryCounter > 0){
                RestartFileRead();
            }
            //pass off to dijkstra's algorithm
        } catch (FileNotFoundException ex){
            System.out.println("File does not exist.");
            RestartFileRead();
        }
    }

    public void RestartFileRead() throws IOException{
        System.out.print("Please input the name of the cost input file: ");
        fileName = sysIn.readLine();
        manualEntryCounter = 0;
        ValidateTopoTxt();
    }

    public void ValidateRouterNums(){
        int routerOne = 0;
        int routerTwo = 0;
        int cost = 0;
        for(int i = 0; i < 3; i++){
            int currentNum = numsInLine[i];
            if(i < 2) {
                if ((currentNum > numOfRouters - 1) || (currentNum < 0)) {
                    System.out.println("Invalid router value at line " + lineCount);
                    manualEntryCounter++;
                    break;
                } else{
                    if(i == 0){
                        routerOne = currentNum;
                    } else{
                        routerTwo = currentNum;
                    }
                }
            } else{
                if (currentNum < 1){
                    System.out.println("Invalid cost value at line " + lineCount);
                    manualEntryCounter++;
                    break;
                } else{
                    cost = currentNum;
                }
            }
        }
        if(manualEntryCounter == 0) {
            AddNumsToMatrix(routerOne, routerTwo, cost);
        }
    }

    public void AddNumsToMatrix(int r1, int r2, int cost){

    }
}