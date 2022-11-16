package helpz;

public class Constants {

    public static class Direction {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class ProjectileType {
        public static final int ARROW = 0;
        public static final int BOMB = 1;
        public static final int BEEM = 2;
        // public static final int ELSE = 3;


        public static float GetSpeed(int enemyType) {
            switch (enemyType) {
                case ARROW:
                    return 3f;
                case BOMB:
                    return 2f;
                case BEEM:
                    return 4f;
            }
            return 0;
        }

    }

    public static class EnemyType {
        public static final int ORC = 0;
        public static final int SLIME = 1;
        public static final int TENTACLE = 2;
        public static final int BALL = 3;

        public static float GetSpeed(int enemyType) {
            switch (enemyType) {
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

        public static int GetStartHealth(int enemyType) {
            switch (enemyType) {
                case ORC:
                    return 100;
                case SLIME:
                    return 200;
                case TENTACLE:
                    return 150;
                case BALL:
                    return 70;
            }
            return 0;
        }
    }

    public static class TowerType {
        public static final int ARCHER = 0;
        public static final int CANNON = 1;
        public static final int MAGE = 2;
        public static final int YES = 3;

        public static String GetName(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return "Archer";
                case CANNON:
                    return "Cannon";
                case MAGE:
                    return "Mage";
                case YES:
                    return "Yes";
            }
            return "";
        }

        public static float GetDefaultCooldown(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return 15;
                case CANNON:
                    return 50;
                case MAGE:
                    return 5;
                case YES:
                    return 500;
            }
            return 0;
        }

        public static float GetDefaultRange(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return 200;
                case CANNON:
                    return 200;
                case MAGE:
                    return 200;
                case YES:
                    return 500;
            }
            return 0;
        }

        public static float GetDefaultDmg(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return 15;
                case CANNON:
                    return 50;
                case MAGE:
                    return 5;
                case YES:
                    return 500;
            }
            return 0;
        }
    }

    public static class Tiles {
        public static final int WATER_TILE = 0;
        public static final int GRASS_TILE = 1;
        public static final int ROAD_TILE = 2;
        // public static final int ELSE = 3;
    }


}
