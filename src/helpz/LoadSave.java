package helpz;

import objects.PathPoint;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadSave {

    public static BufferedImage getSpriteAtlas() {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("pngFile/tak.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }


//    //txt file
//    public static void CreateFile() {
//        File textFile = new File("res/test.txt");
//        try {
//            textFile.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    private static void WriteToFile(File f, int[] idArr, PathPoint start, PathPoint end) {//,int[] directionArr
        try {
            PrintWriter pw = new PrintWriter(f);
            for (Integer i : idArr) {
                pw.println(i);
            }
            pw.println(start.getxCord());
            pw.println(start.getyCord());
            pw.println(end.getxCord());
            pw.println(end.getyCord());
//            for (Integer i : directionArr) {
//                pw.println(i);
//            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void SaveXpToFile(File f,int xp) {//tree unlocked
        try {
            PrintWriter pw = new PrintWriter(f);
            pw.println(xp);
            pw.println(xp);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Integer> ReadSaveFile(File file) {
        ArrayList<Integer> list = new ArrayList<>();
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

    public static int[][] GetXpData() {
        File saveFile = new File("res/save.txt");
        if (saveFile.exists()) {
            ArrayList<Integer> list = ReadSaveFile(saveFile);
            return Utilz.ArrayListTo2Dint(list, 20, 20);
        } else {
            System.out.println("File: save.txt nieistnieje");
            return null;
        }
    }

    private static ArrayList<Integer> ReadFromFile(File file) {
        ArrayList<Integer> list = new ArrayList<>();
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

    public static ArrayList<PathPoint> getPathPoints(){
        File lvlFile = new File("res/level1.txt");
        if (lvlFile.exists()) {
            ArrayList<Integer> list = ReadFromFile(lvlFile);
            ArrayList<PathPoint> points = new ArrayList<>();
            points.add(new PathPoint(list.get(400),list.get(401)));
            points.add(new PathPoint(list.get(402),list.get(403)));
            return points;
        } else {
            System.out.println("File:  newlevel nieistnieje");
            return null;
        }
    }

    public static int[][] GetLevelData() {
        File lvlFile = new File("res/level1.txt");
        if (lvlFile.exists()) {
            ArrayList<Integer> list = ReadFromFile(lvlFile);
            return Utilz.ArrayListTo2Dint(list, 20, 20);
        } else {
            System.out.println("File: level1.txt nieistnieje");
            return null;
        }
    }

    public static void CreateLevel(String name, int[] idArr) {
        File newLvl = new File("res/" + name + ".txt");
        if (newLvl.exists()) {
            System.out.println("file" + name + "exists");

        } else {
            try {
                newLvl.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            WriteToFile(newLvl, idArr, new PathPoint(0,0), new PathPoint(0,0));

        }
    }

    public static void SaveLevel(String name, int[][] idArr, PathPoint start, PathPoint end) {
        File lvlFile = new File("res/" + name + ".txt");
        if (lvlFile.exists()) {
            WriteToFile(lvlFile, Utilz.TwoDto1DintArr(idArr), start, end);
        } else {
            System.out.println("file " + name + " does not exists");
            return ;
        }
    }


}
