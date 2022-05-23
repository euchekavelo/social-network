package ru.skillbox.socnetwork.exception;

public enum ExceptionText {

    POST_INCORRECT_CANT_FIND_ID("Incorrect post data, can't find this id "),
    POST_ID_NOT_FOUND_TO_DELETE("No post id found to delete"),
    POST_INCORRECT_AUTHOR_TO_EDIT("You cannot edit a post, you are not the author."),
    LIKE_WRONG_TYPE("Bad like type. Required 'Post' or 'Comment' types"),
    TAG_MAX_LENGTH(" symbol's is MAX tag length, current length is ");

    private final String message;

    ExceptionText(String s) {
        this.message = s;
    }

    public String getMessage() {
        return this.message;
    }
}
