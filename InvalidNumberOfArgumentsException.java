/**
 * Author:zhiyuan yao
 * StudentId: 1012228
 * Time:2019/5/7 13:29
 * Describe: when the player input invalid number of arguments,
 *           one instance of InvalidNumberOfArgumentsException will be thrown.
 * version:1.3
 */
public class InvalidNumberOfArgumentsException extends Exception {
    public InvalidNumberOfArgumentsException() {
    }

    public InvalidNumberOfArgumentsException(String message) {
        super(message);
    }
}
