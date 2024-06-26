package helpz;

import objects.Card;
import objects.PathPoint;
import objects.TowerPlace;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadSave {

    protected static int startIndex = 400;

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
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("pngFile/" + name + ".png");
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


    private static void WriteToFile(File f, int[] idArr, ArrayList<PathPoint> enemyPathRoad, int sizeOfEnemyPathRoad,ArrayList<TowerPlace> towerPlaces, int sizeOfTowerPlaces) {//
        try {
            PrintWriter pw = new PrintWriter(f);
            for (Integer i : idArr) {
                pw.println(i);
            }
            pw.println(sizeOfEnemyPathRoad);
            for (PathPoint i : enemyPathRoad) {
                pw.println(i.getxCord());
            }
            for (PathPoint i : enemyPathRoad) {
                pw.println(i.getyCord());
            }
            pw.println(sizeOfTowerPlaces);
            for (TowerPlace i : towerPlaces) {
                pw.println(i.getX());
            }
            for (TowerPlace i : towerPlaces) {
                pw.println(i.getY());
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

    private static ArrayList<Boolean> ReadSaveFile(File file, int startOfReading, int amountOfLines) {
        ArrayList<Boolean> list = new ArrayList<>();
        try {
            Scanner sc = new Scanner(file);
            for (int i = 0; i < startOfReading; i++) {
                sc.nextLine();
            }
            for (int i = 0; i < amountOfLines; i++) {
                if (sc.hasNextLine()) {
                    list.add(Boolean.parseBoolean(sc.nextLine()));
                }
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


    public static ArrayList<PathPoint> GetLevelDir() {
        File lvlFile = new File("res/textFile/level1.txt");
        if (lvlFile.exists()) {
            ArrayList<Integer> list = ReadFromFile(lvlFile);
            ArrayList<PathPoint> pathForEnemies = new ArrayList<>();
            int sizeOfDirArr = list.get(startIndex);
            for (int i = 0; i < sizeOfDirArr; i++) {
                pathForEnemies.add(new PathPoint(list.get(startIndex + 1 + i), list.get(startIndex + i + sizeOfDirArr + 1)));
            }
            return pathForEnemies;
        } else {
            System.out.println("File: level1.txt nieistnieje");
            return null;
        }
    }
    public static ArrayList<TowerPlace> GetLevelTowerPlaces() {
        File lvlFile = new File("res/textFile/level1.txt");
        if (lvlFile.exists()) {
            ArrayList<Integer> list = ReadFromFile(lvlFile);
            ArrayList<TowerPlace> pathForEnemies = new ArrayList<>();

            int sizeOfDirArr = list.get(startIndex);
            int sizeOfTowerPlaceArr = list.get(startIndex+sizeOfDirArr*2+1);
            int startCountDown=startIndex+sizeOfDirArr*2+2;

            for (int i = 0; i < sizeOfTowerPlaceArr; i++) {
                pathForEnemies.add(new TowerPlace(list.get(startCountDown  + i), list.get(startCountDown + i + sizeOfTowerPlaceArr )));
            }
            return pathForEnemies;
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

    public static void SaveLevel(String name, int[][] idArr, ArrayList<PathPoint> enemyPathRoad, ArrayList<TowerPlace> towerPlaces) {
        File lvlFile = new File("res/textFile/" + name + ".txt");
        if (lvlFile.exists()) {
            WriteToFile(lvlFile, Utilz.TwoDto1DintArr(idArr), enemyPathRoad, enemyPathRoad.size(),towerPlaces,towerPlaces.size());
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
