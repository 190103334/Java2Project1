import java.util.*;

class Position {
    private int x,y;

    Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public boolean equals(Position p2){
        return this.x == p2.getX() && this.y == p2.getY();
    }
    public String toString() {
        return "(" + x + "," + y + ')';
    }
}

interface Player {
    void moveRight();
    void moveLeft();
    void moveUp();
    void moveDown();
    void setMap(Map map);
    Position getPosition();
}

class MyPlayer implements Player{
    private Map map;
    private Position position;

    public void setMap(Map map){
        this.map = map;
        this.position = this.map.startPosition;
    }

    public void moveRight(){
        char a = this.map.getValueAt(position.getX()+1, position.getY());
        if(a == '0' || a == 'P'){
            position.setX(position.getX()+1);
        }
    }

    public void moveLeft(){
        char a = this.map.getValueAt(position.getX()-1, position.getY());
        if(a == '0' || a == 'P'){
            position.setX(position.getX()-1);
        }
    }

    public void moveUp(){
        char a = this.map.getValueAt(position.getX() , position.getY()-1);
        if(a == '0' || a == 'P'){
            position.setY(position.getY()-1);
        }
    }

    public void moveDown(){
        char a = this.map.getValueAt(position.getX() , position.getY()+1);
        if(a == '0' || a == 'P'){
            position.setY(position.getY()+1);
        }
    }


    public Position getPosition(){
        return position;
    }

}

class Map {

    char[][] map;
    int size;
    Position startPosition;

    public Map(Scanner input) throws InvalidMapException{
        size = input.nextInt();
        this.size = size;

        if(size == 0){
            throw new InvalidMapException("Map size can not be zero");
        }
        else{
            map = new char[size][size];
            for(int i = 0; i < map.length; i++){
                for(int j = 0; j < map.length; j++){
                    if(!input.hasNext()){
                        throw new InvalidMapException("Not enough map elements");
                    }
                    map[i][j] = input.next().charAt(0);
                    if(map[i][j] == 'P'){
                        startPosition = new Position(j, i);
                    }
                }
            }
        }
    }

    public void print(){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map.length; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public int getSize(){
        return this.size;
    }

    public char getValueAt(int x, int y){
        char result = '1';
        if(0 <= x && x < size && 0 <= y && y < size)
            result = this.map[y][x];
        return result;
    }
}

class Game {
    private Map map;
    private Player player;

    public Game(Map map){
        this.map = map;
    }
    public void setMap(Map map){
        this.map = map;
    }
    public void addPlayer(Player player){
        player.setMap(this.map);
        this.player = player;
    }
}

class InvalidMapException extends Exception{
    InvalidMapException(String s) {
        super(s);
    }
}

/*
DO NOT MODIFY THIS PART!!!

Input and Output has been done for you.
Various conditions are meant to check individual classes separately.
You still need to implement completely all the necessary classes with their corresponding methods,
but the correctness for each class is checked individually.
In other words, you already get some partial points
for implementing some classes completely and correctly,
even if other classes are complete but still may not work properly.
*/
public class Solution{

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String className = input.nextLine();

        // Checking the implementation of the Position class
        if(className.equals("Position")){
            Position p1 = new Position(input.nextInt(), input.nextInt());
            System.out.println(p1);
            p1.setX(5);
            System.out.println(p1.getX());

            Position p2 = new Position(5, 10);
            System.out.println(p1.equals(p2));
        }

        // Checking the implementation of the Map class
        else if(className.equals("Map")){
            try{
                Map map = new Map(input);
                map.print();
                int size = map.getSize();
                System.out.println(size);
                System.out.println(map.getValueAt(size/2, size/2));
            }
            catch(Exception e){}
        }

        // Checking the Player interface and the MyPlayer class
        else if(className.equals("Player")){
            Player player = new MyPlayer();
            System.out.println(Player.class.isInterface());
            System.out.println(player instanceof Player);
            System.out.println(player instanceof MyPlayer);
        }

        // Checking the InvalidMapException class
        else if(className.equals("Exception")){
            try{
                throw new InvalidMapException("Some message");
            }
            catch(InvalidMapException e){
                System.out.println(e.getMessage());
            }
        }

        // Checking the Game class and all of its components
        else if(className.equals("Game")){

            // Initialize player, map, and the game
            Player player = new MyPlayer();
            Game game = null;

            try{
                game = new Game(new Map(input));
            }
            catch(InvalidMapException e){ // custom exception
                System.out.println(e.getMessage());
                System.exit(0);
            }

            game.addPlayer(player);

            // Make the player move based on the commands given in the input
            String moves = input.next();
            char move;
            for(int i=0; i<moves.length(); i++){
                move = moves.charAt(i);
                switch(move){
                    case 'R':
                        player.moveRight();
                        break;
                    case 'L':
                        player.moveLeft();
                        break;
                    case 'U':
                        player.moveUp();
                        break;
                    case 'D':
                        player.moveDown();
                        break;
                }
            }

            // Determine the final position of the player after completing all the moves above
            Position playerPosition = player.getPosition();
            System.out.println("Player final position");
            System.out.println("Row: " + playerPosition.getY());
            System.out.println("Col: " + playerPosition.getX());
        }
    }
}

