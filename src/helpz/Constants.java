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
        public static final int FROST_BEEM = 2;
        public static final int MINE = 3;


        public static float getSpeed(int projectileType) {
            switch (projectileType) {
                case ARROW:
                    return 8f;
                case BOMB:
                    return 4f;
                case FROST_BEEM:
                    return 6f;
                case MINE:
                    return 3f;
            }
            return 0;
        }

        public static boolean isRorating(int projectileType) {
            switch (projectileType) {
                case ARROW:
                    return true;
                case BOMB:
                    return false;
                case FROST_BEEM:
                    return true;
                case MINE:
                    return false;
            }
            return false;
        }

        public static boolean isAoe(int projectileType) {
            switch (projectileType) {
                case ARROW:
                    return false;
                case BOMB:
                    return true;
                case FROST_BEEM:
                    return false;
                case MINE:
                    return false;
            }
            return false;
        }

        public static float getRadiusExplosion(int projectileType) {
            if (isAoe(projectileType)) {
                switch (projectileType) {
                    case BOMB:
                        return 40f;
                }
            }
            return 0;
        }

        public static boolean isSlow(int projectileType) {
            switch (projectileType) {
                case ARROW:
                    return false;
                case BOMB:
                    return false;
                case FROST_BEEM:
                    return true;
                case MINE:
                    return false;
            }
            return false;
        }


    }

    public static class EnemyType {
        public static final int ORC = 0;
        public static final int ANIMATED_ORK = 1;
        public static final int TENTACLE = 2;
        public static final int SLIME = 3;

        public static float getSpeed(int enemyType) {
            switch (enemyType) {
                case ORC:
                    return 0.5f;
                case ANIMATED_ORK:
                    return 0.3f;
                case TENTACLE:
                    return 0.4f;
                case SLIME:
                    return 0.7f;
            }
            return 0;
        }

        public static int getStartHealth(int enemyType) {
            switch (enemyType) {
                case ORC:
                    return 100;
                case ANIMATED_ORK:
                    return 200;
                case TENTACLE:
                    return 150;
                case SLIME:
                    return 70;
            }
            return 0;
        }

        public static int getGoldWorth(int enemyType) {
            switch (enemyType) {
                case ORC:
                    return 10;
                case ANIMATED_ORK:
                    return 15;
                case TENTACLE:
                    return 15;
                case SLIME:
                    return 8;
            }
            return 0;
        }
    }

    public static class TowerType {
        public static final int ARCHER = 0;
        public static final int CANNON = 1;
        public static final int FROST_MAGE = 2;
        public static final int MINE_FACTORY = 3;

        public static String getName(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return "Archer";
                case CANNON:
                    return "Cannon";
                case FROST_MAGE:
                    return "Frost Mage";
                case MINE_FACTORY:
                    return "Mine Factory";
            }
            return "";
        }

        public static boolean isSlow(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return false;
                case CANNON:
                    return false;
                case FROST_MAGE:
                    return true;
                case MINE_FACTORY:
                    return false;
            }
            return false;
        }

        public static float getPowerOfSlow(int towerType) {
            if (isSlow(towerType)) {
                switch (towerType) {
                    case FROST_MAGE:
                        return 0.5f;
                }
            }
            return 1f;
        }

        public static int getCost(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return 100;
                case CANNON:
                    return 150;
                case FROST_MAGE:
                    return 125;
                case MINE_FACTORY:
                    return 200;
            }
            return 0;
        }

        public static float getDefaultCooldown(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return 30;
                case CANNON:
                    return 90;
                case FROST_MAGE:
                    return 0;
                case MINE_FACTORY:
                    return 90;
            }
            return 0;
        }

        public static float getDefaultRange(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return 300;
                case CANNON:
                    return 250;
                case FROST_MAGE:
                    return 100;
                case MINE_FACTORY:
                    return 96;
            }
            return 0;
        }

        public static int getDefaultDmg(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return 5;
                case CANNON:
                    return 15;
                case FROST_MAGE:
                    return 0;
                case MINE_FACTORY:
                    return 10;
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
