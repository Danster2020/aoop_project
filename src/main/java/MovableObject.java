public abstract class MovableObject {

    public Boolean ifCollision(Block to) {
        if(to.isWall()){
            return true;
        }
        return false;
    }


}
