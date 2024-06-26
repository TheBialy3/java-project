package helpz;

public class Constants {

    public static class NumbersOf {
        public static final int NUMBER_OF_ENEMIES = 12;
        public static final int NUMBER_OF_TOWERS = 12;
        public static final int NUMBER_OF_PROJECTILE = 6;

    }

    public static class Direction {
        public static final int LEFT = 20;
        public static final int UP = 21;
        public static final int RIGHT = 18;
        public static final int DOWN = 19;
    }

    public static class ProjectileType {
        public static final int ARROW = 0;
        public static final int BOMB = 1;
        public static final int MINES = 2;
        public static final int POISON_POTION = 3;
        public static final int SHORT_BEM = 4;
        public static final int BULLET = 5;


        public static float getSpeed(int projectileType) {
            switch (projectileType) {
                case ARROW:
                    return 8f;
                case BOMB:
                    return 4f;
                case SHORT_BEM:
                    return 10f;
                case MINES:
                    return 6f;
                case POISON_POTION:
                    return 5f;
                case BULLET:
                    return 9f;
            }
            return 0;
        }

        public static boolean isRotating(int projectileType) {
            switch (projectileType) {
                case ARROW:
                    return true;
                case BOMB:
                    return false;
                case SHORT_BEM:
                    return true;
                case MINES:
                    return true;
                case POISON_POTION:
                    return false;
                case BULLET:
                    return true;
            }
            return false;
        }

        public static boolean isAoe(int projectileType) {
            switch (projectileType) {
                case ARROW:
                    return false;
                case BOMB:
                    return true;
                case SHORT_BEM:
                    return false;
                case MINES:
                    return false;
                case POISON_POTION:
                    return true;
                case BULLET:
                    return false;
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

        public static final int BOTH = 0;
        public static final int FLYING = 1;
        public static final int WALKING = 2;

        public static int getProjectileTargetMoveType(int projectileType) {
            switch (projectileType) {
                case ARROW:
                    return BOTH;
                case BOMB:
                    return WALKING;
                case SHORT_BEM:
                    return WALKING;
                case MINES:
                    return BOTH;
                case POISON_POTION:
                    return WALKING;
                case BULLET:
                    return BOTH;
            }
            return 0;
        }
    }

    public static class EnemyType {
        public static final int ORC = 0;
        public static final int ORK_ZOMBIE = 1;
        public static final int TENTACLE = 2;
        public static final int SLIME = 3;
        public static final int CAMEL = 4;
        public static final int CAMEL_JUNIOR = 5;
        public static final int BIRD = 6;
        public static final int GHOST = 7;
        public static final int ROCK = 8;
        public static final int CREEPY_CAT = 9;
        public static final int BIRD_SKELETON = 10;
        public static final int BANSHEE = 11;


        public static String getName(int enemyType) {
            switch (enemyType) {
                case ORC:
                    return "Ork";
                case ORK_ZOMBIE:
                    return "Ork Zombie";
                case TENTACLE:
                    return "Tentacle";
                case SLIME:
                    return "Slime";
                case CAMEL:
                    return "Camel";
                case CAMEL_JUNIOR:
                    return "Camel Spawn";
                case BIRD:
                    return "Bird";
                case GHOST:
                    return "Ghost";
                case ROCK:
                    return "Rock Golem";
                case CREEPY_CAT:
                    return "Creepy Cat";
                case BIRD_SKELETON:
                    return "Bird Skeleton";
                case BANSHEE:
                    return "Banshee";
                default:
                    return "";
            }
        }

        public static float getSpeed(int enemyType) {
            switch (enemyType) {
                case ORC:
                    return 0.35f;
                case ORK_ZOMBIE:
                    return 0.3f;
                case TENTACLE:
                    return 0.4f;
                case SLIME:
                    return 0.7f;
                case CAMEL:
                    return 0.25f;
                case CAMEL_JUNIOR:
                    return 1f;
                case BIRD:
                    return 0.75f;
                case GHOST:
                    return 0.4f;
                case ROCK:
                    return 0.25f;
                case CREEPY_CAT:
                    return 0.65f;
                case BIRD_SKELETON:
                    return 0.8f;
                case BANSHEE:
                    return 0.7f;
            }
            return 0;
        }

        public static final int FLY = 1;
        public static final int WALK = 2;


        public static int getMoveType(int enemyType) {
            switch (enemyType) {
                case ORC:
                    return WALK;
                case ORK_ZOMBIE:
                    return WALK;
                case TENTACLE:
                    return WALK;
                case SLIME:
                    return WALK;
                case CAMEL:
                    return WALK;
                case CAMEL_JUNIOR:
                    return WALK;
                case BIRD:
                    return FLY;
                case GHOST:
                    return WALK;
                case ROCK:
                    return WALK;
                case CREEPY_CAT:
                    return WALK;
                case BIRD_SKELETON:
                    return FLY;
                case BANSHEE:
                    return WALK;
            }
            return 0;
        }

        public static int getHeightOfHitBox(int enemyType) {
            switch (enemyType) {
                case ORC:
                    return 50;
                case ORK_ZOMBIE:
                    return 50;
                case TENTACLE:
                    return 54;
                case SLIME:
                    return 30;
                case CAMEL:
                    return 50;
                case CAMEL_JUNIOR:
                    return 12;
                case BIRD:
                    return 40;
                case GHOST:
                    return 50;
                case ROCK:
                    return 58;
                case BIRD_SKELETON:
                    return 60;
                case CREEPY_CAT:
                    return 64;
                case BANSHEE:
                    return 45;

                default:
                    return 62;
            }
        }

        public static int getWightOfHitBox(int enemyType) {
            switch (enemyType) {
                case ORC:
                    return 44;
                case ORK_ZOMBIE:
                    return 44;
                case TENTACLE:
                    return 40;
                case SLIME:
                    return 54;
                case CAMEL:
                    return 60;
                case CAMEL_JUNIOR:
                    return 12;
                case BIRD:
                    return 60;
                case GHOST:
                    return 30;
                case ROCK:
                    return 34;
                case CREEPY_CAT:
                    return 64;
                case BIRD_SKELETON:
                    return 64;
                case BANSHEE:
                    return 25;
                default:
                    return 56;
            }
        }

        public static int getAttackEnemy(int enemyType) {
            switch (enemyType) {
                case ORC:
                    return 30;
                case ORK_ZOMBIE:
                    return 40;
                case TENTACLE:
                    return 30;
                case SLIME:
                    return 20;
                case CAMEL:
                    return 20;
                case CAMEL_JUNIOR:
                    return 20;
                case BIRD:
                    return 20;
                case GHOST:
                    return 50;
                case ROCK:
                    return 50;
                case CREEPY_CAT:
                    return 30;
                case BIRD_SKELETON:
                    return 30;
                case BANSHEE:
                    return 30;
            }
            return 0;
        }

        public static final int MELEE = 0;
        public static final int NONE = -1;

        public static float getDefaultRangeEnemy(int towerType) {
            switch (towerType) {
                case ORC:
                    return MELEE;
                case ORK_ZOMBIE:
                    return MELEE;
                case TENTACLE:
                    return 80;
                case SLIME:
                    return MELEE;
                case CAMEL:
                    return MELEE;
                case CAMEL_JUNIOR:
                    return MELEE;
                case BIRD:
                    return NONE;
                case GHOST:
                    return MELEE;
                case ROCK:
                    return MELEE;
                case CREEPY_CAT:
                    return MELEE;
                case BIRD_SKELETON:
                    return NONE;
                case BANSHEE:
                    return 70;

                default:
                    return 0;
            }
        }

        public static final int TRUE = 0;
        public static final int PHYSICAL = 1;
        public static final int MAGIC = 2;



        public static int getDmgTypeEnemy(int towerType) {
            switch (towerType) {
                case ORC:
                    return PHYSICAL;
                case ORK_ZOMBIE:
                    return PHYSICAL;
                case TENTACLE:
                    return MAGIC;
                case SLIME:
                    return MAGIC;
                case CAMEL:
                    return MAGIC;
                case CAMEL_JUNIOR:
                    return PHYSICAL;
                case BIRD:
                    return PHYSICAL;
                case GHOST:
                    return MAGIC;
                case ROCK:
                    return PHYSICAL;
                case CREEPY_CAT:
                    return MAGIC;
                case BIRD_SKELETON:
                    return PHYSICAL;
                case BANSHEE:
                    return TRUE;
                default:
                    return 0;
            }
        }

        public static int getStartHealth(int enemyType) {
            switch (enemyType) {
                case ORC:
                    return 300;
                case ORK_ZOMBIE:
                    return 200;
                case TENTACLE:
                    return 150;
                case SLIME:
                    return 70;
                case CAMEL:
                    return 500;
                case CAMEL_JUNIOR:
                    return 50;
                case BIRD:
                    return 100;
                case GHOST:
                    return 250;
                case ROCK:
                    return 450;
                case CREEPY_CAT:
                    return 300;
                case BIRD_SKELETON:
                    return 200;
                case BANSHEE:
                    return 350;
            }
            return 0;
        }



        //MR- magic resist
        public static int getMR(int enemyType) {
            switch (enemyType) {
                case ORC:
                    return 0;
                case ORK_ZOMBIE:
                    return 20;
                case TENTACLE:
                    return 80;
                case SLIME:
                    return 20;
                case CAMEL:
                    return 0;
                case CAMEL_JUNIOR:
                    return 60;
                case BIRD:
                    return 0;
                case GHOST:
                    return 40;
                case ROCK:
                    return 100;
                case CREEPY_CAT:
                    return 40;
                case BIRD_SKELETON:
                    return 20;
                case BANSHEE:
                    return 20;
            }
            return 0;
        }

        public static int getArmor(int enemyType) {
            switch (enemyType) {
                case ORC:
                    return 40;
                case ORK_ZOMBIE:
                    return 20;
                case TENTACLE:
                    return 0;
                case SLIME:
                    return 80;
                case CAMEL:
                    return 60;
                case CAMEL_JUNIOR:
                    return 20;
                case BIRD:
                    return 20;
                case GHOST:
                    return 100;
                case ROCK:
                    return 40;
                case CREEPY_CAT:
                    return 20;
                case BIRD_SKELETON:
                    return 0;
                case BANSHEE:
                    return 60;
            }
            return 0;
        }

        public static int getGoldWorth(int enemyType) {
            switch (enemyType) {
                case ORC:
                    return 10;
                case ORK_ZOMBIE:
                    return 7;
                case TENTACLE:
                    return 15;
                case SLIME:
                    return 8;
                case CAMEL:
                    return 30;
                case CAMEL_JUNIOR:
                    return 3;
                case BIRD:
                    return 9;
                case GHOST:
                    return 13;
                case ROCK:
                    return 14;
                case CREEPY_CAT:
                    return 20;
                case BIRD_SKELETON:
                    return 10;
                case BANSHEE:
                    return 25;
            }
            return 0;
        }

        public static boolean isRegainHP(int enemyType) {
            switch (enemyType) {
                case ORC:
                    return true;
                case ORK_ZOMBIE:
                    return false;
                case TENTACLE:
                    return true;
                case SLIME:
                    return true;
                case CAMEL:
                    return false;
                case CAMEL_JUNIOR:
                    return false;
                case BIRD:
                    return true;
                case GHOST:
                    return false;
                case ROCK:
                    return false;
                case CREEPY_CAT:
                    return false;
                case BIRD_SKELETON:
                    return false;
                case BANSHEE:
                    return false;
                default:
                    return false;
            }

        }
    }
    public static class AllyType{
        public static final int KNIGHT=0;
        public static final int HUNTER=1;
        public static final int PALADIN=2;
        public static final int DRUID=3;
        public static final int MAGE=4;
        public static final int PRIEST=5;
        public static final int ZOMBIE_SOLDIER=6;
        public static final int COMMANDO=7;

        public static String getName(int allyType) {
            switch (allyType) {
                case KNIGHT:
                    return "Knight";
                case HUNTER:
                    return "Hunter";
                case PALADIN:
                    return "Paladin";
                case DRUID:
                    return "Druid";
                case MAGE:
                    return "Mage";
                case PRIEST:
                    return "Priest";
                case ZOMBIE_SOLDIER:
                    return "Zombie Soldier";
                case COMMANDO:
                    return "Commando";
//                case SNIPER:
//                    return "Sniper";
//                case LASER_TOWER:
//                    return "Eye laser Tower";
//                case SCARECROW:
//                    return "Scarecrow";
//                case BANK:
//                    return "Bank";
                default:
                    return "";
            }
        }
        public static float getSpeed(int allyType) {
            switch (allyType) {
                case KNIGHT:
                    return 0.45f;
                case HUNTER:
                    return 1f;
                case PALADIN:
                    return 0.5f;
                case DRUID:
                    return 0.7f;
                case MAGE:
                    return 0.1f;
                case PRIEST:
                    return 0.3f;
                case ZOMBIE_SOLDIER:
                    return 0.25f;
                case COMMANDO:
                    return 0.7f;
//                case ROCK:
//                    return 0.25f;
//                case CREEPY_CAT:
//                    return 0.65f;
//                case BIRD_SKELETON:
//                    return 0.8f;
//                case BANSHEE:
//                    return 0.7f;
            }
            return 0;
        }

        public static int getAttackAnimationLimit(int allyType) {
//            switch (allyType) {
//                case KNIGHT:
//                    return 0.45f;
//                case HUNTER:
//                    return 1f;
//                case PALADIN:
//                    return 0.5f;
//                case DRUID:
//                    return 0.7f;
//                case MAGE:
//                    return 0.1f;
//                case PRIEST:
//                    return 0.3f;
//                case ZOMBIE_SOLDIER:
//                    return 0.25f;
//                case COMMANDO:
//                    return 0.7f;
////                case ROCK:
////                    return 0.25f;
////                case CREEPY_CAT:
////                    return 0.65f;
////                case BIRD_SKELETON:
////                    return 0.8f;
////                case BANSHEE:
////                    return 0.7f;
//            }
            return 0;
        }
        public static int getStartHealth(int allyType) {
            switch (allyType) {
                case KNIGHT:
                    return 1000;
                case HUNTER:
                    return 500;
                case PALADIN:
                    return 800;
                case DRUID:
                    return 900;
                case MAGE:
                    return 400;
                case PRIEST:
                    return 600;
                case ZOMBIE_SOLDIER:
                    return 700;
                case COMMANDO:
                    return 750;
//                case ROCK:
//                    return 450;
//                case CREEPY_CAT:
//                    return 300;
//                case BIRD_SKELETON:
//                    return 200;
//                case BANSHEE:
//                    return 350;
            }
            return 0;
        }

        public static int getMR(int allyType) {
            switch (allyType) {
                case KNIGHT:
                    return 20;
                case HUNTER:
                    return 20;
                case PALADIN:
                    return 60;
                case DRUID:
                    return 80;
                case MAGE:
                    return 80;
                case PRIEST:
                    return 60;
                case ZOMBIE_SOLDIER:
                    return 20;
                case COMMANDO:
                    return 40;
//                case ROCK:
//                    return 100;
//                case CREEPY_CAT:
//                    return 40;
//                case BIRD_SKELETON:
//                    return 20;
//                case BANSHEE:
//                    return 20;
            }
            return 0;
        }

        public static int getArmor(int allyType) {
            switch (allyType) {
                case KNIGHT:
                    return 80;
                case HUNTER:
                    return 20;
                case PALADIN:
                    return 60;
                case DRUID:
                    return 40;
                case MAGE:
                    return 20;
                case PRIEST:
                    return 40;
                case ZOMBIE_SOLDIER:
                    return 20;
                case COMMANDO:
                    return 80;
//                case ROCK:
//                    return 40;
//                case CREEPY_CAT:
//                    return 20;
//                case BIRD_SKELETON:
//                    return 0;
//                case BANSHEE:
//                    return 60;
            }
            return 0;
        }
        public static int getAttack(int allyType) {
            switch (allyType) {
                case KNIGHT:
                    return 60;
                case HUNTER:
                    return 100;
                case PALADIN:
                    return 70;
                case DRUID:
                    return 80;
                case MAGE:
                    return 100;
                case PRIEST:
                    return 50;
                case ZOMBIE_SOLDIER:
                    return 60;
                case COMMANDO:
                    return 100;
//                case ROCK:
//                    return 40;
//                case CREEPY_CAT:
//                    return 20;
//                case BIRD_SKELETON:
//                    return 0;
//                case BANSHEE:
//                    return 60;
            }
            return 0;
        }
        public static final int TRUE = 0;
        public static final int PHYSICAL = 1;
        public static final int MAGIC = 2;



        public static int getDmgType(int allyType) {
            switch (allyType) {
                case KNIGHT:
                    return PHYSICAL;
                case HUNTER:
                    return PHYSICAL;
                case PALADIN:
                    return PHYSICAL;
                case DRUID:
                    return MAGIC;
                case MAGE:
                    return MAGIC;
                case PRIEST:
                    return MAGIC;
                case ZOMBIE_SOLDIER:
                    return PHYSICAL;
                case COMMANDO:
                    return PHYSICAL;
//                case SNIPER:
//                    return TRUE;
//                case LASER_TOWER:
//                    return TRUE;
//                case SCARECROW:
//                    return MAGIC;
//                case BANK:
//                    return 0;
                default:
                    return 0;
            }
        }

        public static final int MELEE = 0;
        public static final int NONE = -1;
        public static float getDefaultRangeAlly(int allyType) {
            switch (allyType) {
                case KNIGHT:
                    return MELEE;
                case HUNTER:
                    return 80;
                case PALADIN:
                    return MELEE;
                case DRUID:
                    return MELEE;
                case MAGE:
                    return 100;
                case PRIEST:
                    return 50;
                case ZOMBIE_SOLDIER:
                    return MELEE;
                case COMMANDO:
                    return MELEE;

                default:
                    return 0;
            }
        }
    }

    public static class TowerType {
        public static final int ARCHER = 0;
        public static final int CANNON = 1;
        public static final int FROST_MAGE = 2;
        public static final int MINES_FACTORY = 3;
        public static final int POISON_TOWER = 4;
        public static final int BOOM_VOLCANO = 5;
        public static final int CROSSBOW = 6;
        public static final int MOUSE_FOLLOWS_TOWER = 7;
        public static final int SNIPER = 8;
        public static final int LASER_TOWER = 9;
        public static final int SCARECROW = 10;
        public static final int BANK = 11;


        public static String getNameTower(int towerType) {
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
                case CROSSBOW:
                    return "Crossbow";
                case MOUSE_FOLLOWS_TOWER:
                    return "Mouse Follower";
                case SNIPER:
                    return "Sniper";
                case LASER_TOWER:
                    return "Eye laser Tower";
                case SCARECROW:
                    return "Scarecrow";
                case BANK:
                    return "Bank";
                default:
                    return "";
            }
        }


        public static String getSpecialStringTower(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return null;
                case CANNON:
                    return null;
                case FROST_MAGE:
                    return "Slows without damage";
                case MINES_FACTORY:
                    return "Produce ammo and fire them rapidly when enemy is in range";
                case POISON_TOWER:
                    return null;
                case BOOM_VOLCANO:
                    return null;
                case CROSSBOW:
                    return null;
                case MOUSE_FOLLOWS_TOWER:
                    return "Aiming at the mouse";
                case SNIPER:
                    return null;
                case LASER_TOWER:
                    return "Constant single target damage";
                case SCARECROW:
                    return null;
                case BANK:
                    return "Can't attack but u can sell it with interest";
                default:
                    return null;
            }
        }
        public static boolean isSlowTower(int towerType) {
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
                case CROSSBOW:
                    return false;
                case MOUSE_FOLLOWS_TOWER:
                    return false;
                case SNIPER:
                    return false;
                case LASER_TOWER:
                    return true;
                case SCARECROW:
                    return true;
                case BANK:
                    return false;
                default:
                    return false;
            }
        }

        public static float getPowerOfSlowTower(int towerType) {
            if (isSlowTower(towerType)) {
                switch (towerType) {
                    case FROST_MAGE:
                        return 0.5f;
                    case POISON_TOWER:
                        return 0.8f;
                    case LASER_TOWER:
                        return 0.9f;
                    case SCARECROW:
                        return 0.7f;
                    default:
                        return 0;
                }
            }
            return 1f;
        }

        public static boolean isAoeTower(int projectileType) {
            switch (projectileType) {
                case ARCHER:
                    return false;
                case CANNON:
                    return true;
                case FROST_MAGE:
                    return false;
                case MINES_FACTORY:
                    return false;
                case POISON_TOWER:
                    return true;
                case BOOM_VOLCANO:
                    return true;
                case CROSSBOW:
                    return false;
                case MOUSE_FOLLOWS_TOWER:
                    return false;
                case SNIPER:
                    return false;
                case LASER_TOWER:
                    return false;
                case SCARECROW:
                    return false;
                case BANK:
                    return false;
                default:
                    return false;
            }
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
                    return 300;
                case POISON_TOWER:
                    return 200;
                case BOOM_VOLCANO:
                    return 400;
                case CROSSBOW:
                    return 250;
                case MOUSE_FOLLOWS_TOWER:
                    return 325;
                case SNIPER:
                    return 425;
                case LASER_TOWER:
                    return 800;
                case SCARECROW:
                    return 500;
                case BANK:
                    return 1000;
                default:
                    return 0;
            }
        }

        public static float getDefaultCoolDownTower(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return 30;
                case CANNON:
                    return 90;
                case FROST_MAGE:
                    return 5;
                case MINES_FACTORY:
                    return 180;
                case POISON_TOWER:
                    return 90;
                case BOOM_VOLCANO:
                    return 100;
                case CROSSBOW:
                    return 50;
                case MOUSE_FOLLOWS_TOWER:
                    return 40;
                case SNIPER:
                    return 110;
                case LASER_TOWER:
                    return 2;
                case SCARECROW:
                    return 10;
                case BANK:
                    return 0;
                default:
                    return 0;
            }

        }

        public static float getDefaultRangeTower(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return 300;
                case CANNON:
                    return 250;
                case FROST_MAGE:
                    return 100;
                case MINES_FACTORY:
                    return 150;
                case POISON_TOWER:
                    return 250;
                case BOOM_VOLCANO:
                    return 200;
                case CROSSBOW:
                    return 2000;
                case MOUSE_FOLLOWS_TOWER:
                    return 2000;
                case SNIPER:
                    return 2000;
                case LASER_TOWER:
                    return 325;
                case SCARECROW:
                    return 96;
                case BANK:
                    return 0;

                default:
                    return 0;
            }
        }

        public static int getDefaultDmg(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return 20;
                case CANNON:
                    return 15;
                case FROST_MAGE:
                    return 0;
                case MINES_FACTORY:
                    return 10;
                case POISON_TOWER:
                    return 5;
                case BOOM_VOLCANO:
                    return 30;
                case CROSSBOW:
                    return 30;
                case MOUSE_FOLLOWS_TOWER:
                    return 25;
                case SNIPER:
                    return 30;
                case LASER_TOWER:
                    return 1;
                case SCARECROW:
                    return 50;
                case BANK:
                    return 0;
                default:
                    return 0;
            }
        }


        public static final int TRUE = 0;
        public static final int PHYSICAL = 1;
        public static final int MAGIC = 2;
        public static final int NONE = 3;


        public static int getDmgTypeTower(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return PHYSICAL;
                case CANNON:
                    return PHYSICAL;
                case FROST_MAGE:
                    return MAGIC;
                case MINES_FACTORY:
                    return PHYSICAL;
                case POISON_TOWER:
                    return MAGIC;
                case BOOM_VOLCANO:
                    return PHYSICAL;
                case CROSSBOW:
                    return PHYSICAL;
                case MOUSE_FOLLOWS_TOWER:
                    return PHYSICAL;
                case SNIPER:
                    return TRUE;
                case LASER_TOWER:
                    return TRUE;
                case SCARECROW:
                    return MAGIC;
                case BANK:
                    return 0;
                default:
                    return 0;
            }
        }

        public static final int BOTH = 0;
        public static final int FLYING = 1;
        public static final int WALKING = 2;

        public static int getTargetMoveTypeTower(int towerType) {
            switch (towerType) {
                case ARCHER:
                    return BOTH;
                case CANNON:
                    return WALKING;
                case FROST_MAGE:
                    return BOTH;
                case MINES_FACTORY:
                    return BOTH;
                case POISON_TOWER:
                    return WALKING;
                case BOOM_VOLCANO:
                    return BOTH;
                case CROSSBOW:
                    return WALKING;
                case MOUSE_FOLLOWS_TOWER:
                    return BOTH;
                case SNIPER:
                    return BOTH;
                case LASER_TOWER:
                    return BOTH;
                case SCARECROW:
                    return FLYING;
                case BANK:
                    return NONE;
                default:
                    return 0;
            }
        }

        //0=range, 1=dmg, 2=attackSpeed, 3=duration,4= Invest
        public static int getUpgradeTowerImage(int towerType, int upgrade) {
            switch (towerType) {
                case ARCHER:
                    switch (upgrade) {
                        case 1:
                            return 2;
                        case 2:
                            return 1;
                        case 3:
                            return 0;
                    }
                    return 1;
                case CANNON:
                    switch (upgrade) {
                        case 1:
                            return 2;
                        case 2:
                            return 1;
                        case 3:
                            return 1;
                    }
                    return 1;
                case FROST_MAGE:
                    switch (upgrade) {
                        case 1:
                            return 0;
                        case 2:
                            return 0;
                        case 3:
                            return 0;
                    }
                    return 1;
                case MINES_FACTORY:
                    switch (upgrade) {
                        case 1:
                            return 2;
                        case 2:
                            return 1;
                        case 3:
                            return 2;
                    }
                    return 1;
                case POISON_TOWER:
                    switch (upgrade) {
                        case 1:
                            return 1;
                        case 2:
                            return 3;
                        case 3:
                            return 0;
                    }
                    return 1;
                case BOOM_VOLCANO:
                    switch (upgrade) {
                        case 1:
                            return 2;
                        case 2:
                            return 1;
                        case 3:
                            return 0;
                    }
                    return 1;
                case CROSSBOW:
                    switch (upgrade) {
                        case 1:
                            return 2;
                        case 2:
                            return 1;
                        case 3:
                            return 2;
                    }
                    return 1;
                case MOUSE_FOLLOWS_TOWER:
                    switch (upgrade) {
                        case 1:
                            return 2;
                        case 2:
                            return 1;
                        case 3:
                            return 2;
                    }
                    return 1;
                case SNIPER:
                    switch (upgrade) {
                        case 1:
                            return 2;
                        case 2:
                            return 1;
                        case 3:
                            return 1;
                    }
                    return 1;
                case LASER_TOWER:
                    switch (upgrade) {
                        case 1:
                            return 0;
                        case 2:
                            return 1;
                        case 3:
                            return 1;
                    }
                    return 1;
                case SCARECROW:
                    switch (upgrade) {
                        case 1:
                            return 1;
                        case 2:
                            return 0;
                        case 3:
                            return 0;
                    }
                    return 1;
                case BANK:
                    switch (upgrade) {
                        case 1:
                            return 4;
                        case 2:
                            return 4;
                        case 3:
                            return 4;
                    }
                    return 1;
                default:
                    return 1;
            }
        }

        //dot - damage over time
        public static boolean isDOTTower(int towerType) {
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
                case CROSSBOW:
                    return false;
                case MOUSE_FOLLOWS_TOWER:
                    return false;
                case SNIPER:
                    return false;
                case LASER_TOWER:
                    return false;
                case SCARECROW:
                    return false;
                default:
                    return false;
            }
        }

        public static int getDurationTower(int towerType) {
            if (isSlowTower(towerType)) {
                switch (towerType) {
                    case FROST_MAGE:
                        return 10;
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
