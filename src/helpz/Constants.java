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


        public static float GetSpeed(int projectileType) {
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
                    return true;
                case MINE:
                    return true;
            }
            return false;
        }

        public static float GetRadiusExplosion(int projectileType) {
            if (isAoe(projectileType)) {
                switch (projectileType) {
                    case BOMB:
                        return 40f;
                    case FROST_BEEM:
                        return 50f;
                    case MINE:
                        return 50f;
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

        public static float GetPowerOfSlow(int projectileType) {
            if (isSlow(projectileType)) {
                switch (projectileType) {
                    case FROST_BEEM:
                        return 0.5f;
                }
            }
            return 1f;
        }
    }

    public static class EnemyType {
        public static final int ORC = 0;
        public static final int ANIMATED_ORK = 1;
        public static final int TENTACLE = 2;
        public static final int SLIME = 3;

        public static float GetSpeed(int enemyType) {
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

        public static int GetStartHealth(int enemyType) {
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

        public static int GetGoldWorth(int enemyType) {
            switch (enemyType) {
                case ORC:
                    return 10;
                case ANIMATED_ORK:
                    return 12;
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

        public static String GetName(int towerType) {
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

        public static int GetCost(int towerType) {
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

        public static float GetDefaultCooldown(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return 25;
                case CANNON:
                    return 100;
                case FROST_MAGE:
                    return 40;
                case MINE_FACTORY:
                    return 22;
            }
            return 0;
        }

        public static float GetDefaultRange(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return 400;
                case CANNON:
                    return 200;
                case FROST_MAGE:
                    return 300;
                case MINE_FACTORY:
                    return 96;
            }
            return 0;
        }

        public static int GetDefaultDmg(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return 5;
                case CANNON:
                    return 15;
                case FROST_MAGE:
                    return 1;
                case MINE_FACTORY:
                    return 10;
            }
            return 0;
        }
    }

    public static class Upgrades {
        public static final int ONE1 = 0;
        public static final int ONE2 = 1;
        public static final int ONE3 = 2;
        // public static final int ELSE = 3;

        public static int GetCost(int upgrade) {
            switch (upgrade) {
                case ONE1:
                    return 100;
                case ONE2:
                    return 150;
                case ONE3:
                    return 125;
            }
            return 0;
        }

        public static int GetBuff(int upgrade) {
            switch (upgrade) {
                case ONE1:
                    return 100;
                case ONE2:
                    return 150;
                case ONE3:
                    return 125;
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
