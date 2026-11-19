package models.Request;

public class CreateUserRequest {

    private String title;
    private String body;
    private int userId;

    public CreateUserRequest(String title, String body, int userId) {
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String toString() {
        return String.format("{\"title\": \"%s\", \"body\": \"%s\", \"userId\": %d}", title, body, userId);
    }

    public static class Builder {
        private String title;
        private String body;
        private int userId;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public Builder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public CreateUserRequest build() {
            return new CreateUserRequest(title, body, userId);
        }
    }

}
