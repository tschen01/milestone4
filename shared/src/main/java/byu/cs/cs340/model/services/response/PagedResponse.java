package byu.cs.cs340.model.services.response;

public class PagedResponse extends Response {

    private final boolean hasMorePages;
    private final String lastObject;

    PagedResponse(boolean success, boolean hasMorePages, String lastObject) {
        super(success);
        this.hasMorePages = hasMorePages;
        this.lastObject = lastObject;
    }

    PagedResponse(boolean success, String message, boolean hasMorePages) {
        super(success, message);
        this.hasMorePages = hasMorePages;
        this.lastObject = null;
    }

    public boolean getHasMorePages() {
        return hasMorePages;
    }

    public String getLastObject() {
        return lastObject;
    }
}
