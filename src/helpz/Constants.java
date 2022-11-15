package helpz;

public class Constants {

    public static class Direction{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class EnemyType{
        public static final int ORC = 0;
        public static final int SLIME = 1;
        public static final int TENTACLE = 2;
        public static final int BALL = 3;

        public static float GetSpeed(int enemyType){
            switch(enemyType){
                case ORC:
                    return 0.5f;
                case SLIME:
                    return 0.3f;
                case TENTACLE:
                    return 0.4f;
                case BALL:
                    return 0.7f;
            }
            return 0;
        }
    }

    public static class TowerType{
        public static final int ARCHER = 0;
        public static final int CANNON = 1;
        public static final int MAGE = 2;
        public static final int YES = 3;
    }

    public static class Tiles{
        public static final int WATER_TILE = 0;
        public static final int GRASS_TILE = 1;
        public static final int ROAD_TILE = 2;
       // public static final int ELSE = 3;
    }





}
