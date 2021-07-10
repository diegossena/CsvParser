import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;

import java.util.List;
import java.util.ArrayList;

import java.io.IOException;

public class csvParser {

    public static void main(String[] args) throws IOException {
        byte[] lines = Files.readAllBytes(Paths.get("sample.csv"));
        String buffer = new String(lines,Charset.forName("UTF-8"));

        String[][] csv = parseLines(buffer);
        //System.out.println(csv[34][0]);
        for (String[] row : csv) {
            for (String col : row) {
                System.out.print(col+'\t');
            }
            System.out.println();
        }
    }
    /* Config
    ----------*/
    private static char quote = '"';
    private static char separator = ',';
    /* CSV Parse
    -------------*/
    private static String[][] parseLines(String csvTextString) {
        String[] lines = csvTextString.split("\n");
        String[][] parsedCSV = new String[lines.length][];

        for(int i=0;i<lines.length;i++){
            parsedCSV[i] = parseColumns(lines[i]);
        }

        return parsedCSV;
    }
    private static String[] parseColumns(String csvLine) {
        List<String> columns = new ArrayList<>(); // Column list
        StringBuffer curVal = new StringBuffer();

        boolean inQuotes = false;
        int i = 0;
        int lastIndex = csvLine.length()-1;
        char ch = csvLine.charAt(i);
        if(ch == quote){
            i = 1;
            ch = csvLine.charAt(i);
            char nextCh = csvLine.charAt(i+1);
            boolean firstQuote = false;

            for(;i < lastIndex && firstQuote == false;i++){
                ch = csvLine.charAt(i);
                nextCh = csvLine.charAt(i+1);
                if(ch == '"'){
                    if(nextCh == '"'){
                        if(inQuotes){
                            inQuotes = false;
                        }else{
                            inQuotes = true;
                        }
                        i++;
                    }else{
                        firstQuote = true;
                    }
                }else{
                    if(inQuotes || ch != separator){
                        curVal.append(ch);
                    }else{
                        columns.add(curVal.toString());
                        curVal = new StringBuffer();
                    }
                }
            }
            for(;csvLine.charAt(i)!=',' && i < lastIndex;i++);
            if(i!=lastIndex){
                columns.add(curVal.toString());
                curVal = new StringBuffer();
                i++;
            }
        }
        for(;i<=lastIndex;i++){
            ch = csvLine.charAt(i);
            if(ch == '"'){
                if(inQuotes){
                    inQuotes = false;
                }else{
                    inQuotes = true;
                }
            }else{
                if(inQuotes || ch != separator){
                    curVal.append(ch);
                }else{
                    columns.add(curVal.toString());
                    curVal = new StringBuffer();
                }
            }
        }
        columns.add(curVal.toString());
        return columns.toArray(new String[columns.size()]);
    }
}
