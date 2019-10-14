import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//java -Dfile.encoding=UTF-8 DictBuild <lexicon_path> <input_path> <output_path>
public class DictBuild {
    private static class Dict{
        ArrayList<String> dict;
        int length;

        public Dict(ArrayList<String> dict){
            int t=0;
            this.dict = dict;
            for(String i : dict){
                if(i.length() > t)
                    t = i.length();
            }
            this.length = t;
        }
    }
    private static PrintWriter pw;

    public static void main(String args[]) throws IOException{
        ArrayList<String> dictSource = new ArrayList<>();
        ArrayList<String> target = new ArrayList<>();
        String temp = "";
        int count = 0;
        //readFile(dictSource, "work1/lexicon_test.txt");
        readFile(dictSource, args[0]);
        //readFile(target, "work1/wsegInput_test.txt");
        readFile(target, args[1]);
        Dict dict = new Dict(dictSource);
        //PrintWriter writer = new PrintWriter("work1/wsegOutput.txt", StandardCharsets.UTF_8);
        PrintWriter writer = new PrintWriter(args[2], StandardCharsets.UTF_8);
        pw = writer;

        for(String i: target){
            System.out.println("Sentence : " + i);
            writer.println("Sentence " + ++count + ": " + i);
            segment(dict, i, 0, temp);
            //System.out.println();
            writer.println();
        }
        //show(target);
        //System.out.println(target.get(0));
        //segment(dict, target.get(0), 0, temp);
        writer.close();
    }

    public static void readFileTest() throws IOException {
        FileReader fr = new FileReader("src/test.txt");
        BufferedReader br = new BufferedReader(fr);
        StringBuilder sb = new StringBuilder();

        while(br.ready()){
            sb.append(br.readLine());
            System.out.println("length:" + sb.length());
            System.out.println(sb);
            sb.setLength(0);
        }
        fr.close();
    }

    public static void readFile(ArrayList<String> dict, String path) throws IOException{
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        String temp;

        while(br.ready()){
            temp = br.readLine();
            dict.add(temp);
        }
        fr.close();
    }

    public static void show(ArrayList<String> list){
        int i=0;
        for(String n : list){
            System.out.println("line" + ++i + " = " + n);
        }
    }

    public static void segment(Dict dict, String input, int head, String temp) throws IOException{
        if(head >= input.length()){
            System.out.println(temp);
            pw.println(temp);
            return;
        }

        for(int i=dict.length; i>0; i--){
            if(head+i <= input.length() && dict.dict.contains(input.substring(head, head+i))){
                //System.out.print("temp " + input.substring(head, head+i) + " ");
                segment(dict, input, head+i, temp.concat(" ").concat(input.substring(head, head+i)));
            }
        }
    }
}