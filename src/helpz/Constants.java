package helpz;

public class Constants {

    public static class Direction {
        public static final int LEFT = 20;
        public static final int UP = 21;
        public static final int RIGHT =18;
        public static final int DOWN = 19;
    }

    public static class ProjectileType {
        public static final int ARROW = 0;
        public static final int BOMB = 1;
        public static final int FROST_BEEM = 2;
        public static final int MINES = 3;
        public static final int POISON_POTION = 4;


        public static float getSpeed(int projectileType) {
            switch (projectileType) {
                case ARROW:
                    return 8f;
                case BOMB:
                    return 4f;
                case FROST_BEEM:
                    return 6f;
                case MINES:
                    return 3f;
                case POISON_POTION:
                    return 4f;
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
                case MINES:
                    return false;
                case POISON_POTION:
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
                case MINES:
                    return false;
                case POISON_POTION:
                    return true;
            }
            return false;
        }

        public static float getRadiusExplosion(int projectileType) {
            if (isAoe(projectileType)) {
                switch (projectileType) {
                    case BOMB:
                        return 80f;
                    case POISON_POTION:
                        return 80f;
                }
            }
            return 0;
        }

    }

    public static class EnemyType {
        public static final int ORC = 0;
        public static final int ORK_ZOMBI = 1;
        public static final int TENTACLE = 2;
        public static final int SLIME = 3;

        public static float getSpeed(int enemyType) {
            switch (enemyType) {
                case ORC:
                    return 0.5f;
                case ORK_ZOMBI:
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
                case ORK_ZOMBI:
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
                case ORK_ZOMBI:
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
        public static final int MINES_FACTORY = 3;
        public static final int POISON_TOWER = 4;
      public static final int BOOM_VOLCANO = 5;
//        public static final int CANNON = 6;
//        public static final int FROST_MAGE = 7;
//        public static final int MINES_FACTORY = 8;
//        public static final int POISON_TOWER = 9;

        public static String getName(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return "Archer";
                case CANNON:
                    return "Cannon";
                case FROST_MAGE:
                    return "Frost Mage";
                case MINES_FACTORY:
                    return "Mines Factory";
                case POISON_TOWER:
                    return "Poison Tower";
                case BOOM_VOLCANO:
                    return "Volcano";
//                case CANNON:
//                    return "Cannon";
//                case FROST_MAGE:
//                    return "Frost Mage";
//                case MINES_FACTORY:
//                    return "Mines Factory";
//                case POISON_TOWER:
//                    return "Poison Tower";
                default:
                    return "";
            }
        }

        public static boolean isSlow(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return false;
                case CANNON:
                    return false;
                case FROST_MAGE:
                    return true;
                case MINES_FACTORY:
                    return false;
                case POISON_TOWER:
                    return true;
                case BOOM_VOLCANO:
                    return false;
//                case CANNON:
//                    return false;
//                case FROST_MAGE:
//                    return true;
//                case MINES_FACTORY:
//                    return false;
//                case POISON_TOWER:
//                    return true;
                default:
                    return false;
            }
        }

        public static float getPowerOfSlow(int towerType) {
            if (isSlow(towerType)) {
                switch (towerType) {
                    case FROST_MAGE:
                        return 0.5f;
                    case POISON_TOWER:
                        return 0.5f;
//                    case FROST_MAGE:
//                        return 0.5f;
//                    case POISON_TOWER:
//                        return 0.5f;
                    default:
                        return 0;
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
                case MINES_FACTORY:
                    return 200;
                case POISON_TOWER:
                    return 200;
                case BOOM_VOLCANO:
                    return 400;
//                case CANNON:
//                    return 150;
//                case FROST_MAGE:
//                    return 125;
//                case MINES_FACTORY:
//                    return 200;
//                case POISON_TOWER:
//                    return 200;
                default:
                    return 0;
            }
        }

        public static float getDefaultCooldown(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return 30;
                case CANNON:
                    return 90;
                case FROST_MAGE:
                    return 0;
                case MINES_FACTORY:
                    return 90;
                case POISON_TOWER:
                    return 90;
                case BOOM_VOLCANO:
                    return 100;
//                case CANNON:
//                    return 90;
//                case FROST_MAGE:
//                    return 0;
//                case MINES_FACTORY:
//                    return 90;
//                case POISON_TOWER:
//                    return 90;
                default:
                    return 0;
            }

        }

        public static float getDefaultRange(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return 300;
                case CANNON:
                    return 250;
                case FROST_MAGE:
                    return 100;
                case MINES_FACTORY:
                    return 96;
                case POISON_TOWER:
                    return 250;
                case BOOM_VOLCANO:
                    return  200;
//                case CANNON:
//                    return 250;
//                case FROST_MAGE:
//                    return 100;
//                case MINES_FACTORY:
//                    return 96;
//                case POISON_TOWER:
//                    return 250;
                default:
                    return 0;
            }
        }

        public static int getDefaultDmg(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return 5;
                case CANNON:
                    return 15;
                case FROST_MAGE:
                    return 0;
                case MINES_FACTORY:
                    return 10;
                case POISON_TOWER:
                    return 1;
                case BOOM_VOLCANO:
                    return 10;
//                case CANNON:
//                    return 15;
//                case FROST_MAGE:
//                    return 0;
//                case MINES_FACTORY:
//                    return 10;
//                case POISON_TOWER:
//                    return 1;
                default:
                    return 0;
            }
        }
//dot - damage over time
        public static boolean isDOT(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return false;
                case CANNON:
                    return false;
                case FROST_MAGE:
                    return true;
                case MINES_FACTORY:
                    return false;
                case POISON_TOWER:
                    return true;
                case BOOM_VOLCANO:
                    return false;
//                case CANNON:
//                    return false;
//                case FROST_MAGE:
//                    return true;
//                case MINES_FACTORY:
//                    return false;
//                case POISON_TOWER:
//                    return true;
                default:
                    return false;
            }
        }
        public static int getDefaulDuration(int towerType) {
            if (isSlow(towerType)) {
                switch (towerType) {
                    case FROST_MAGE:
                        return 3;
                    case POISON_TOWER:
                        return 300;
                    default:
                        return 0;
                }
            }
            return 1;
        }

    }

    public static class Tiles {
        public static final int WATER_TILE = 0;
        public static final int GRASS_TILE = 1;
        public static final int ROAD_TILE = 2;
        public static final int ROAD_DIR = 3;
    }


}
