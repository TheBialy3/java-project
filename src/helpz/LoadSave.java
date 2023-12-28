package helpz;

import objects.Card;
import objects.PathPoint;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadSave {



    public static BufferedImage getSpriteAtlas() {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("pngFile/final.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public static BufferedImage getImg(String name) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("pngFile/"+name+".png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
    public static BufferedImage getBackgroundImg() {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("pngFile/back.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public static BufferedImage getCardSprite() {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("pngFile/cardLong.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
    public static BufferedImage getCardChooseSprite() {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("pngFile/cardLongChoose.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }



    private static void WriteToFile(File f, int[] idArr, PathPoint start, PathPoint end, int[] directionArr) {//
        try {
            PrintWriter pw = new PrintWriter(f);
            for (Integer i : idArr) {
                pw.println(i);
            }
            pw.println(start.getxCord());
            pw.println(start.getyCord());
            pw.println(end.getxCord());
            pw.println(end.getyCord());
            for (Integer i : directionArr) {
                pw.println(i);
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static ArrayList<Integer> ReadSaveFile(File file) {
        ArrayList<Integer> list = new ArrayList<>();
        try {
            Scanner sc = new Scanner(file);
            list.add(Integer.parseInt(sc.nextLine()));
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }


    private static ArrayList<Boolean> ReadSaveFile(File file, int startOfReading) {
        ArrayList<Boolean> list = new ArrayList<>();
        try {
            Scanner sc = new Scanner(file);
            for (int i = 0; i < startOfReading; i++) {
                sc.nextLine();
            }
            while (sc.hasNextLine()) {
                list.add(Boolean.parseBoolean(sc.nextLine()));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static ArrayList<Integer> GetXpData() {
        File saveFile = new File("res/textFile/save.txt");
        if (saveFile.exists()) {
            ArrayList<Integer> list = ReadSaveFile(saveFile);
            return list;
        } else {
            System.out.println("File: save.txt nieistnieje");
            return null;
        }
    }

    public static ArrayList<Boolean> GetCardSave() {
        File saveFile = new File("res/textFile/save.txt");
        if (saveFile.exists()) {
            ArrayList<Boolean> list = ReadSaveFile(saveFile, 1);
            return list;
        } else {
            System.out.println("File: save.txt nieistnieje");
            return null;
        }
    }

    public static void SaveXpToFile(int xp, ArrayList<Card> cards) {//tree unlocked

        try {
            PrintWriter pw = new PrintWriter("res/textFile/save.txt");
            pw.println(xp);
            for (Card card : cards) {
                pw.println(card.isUnlocked());
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
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

    public static ArrayList<PathPoint> getPathPoints() {
        File lvlFile = new File("res/textFile/level1.txt");
        if (lvlFile.exists()) {
            ArrayList<Integer> list = ReadFromFile(lvlFile);
            ArrayList<PathPoint> points = new ArrayList<>();
            points.add(new PathPoint(list.get(400), list.get(401)));
            points.add(new PathPoint(list.get(402), list.get(403)));
            return points;
        } else {
            System.out.println("File:  newlevel nieistnieje");
            return null;
        }
    }

    public static int[][] GetLevelDir() {
        File lvlFile = new File("res/textFile/level1.txt");
        if (lvlFile.exists()) {
            ArrayList<Integer> list = ReadFromFile(lvlFile);
            return Utilz.ArrayListTo2DintStartAt(list, 20, 20, 404);
        } else {
            System.out.println("File: level1.txt nieistnieje");
            return null;
        }
    }

    public static int[][] GetLevelData() {
        File lvlFile = new File("res/textFile/level1.txt");
        if (lvlFile.exists()) {
            ArrayList<Integer> list = ReadFromFile(lvlFile);
            return Utilz.ArrayListTo2Dint(list, 20, 20);
        } else {
            System.out.println("File: level1.txt nieistnieje");
            return null;
        }
    }

    public static void CreateSaveFile() {
        File saveFile = new File("res/textFile/save.txt");
        if (saveFile.exists()) {
            System.out.println("file save.txt exists");

        } else {
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //  WriteToFile(newLvl, idArr, new PathPoint(0,0), new PathPoint(0,0));

        }
    }
    //can be used in future
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

            //  WriteToFile(newLvl, idArr, new PathPoint(0,0), new PathPoint(0,0));

        }
    }

    public static void SaveLevel(String name, int[][] idArr, PathPoint start, PathPoint end, int[][] dirArr) {
        File lvlFile = new File("res/textFile/" + name + ".txt");
        if (lvlFile.exists()) {
            WriteToFile(lvlFile, Utilz.TwoDto1DintArr(idArr), start, end, Utilz.TwoDto1DintArr(dirArr));
        } else {
            System.out.println("file " + name + " does not exists");
            return;
        }
    }


    public static BufferedImage getSpriteLogos() {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("pngFile/logos.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}
