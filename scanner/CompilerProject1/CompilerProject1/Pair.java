class Pair<tt, String> {
    tt token;
    String data;

    public Pair(tt token, String data) {
        this.token = token;
        this.data = data;
    }

    public tt getTokenType() {
        return token;
    }
    public String getData() {
        return data;
    }

    public void setTokenType(tt token) {
        this.token = token;
    }
    public void setData(String data) {
        this.data = data;
    }
}