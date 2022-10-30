package helpz;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadSave {

    public static BufferedImage getSpriteAtlas() {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("tak.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }


    //txt file
    public static void CreateFile() {
        File textFile = new File("res/test.txt");
        try {
            textFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void WriteToFile(File f, int[] idArr) {
        try {
            PrintWriter pw = new PrintWriter(f);
            for (Integer i : idArr) {
                pw.println(i);
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Integer> ReadFromFile(File file) {
        ArrayList<Integer> list=new ArrayList<>();
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                list.add(Integer.parseInt(sc.nextLine()));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static int[][] GetLevelData(String name){
        File lvlFile = new File("res/"+name+".txt");
        if(lvlFile.exists()){
            ArrayList<Integer> list = ReadFromFile(lvlFile);
            return Utilz.ArrayListTo2Dint(list,20,20);
        }else{
            System.out.println("File: "+name+" nieistnieje");
            return null;
        }

    }

    public static void CreateLevel(String name, int[] idArr) {
        File newLvl = new File("res/" + name + ".txt");
        if (newLvl.exists()) {
            System.out.println("file" + name + "exists");
            return;
        } else {
            try {
                newLvl.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            WriteToFile(newLvl,idArr);

        }
    }

    public static void SaveLevel(String name,int[][] idArr) {
        File lvlFile = new File("res/"+name+".txt");
        if (lvlFile.exists()) {
           WriteToFile(lvlFile,Utilz.TwoDTo1Dint(idArr));
        } else {
            System.out.println("file " + name + " does not exists");
            return;
        }
    }
}
