package ru.skillbox.socnetwork.exception;

public enum ExceptionText {

    POST_INCORRECT_CANT_FIND_ID("Incorrect post data, can't find this id "),
    POST_ID_NOT_FOUND_TO_DELETE("No post id found to delete"),
    POST_INCORRECT_AUTHOR_TO_DELETE("You cannot delete this post, you are not the author. Author ID is "),
    POST_INCORRECT_AUTHOR_TO_EDIT("You cannot edit this post, you are not the author. Author ID is "),
    COMMENT_INCORRECT_CANT_FIND_ID("Incorrect comment data, can't find this id "),
    COMMENT_INCORRECT_AUTHOR_TO_DELETE("You cannot delete this comment, you are not the author. Author ID is "),
    COMMENT_INCORRECT_AUTHOR_TO_EDIT("You cannot edit this comment, you are not the author. Author ID is "),
    LIKE_WRONG_TYPE("Bad like type. Required 'Post' or 'Comment' types"),
    TAG_MAX_LENGTH(" symbol's is MAX tag length, current length is "),
    INCORRECT_CAPTCHA("Incorrect captcha, please try again"),
    INCORRECT_PASSWORD("Passwords do not match"),
    INCORRECT_EMAIL("Incorrect email, please try again"),

    DEFAULT_ERROR_DESCRIPTION ("This request was denied. " +
            "Please register or use your existing account to get the updated token value and try again.");




    private final String message;

    ExceptionText(String s) {
        this.message = s;
    }

    public String getMessage() {
        return this.message;
    }
}
