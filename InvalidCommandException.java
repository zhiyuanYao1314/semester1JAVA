/**
 * Author:zhiyuan yao
 * StudentId: 1012228
 * Time:2019/5/7 13:29
 * Describe: when the player input invalid arguments,
 *           one instance of InvalidCommandException will be thrown.
 * version:1.3
 */
public class InvalidCommandException extends Exception {

    public InvalidCommandException() {
    }

    public InvalidCommandException(String message) {
        super(message);
    }
}
