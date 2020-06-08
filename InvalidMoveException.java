/**
 * Author:zhiyuan yao
 * StudentId: 1012228
 * Time:2019/5/7 13:29
 * Describe: when the player input invalid move,
 *           one instance of InvalidMoveException will be thrown.
 * version:1.3
 */
public class InvalidMoveException extends Exception{
    public InvalidMoveException() {
    }

    public InvalidMoveException(String message) {
        super(message);
    }
}
